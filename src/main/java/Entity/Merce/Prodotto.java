package Entity.Merce;

import jakarta.persistence.*;



@Entity
public class Prodotto {

    public Prodotto() {}//richiesto per JPA
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long product_id;
    private String nome;
    private float prezzo;
    private  String descrizione;

    @Enumerated (EnumType.STRING)
    private  Categoria categoria;
    private int sconto;
    private int quantita;


    public Prodotto(String nome, float prezzo, String descrizione, int quantita, Categoria categoria){
        setNome(nome);
        setPrezzo(prezzo);
        setDescrizione(descrizione);
        setCategoria(categoria);
        setQuantita(quantita);
        this.sconto=0;
    }


    public String getNome()             { return nome; }
    public float getPrezzo()            { return prezzo; }
    public String getDescrizione()      { return descrizione; }
    public Categoria getCategoria()        { return categoria; }
    public int getQuantita()            { return quantita; }
    public int getSconto()              { return sconto; } // int, non String

    public void setNome(String nome) {
        if(nome==null) throw new IllegalArgumentException("Nome non valido");
        if (nome.isEmpty()) throw new IllegalArgumentException("Nome non puo essere vuoto");
        if(nome.length()>20) throw new IllegalArgumentException("Nome troppo lungo");
        if(nome.contains(" ")) throw new IllegalArgumentException("Nome non puo contenere spazi");
        this.nome = nome;
    }
    public void setPrezzo(float prezzo) {
        if (prezzo <= 0) throw new IllegalArgumentException("Prezzo non puo essere negativo");
        this.prezzo = prezzo;
    }
    public void setSconto(int sconto) {
        if (sconto < 0 || sconto > 100) throw new IllegalArgumentException("Sconto non valido");
        this.sconto = sconto;
    }
    private void setQuantita(int quantita) {
        if(quantita<0){
            throw new IllegalArgumentException("Quantità non puo essere negativa");
        }else {
            this.quantita=quantita;
        }
    }
  public void incrementQt(int quantita){
        if(quantita<0){
            throw new IllegalArgumentException("Quantità non puo essere negativa");
        }else {
            this.quantita+=quantita;
        }
  }
  public void decrementQt(int quantita){
        if(quantita<0){
            throw new IllegalArgumentException("Quantità non puo essere negativa");
        }else if(quantita>this.quantita){
            throw new IllegalArgumentException("Al momento non ci sono "+quantita+" "+nome+" in magazzino");
        }else {
            this.quantita-=quantita;
        }
  }
    public void setDescrizione(String descrizione) {
        if (descrizione.isEmpty()) throw new IllegalArgumentException();
        if(descrizione.length()>40) throw new IllegalArgumentException("Descrizione troppo lunga");
        this.descrizione = descrizione;
    }
    public void setCategoria(Categoria categoria) {
        if(categoria!=null){
                this.categoria = categoria;
        }else throw new IllegalArgumentException("Categoria non valida");
    }
    public long getProduct_id() {
        return product_id;
    }

}