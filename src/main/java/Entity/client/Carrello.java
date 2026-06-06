package Entity.client;

import Database.GestorePersistenza;
import Entity.Merce.Prodotto;
import jakarta.persistence.Embeddable;

import java.util.*;
@Embeddable
public class Carrello {


    private Map<Long, RigaCarrello> prodotti=new LinkedHashMap<>();//UTILIZZO QUESTA LINKEDHASKMAP PERCHè UNISCE I VANTAGGI DELL' ARRAYLIST, CHE MOSTRA IN MANIERA ORDINATA I PRODTTTI, CON L'HASHMAP CHE MI FORNISCE TEMPO DI ACCESSO MINORE




    public ArrayList<RigaCarrello> getProdotti() {
        return new ArrayList<>(prodotti.values());//ESTRAE TUTTI I VALORI IGNORANDO LA CHIAVE
    }


    public float getTotale() {
        float totale=0;
        Collection<RigaCarrello> prod=prodotti.values();
        for(RigaCarrello riga:prod){
            totale=totale+(riga.getQuantita()*riga.getProdotto().getPrezzo());
        }
        return totale;
    }




    public void aggiungiProdotto(Prodotto prodotto, int quantita)
    {
        long id=prodotto.getProduct_id();

        if(prodotti.containsKey(id))
        {
            RigaCarrello esistente=prodotti.get(id);
            esistente.incrementQuantita();
        }
        else{

            RigaCarrello nuovo=new RigaCarrello(prodotto, quantita);
            prodotti.put(id,nuovo);
        }
    }

    public void rimuoviProdotto(long idProdotto)
    {
        GestorePersistenza gp = new GestorePersistenza();
        Prodotto prodotto = gp.trovaPerId(Prodotto.class, idProdotto);
        prodotti.remove(idProdotto);
        gp.aggiorna(this);
    }

    public void svuotaCarrello()
    {
        Collection<RigaCarrello> prod=prodotti.values();
        for(RigaCarrello riga:prod)
        {
            rimuoviProdotto(riga.getProdotto().getProduct_id());
        }
    }




}
