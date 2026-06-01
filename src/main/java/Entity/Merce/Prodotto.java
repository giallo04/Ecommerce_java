package Entity.Merce;

import Entity.Ordini.RigaOrdine;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Prodotto {

    public Prodotto() {}//richiesto per JPA
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private long product_id;
    private String nome;
    private float prezzo;
    private  String descrizione;
    private  String categoria;
    private int sconto;
    private int quantita;
    //TODO collegare prodotto con ordine in persistenza
    public Prodotto(String nome, float prezzo, String descrizione, int quantita, String categoria)
            throws IllegalArgumentException {
        if (nome.isEmpty() || prezzo <= 0 || descrizione.isEmpty() || categoria.isEmpty() || quantita < 0) {
            throw new IllegalArgumentException();
        }
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.quantita = quantita;
        this.sconto = 0;
    }


    public String getNome()             { return nome; }
    public float getPrezzo()            { return prezzo; }
    public String getDescrizione()      { return descrizione; }
    public String getCategoria()        { return categoria; }
    public int getQuantita()            { return quantita; }
    public int getSconto()              { return sconto; } // int, non String

    public void setNome(String nome) {
        if (nome.isEmpty()) throw new IllegalArgumentException();
        this.nome = nome;
    }
    public void setPrezzo(float prezzo) {
        if (prezzo <= 0) throw new IllegalArgumentException();
        this.prezzo = prezzo;
    }
    public void setSconto(int sconto) {
        if (sconto < 0 || sconto > 100) throw new IllegalArgumentException();
        this.sconto = sconto;
    }
    public void setQuantita(int quantita) {
        if (quantita < 0) throw new IllegalArgumentException();
        this.quantita = quantita;
    }
    public void setDescrizione(String descrizione) {
        if (descrizione.isEmpty()) throw new IllegalArgumentException();
        this.descrizione = descrizione;
    }
    public void setCategoria(String categoria) {
        if (categoria.isEmpty()) throw new IllegalArgumentException();
        this.categoria = categoria;
    }
    public long getProduct_id() {
        return product_id;
    }
}