package Entity.client;

import Database.GestorePersistenza;
import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;

import java.util.List;
import java.util.Map;

public class RegistroUtenti {

    private Long current_user_id;
    private GestorePersistenza gestorePersistenza;

    public RegistroUtenti()
    {
        this.gestorePersistenza=new GestorePersistenza();
    }

    public Utente cercaUtentePerId(long idUtente)
    {
        return gestorePersistenza.trovaPerId(Utente.class,idUtente);
    }

    public boolean registraUtente(Utente utente)
    {
        return gestorePersistenza.salva(utente);
    }

    public void aggiornaUtente(long user_id,String nome,String cognome,String email,String password,Indirizzo indirizzo) throws IllegalArgumentException{
        Utente utente=cercaUtentePerId(user_id);
        if(utente==null){
            throw new IllegalArgumentException("Utente "+user_id+" non esistente nel database");
        }else {
            Utente utenteEsistente=cercaUtentePerEmail(email);
            if(utenteEsistente==null||utenteEsistente.getUser_id()==user_id) {
            utente.modificaProfilo(nome,cognome,email,password);
            utente.setIndirizzo(indirizzo);
            gestorePersistenza.aggiorna(utente);
            }else{
                throw new IllegalArgumentException("Esiste già un prodotto con il nome "+nome+" nel catalogo");
            }
        }
    }
    public Utente cercaUtentePerEmail(String email)
    {
        return (Utente) gestorePersistenza.cercaPrimoPerCampi(Utente.class,Map.of("email",email));
    }

    public int conteggioUtenti(){
        GestorePersistenza gestore=new GestorePersistenza();
        List<Utente> user=gestore.cercaPerCampi(Utente.class, Map.of());
        return user.size();
    }

    public Long getCurrent_user_id() {return current_user_id;}
}
