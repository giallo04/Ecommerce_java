package Entity.client;

import Database.GestorePersistenza;

import java.util.Map;

public class RegistroUtenti {

    private GestorePersistenza gestorePersistenza;
    private Long current_user_id;
    //private Long carrello_id;

    public RegistroUtenti(Long current_user_id)
    {
        this.current_user_id=current_user_id;
        this.gestorePersistenza=new GestorePersistenza();
    }

    public Utente cercaUtentePerId(Long idUtente)
    {
        return gestorePersistenza.trovaPerId(Utente.class,idUtente);
    }

    public boolean salvaUtente(Utente utente)
    {
        return gestorePersistenza.salva(utente);
    }

    public Utente cercaUtentePerEmail(String email)
    {
        return (Utente) gestorePersistenza.cercaPerCampo(Utente.class, "email", (email));//RESTITUISCE LISTA UTENTI CON STESSA EMAIL
    }
}
