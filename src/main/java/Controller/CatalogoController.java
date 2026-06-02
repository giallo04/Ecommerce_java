package Controller;

import Boundary.DTO.ProductDTO;
import Entity.Merce.Catalogo;
import Entity.Merce.Prodotto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CatalogoController {

    public ArrayList<ProductDTO> loadCatalogo() {
        List<Prodotto> prodotti = Catalogo.getInstance().getProdotti();
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        String imgUrl="/products/";
        for (Prodotto p : prodotti) {
            productDTOS.add(new ProductDTO(
                    p.getNome(),
                    p.getDescrizione(),
                    p.getPrezzo(),
                    p.getSconto(),
                    p.getCategoria(),
                    p.getQuantita(),
                    p.getProduct_id(),
                    imgUrl+p.getNome().trim()+".png"
            ));
        }
        return productDTOS;
    }

    public String addProduct(ProductDTO productDTO) {//TODO gestire campi di formato sbagliato
        Catalogo catalogo = Catalogo.getInstance();
        try {
                 catalogo.aggiungiProdotto(
                    productDTO.getNome(),
                    productDTO.getPrezzo(),
                    productDTO.getDescrizione(),
                    productDTO.getQuantita(),
                    productDTO.getCategoria()
            );
            try {
                Files.copy(Paths.get(productDTO.getImgPath()), Paths.get("src/main/resources/products/"+ productDTO.getNome().trim()+".png"), StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                return "Errore durante l'aggiunta dell'immagine del prodotto";
            }catch (NumberFormatException e){
                return "Errore durante l'aggiunta del prodotto";
            }

            return "Prodotto aggiunto con successo";

        } catch (IllegalArgumentException e) {
            return "Errore durante l'aggiunta del prodotto";
        }
    }
    public String removeProduct(long id) {
        Catalogo catalogo=Catalogo.getInstance();
        try{
            catalogo.rimuoviProdotto(id);
            return "Prodotto rimosso con successo";
        }catch(IllegalArgumentException e){
            return "Prodotto non trovato";
        }
    }
    public String modProdotto(ProductDTO productDTO){
            Catalogo catalogo=Catalogo.getInstance();
            try {
                catalogo.modificaProdotto(
                        productDTO.getId(),
                        productDTO.getNome(),
                        productDTO.getPrezzo(),
                        productDTO.getDescrizione(),
                        productDTO.getQuantita(),
                        productDTO.getCategoria(),
                        productDTO.getSconto()
                );
                return "Prodotto modificato con successo";
            }catch (IllegalArgumentException e){
                return "Prodotto non trovato";
            }
    }
}