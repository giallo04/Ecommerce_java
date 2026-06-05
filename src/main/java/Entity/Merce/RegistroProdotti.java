package Entity.Merce;

import Database.GestorePersistenza;

import java.util.List;
import java.util.Map;

public class RegistroProdotti {
    private GestorePersistenza gestore;

    public  RegistroProdotti(){
        gestore=new GestorePersistenza();
    }
    public void aggiungiProdotto(String nome, String descrizione, float prezzo, Categoria categoria,int quantita) throws IllegalArgumentException{
        //Cerca se il prodotto esiste gia
        Prodotto prodottoEsistente=cercaProdottoNome(nome);
        if(prodottoEsistente!=null){
            throw new IllegalArgumentException("Prodotto "+nome+" già presente in catalogo");
        }else {
            Prodotto prodotto=new Prodotto(nome,prezzo,descrizione,quantita,categoria);
            //Creo il prodotto e lo aggiungo al catalogo
            gestore.salva(prodotto);
        }
    }
    public Prodotto cercaProdottoNome(String nome){
        return gestore.cercaPrimoPerCampi(Prodotto.class, Map.of("nome",nome));
    }
    public Prodotto cercaProdottoId(Long id){
        return gestore.trovaPerId(Prodotto.class,id);
    }
    public void aggiornaProdotto(Long product_id,String nome, String descrizione, float prezzo, Categoria categoria,int quantita,int sconto) throws IllegalArgumentException{
        Prodotto prodotto=cercaProdottoId(product_id);
        if(prodotto==null){
            throw new IllegalArgumentException("Prodotto "+product_id+" non presente nel catalogo");
        }else {
            prodotto.setNome(nome);
            prodotto.setPrezzo(prezzo);
            prodotto.setSconto(sconto);
            prodotto.setCategoria(categoria);
            gestore.aggiorna(prodotto);
        }
    }
public void eliminaProdotto(Long id){
        //Verifica internamente se esiste
       boolean result=gestore.elimina(Prodotto.class,id);
       if(!result){
           throw new IllegalArgumentException("Prodotto "+id+" non presente nel catalogo");
       }
}
public List<Prodotto> cercaProdottiPerCategoria(Categoria categoria){
        return gestore.cercaPerCampi(Prodotto.class,Map.of("categoria",categoria.toString()));
}
public List<Prodotto> cercaProdottiPerStringa(String stringa){
        return gestore.cercaNeiCampi(Prodotto.class,Map.of("nome",stringa, "descrizione",stringa));
}
public List<Prodotto> caricaCatalogo(){
        return gestore.cercaPerCampo(Prodotto.class, "", null);
}

}
