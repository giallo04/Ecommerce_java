package Controller;

import Database.GestorePersistenza;

import Entity.Ordini.Ordine;
import Entity.Ordini.RegistroOrdini;
import Entity.Ordini.RigaOrdine;
import Entity.Ordini.StatoOrdine;
import Entity.client.RegistroUtenti;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrdiniController {

    public static final int  ORDER_ID=0;
    public static final int  DATA=1;
    public static final int  INDIRIZZO=2;
    public static final int  TOTALE=3;
    public static final int  STATO=4;

    public static final int  PRODUCT_ID=0;
    public static final int  QUANTITA=1;
    public static final int  PREZZO=2;

    private String error_msg;
    public String getError_msg() {
        return error_msg;
    }
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public  List<String[]> caricaOrdini(){ //qui non passo i prodotti perché nella tabella generica la lista dei prodotti contenuti nell'ordine non è mostrata
        RegistroOrdini reg = new RegistroOrdini();
        List<Ordine> ordini = reg.caricaOrdini();
        if(ordini.isEmpty()){
            this.setError_msg("Non esiste alcun ordine");
            return null;
        }
        return convertOrderToGui(ordini);
    }

    public  List<String[]> caricaOrdiniUtente(){
        RegistroOrdini reg = new RegistroOrdini();
        long u_id = AccountController.get_current_user_id();
        List<Ordine> ordini = reg.caricaOrdiniUtente(u_id);
        if(ordini.isEmpty()){
            this.setError_msg("Non esiste alcun ordine per questo utente");
            return null;
        }
        return convertOrderToGui(ordini);
    }
    public String[] caricaOrdine(String order_id){
        RegistroOrdini reg = new RegistroOrdini();
        Ordine ordine=reg.cercaOrdinePerId(Long.parseLong(order_id));
        if(ordine==null){
            setError_msg("Non esiste alcun ordine relativo a questo order_id");
        }
            return convertOrderToGui(List.of(ordine)).get(0);
    }
    private List<String[]> convertOrderToGui(List<Ordine> ordini){
        List<String[]> righe = new ArrayList<>();
        for(Ordine ordine: ordini){
            String[] riga = new String[]{
                    String.valueOf(ordine.getOrderId()),
                    String.valueOf(ordine.getData()),
                    String.valueOf(ordine.getIndirizzo()),
                    String.valueOf(ordine.calcolaTotaleOrdine()),
                    String.valueOf(ordine.getStatoOrdine())
            };
            righe.add(riga);
        }
        return righe;
        }
    public List<String[]> caricaRigheOrdine(String order_id){
        RegistroOrdini reg = new RegistroOrdini();
        Ordine ordine=reg.cercaOrdinePerId(Long.parseLong(order_id));
        if(ordine==null){
            setError_msg("Non esiste alcun ordine relativo a questo order_id");
            return null;
        }
        List<RigaOrdine> righeInOrdine=ordine.getInOrdine();
        if(righeInOrdine.isEmpty()){
             setError_msg("Non esiste alcun ordine relativo a questo order_id");
             return null;
        }
        return convertOrderLineToGui(righeInOrdine);
    }

    private static List<String[]> convertOrderLineToGui(List<RigaOrdine> righeInOrdine) {
        List<String[]> righe = new ArrayList<>();
        for(RigaOrdine rigaInOrdine: righeInOrdine){
            String[] riga = new String[]{
                //passo solo il product_id poi la boundary si chiama caricaProdotto da CatalogoController e mostro solo le info necessarie
                    String.valueOf(rigaInOrdine.getProdotto().getProduct_id()),
                    String.valueOf(rigaInOrdine.getQtaProdotto()),
                    String.valueOf(rigaInOrdine.getPrezzo())
            };
            righe.add(riga);
        }
        return righe;
    }

    //annulla ordini da implementare anche in registro ordini va messo qua dentro
    public boolean modificaOrdine(String order_id, String newStatoOrdine){
        RegistroOrdini reg = new RegistroOrdini();
        try{
            reg.modificaOrdine(Long.parseLong(order_id), StatoOrdine.valueOf(newStatoOrdine));
            return true;
        }catch (IllegalArgumentException | IllegalStateException e){
            setError_msg(e.getMessage());
            return false;
        }
    }


    public List<String[]> reportOrdiniMensili(){
        RegistroOrdini reg = new RegistroOrdini();
        List<Ordine> ordiniMese = reg.caricaOrdiniUltimoMese();
        if(ordiniMese.isEmpty()){return null;}
        List<String[]> righe = new ArrayList<>();
        return convertOrderToGui(ordiniMese);
    }

    public List<String> getStatiOrdine(){
        List<String> stati=new ArrayList<>();
        for(StatoOrdine s:StatoOrdine.values()){
            stati.add(s.toString());
        }
        return stati;
    }
    public String getNumberOrderInLavorazione(){
        RegistroOrdini registroOrdini=new RegistroOrdini();
        return String.valueOf(registroOrdini.conteggioOrdiniInLavorazione());
    }

}
