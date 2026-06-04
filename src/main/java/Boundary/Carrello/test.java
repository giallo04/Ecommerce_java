package Boundary.Carrello;

import Boundary.Carrello.MainCarrello;
import Boundary.DTO.CarrelloDTO;
import Boundary.DTO.ProductDTO;
import Boundary.DTO.RigaCarrelloDTO;
import Controller.CarrelloController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class test {

    public static void main(String[] args) {

        // 1. Creo il DTO con dati finti
        CarrelloDTO carrelloDTO = new CarrelloDTO();
        carrelloDTO.setEmail("test@test.com");

        // 2. Aggiungo prodotti finti al DTO
        ProductDTO p1 = new ProductDTO("Nike Air Max", "Scarpe comode", 120.0f, 10, "Scarpe", 1, 1L, "/products/nike.png");
        ProductDTO p2 = new ProductDTO("Adidas Samba", "Scarpe classiche", 90.0f, 0, "Scarpe", 2, 2L, "/products/adidas.png");
        ProductDTO p3 = new ProductDTO("Nike x", "Scarpe comode", 120.0f, 10, "Scarpe", 1, 1L, "/products/nike.png");
        ProductDTO p4 = new ProductDTO("Adidas ", "Scarpe classiche", 90.0f, 0, "Scarpe", 2, 2L, "/products/adidas.png");

        carrelloDTO.aggiungiProdotto(p1, 1);
        carrelloDTO.aggiungiProdotto(p2, 2);
        carrelloDTO.aggiungiProdotto(p3, 2);
        carrelloDTO.aggiungiProdotto(p4, 2);



        // 3. Controller finto che non va sul DB
        CarrelloController controller = new CarrelloController() {
            @Override
            public CarrelloDTO loadCarrello(CarrelloDTO dto) {
                return dto; // restituisce il dto così com'è
            }
            @Override
            public String eliminaProdotto(CarrelloDTO dto, long id) {
                dto.getProdottiInCarrello().removeIf(r -> r.getProductDTO().getId() == id);
                return "Eliminato";
            }
            @Override
            public String aumentaQuantita(CarrelloDTO dto, long id) {
                for(RigaCarrelloDTO riga : dto.getProdottiInCarrello()) {
                    if(riga.getProductDTO().getId() == id) {
                        // RigaCarrelloDTO deve avere setQuantita!
                        riga.setQuantita(riga.getQuantita() + 1);
                    }
                }
                return "Aggiornato";
            }
        };

        // 4. Apri la finestra
        JFrame frame = new JFrame("Test Carrello");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        MainCarrello mainCarrello = new MainCarrello(carrelloDTO, controller);
        frame.setContentPane(mainCarrello.getMainPanel());

        frame.setVisible(true);
    }
}