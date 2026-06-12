package Controller;

import Database.GestorePersistenza;
import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;
import Entity.Ordini.RigaOrdine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CatalogoController {
    private final String PRODUCTS_IMG_PATH="img/products/";

    public static final int  ID=0;
    public static final int  IMG_PATH=1;
    public static final int  NOME=2;
    public static final int  PREZZO=3;
    public static final int  QUANTITA=4;
    public static final int  CATEGORIA=5;
    public static final int  SCONTO=6;
    public static final int  PREZZO_CON_SCONTO=7;
    public static final int  DESCRIZIONE=8;
    private  String messaggio_errore;

    private void setMsg(String msg){messaggio_errore=msg;}
    public String getMsg(){return messaggio_errore;}


    private List<String[]> convertToGui(List<Prodotto> prodotti){
        List<String[]> risultati=new ArrayList<>(prodotti.size());
        for(Prodotto p:prodotti){
            String[] prodotto=new String[]{
                    String.valueOf(p.getProduct_id()),
                    PRODUCTS_IMG_PATH+String.valueOf(p.getProduct_id())+".png",
                    p.getNome(),
                    "$ "+String.format("%.2f",p.getPrezzo()),
                    String.valueOf(p.getQuantita()),
                    p.getCategoria().toString(),
                    String.valueOf(p.getSconto()),
                    "$ "+String.format("%.2f", p.getPrezzo() - p.getPrezzo() * p.getSconto() / 100.0),
                    p.getDescrizione()
            };

            risultati.add(prodotto);
        }
        return risultati;
    }


    public List<String[]> caricaProdotti(String query, String categoria, boolean soloOfferte, int ordinamento) {
        RegistroProdotti registro = new RegistroProdotti();
        List<Prodotto> prodotti;

        boolean hasQuery    = query    != null && !query.trim().isEmpty();
        boolean hasCategoria = categoria != null;

        if (!hasQuery && !hasCategoria)
            prodotti = registro.caricaCatalogo();
        else if (hasQuery && !hasCategoria)
            prodotti = registro.cercaProdottiPerStringa(query);
        else if (!hasQuery)
            prodotti = registro.cercaProdottiPerCategoria(Categoria.valueOf(categoria));
        else
            prodotti = registro.cercaProdottoPerStringaECategoria(query, Categoria.valueOf(categoria));

        if (soloOfferte)
            prodotti.removeIf(p -> p.getSconto() <= 0);

        switch (ordinamento) {
            case 1: // Nome
                prodotti.sort(Comparator.comparing(p -> p.getNome().toLowerCase()));
                break;
            case 2: // Prezzo crescente
                prodotti.sort(Comparator.comparingDouble(Prodotto::getPrezzo));
                break;
            case 3: // Prezzo decrescente
                prodotti.sort(Comparator.comparingDouble(Prodotto::getPrezzo).reversed());
                break;
            default:
                break;
        }

        return prodotti.isEmpty() ? null : convertToGui(prodotti);
    }

    public List<String[]> caricaCatalogo(){
        RegistroProdotti registro=new RegistroProdotti();
        List<Prodotto> prodotti=registro.caricaCatalogo();
        if(prodotti.isEmpty())return null;
        else {
            return convertToGui(prodotti);
        }
    }
    public String[] caricaProdotto(long id){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            Prodotto prodotto=registro.cercaProdottoId(id);
            ArrayList<Prodotto> prodotti=new ArrayList<>();
            prodotti.add(prodotto);
            return convertToGui(prodotti).getFirst();
        }catch (IllegalArgumentException e){
            setMsg(e.getMessage());
            return null;
        }
    }
    public String[] caricaProdotto(String nome){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            Prodotto prodotto=registro.cercaProdottoNome(nome);
            ArrayList<Prodotto> prodotti=new ArrayList<>();
            prodotti.add(prodotto);
            return convertToGui(prodotti).getFirst();
        }catch (IllegalArgumentException e){
            setMsg(e.getMessage());
            return null;
        }
    }

    public boolean aggiungiProdotto(String nome, String prezzo, String descrizione,String categoria,String quantita,String imgPath,String sconto){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            long id=registro.aggiungiProdotto(nome,descrizione,Float.parseFloat(prezzo), Categoria.valueOf(categoria),Integer.parseInt(quantita),Integer.parseInt(sconto));
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
    public boolean eliminaProdotto(String id){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            registro.eliminaProdotto(Long.parseLong(id));
            Files.delete(Path.of(PRODUCTS_IMG_PATH+id+".png"));
            return true;
        }catch(IllegalArgumentException e){
            setMsg(e.getMessage());
            return false;
        }catch (IOException e){
            setMsg("Errore durante l'eliminazione dell'immagine dal File System");
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

    public List<String[]> inEsaurimento(){
        RegistroProdotti registroProdotti=new RegistroProdotti();
        List<Prodotto> risultati=registroProdotti.caricaProdottiInEsaurimento();
        if(risultati.isEmpty())return null;
        List<String[]> prodotti=new ArrayList<>();
        for(Prodotto p:risultati){
            prodotti.add(new String[]{p.getNome(),String.valueOf(p.getQuantita())});
        }
        return prodotti;
    }
    private void copyImage(Long id, String imgPath) throws IOException {
        Path sorgente= Path.of(imgPath.trim());
        Path destinazione=Path.of(PRODUCTS_IMG_PATH+id+".png");
        Files.copy(sorgente,destinazione);
    }


    public List<String[]> prodottiPiuVenduti(){
        RegistroProdotti registro=new RegistroProdotti();
        List<String[]> prodotti=registro.ProdottiPiuVenduti();
        if(prodotti.isEmpty())return null;
        return prodotti;
    }


}