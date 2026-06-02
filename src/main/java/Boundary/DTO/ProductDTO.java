package Boundary.DTO;

public class ProductDTO {
    private String nome;
    private String descrizione;
    private float prezzo;
    private int sconto;
    private String categoria;
    private int quantita;
    private long id;
    private String imgPath;
    public  ProductDTO(String nome, String descrizione, float prezzo, int sconto, String categoria, int quantita,long id,String imgPath){
        this.nome=nome;
        this.descrizione=descrizione;
        this.prezzo=prezzo;
        this.sconto=sconto;
        this.categoria=categoria;
        this.quantita=quantita;
        this.id=id;
        this.imgPath=imgPath;
    }
    public long getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getImgPath() {
        return imgPath;
    }
    public String getDescrizione() {
        return descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getSconto() {
        return sconto;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQuantita() {
        return quantita;
    }
}
