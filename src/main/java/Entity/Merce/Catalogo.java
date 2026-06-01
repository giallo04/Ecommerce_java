package Entity.Merce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Catalogo {

    //Singleton
    private final ArrayList<Prodotto> prodotti;
    private static Catalogo instance=new Catalogo();

    private Catalogo(){
        prodotti = new ArrayList<>();
    }
    private boolean verificaPresenza(String nome){
        for(Prodotto p:prodotti){
            if(p.getNome().equals(nome)) return true;
        }
        return false;
    }
    public static Catalogo getInstance(){
        return instance;
    }
    public void aggiungiProdotto(String nome, float prezzo, String descrizione, int quantita, String categoria){
        if(!verificaPresenza(nome)){
            prodotti.add(new Prodotto(nome,prezzo,descrizione,quantita,categoria));
        }else throw new IllegalArgumentException("Prodotto "+nome+"già presente nel catalogo");
    }
    public List<Prodotto> getProdotti(){
        return Collections.unmodifiableList(prodotti);
    }
}
