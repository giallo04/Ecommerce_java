package Controller;

import Entity.client.Indirizzo;
import Entity.client.RegistroUtenti;
import Entity.client.Utente;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AccountController {
    private static long current_user_id=0;
    private static String USER_IMG_PATH="img/users/";
    private  String msg;

    //USER DATA CONVERTION TO GUI
    public final static int NOME=0;
    public final static int COGNOME=1;
    public final static int CITTA=2;
    public final static int PROVINCIA=3;
    public final static int VIA=4;
    public final static int CAP=5;
    public final static int IMMAGINE=6;
    public final static int EMAIL=7;


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
            set_msg("Email già in uso");
            return false;
        }else {
            try{
                Utente newUser=new Utente(nome,cognome,email,password,new Indirizzo(citta,provincia,via,Integer.parseInt(cap)));
                gestoreUtenti.registraUtente(newUser);
                //Salvo l'immagine dell'utente
                Files.copy(Path.of(imgPath), Path.of(USER_IMG_PATH + newUser.getUser_id() + ".png"));
                set_msg("Registrazione effettuata con successo");
                return true;
            }catch (IllegalArgumentException e){
                set_msg(e.getMessage());
            }catch (IOException e){
                set_msg("Registrazione effettuata non è stato possibile caricare l'immagine");
                return true;
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

    public boolean modificaProfilo(String nome, String cognome,String citta, String provincia, String via, String cap,String img,String vecchiaPassword,String nuovaPassword){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerId(current_user_id);
        if(user==null){
            set_msg("Account inesistente");
        }else{
            try{
                if(!Utente.hidePassword(vecchiaPassword,user.getEmail()).equals(user.getPassword())){
                    set_msg("Password errata");
                    return false;
                }
                if(nuovaPassword.isEmpty()){nuovaPassword=vecchiaPassword;}
                gestoreUtenti.aggiornaUtente(current_user_id,nome,cognome,user.getEmail(),nuovaPassword,new Indirizzo(citta,provincia,via,Integer.parseInt(cap)));
                if(!img.isEmpty()){
                    //elimino l'immagine vecchia'
                    Files.copy(Path.of(img),Path.of(USER_IMG_PATH+current_user_id+".png"), StandardCopyOption.REPLACE_EXISTING);
                }
                return true;
            }catch (IllegalArgumentException e){
                set_msg(e.getMessage());
                return false;
            }catch (IOException e){
                set_msg("Errore durante la modifica dell'immagine profilo");
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
    public String getNumberUtenti(){
        RegistroUtenti registroUtenti=new RegistroUtenti();
        return String.valueOf(registroUtenti.conteggioUtenti());
    }


    public String[] caricaProfilo(){
        RegistroUtenti gestoreUtenti=new RegistroUtenti();
        Utente user=gestoreUtenti.cercaUtentePerId(current_user_id);
        if(user==null){
            set_msg("Account inesistente");
            return null;
        }else{
            return new String[]{user.getNome(),user.getCognome(),user.getIndirizzo().getCitta(),user.getIndirizzo().getProvincia(),user.getIndirizzo().getVia(),String.valueOf(user.getIndirizzo().getCap()),USER_IMG_PATH+user.getUser_id()+".png",user.getEmail()};
        }
    }





}
