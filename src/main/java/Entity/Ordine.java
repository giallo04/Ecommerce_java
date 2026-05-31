package Entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Ordine {
    private LocalDate data;
    private String indirizzo; // per ora ho messo string perché la deve implementare checco
    private StatoOrdine statoOrdine;
    private ArrayList<RigaOrdine> inOrdine;

    public Ordine(String indirizzo) { //il resto degli attributi non devono esistere al di fuori dell'ordine quindi vengono creati nel costruttore
        this.indirizzo = indirizzo;
        this.data = LocalDate.now();
        this.statoOrdine = StatoOrdine.INSERITO;
        this.inOrdine = new ArrayList<>();
    }

    public LocalDate getData() {
        return data;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setStatoOrdine(StatoOrdine statoOrdine) {
        this.statoOrdine = statoOrdine;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Ordine ordine)) return false;
        return Objects.equals(data, ordine.data) && Objects.equals(indirizzo, ordine.indirizzo) && statoOrdine == ordine.statoOrdine && Objects.equals(inOrdine, ordine.inOrdine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, indirizzo, statoOrdine, inOrdine);
    }

    @Override
    public String toString() {
        return "Ordine{" +
                "data=" + data +
                ", indirizzo='" + indirizzo + '\'' +
                ", statoOrdine=" + statoOrdine +
                ", inOrdine=" + inOrdine +
                '}';
    }
}
