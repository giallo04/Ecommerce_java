package Entity.client;

import Entity.Ordini.Ordine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utente {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private Indirizzo indirizzo;
    private ArrayList<Ordine> ordini;
    private Carrello carrello;

    //COSTRUTTORE
    public Utente(String nome,String cognome,String email,String password, Indirizzo indirizzo)
    {
        if(nome==null){

            throw new IllegalArgumentException("\nInserisci un nome\n");
        }
        this.nome=nome;
        if(cognome==null){

            throw new IllegalArgumentException("\nInserisci un cognome\n");
        }
        this.cognome=cognome;
        if(email==null){

            throw new IllegalArgumentException("\nInserisci un email\n");
        }
        this.email=email;
        if(password==null){

            throw new IllegalArgumentException("\nInserisci una password\n");
        }
        this.password=password;

        if(indirizzo==null)
        {
            throw new IllegalArgumentException("\nInserisci un indirizzo\n");
        }
        this.indirizzo=indirizzo;

        this.ordini=new ArrayList<Ordine>();
    }


    //METODI DI GETTER
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImgPath() {
        return "users/"+email+".png";
    }

    public String getIndirizzo() {
        return indirizzo.toString();
    }

    public void ModificaProfilo(String nome,String cognome,String email,String password, Indirizzo indirizzo)//Indirizzo contenimento o associazione?TODO
    {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.indirizzo=indirizzo;

    }
//TODO add setter per tutti i campi per modificare i dati

    public List<Ordine> storicoOrdini()
    {
        return  Collections.unmodifiableList(ordini);
    }

}
