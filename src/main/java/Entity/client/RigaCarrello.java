package Entity.client;
import Entity.Merce.Prodotto;
import jakarta.persistence.*;

@Entity
public class RigaCarrello {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long riga_id;
    @ManyToOne @JoinColumn(name = "product_id")
    private Prodotto prodotto;
    private int quantita;

    public RigaCarrello() {}
    public RigaCarrello(Prodotto prodotto, int quantita)
    {
        if(prodotto==null)throw new IllegalArgumentException("\nInserire un articolo valido\n");
        this.prodotto=prodotto;
        if(quantita<=0)throw new IllegalArgumentException("\nInserire una quantità maggiore di zero, se si vuole inserire il prodotto nel carrello\n");
        if(prodotto.getQuantita()<quantita)throw new IllegalArgumentException("Prodotto "+prodotto.getNome()+" non disponibile in magazzino, disponibile solo "+prodotto.getQuantita());
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

    public void incrementQuantita(int qt)
    {
        if(quantita+qt>prodotto.getQuantita()) throw new IllegalArgumentException(
                "Non puoi aggiungere altre unità di \"" + prodotto.getNome() +
                        "\": ne hai già " + this.quantita + " nel carrello e la disponibilità massima è " +
                        prodotto.getQuantita()
        );
        this.quantita+=qt;
    }

    public void decrementQuantita(int qt)
    {
        this.quantita-=qt;
    }

   public float calcolaSubTotale()
   {
       float subTotale=0;

       subTotale=prodotto.getPrezzo()*quantita;

       return subTotale;

   }

}
