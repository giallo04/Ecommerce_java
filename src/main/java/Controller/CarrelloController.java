package Controller;

import Boundary.Carrello.MainCarrello;
import Boundary.DTO.ProductDTO;
import Boundary.DTO.RigaCarrelloDTO;
import Entity.Merce.Prodotto;
import Entity.client.Carrello;
import Entity.client.RigaCarrello;

import java.util.ArrayList;

public class CarrelloController {
    private MainCarrello boundaryCarrello;
    private Carrello entityCarrello;

    public CarrelloController(MainCarrello boundaryCarrello, Carrello entityCarrello) {
        this.boundaryCarrello = boundaryCarrello;
        this.entityCarrello = entityCarrello;
    }

    public void caricaInterefacciaCarrello()
    {
        ArrayList<RigaCarrelloDTO> lista = new ArrayList<>();

        System.out.println("[DEBUG CONTROLLER] Inizio ciclo sui prodotti del carrello. Elementi totali nell'entity: " + entityCarrello.getProdotti().size());

        for(RigaCarrello riga : entityCarrello.getProdotti())
        {
            Prodotto p = riga.getProdotto();

            // STAMPA DI DIAGNOSTICA: Vediamo cosa c'è davvero dentro l'Entity prima del DTO
            if (p != null) {
                System.out.println("[DEBUG CONTROLLER] Sto leggendo dall'Entity: " + p.getNome() + " - Prezzo: " + p.getPrezzo());
            } else {
                System.err.println("[DEBUG CONTROLLER] ATTENZIONE: Il prodotto dentro questa riga è NULL!");
                continue;
            }

            ProductDTO product = new ProductDTO(
                    p.getNome(),
                    p.getDescrizione(),
                    p.getPrezzo(),
                    p.getSconto(),
                    p.getCategoria(),
                    p.getQuantita(),
                    p.getProduct_id(),
                    null);

            lista.add(new RigaCarrelloDTO(product, riga.getQuantita()));
        }

        System.out.println("[DEBUG CONTROLLER] Lista DTO creata. Dimensioni: " + lista.size());

        // Passiamo la lista alla boundary
        boundaryCarrello.popolaCatalogoProdotti(lista, this);

        aggiornaPrezzoTotale();
    }

    private void aggiornaPrezzoTotale() {
        float prezzoTotale = 0;
        for (RigaCarrello riga : entityCarrello.getProdotti()) {
            prezzoTotale += riga.getProdotto().getPrezzo() * riga.getQuantita();
        }
        boundaryCarrello.aggiornaTotale(prezzoTotale);
    }

    public void incrementaProdotto(long idprod) {
        ArrayList<RigaCarrello> listaRighe = new ArrayList<>(entityCarrello.getProdotti());

        boolean trovato = false;
        int i = 0;
        while (i < listaRighe.size() && !trovato) {
            RigaCarrello riga = listaRighe.get(i);
            if (riga.getProdotto().getProduct_id() == idprod) {
                riga.incrementQuantita(1);
                trovato = true;
            }
            i++;
        }
        // Forza l'aggiornamento visivo a schermo dopo l'incremento
        caricaInterefacciaCarrello();
    }

    public void eliminaProdotto(long idprod) {
        ArrayList<RigaCarrello> listaRighe = new ArrayList<>(entityCarrello.getProdotti());

        boolean trovato = false;
        int i = 0;
        while (i < listaRighe.size() && !trovato) {
            RigaCarrello riga = listaRighe.get(i);
            if (riga.getProdotto().getProduct_id() == idprod) {
                entityCarrello.rimuoviProdotto(riga.getProdotto());
                trovato = true;
            }
            i++;
        }
        // Forza l'aggiornamento visivo a schermo (la card sparirà)
        caricaInterefacciaCarrello();
    }
}