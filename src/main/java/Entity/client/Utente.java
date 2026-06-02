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
    private String imgPath;
    private ArrayList<Ordine> ordini;
    private Carrello carrello;

    //COSTRUTTORE
    public Utente(String nome,String cognome,String email,String password, String imgPath, String citta, String provincia,String via,int cap) throws IllegalArgumentException
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


        this.indirizzo=new Indirizzo(citta,provincia,via,cap);

        this.imgPath=imgPath;

        this.ordini=new ArrayList<Ordine>();
        this.carrello=new Carrello();
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
        return "users/"+imgPath+".png";
    }

    public String getIndirizzo() {
        return indirizzo.toString();
    }

    public void ModificaProfilo(String nome,String cognome,String email,String password, String citta, String provincia,String via,int cap)
    {
        if(nome!=null){

            this.nome=nome;
        }

        if(cognome!=null){

            this.cognome=cognome;
        }

        if(email!=null)
        {
            this.email=email;
        }

        if(password!=null)
        {
            this.password = password;
        }

        if(citta!=null && via!=null&& provincia!=null) {
            indirizzo.setCap(cap);
            indirizzo.setProvincia(provincia);
            indirizzo.setVia(via);
            indirizzo.setCitta(citta);
        }

    }


    public List<Ordine> storicoOrdini()
    {
        return  Collections.unmodifiableList(ordini);
    }



    //TODO effettuaOrdine

}
