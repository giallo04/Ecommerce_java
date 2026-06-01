package Kekko;

public class InCarrello {

    Prodotto prodotto;
    int quantita;


    public InCarrello(Prodotto prodotto, int quantita)
    {
        if(prodotto==null)throw new IllegalArgumentException("\nInserire un articolo valido\n");
        this.prodotto=prodotto;
        if(quantita<=0)throw new IllegalArgumentException("\nInserire una quantità maggiore di zero, se si vuole inserire il prodotto nel carrello\n");
        this.quantita=quantita;

    }

    //GETTER
    public Prodotto getProdotto() {
        return prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    //SETTER

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }


    //TO STRING


    @Override
    public String toString() {
        return "InCarrello{" +
                "prodotto=" + prodotto +
                ", quantita=" + quantita +
                '}';
    }
}
