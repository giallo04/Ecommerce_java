package Kekko;

import java.util.ArrayList;
import java.util.List;

public class Carrello {

    private List<InCarrello> prodotti;


    public Carrello(List<InCarrello> prodotti)
    {
        prodotti=new ArrayList<InCarrello>();
        this.prodotti=prodotti;
    }

    public void mostraProdotti()
    {
        for(int i=0;i<prodotti.size();i++)
        {
            System.out.print(prodotti.get(i).toString());
        }
    }

    public void aggiungiProdotto(InCarrello prodotto)
    {
        prodotti.add(prodotto);
    }

    public void rimuoviProdotto(InCarrello prodotto)
    {
        prodotti.remove(prodotto);
    }



}
