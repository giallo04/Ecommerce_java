package Entity.Ordini;

import java.util.Objects;
import Entity.Merce.Prodotto;
import jakarta.persistence.ManyToOne;

public class RigaOrdine {
    private Prodotto prodotto;
    int qtaProdotto;
    float prezzo;
    public RigaOrdine(Prodotto prodotto, int qtaProdotto) {
        this.prodotto = prodotto;
        this.qtaProdotto = qtaProdotto;
        this.prezzo=prodotto.getPrezzo();
    }

    public int getQtaProdotto() {
        return qtaProdotto;
    }
    public float getPrezzo() {
        return prezzo;
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
