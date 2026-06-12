import Entity.Merce.Prodotto;
import Entity.client.Indirizzo;
import Entity.client.Utente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//Test suite for Utente class made by Francesco D'auria
class UtenteTest {


    private static final String NOME_VALIDO     = "Mario";
    private static final String COGNOME_VALIDO  = "Rossi";
    private static final String EMAIL_VALIDA    = "test@email.it";
    private static final String PASSWORD_VALIDA = "Password@1!";


    private Indirizzo indirizzoValido;

    private Utente utente;
    @BeforeEach
    void setUp() {

        indirizzoValido = new Indirizzo();
        utente=creaUtenteValido();
    }

    private Utente creaUtenteValido() {
        return new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                PASSWORD_VALIDA, indirizzoValido);
    }


    // 1. COSTRUTTORE





    @Test
    void nomeNull_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(null, COGNOME_VALIDO, EMAIL_VALIDA,
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void cognomeNull_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, null, EMAIL_VALIDA,
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void emailNull_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, null,
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void indirizzoNull_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                        PASSWORD_VALIDA, null));
    }

    @Test
    void nomeVuoto_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente("   ", COGNOME_VALIDO, EMAIL_VALIDA,
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void cognomeVuoto_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, "  ", EMAIL_VALIDA,
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void emailVuota_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, "  ",
                        PASSWORD_VALIDA, indirizzoValido));
    }

    @Test
    void passwordTroppoCorta_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                        "Abc1!", indirizzoValido));
    }

    @Test
    void passwordSenzaMaiuscola_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                        "password@1!", indirizzoValido));
    }

    @Test
    void passwordSenzaNumero_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                        "Password@!", indirizzoValido));
    }

    @Test
    void passwordSenzaSpeciale_test() {
        assertThrows(IllegalArgumentException.class, () ->
                new Utente(NOME_VALIDO, COGNOME_VALIDO, EMAIL_VALIDA,
                        "Password1", indirizzoValido));
    }

    @Test
    void passwordVieneSalvataHashata_test() {
        Utente u = creaUtenteValido();
        assertNotEquals(PASSWORD_VALIDA, u.getPassword());
    }

    @Test
    void carrelloInizializzato_test() {
        Utente u = creaUtenteValido();
        assertNotNull(u.getCarrello());
    }

    @Test
    void indirizzoInizializzato_test() {
        Utente u = creaUtenteValido();
        assertNotNull(u.getIndirizzo());
    }



    // 2. GETTER

    @Test
    void getNome_test() {
        assertEquals(NOME_VALIDO, utente.getNome());
    }

    @Test
    void getCognome_test() {
        assertEquals(COGNOME_VALIDO, utente.getCognome());
    }

    @Test
    void getEmail_test() {
        assertEquals(EMAIL_VALIDA, utente.getEmail());
    }

    @Test
    void getIndirizzo_test() {
        assertEquals(indirizzoValido, utente.getIndirizzo());
    }



    // 3. SETTER



    @Test
    void setNomeValido_test() {
        utente.setNome("Luigi");
        assertEquals("Luigi", utente.getNome());
    }

    @Test
    void setNomeNull_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setNome(null));
    }

    @Test
    void setNomeVuoto_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setNome(""));
    }

    // --- setCognome ---
    @Test
    void setCognomeValido_test() {
        utente.setCognome("Verdi");
        assertEquals("Verdi", utente.getCognome());
    }

    @Test
    void setCognomeNull_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setCognome(null));
    }

    @Test
    void setCognomeVuoto_test() {assertThrows(IllegalArgumentException.class, () -> utente.setCognome(" "));}
    // --- setEmail ---
    @Test
    void setEmailValida_test() {
        utente.setEmail("nuova@email.it");
        assertEquals("nuova@email.it", utente.getEmail());
    }

    @Test
    void setEmailNull_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setEmail(null));
    }

    @Test

    void setEmailSenzaChiocciola_test() {
        assertThrows(IllegalArgumentException.class,
                () -> utente.setEmail("emailsenzachiocciola.com"));
    }

    @Test
    void setEmailRicalcolaPassword_test() {
        String passwordPrima = utente.getPassword();
        utente.setEmail("altra@email.it");
        // Con email diversa l'hash deve cambiare
        assertNotEquals(passwordPrima, utente.getPassword());
    }

    @Test
    void setEmailVuota_test() {

        assertThrows(IllegalArgumentException.class,
                () -> utente.setEmail(" "));
    }


    // --- setPassword ---
    @Test
    void setPasswordValida_test() {
        assertDoesNotThrow( () -> utente.setPassword("Password@1"));
    }



    @Test
    void setPasswordNull_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setPassword(null));
    }

    @Test
    void setPasswordCorta_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setPassword("Ab1!"));
    }

    @Test
    void setPasswordSenzaMaiuscola_test() {
        assertThrows(IllegalArgumentException.class,
                () -> utente.setPassword("password@1"));
    }

    @Test
    void setPasswordSenzaNumero_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setPassword("Password@"));
    }

    @Test
    void setPasswordSenzaSpeciale_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setPassword("Password1"));
    }





    // --- setIndirizzo ---
    @Test
    void setIndirizzoValido_test() {
        Indirizzo nuovo = new Indirizzo();
        utente.setIndirizzo(nuovo);
        assertEquals(nuovo, utente.getIndirizzo());
    }

    @Test
    void setIndirizzoNull_test() {
        assertThrows(IllegalArgumentException.class, () -> utente.setIndirizzo(null));
    }



    // 4. hidePassword (metodo statico)



    @Test
    void stessaEmail_test() {
        String h1 = Utente.hidePassword("Password@1", "test@test.com");
        String h2 = Utente.hidePassword("Password@1", "test@test.com");
        assertEquals(h1, h2);
    }

    @Test
    void emailDiversaHashDiverso_test() {
        String h1 = Utente.hidePassword("Password@1", "a@test.com");
        String h2 = Utente.hidePassword("Password@1", "b@test.com");
        assertNotEquals(h1, h2);
    }

    @Test
    void passwordDiversaHashDiverso_test() {
        String h1 = Utente.hidePassword("Password1!", "test@test.com");
        String h2 = Utente.hidePassword("Password2!", "test@test.com");
        assertNotEquals(h1, h2);
    }

    @Test
    void nonNull_test() {
        assertNotNull(Utente.hidePassword("Password1!", "email@test.it"));
    }



    // 5. modificaProfilo






    @Test
    void modificaValida_test() {
        utente.modificaProfilo("Luca", "Bianchi", "luca@bianchi.it", "Nuova1!Pass");
        assertEquals("Luca",           utente.getNome());
        assertEquals("Bianchi",        utente.getCognome());
        assertEquals("luca@bianchi.it",utente.getEmail());
    }

    @Test
    void modificaProfilo_EmailEPasswordInvertiti_test() {//nel caso in cui nel metodo si invertisse setEmail e setPassword
        utente.modificaProfilo("Luca", "Bianchi", "nuova@email.it", "Nuova1!Pass");
        String hashAtteso = Utente.hidePassword("Nuova1!Pass", "nuova@email.it");
        assertEquals(hashAtteso, utente.getPassword());
    }





    // 6. equals e hashCode



    @Test
    void equalsStessoOggetto_test() {
        Utente u = creaUtenteValido();
        assertEquals(u, u);
    }

    @Test
    void equalsNull_test() {
        Utente u = creaUtenteValido();
        Utente u1 = null;
        assertFalse(u.equals(u1));
    }

    @Test
    void equalsClass_test() {
        Utente u = creaUtenteValido();
        Prodotto prodotto = new Prodotto();
        assertFalse(u.equals(prodotto));}

    @Test
    void equalsPerId_test() {
        // Entrambi creati con costruttore vuoto: user_id = 0 (default long)
        Utente u1 = new Utente();
        Utente u2 = new Utente();
        assertEquals(u1, u2);
    }

    @Test
    void hashCodeCoerente_test() {
        Utente u1 = new Utente();
        Utente u2 = new Utente();
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void equalsAltroTipo_test() {
        Utente u = creaUtenteValido();
        assertNotEquals("stringa", u);
    }



    // 7. verificaCredito


    @Test
    void verificaCredito_test() {
        Utente u = creaUtenteValido();
        assertTrue(u.verificaCredito(100.0f));
        assertTrue(u.verificaCredito(0.0f));
        assertFalse(u.verificaCredito(-1f));
    }

}