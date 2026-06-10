package Entity.Ordini;

import Database.GestorePersistenza;
import Entity.client.Indirizzo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    public boolean registraOrdine(Ordine ordine){
        return gestorePersistenza.salva(ordine);
    }

    public boolean isTerminale(long order_id){
        Ordine ordine = gestorePersistenza.trovaPerId(Ordine.class, order_id);
        return ordine.getStatoOrdine().isTerminale();
    }

    public boolean inviaMessaggio(){
        return true;
    }
}

