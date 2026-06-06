package Entity.client;

import Database.GestorePersistenza;
import Entity.Ordini.Ordine;
import jakarta.persistence.*;

import java.util.*;

@Entity
public class Utente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String  nome;
    private String  cognome;
    private String  email;
    private String  password;
    @Embedded
    private Indirizzo indirizzo;






    public Utente(String nome, String cognome, String email, String password, Indirizzo indirizzo)throws IllegalArgumentException
    {
        if(nome==null && cognome==null && email==null &&indirizzo==null) {
            throw  new IllegalArgumentException("\nNome, cognome, email e indirizzo devono essere validi\n");
        } else{

            this.nome = nome;
            this.cognome = cognome;
            this.email = email;
            this.indirizzo = indirizzo;
            }

        if(!verficapassword(password)) {
            throw  new IllegalArgumentException();
        } else {
            this.password = password;
        }


    }

    public Utente() {
        //COSTRUTTORE VUOTO RICHIESTO DA HIBERNATE
    }


    public String getNome() {return nome;}

    public String getCognome() {return cognome;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public Indirizzo getIndirizzo() {return indirizzo;}

    public Long getUser_id() {return user_id;}



    private boolean verficapassword(String password)
    {
        if (password == null || password.length() < 8)
        {
            return false;
        }

        boolean haMaiuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;

        for (char c : password.toCharArray())
        {
            if (Character.isUpperCase(c)) {
                haMaiuscola = true;
            }
            else if (Character.isDigit(c)) {
                haNumero = true;
            }
            else if (!Character.isLetterOrDigit(c)) {
                haSpeciale = true;
            }
        }

        return haMaiuscola && haNumero && haSpeciale;
    }


    public void modificaProfilo(String nome, String cognome, String email, String password)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        if(verficapassword(password)) {
            this.password = password;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return this.user_id==utente.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }

    @Override
    public String toString() {
        return "Utente{" +
                "User_id=" + user_id +
                ", Nome='" + nome + '\'' +
                ", Cognome='" + cognome + '\'' +
                ", Email='" + email + '\'' +
                ", indirizzo=" + indirizzo +
                '}';
    }
}
