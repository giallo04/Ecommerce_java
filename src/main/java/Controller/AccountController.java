package Controller;

import Entity.client.Indirizzo;
import Entity.client.RegistroUtenti;
import Entity.client.Utente;

public class AccountController {
    private static long current_user_id=0;

    private  String msg;

    public AccountController(){}
    public  String get_msg(){return msg;}
    private void set_msg(String msg){this.msg=msg;}
    public static long get_current_user_id(){
        return current_user_id;
    }
    public void logout(){
        current_user_id=0;
    }

    public boolean register(String email, String password, String nome, String cognome,String citta, String provincia, String via, String cap,String imgPath){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerEmail(email);
        if(user!=null){
            set_msg("Email già esistente");
            return false;
        }else {
            try{
                gestoreUtenti.registraUtente(new Utente(nome,cognome,email,password,new Indirizzo(citta,provincia,via,Integer.parseInt(cap))));
                return true;
            }catch (IllegalArgumentException e){
                set_msg(e.getMessage());
            }
        }

        return false;
    }

    public boolean login(String email, String password){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerEmail(email);
        if(user==null){
            set_msg("Account inesistente");
            return false;
        }else if(Utente.hidePassword(password,email).equals(user.getPassword())){
            current_user_id=user.getUser_id();
            return true;
        }else{
            set_msg("Password errata");
            return false;
        }

    }

//TODO add photo to filesystem
    public boolean modificaProfilo(String nome, String cognome,String citta, String provincia, String via, String cap){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerId(current_user_id);
        if(user==null){
            set_msg("Account inesistente");
        }else{
            try{
                gestoreUtenti.aggiornaUtente(current_user_id,nome,cognome,user.getEmail(),user.getPassword(),new Indirizzo(citta,provincia,via,Integer.parseInt(cap)));
                return true;
            }catch (IllegalArgumentException e){
                set_msg(e.getMessage());
                return false;
            }
        }
        return false;
    }
    public boolean modificaPassword(String password, String password2){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerId(current_user_id);
        if(user==null){
            set_msg("Account inesistente");
        }else if(!Utente.hidePassword(password,user.getEmail()).equals(user.getPassword())){
            set_msg("Password errata");
        }else{
            try{
                gestoreUtenti.aggiornaUtente(current_user_id,user.getNome(),user.getCognome(),user.getEmail(),Utente.hidePassword(password2,user.getEmail()),user.getIndirizzo());
                return true;
            }catch (IllegalArgumentException e){
                set_msg(e.getMessage());
                return false;
            }
        }
        return false;
    }
}
