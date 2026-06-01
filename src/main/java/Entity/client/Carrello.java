package Entity.client;

import Entity.Merce.Prodotto;
import Entity.Ordini.GestoreOrdini;
import Entity.Ordini.Ordine;
import Entity.Ordini.RigaOrdine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Carrello {

    private final List<RigaCarrello> prodotti;

    public Carrello() {
        this.prodotti = new ArrayList<>();
    }

    public List<RigaCarrello> getProdotti() {
        return Collections.unmodifiableList(prodotti);
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        if (quantita <= 0) return;

        for (RigaCarrello riga : prodotti) {
            if (riga.getProdotto().equals(prodotto)) {
                riga.incrementQuantita(quantita);
                return;
            }
        }
        prodotti.add(new RigaCarrello(prodotto, quantita));
    }

    public void rimuoviProdotto(Prodotto prodotto, int quantita) {
        if (quantita <= 0) return;

        Iterator<RigaCarrello> iterator = prodotti.iterator();
        while (iterator.hasNext()) {
            RigaCarrello riga = iterator.next();
            if (riga.getProdotto().equals(prodotto)) {
                if (riga.getQuantita() > quantita) {
                    riga.decrementQuantita(quantita);
                } else {
                    iterator.remove();
                }
                break;
            }
        }
    }

    public Ordine effettuaOrdine(Indirizzo indirizzo) {
        if (prodotti.isEmpty()) return null;
        for (RigaCarrello riga : prodotti) {
            if (riga.getQuantita() > riga.getProdotto().getQuantita()) {
                return null;
            }
        }
        Ordine order = new Ordine(indirizzo);
        for (RigaCarrello riga : prodotti) {
            order.addRigaOrdine(riga.getProdotto(), riga.getQuantita());
        }
        for (RigaCarrello riga : prodotti) {
            int nuovaScorta = riga.getProdotto().getQuantita() - riga.getQuantita();
            riga.getProdotto().setQuantita(nuovaScorta);
        }
        prodotti.clear();

        GestoreOrdini gestore = GestoreOrdini.getInstance();
        gestore.addOrdine(order);

        return order;
    }
}
