package Entity.Merce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
        if(!verificaPresenza(nome)) {
        prodotti.add(new Prodotto(nome, prezzo, descrizione, quantita, categoria));
        }else throw new IllegalArgumentException("Prodotto gia presente nel catalogo");
    }
    public List<Prodotto> getProdotti(){
        return Collections.unmodifiableList(prodotti);
    }
    public void rimuoviProdotto(Long id){
        Iterator<Prodotto> iterator=prodotti.iterator();
        while (iterator.hasNext()){
            Prodotto p=iterator.next();
            if(p.getProduct_id()==id){
                iterator.remove();
                break;
            }
        }
        throw new IllegalArgumentException("Prodotto non trovato");
    }
    public void modificaProdotto(Long id, String nome, float prezzo, String descrizione, int quantita, String categoria,int sconto){
        Prodotto p=null;
        for(Prodotto prodotto:prodotti){
            if(prodotto.getProduct_id()==id){
                p=prodotto;
                break;
            }
        }
        if(p!=null){
            p.setNome(nome);
            p.setPrezzo(prezzo);
            p.setDescrizione(descrizione);
            p.setQuantita(quantita);
            p.setCategoria(categoria);
            p.setSconto(sconto);
        }else throw new IllegalArgumentException("Prodotto non trovato");
    }
}
