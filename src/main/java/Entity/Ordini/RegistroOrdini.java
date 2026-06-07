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


    public void modificaOrdine(Ordine ordine, StatoOrdine newStato){
        ordine.setStatoOrdine(newStato);
        gestorePersistenza.aggiorna(ordine);
    }

    public List<Ordine> caricaOrdini(){
        return gestorePersistenza.cercaPerCampi(Ordine.class, new HashMap<>());
    }

    public List<Ordine> caricaOrdiniUtente(String user_id){
        return gestorePersistenza.cercaPerCampo(Ordine.class, user_id, Collections.emptyMap());
    }
}
