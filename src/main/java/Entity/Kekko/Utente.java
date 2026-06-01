package Entity.Kekko;


import java.util.*;

import Entity.Merce.Prodotto;
import Entity.Ordini.Ordine;

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return Objects.equals(nome, utente.nome) && Objects.equals(cognome, utente.cognome) && Objects.equals(email, utente.email) && Objects.equals(password, utente.password) && Objects.equals(imgPath, utente.imgPath) && Objects.equals(indirizzo, utente.indirizzo) && Objects.equals(ordini, utente.ordini) && Objects.equals(carrello, utente.carrello);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, cognome, email, password, imgPath, indirizzo, ordini, carrello);
    }


    @Override
    public String toString() {
        return "Utente{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", indirizzo=" + indirizzo +
                '}';
    }

    public boolean effettuaOrdine()
    {
       Collection<InCarrello> prodotti=carrello.getProdotti();

       for(InCarrello item: prodotti)
       {
           Prodotto p=item.getProdotto();

       }

        return true;
    }

    //FAI EFFETTUA ORDINE E LE INTERFACCE
}
