package Entity.Merce;

import Database.GestorePersistenza;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegistroCatalogo {
    private final GestorePersistenza gestore;
    public RegistroCatalogo(){
        gestore = new GestorePersistenza();
    }

    public void aggiungi(String nome, float prezzo, int sconto, String descrizione, String categoria){
        try{
            Prodotto prodotto=new Prodotto(nome,prezzo,descrizione,sconto,categoria);
            if(gestore.cercaPerCampo(Prodotto.class,"nome",nome)==null){
                gestore.salva(prodotto);
            }else{
                throw new IllegalArgumentException("Prodotto: "+nome+" già presente nel catalogo");
            }
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Errore: "+e.getMessage());
        }
    }
    public  void aggiorna(long product_id,String nome, float prezzo, int sconto, String descrizione, String categoria){
        Prodotto prodotto=gestore.trovaPerId(Prodotto.class,product_id);
        if(prodotto!=null){
            prodotto.setNome(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setSconto(sconto);
            prodotto.setDescrizione(descrizione);
            prodotto.setCategoria(categoria);
            gestore.salva(prodotto);
        }else{
            throw new IllegalArgumentException("Prodotto non trovato");
        }
    }

    public  void rimuovi(int product_id){
        //wrappo il metodo di gestione della persistenza
        try {
          gestore.rimuovi(product_id);
        }catch (RuntimeException e){
            throw new IllegalArgumentException("Prodotto non trovato");
        }
    }
    public List<Prodotto> getListaProdotti(){
        return gestore.cercaPerCampi(Prodotto.class,Map.of());
    }
    public void salvaTutti(Prodotto... prodotti){
        gestore.salvaTutti(prodotti);
    }
}
