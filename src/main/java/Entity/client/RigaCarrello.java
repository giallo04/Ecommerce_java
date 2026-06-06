package Entity.client;
import Entity.Merce.Prodotto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class RigaCarrello {

    @Id @ManyToOne
    private Prodotto prodotto;
    int quantita;

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

    public void incrementQuantita()
    {
        this.quantita+=1;
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
