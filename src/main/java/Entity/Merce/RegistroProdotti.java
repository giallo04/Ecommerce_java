package Entity.Merce;

import Database.GestorePersistenza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RegistroProdotti {
    private GestorePersistenza gestore;

    public  RegistroProdotti(){
        gestore=new GestorePersistenza();
    }
    public long aggiungiProdotto(String nome, String descrizione, float prezzo, Categoria categoria,int quantita) throws IllegalArgumentException{
        //Cerca se il prodotto esiste gia
        Prodotto prodottoEsistente=cercaProdottoNome(nome);
        if(prodottoEsistente!=null){
            throw new IllegalArgumentException("Prodotto "+nome+" già presente in catalogo");
        }else {
            Prodotto prodotto=new Prodotto(nome,prezzo,descrizione,quantita,categoria);
            //Creo il prodotto e lo aggiungo al catalogo
            gestore.salva(prodotto);
            return prodotto.getProduct_id();
        }
    }
    public Prodotto cercaProdottoNome(String nome){
        return gestore.cercaPrimoPerCampi(Prodotto.class, Map.of("nome",nome));
    }
    public Prodotto cercaProdottoId(long id){
        return (Prodotto)  gestore.trovaPerId(Prodotto.class,id);
    }
    public void aggiornaProdotto(long product_id,String nome, String descrizione, float prezzo, Categoria categoria,int quantita,int sconto) throws IllegalArgumentException{
        Prodotto prodotto=cercaProdottoId(product_id);
        if(prodotto==null){
            throw new IllegalArgumentException("Prodotto "+product_id+" non presente nel catalogo");
        }else {
            Prodotto prodottoEsistente=cercaProdottoNome(nome);
            if(prodottoEsistente==null||prodottoEsistente.getProduct_id()==product_id) {
                prodotto.setNome(nome);
                prodotto.setPrezzo(prezzo);
                prodotto.setSconto(sconto);
                prodotto.setCategoria(categoria);
                prodotto.setDescrizione(descrizione);
                if (quantita > 0) prodotto.incrementQt(quantita);
                else prodotto.decrementQt(-quantita);
                gestore.aggiorna(prodotto);
            }else{
                throw new IllegalArgumentException("Esiste già un prodotto con il nome "+nome+" nel catalogo");
            }
        }
    }
    public void eliminaProdotto(long id){
            //Verifica internamente se esiste
           boolean result=gestore.elimina(Prodotto.class,id);
           if(!result){
               throw new IllegalArgumentException("Prodotto "+id+" non presente nel catalogo");
           }
    }
    public List<Prodotto> cercaProdottiPerCategoria(Categoria categoria){
            return gestore.cercaPerCampi(Prodotto.class,Map.of("categoria",categoria));
    }
    public List<Prodotto> cercaProdottiPerStringa(String stringa){
            return gestore.cercaNeiCampi(Prodotto.class,Map.of("nome",stringa, "descrizione",stringa));
    }
    public List<Prodotto> cercaProdottoPerStringaECategoria(String stringa, Categoria categoria){
            List<Prodotto> prodotti=cercaProdottiPerStringa(stringa);
            if(prodotti==null)return null;
            else{
                List<Prodotto> risultati=new ArrayList<>();
                for(Prodotto p:prodotti){
                    if(p.getCategoria()==categoria)risultati.add(p);
                }
                return risultati;
            }
        }
        public void aggiornaProdotto(Prodotto prodotto){
        gestore.aggiorna(prodotto);
        }
    public List<Prodotto> caricaCatalogo(){
            return gestore.cercaPerCampi(Prodotto.class, Collections.emptyMap());
    }
    public List<Prodotto> caricaProdottiInEsaurimento(){
        GestorePersistenza gestore=new GestorePersistenza();
        return  gestore.cercePerCampoOrdinato(Prodotto.class, "quantita", true);
    }

}
