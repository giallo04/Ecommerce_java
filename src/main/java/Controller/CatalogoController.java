package Controller;

import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CatalogoController {

    public static final int  ID=0;
    public static final int  IMG_PATH=1;
    public static final int  NOME=2;
    public static final int  PREZZO=3;
    public static final int  QUANTITA=4;
    public static final int  CATEGORIA=5;
    public static final int  SCONTO=6;
    public static final int  PREZZO_CON_SCONTO=7;
    public static final int  DESCRIZIONE=8;
    private  String messaggio_errore;//Used to pass error messages to the view

    private void setMsg(String msg){messaggio_errore=msg;}
    public String getMsg(){return messaggio_errore;}


    private List<String[]> convertToGui(List<Prodotto> prodotti){
        List<String[]> risultati=new ArrayList<>(prodotti.size());
        for(Prodotto p:prodotti){
            String[] prodotto=new String[]{
                    String.valueOf(p.getProduct_id()),
                    "/products/"+String.valueOf(p.getProduct_id())+".png",//Hardcoded image path
                    p.getNome(),
                    "$ "+String.valueOf(p.getPrezzo()),
                    String.valueOf(p.getQuantita()),
                    p.getCategoria().toString(),
                    String.valueOf(p.getSconto()),
                    String.valueOf(p.getPrezzo()-p.getPrezzo()*p.getSconto()/100),//Prezzo con sconto
                    p.getDescrizione()
            };

            risultati.add(prodotto);
        }
        return risultati;
    }

    public List<String[]> caricaCatalogo(){
        RegistroProdotti registro=new RegistroProdotti();
        List<Prodotto> prodotti=registro.caricaCatalogo();
        if(prodotti.isEmpty())return null;
        else {
            return convertToGui(prodotti);
        }
    }
    public List<String[]> caricaProdottiCategoria(String categoria){
        RegistroProdotti registro=new RegistroProdotti();
        return convertToGui(registro.cercaProdottiPerCategoria(Categoria.valueOf(categoria)));
    }
public List<String[]> caricaProdottiStringa(String stringa){
        RegistroProdotti registro=new RegistroProdotti();
        return convertToGui(registro.cercaProdottiPerStringa(stringa));
}
    public String[] caricaProdotto(Long id){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            Prodotto prodotto=registro.cercaProdottoId(id);
            ArrayList<Prodotto> prodotti=new ArrayList<>();
            prodotti.add(prodotto);
            return convertToGui(prodotti).getFirst();
            }catch (IllegalArgumentException e){
                    setMsg(e.getMessage());//Passo il messaggio di errore
                    return null;
                }
        }

        public boolean aggiungiProdotto(String nome, String prezzo, String descrizione,String categoria,String quantita,String imgPath){
            RegistroProdotti registro=new RegistroProdotti();
            try{
                long id=registro.aggiungiProdotto(nome,descrizione,Float.parseFloat(prezzo), Categoria.valueOf(categoria),Integer.parseInt(quantita));
                copyImage(id,imgPath);
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }catch (IOException e){
                setMsg("Errore durante il salvataggio dell'immagine");
                return false;
            }
        }
        public boolean modificaProdotto(Long id,String nome, String prezzo, String descrizione,String categoria,String quantita,String sconto,String imgPath){
            RegistroProdotti registro=new RegistroProdotti();
            try{
                registro.aggiornaProdotto(id,nome,descrizione,Float.parseFloat(prezzo), Categoria.valueOf(categoria),Integer.parseInt(quantita),Integer.parseInt(sconto));
                if(!imgPath.isEmpty()){
                    copyImage(id,imgPath);
                }
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }catch (IOException e){
                setMsg("Errore durante il salvataggio dell'immagine");
                return false;
            }
        }
        public boolean eliminaProdotto(Long id){
            RegistroProdotti registro=new RegistroProdotti();
            try{
                registro.eliminaProdotto(id);
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }
        }
    public List<String> getCategorie(){
        List<String> categorie=new ArrayList<>();
        for(Categoria c:Categoria.values()){
            categorie.add(c.toString());
        }
        return categorie;
    }
    public List<String[]> caricaPerCategoriaAndStringa(String categoria,String stringa){
        RegistroProdotti registro=new RegistroProdotti();
        return convertToGui(registro.cercaProdottoPerStringaECategoria(stringa, Categoria.valueOf(categoria)));
    }
    private void copyImage(Long id, String imgPath) throws IOException {
        Path sorgente= Path.of(imgPath);
        Path destinazione=Path.of("src/main/resources/products/"+id+".png");
        Files.copy(sorgente,destinazione);
    }


    }

