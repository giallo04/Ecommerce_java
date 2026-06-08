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


    public boolean aggiungiAlCarrello(Long idUtente, Prodotto prodotto, int quantita)
    {
        RegistroUtenti registroUtenti = new RegistroUtenti();

        Utente utente=registroUtenti.cercaUtentePerId(idUtente);
        if(utente==null) return  false;
        else {

            Carrello carrello = utente.getCarrello();

            carrello.aggiungiProdotto(prodotto, quantita);


            return true;

        }

    }

    public boolean rimuoviDalCarrello(Long idUtente, Prodotto prodotto)
    {
        RegistroUtenti registroUtenti = new RegistroUtenti();

        Utente utente=registroUtenti.cercaUtentePerId(registroUtenti.getCurrent_user_id());
        if(utente==null) return  false;
        else {

            Carrello carrello = utente.getCarrello();

            carrello.rimuoviProdotto(prodotto.getProduct_id());



            return true;

        }
    }



    private List<String[]> convertToGui(List<RigaCarrello> righeCarrello)
    {
        List<String[]> lista=new ArrayList<>(righeCarrello.size());

        for(RigaCarrello riga :righeCarrello)
        {
            String[] prodotto=new String[]{
                    String.valueOf(riga.getProdotto().getProduct_id()),
                    PRODUCTS_IMG_PATH+String.valueOf(riga.getProdotto().getProduct_id())+".png",
                    riga.getProdotto().getNome(),
                    "$ "+String.format("%.2f",riga.getProdotto().getPrezzo()),
                    String.valueOf(riga.getQuantita()),
                    riga.getProdotto().getCategoria().toString(),
                    String.valueOf(riga.getProdotto().getSconto()),
                    "$ "+String.format("%.2f", riga.getProdotto().getPrezzo() - riga.getProdotto().getPrezzo() * riga.getProdotto().getSconto() / 100.0),
                    riga.getProdotto().getDescrizione()
                };

            lista.add(prodotto);
        }
        return lista;

    }


    public List<String[]> caricaCarrello(Carrello carrello)
    {
        List<RigaCarrello> righe = carrello.getProdotti();
        if (righe.isEmpty()) return null;
        return convertToGui(righe);
    }

    public String caricaTotale(Carrello carrello)
    {
        return "$ " + String.format("%.2f", carrello.getTotale());
    }

    private boolean verificaCredito(float totale)
    {
        return true;
    }



    public boolean effettuaOrdine(Long idUtente)
    {
        RegistroUtenti registroUtenti = new RegistroUtenti();
        RegistroProdotti registroProdotto = new RegistroProdotti();

        Utente utente=registroUtenti.cercaUtentePerId(idUtente);

        Carrello carrello = utente.getCarrello();

        List<RigaCarrello> righe = carrello.getProdotti();
        float totale=0;

        for(RigaCarrello riga :righe)
        {

            Prodotto p=registroProdotto.cercaProdottoId(riga.getProdotto().getProduct_id());
            if(p.getQuantita()>=riga.getQuantita())
            {
                totale+=riga.calcolaSubTotale();
            }else return false;
        }

        if(verificaCredito(totale))
        {
            RegistroOrdini registroOrdini = new RegistroOrdini();
            Ordine ordine=new Ordine(utente.getIndirizzo());

            for(RigaCarrello riga :righe)
            {
                ordine.addRigaOrdine(riga.getProdotto(),riga.getQuantita());
            }
            registroOrdini.registraOrdine(ordine);
            System.out.println("Ordine effettuato correttamente e salvato");
            return true;
        }else return false;
    }

}
