package Boundary.DTO;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarrelloDTO {
    private String email;
    private ArrayList<RigaCarrelloDTO> prodottiInCarrello=new ArrayList<>();

    public ArrayList<RigaCarrelloDTO> getProdottiInCarrello() {
        return prodottiInCarrello;
    }

    public void setProdottiInCarrello(ArrayList<RigaCarrelloDTO> prodottiInCarrello) {
        this.prodottiInCarrello = prodottiInCarrello;
    }

    public float prezzoTotale()
    {
        float prezzoTotale=0;
        for(RigaCarrelloDTO riga:  this.prodottiInCarrello)
        {
            prezzoTotale+=riga.getPrezzoTotaleRiga();
        }

        return prezzoTotale;
    }

    public void aggiungiProdotto(ProductDTO productDTO, int quantita)
    {
        prodottiInCarrello.add(new RigaCarrelloDTO(productDTO,quantita));
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }
}
