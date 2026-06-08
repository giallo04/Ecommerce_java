package Entity.client;

import Entity.Merce.Prodotto;
import jakarta.persistence.*;
import java.util.*;

@Embeddable
public class Carrello {


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RigaCarrello.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private Map<Long, RigaCarrello> prodotti = new LinkedHashMap<>();

    public List<RigaCarrello> getProdotti() {
        return List.copyOf(prodotti.values());
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

    public void rimuoviProdotto(long idProdotto,int quantita) {

        if(prodotti.containsKey(idProdotto)){
            RigaCarrello riga=prodotti.get(idProdotto);
            if(riga.getQuantita()>=quantita){
                riga.decrementQuantita(quantita);
                if(riga.getQuantita()==0){
                    prodotti.remove(idProdotto);
                }
            }else {
                throw new IllegalArgumentException("Nel carrello non ci sono "+quantita+" "+riga.getProdotto().getNome()+" da eliminare");
            }
        };
    }

    public void svuotaCarrello() {
        prodotti.clear();
    }
}