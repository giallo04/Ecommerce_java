package Entity;

import java.util.Objects;
import Entity.Merce.Prodotto;

public class RigaOrdine {
    Prodotto prodotto;
    int qtaProdotto;

    public RigaOrdine(Prodotto prodotto, int qtaProdotto) {
        this.prodotto = prodotto;
        this.qtaProdotto = qtaProdotto;
    }

    public int getQtaProdotto() {
        return qtaProdotto;
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
