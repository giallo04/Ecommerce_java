package Entity.Ordini;

import Database.GestorePersistenza;

import java.util.*;

public class RegistroOrdini {

    private GestorePersistenza gestorePersistenza;

    public RegistroOrdini(){
        gestorePersistenza = new GestorePersistenza();
    }


    public void modificaOrdine(long order_id, StatoOrdine newStato){
        Ordine ordine=gestorePersistenza.trovaPerId(Ordine.class,order_id);
        if(ordine==null){
            throw new IllegalArgumentException("Ordine "+order_id+" non presente nel database");
        }else{
            ordine.setStatoOrdine(newStato);
            gestorePersistenza.aggiorna(ordine);
        }

    }

    public List<Ordine> caricaOrdini(){
        return gestorePersistenza.cercaPerCampi(Ordine.class, new HashMap<>());
    }

    public List<Ordine> caricaOrdiniUtente(long user_id){
        return gestorePersistenza.cercaPerCampo(Ordine.class, "user_id", user_id);
    }

    public Ordine cercaOrdinePerId(long order_id){
        return gestorePersistenza.trovaPerId(Ordine.class,order_id);
    }
    public void registraOrdine(Ordine ordine){
        gestorePersistenza.salva(ordine);
    }
    public List<Ordine> caricaOrdiniUltimoMese(){
        return gestorePersistenza.caricaElementiUltimoMese(Ordine.class,"data");
    }

    public int conteggioOrdiniInLavorazione(){
        GestorePersistenza gestore=new GestorePersistenza();
        List<Ordine> ordini=gestore.cercaPerCampi(Ordine.class, Map.of("statoOrdine",StatoOrdine.IN_PREPARAZIONE));
        return ordini.size();
    }
}

