package Entity.client;

import java.util.ArrayList;

public class Ecommerce {

    //Singleton
    private ArrayList<Utente> utenti;
    private static Ecommerce instance=new Ecommerce();
    private Ecommerce(){
        utenti = new ArrayList<>();
    }


    public static Ecommerce getInstance(){
        return instance;
    }
    public Utente getUtente(String email){
        for(Utente user:utenti){
            if(user.getEmail().equals(email)) return user;
        }
        return null;
    }
    public void aggiungiUtente(Utente user){
        utenti.add(user);
    }

}
