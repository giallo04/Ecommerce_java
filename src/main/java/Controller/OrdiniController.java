package Controller;

import Database.GestorePersistenza;
import Entity.Ordini.Ordine;
import Entity.Ordini.RegistroOrdini;
import Entity.Ordini.RigaOrdine;
import Entity.Ordini.StatoOrdine;
import com.google.protobuf.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrdiniController {

    public static List<String[]> caricaOrdini(){ //qui non passo i prodotti perché nella tabella generica la lista dei prodotti contenuti nell'ordine non è mostrata
        RegistroOrdini reg = new RegistroOrdini();
        List<Ordine> ordini = reg.caricaOrdini();

        List<String[]> righe = new ArrayList<>();

        for(Ordine ordine : ordini){

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

    public static List<String[]> caricaOrdiniUtente(String user_id){
        RegistroOrdini reg = new RegistroOrdini();
        GestorePersistenza gestore = new GestorePersistenza();
        long u_id = Long.parseLong(user_id);
        List<Ordine> ordini = reg.caricaOrdiniUtente(u_id);
        List<String[]> righe = new ArrayList<>();

        for(Ordine ordine : ordini){

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

    public String[] caricaOrdine(String order_id){
        RegistroOrdini reg = new RegistroOrdini();
        GestorePersistenza gestore = new GestorePersistenza();
        Ordine ordine = gestore.cercaPrimoPerCampi(Ordine.class, Map.of("order_id", order_id));
        if(ordine==null){
            throw new IllegalArgumentException("Non esiste alcun ordine relativo a questo user_id");
        }
            String[] riga = new String[]{
                    String.valueOf(ordine.getOrderId()),
                    String.valueOf(ordine.getData()),
                    String.valueOf(ordine.getIndirizzo()),
                    String.valueOf(ordine.calcolaTotaleOrdine()),
                    String.valueOf(ordine.getStatoOrdine())
            };

            return riga;
    }

    public List<String[]> caricaRigheOrdine(String order_id){
        GestorePersistenza gestore = new GestorePersistenza();
        List<RigaOrdine> righeInOrdine = gestore.cercaPrimoPerCampi(Ordine.class,Map.of("order_id",order_id)).getInOrdine();
        if(righeInOrdine.isEmpty()){
            throw  new IllegalArgumentException("Non esiste alcun ordine relativo a questo user_id");
        }
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

    public void modificaOrdine(String order_id, String newStatoOrdine){
        StatoOrdine so = Enum.valueOf(StatoOrdine.class, newStatoOrdine);
        long orderID = Long.parseLong(order_id);
        RegistroOrdini reg = new RegistroOrdini();
        reg.modificaOrdine(orderID, so);
    }

    public boolean isTerminale(String order_id){
        long orderId = Long.parseLong(order_id);
        RegistroOrdini reg = new RegistroOrdini();
        return reg.isTerminale(orderId);
    }

    //aggiungere statistiche ordini e annulla ordini da implementare anche in registro ordini

}
