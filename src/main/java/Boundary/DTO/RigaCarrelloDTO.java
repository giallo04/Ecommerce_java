package Boundary.DTO;

public class RigaCarrelloDTO {

    private ProductDTO productDTO;
    private int quantita;

    public RigaCarrelloDTO(ProductDTO productDTO, int quantita)
    {
        this.productDTO = productDTO;
        this.quantita = quantita;
    }

    public int getQuantita() {
        return quantita;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public float getPrezzoTotaleRiga() {

        return this.productDTO.getPrezzo() * this.quantita;
    }
}
