package Entity.client;

import Entity.Merce.Prodotto;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collection;

public class Carrello {

    private Map<Long, RigaCarrello> prodotti=new LinkedHashMap<>();//UTILIZZO QUESTA LINKEDHASKMAP PERCHè UNISCE I VANTAGGI DELL' ARRAYLIST, CHE MOSTRA IN MANIERA ORDINATA I PRODTTTI, CON L'HASHMAP CHE MI FORNISCE TEMPO DI ACCESSO MINORE


    public Collection<RigaCarrello> getProdotti() {
        return prodotti.values();//ESTRAE TUTTI I VALORI IGNORANDO LA CHIAVE
    }


    public float getTotale() {
        float tot=0;
        Collection<RigaCarrello> prod=prodotti.values();
        for(RigaCarrello riga:prod){
            tot=tot+(riga.getQuantita()*riga.getProdotto().getPrezzo());
        }
        return tot;
    }


    public void mostraProdotti()
    {
        for(RigaCarrello item : getProdotti())
        {
            System.out.print(prodotti.toString());
        }
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita)
    {
        long id=prodotto.getProduct_id();

        if(prodotti.containsKey(id))
        {
            RigaCarrello esistente=prodotti.get(id);
            esistente.incrementQuantita(quantita);
        }
        else{

            RigaCarrello nuovo=new RigaCarrello(prodotto, quantita);
            prodotti.put(id,nuovo);
        }
    }

    public void rimuoviProdotto(Prodotto prodotto)
    {
        prodotti.remove(prodotto.getProduct_id());
    }


}
