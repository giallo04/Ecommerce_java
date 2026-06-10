package Controller;

import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;
import Entity.Ordini.Ordine;
import Entity.Ordini.RegistroOrdini;
import Entity.client.Carrello;
import Entity.client.RegistroUtenti;
import Entity.client.RigaCarrello;
import Entity.client.Utente;

import java.util.ArrayList;
import java.util.List;

public class CarrelloController {
    private final String PRODUCTS_IMG_PATH="img/products/";

    public static final int  ID=0;
   public static final int  QUANTITA=1;
    private  String messaggio_errore;


    private void setMsg(String msg){messaggio_errore=msg;}
    public String getMsg(){return messaggio_errore;}


    public boolean aggiungiAlCarrello(long product_id, int quantita)
    {
        long idUtente=AccountController.get_current_user_id();
        RegistroUtenti registroUtenti = new RegistroUtenti();

        Utente utente=registroUtenti.cercaUtentePerId(idUtente);
        if(utente==null){ setMsg("Utente inesistente");return false;}
        else {

            Carrello carrello = utente.getCarrello();

            try{
                RegistroProdotti registroProdotto = new RegistroProdotti();
                Prodotto prodotto=registroProdotto.cercaProdottoId(product_id);
                carrello.aggiungiProdotto(prodotto, quantita);
                registroUtenti.aggiornaUtente(utente);
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }


        }

    }

    public boolean rimuoviDalCarrello(Long product_id, int quantita)
    {
        RegistroUtenti registroUtenti = new RegistroUtenti();

        Utente utente=registroUtenti.cercaUtentePerId(AccountController.get_current_user_id());
        if(utente==null){ setMsg("Utente inesistente");return false;}
        else {
            try {
                Carrello carrello = utente.getCarrello();
                carrello.rimuoviProdotto(product_id, quantita);
                registroUtenti.aggiornaUtente(utente);
                return true;
            }catch (IllegalArgumentException e){
                setMsg(e.getMessage());
                return false;
            }

        }
    }



    private List<String[]> convertToGui(List<RigaCarrello> righeCarrello)
    {
        List<String[]> lista=new ArrayList<>(righeCarrello.size());

        for(RigaCarrello riga :righeCarrello)
        {
            String[] prodotto=new String[]{
                    String.valueOf(riga.getProdotto().getProduct_id()),
                    String.valueOf(riga.getQuantita())
                };
            lista.add(prodotto);
        }
        return lista;

    }


    public List<String[]> caricaCarrello()
    {
        long idUtente=AccountController.get_current_user_id();
        RegistroUtenti registroUtenti = new RegistroUtenti();
        Utente utente=registroUtenti.cercaUtentePerId(idUtente);
        if(utente==null){ setMsg("Utente inesistente");return null;}
        try {
            Carrello carrello = utente.getCarrello();
            List<RigaCarrello> righe = carrello.getProdotti();
            if (righe.isEmpty()) return null;
            else return convertToGui(righe);
        }catch (IllegalArgumentException e){
            setMsg(e.getMessage());
            return null;
        }

    }

    public String caricaTotale()
    {
        long user_id=AccountController.get_current_user_id();
        RegistroUtenti registroUtenti = new RegistroUtenti();
        Utente user=registroUtenti.cercaUtentePerId(user_id);
        if(user==null){ setMsg("Utente inesistente");return null;}
        else {
            Carrello carrello = user.getCarrello();
            return "$ "+String.format("%.2f",carrello.getTotale());
        }
    }

    public boolean effettuaOrdine() {
        long idUtente = AccountController.get_current_user_id();
        RegistroUtenti registroUtenti = new RegistroUtenti();

        Utente utente = registroUtenti.cercaUtentePerId(idUtente);

        Carrello carrello = utente.getCarrello();

        List<RigaCarrello> righe = carrello.getProdotti();

            RegistroOrdini registroOrdini = new RegistroOrdini();
            Ordine ordine = new Ordine(utente.getIndirizzo(),idUtente);
            try {
                for (RigaCarrello riga : righe) {
                    ordine.addRigaOrdine(riga.getProdotto(), riga.getQuantita());
                }
            } catch (IllegalArgumentException e) {
                setMsg(e.getMessage());
                return false;
            }

            if(utente.verificaCredito(carrello.getTotale())) {

                //Tolgo dal magazzino solo se tutti i prodotti sono stati ordinati correttamente
                RegistroProdotti registroProdotti=new RegistroProdotti();
                for (RigaCarrello riga : righe) {
                    riga.getProdotto().decrementQt(riga.getQuantita());
                    registroProdotti.aggiornaProdotto(riga.getProdotto());
                }

                    //svuoto carrello
                    carrello.svuotaCarrello();
                    registroUtenti.aggiornaUtente(utente);
                }else{
                    setMsg("Credito insufficiente per effettuare l'ordine");
                    return false;
                }

            registroOrdini.registraOrdine(ordine);
            return true;
        }
}


