package Entity.client;
import Entity.Merce.Prodotto;
public class RigaCarrello {

    private Prodotto prodotto;
    int quantita;


    public RigaCarrello(Prodotto prodotto, int quantita)
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

    public void incrementQuantita(int quantita)
    {
        if(quantita>0)
            this.quantita+=quantita;
        else throw new IllegalArgumentException("Quantità non valida");
    }


    public void decrementQuantita(int quantita){
        if(quantita>0&&quantita<=this.quantita){
            this.quantita-=quantita;
        }else throw new IllegalArgumentException("Quantità non valida");
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
