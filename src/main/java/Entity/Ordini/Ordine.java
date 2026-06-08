package Entity.Ordini;

import Entity.Merce.Prodotto;
import Entity.client.Indirizzo;
import Entity.client.Utente;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;
    private long user_id;

    private LocalDate data;
    @Embedded
    private Indirizzo indirizzo;
    private StatoOrdine statoOrdine;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<RigaOrdine> inOrdine = new ArrayList<>();


     
    //costruttore
    public Ordine(Indirizzo indirizzo,long user_id) { //il resto degli attributi non devono esistere al di fuori dell'ordine quindi vengono creati nel costruttore
        this.indirizzo = indirizzo;
        this.data = LocalDate.now();
        this.statoOrdine = StatoOrdine.INSERITO;
        this.inOrdine = new ArrayList<>();
        this.user_id=user_id;
    }
    protected  Ordine(){};

    //getter e setter
    public LocalDate getData() {
        return data;
    }

    public StatoOrdine getStatoOrdine() {
        return statoOrdine;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public float getTotale() {
        float totale=0;
        for(RigaOrdine riga:inOrdine){
            totale+=riga.getPrezzo()*riga.getQtaProdotto();
        }
        return totale;
    }

    public List<RigaOrdine> getInOrdine() {
        return Collections.unmodifiableList(inOrdine);
    }

    //modifica ordine
    public void setStatoOrdine(StatoOrdine statoOrdine) {

        if(statoOrdine.ordinal() < this.statoOrdine.ordinal()) throw new IllegalArgumentException("Lo stato inserito non è valido");
        this.statoOrdine = statoOrdine;
    }

    public void addRigaOrdine(Prodotto prodotto, int qtaProdotto){
        if(prodotto.getQuantita()<qtaProdotto){
            throw new IllegalArgumentException("Non ci sono "+qtaProdotto+" "+prodotto.getNome()+" in magazzino ordine non valido, per favore rimuovere "+(prodotto.getQuantita()-qtaProdotto)+" "+prodotto.getNome());//NON sapevo che scrivere
        }else {
            inOrdine.add(new RigaOrdine(prodotto, qtaProdotto));
        }
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
