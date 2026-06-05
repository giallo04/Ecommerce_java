package Entity.Merce;

import Entity.Ordini.RigaOrdine;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this.quantita=quantita;
        this.sconto=0;
    }


    public String getNome()             { return nome; }
    public float getPrezzo()            { return prezzo; }
    public String getDescrizione()      { return descrizione; }
    public Categoria getCategoria()        { return categoria; }
    public int getQuantita()            { return quantita; }
    public int getSconto()              { return sconto; } // int, non String

    public void setNome(String nome) {
        if (nome.isEmpty()) throw new IllegalArgumentException("Nome non puo essere vuoto");
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
        this.descrizione = descrizione;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public long getProduct_id() {
        return product_id;
    }

}