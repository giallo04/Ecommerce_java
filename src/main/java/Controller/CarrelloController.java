package Controller;

import Boundary.DTO.CarrelloDTO;
import Boundary.DTO.ProductDTO;
import Boundary.DTO.RigaCarrelloDTO;
import Entity.Merce.Prodotto;
import Entity.client.Carrello;
import Entity.client.RigaCarrello;
import Entity.client.Utente;

import java.util.ArrayList;


public class CarrelloController {





    public CarrelloDTO loadCarrello(CarrelloDTO carrelloDTO) {
        Utente utente = Utente.caricaDaEmail(carrelloDTO.getEmail());
        Carrello carrello = utente.getCarrello();
        ArrayList<RigaCarrello> rigaEntity = carrello.getProdotti();

        CarrelloDTO result = new CarrelloDTO();

        for(RigaCarrello riga : rigaEntity)
        {
            Prodotto p = riga.getProdotto();
            String imgUrl = "/products/";
            ProductDTO prodotto = new ProductDTO(
                    p.getNome(), p.getDescrizione(), p.getPrezzo(),
                    p.getSconto(), p.getCategoria(), p.getQuantita(),
                    p.getProduct_id(), imgUrl + p.getNome().trim() + ".png"
            );
            result.aggiungiProdotto(prodotto, riga.getQuantita());
        }

        return result;

    }


    public String eliminaProdotto(CarrelloDTO carrelloDTO,long idProdotto)
    {
        String email=carrelloDTO.getEmail();

        Utente utente = Utente.caricaDaEmail(email);
        Carrello carrello = utente.getCarrello();
        carrello.rimuoviProdotto(idProdotto);

        return "Prodotto eliminato con successo";



    }

    public String aumentaQuantita(CarrelloDTO carrelloDTO, long idProdotto)
    {
        String email=carrelloDTO.getEmail();

        Utente utente = Utente.caricaDaEmail(email);
        Carrello carrello = utente.getCarrello();
        carrello.aumentaQuantita(idProdotto, 1);
        return "Quantità aggiornata";
    }





}
