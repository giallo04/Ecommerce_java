package Entity;

import java.util.ArrayList;

public class Catalogo {
    private final ArrayList<Prodotto> prodotti;

    public Catalogo(){
        prodotti = new ArrayList<>();
    }
    private boolean verificaPresenza(String nome){
        for(Prodotto p:prodotti){
            if(p.getNome().equals(nome)) return true;
        }
        return false;
    }
    public void aggiungiProdotto(String nome, float prezzo, String descrizione, int quantita, String categoria){
        if(!verificaPresenza(nome)){
            prodotti.add(new Prodotto(nome,prezzo,descrizione,quantita,categoria));
        }
    }
}
