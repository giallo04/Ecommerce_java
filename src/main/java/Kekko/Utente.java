package Kekko;

import java.util.ArrayList;

public class Utente {

    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String imgPath;
    private Indirizzo indirizzo;
    private List<Ordine> ordini;
    private Carrello carrello;

    //COSTRUTTORE
    public Utente(String nome,String cognome,String email,String password,String imgPath, Indirizzo indirizzo)
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
        this.imgPath=imgPath;

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
        return imgPath;
    }

    public Indirizzo getIndirizzo() {
        return indirizzo;
    }

    public void ModificaProfilo(String nome,String cognome,String email,String password,String imgPath, Indirizzo indirizzo)
    {
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.password=password;
        this.imgPath=imgPath;
        this.indirizzo=indirizzo;

    }


    public void storicoOrdini()
    {
        for(int i=0;i<ordini.size();i++)
        {
            System.out.println(ordini.get(i).toString());
        }
    }

    public boolean effettuaOrdine()
    {}

    //FAI EFFETTUA ORDINE E LE INTERFACCE
}
