package Entity.Kekko;

import Entity.Merce.Prodotto;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Collection; // Ti servirà per estrarre i prodotti

public class Carrello {

    private Map<Long, InCarrello> prodotti=new LinkedHashMap<>();//UTILIZZO QUESTA LINKEDHASKMAP PERCHè UNISCE I VANTAGGI DELL' ARRAYLIST, CHE MOSTRA IN MANIERA ORDINATA I PRODTTTI, CON L'HASHMAP CHE MI FORNISCE TEMPO DI ACCESSO MINORE
    private static float prezzoTotale;


    public Collection<InCarrello> getProdotti() {
        return prodotti.values();//ESTRAE TUTTI I VALORI IGNORANDO LA CHIAVE
    }

    public void mostraProdotti()
    {
        for(InCarrello item : getProdotti())
        {
            System.out.print(prodotti.toString());
        }
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita)
    {
        long id=prodotto.getProduct_id();

        if(prodotti.containsKey(id))
        {
            InCarrello esistente=prodotti.get(id);
            esistente.setQuantita(quantita);
        }
        else{

            InCarrello nuovo=new InCarrello(prodotto, quantita);
            prodotti.put(id,nuovo);
        }

        prezzoTotale=prezzoTotale+prodotto.getPrezzo();
    }

    public void rimuoviProdotto(Prodotto prodotto)
    {

        prodotti.remove(prodotto.getProduct_id());
        prezzoTotale=prezzoTotale- prodotto.getPrezzo();
    }






}
