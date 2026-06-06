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
        this.quantita+=qt;
    }


   public float calcolaSubTotale()
   {
       float subTotale=0;

       subTotale=prodotto.getPrezzo()*quantita;

       return subTotale;

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
