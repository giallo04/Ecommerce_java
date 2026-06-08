package Entity.client;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String nome;
    private String cognome;
    private String email;
    private String password;

    @Embedded
    private Indirizzo indirizzo;
    @Embedded
    private  Carrello carrello=new Carrello();



    public static String hidePassword(String password, String email) {
        return String.valueOf((password + email).hashCode());
    }



    // COSTRUTTORE
    public Utente(String nome, String cognome, String email, String password, Indirizzo indirizzo) throws IllegalArgumentException {
        if (nome == null || cognome == null || email == null || indirizzo == null) {
            throw new IllegalArgumentException("Nome, cognome, email e indirizzo non possono essere nulli");
        }

        if (nome.trim().isEmpty() || cognome.trim().isEmpty() || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome, cognome e email non possono essere vuoti");
        }

        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.indirizzo = indirizzo;
        verificapassword(password);
        this.password = hidePassword(password, email);
    }

    public Utente() {
        // COSTRUTTORE VUOTO RICHIESTO DA HIBERNATE
    }

    // --- GETTER ---
    public long getUser_id() { return user_id; }
    public String getNome() { return nome; }
    public String getCognome() { return cognome; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Indirizzo getIndirizzo() { return indirizzo; }
    public Carrello getCarrello() {
        return carrello;
    }


    // --- SETTER CON VALIDAZIONE ED ECCEZIONI ---

    public void setNome(String nome) throws IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome non può essere vuoto");
        }
        this.nome = nome;
    }

    public void setCognome(String cognome) throws IllegalArgumentException {
        if (cognome == null || cognome.trim().isEmpty()) {
            throw new IllegalArgumentException("Il cognome non può essere vuoto");
        }
        this.cognome = cognome;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("L'email non può essere vuota");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("L'email deve contenere un @");
        }
        this.email = email;
        password = hidePassword(password, email);//ricalcola la password
    }

    public void setIndirizzo(Indirizzo indirizzo) throws IllegalArgumentException {
        if (indirizzo == null) {
            throw new IllegalArgumentException("L'indirizzo non può essere vuoto");
        }
        this.indirizzo = indirizzo;
    }

    public void setPassword(String password) throws IllegalArgumentException {
        verificapassword(password);
        this.password = hidePassword(password, this.email);
    }


    private void verificapassword(String password) throws IllegalArgumentException {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La password deve essere lunga almeno 8 caratteri");
        }

        boolean haMaiuscola = false;
        boolean haNumero = false;
        boolean haSpeciale = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                haMaiuscola = true;
            } else if (Character.isDigit(c)) {
                haNumero = true;
            } else if (!Character.isLetterOrDigit(c)) {
                haSpeciale = true;
            }
        }

        if (!haMaiuscola) {
            throw new IllegalArgumentException("La password deve contenere almeno una lettera maiuscola");
        }
        if (!haNumero) {
            throw new IllegalArgumentException("La password deve contenere almeno un numero");
        }
        if (!haSpeciale) {
            throw new IllegalArgumentException("La password deve contenere almeno un carattere speciale");
        }
    }

    public void modificaProfilo(String nome, String cognome, String email, String password) throws IllegalArgumentException {
        setNome(nome);
        setCognome(cognome);
        setEmail(email);
        setPassword(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utente utente = (Utente) o;
        return this.user_id == utente.user_id;
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