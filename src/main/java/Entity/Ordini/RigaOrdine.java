package Entity.Ordini;

import java.util.Objects;
import Entity.Merce.Prodotto;
import jakarta.persistence.*;

@Entity
public class RigaOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long riga_id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Prodotto prodotto;
    int qtaProdotto;
    float prezzo;


    protected RigaOrdine() {}
    //costruttore
    public RigaOrdine(Prodotto prodotto, int qtaProdotto) {
        this.prodotto = prodotto;
        this.qtaProdotto = qtaProdotto;
        this.prezzo=(prodotto.getPrezzo()*qtaProdotto);
    }

    //get
    public int getQtaProdotto() {
        return qtaProdotto;
    }
    public float getPrezzo() {
        return prezzo;
    }
    public Prodotto getProdotto() {
        return prodotto;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RigaOrdine that)) return false;
        return qtaProdotto == that.qtaProdotto && Objects.equals(prodotto, that.prodotto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prodotto, qtaProdotto);
    }

    @Override
    public String toString() {
        return "RigaOrdine{" +
                "prodotto=" + prodotto +
                ", qtaProdotto=" + qtaProdotto +
                '}';
    }

}
