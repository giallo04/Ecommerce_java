package Controller;

import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;

import java.util.ArrayList;
import java.util.List;

public class CatalogoController {

    private  String messaggio_errore;//Used to pass error messages to the view

    private void setMsg(String msg){messaggio_errore=msg;}
    public String getMsg(){return messaggio_errore;}

    public List<String[]> caricaCatalogo(){
        RegistroProdotti registro=new RegistroProdotti();
        List<Prodotto> prodotti=registro.caricaCatalogo();
        List<String[]> risultati=new ArrayList<>(prodotti.size());
        if(prodotti.isEmpty())return null;
        else {
            for(Prodotto p:prodotti){


                String[] prodotto=new String[]{//no need for description
                        String.valueOf(p.getProduct_id()),
                        "products/"+String.valueOf(p.getProduct_id())+".png",//Hardcoded image path
                        p.getNome(),
                        String.valueOf(p.getSconto()),
                        String.valueOf(p.getQuantita()),
                        p.getCategoria().toString(),
                        String.valueOf(p.getSconto())
                    };

                    risultati.add(prodotto);
            }
            return risultati;


        }

    }

    public String[] caricaProdotto(Long id){
        RegistroProdotti registro=new RegistroProdotti();
        try{
            Prodotto prodotto=registro.cercaProdottoId(id);
            String[] prodotto_array=new String[]{
                    String.valueOf(prodotto.getProduct_id()),
                    "products/"+String.valueOf(prodotto.getProduct_id())+".png",//Hardcoded image path
                    prodotto.getNome(),
                    prodotto.getDescrizione(),
                    String.valueOf(prodotto.getSconto()),
                    String.valueOf(prodotto.getQuantita()),
                    prodotto.getCategoria().toString(),
                    String.valueOf(prodotto.getSconto())
            };
        }catch (IllegalArgumentException e){
                setMsg(e.getMessage());//Passo il messaggio di errore
                return null;
            }
        return null;
        }

        public boolean aggiungiProdotto(String nome, String prezzo, String descrizione,String categoria,String quantita){
            RegistroProdotti registro=new RegistroProdotti();
            try{
                registro.aggiungiProdotto(nome,descrizione,Float.parseFloat(prezzo), Categoria.valueOf(categoria),Integer.parseInt(quantita));
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }
        }
        public boolean modificaProdotto(Long id,String nome, String prezzo, String descrizione,String categoria,String quantita){
            RegistroProdotti registro=new RegistroProdotti();
            try{
                registro.aggiornaProdotto(id,nome,descrizione,Float.parseFloat(prezzo), Categoria.valueOf(categoria),Integer.parseInt(quantita),0);
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
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



    }

