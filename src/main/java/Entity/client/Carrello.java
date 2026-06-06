package Entity.client;

import Entity.Merce.Prodotto;
import jakarta.persistence.*;
import java.util.*;

@Embeddable
public class Carrello {

    // Risolto: Diciamo a Hibernate di usare la relazione "prodotto" dentro RigaCarrello come chiave della Mappa
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RigaCarrello.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @MapKey(name = "prodotto")
    private Map<Long, RigaCarrello> prodotti = new LinkedHashMap<>();

    public ArrayList<RigaCarrello> getProdotti() {
        return new ArrayList<>(prodotti.values());
    }

    public float getTotale() {
        float totale = 0;
        for (RigaCarrello riga : prodotti.values()) {
            totale += riga.calcolaSubTotale();
        }
        return totale;
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        long id = prodotto.getProduct_id();
        if (prodotti.containsKey(id)) {
            RigaCarrello esistente = prodotti.get(id);
            esistente.incrementQuantita(quantita);
        } else {
            RigaCarrello nuovo = new RigaCarrello(prodotto, quantita);
            prodotti.put(id, nuovo);
        }
    }

    public void rimuoviProdotto(long idProdotto) {
        prodotti.remove(idProdotto);
    }

    public void svuotaCarrello() {
        prodotti.clear();
    }
}