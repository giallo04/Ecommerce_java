
import Entity.Merce.Prodotto;
import Entity.Merce.Categoria;
import Entity.Ordini.*;
import Entity.client.Indirizzo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdineTest {

    private Ordine o;
    private Prodotto p;

    @BeforeEach
    void setUp() throws Exception {
        Indirizzo indirizzo = new Indirizzo("Milano", "MI", "Via Roma 1", "20100");
        o = new Ordine(indirizzo, 1L);
        p = new Prodotto("Laptop", 999.99f, "Laptop gaming", 10, Categoria.Elettronica);
    }

    @Test
    void getData_restituisce_dataOggi() {
        Assertions.assertEquals(java.time.LocalDate.now(), o.getData());
    }

    @Test
    void getStatoOrdine_statoIniziale_INSERITO() {
        Assertions.assertEquals(StatoOrdine.INSERITO, o.getStatoOrdine());
    }

    @Test
    void getStatoOrdine_dopoModifica() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        Assertions.assertEquals(StatoOrdine.IN_PREPARAZIONE, o.getStatoOrdine());
    }

    @Test
    void getInOrdine_senzaProdotti_listaVuota() {
        Assertions.assertTrue(o.getInOrdine().isEmpty());
    }

    @Test
    void getInOrdine_conUnProdotto_listaConUnElemento() {
        o.addRigaOrdine(p, 2);
        Assertions.assertEquals(1, o.getInOrdine().size());
    }

    @Test
    void getInOrdine() {
        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> o.getInOrdine().add(new RigaOrdine(p, 1)));
    }

    @Test
    void calcolaTotaleOrdine_senzaProdotti() {
        Assertions.assertEquals(0f, o.calcolaTotaleOrdine());
    }

    @Test
    void calcolaTotaleOrdine_conUnProdotto() {
        o.addRigaOrdine(p, 3);
        Assertions.assertEquals(p.getPrezzo()*3, o.calcolaTotaleOrdine(), 0.01f);
    }

    @Test
    void tc01_statoTerminale_lancia_IllegalStateException() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        o.setStatoOrdine(StatoOrdine.CONSEGNATO);
        Assertions.assertThrows(IllegalStateException.class,
                () -> o.setStatoOrdine(StatoOrdine.SPEDITO));
    }

    @Test
    void tc01_messaggio_IllegalStateException() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        o.setStatoOrdine(StatoOrdine.CONSEGNATO);
        IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class,
                () -> o.setStatoOrdine(StatoOrdine.SPEDITO));
        Assertions.assertEquals("L'ordine è in uno stato terminale e non puo essere modificato", ex.getMessage());
    }


    @Test
    void tc02_nuovoStatoAntecedente_lancia_IllegalArgumentException() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE));
    }

    @Test
    void tc02_messaggio_IllegalArgumentException() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        IllegalArgumentException ex = Assertions.assertThrows(IllegalArgumentException.class,
                () -> o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE));
        Assertions.assertEquals("Lo stato inserito non è valido", ex.getMessage());
    }


    @Test
    void tc03_statoUguale_nessunaEccezione() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        Assertions.assertDoesNotThrow(() -> o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE));
    }

    @Test
    void tc03_statoUguale_statoNonCambia() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        Assertions.assertEquals(StatoOrdine.IN_PREPARAZIONE, o.getStatoOrdine());
    }


    @Test
    void tc04_nuovoStatoValido_aggiornaStato() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        o.setStatoOrdine(StatoOrdine.CONSEGNATO);
        Assertions.assertEquals(StatoOrdine.CONSEGNATO, o.getStatoOrdine());
    }


    @Test
    void tc05_annullato_aggiornaStato() {
        o.addRigaOrdine(p, 3);
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        o.setStatoOrdine(StatoOrdine.ANNULLATO);
        Assertions.assertEquals(StatoOrdine.ANNULLATO, o.getStatoOrdine());
    }

    @Test
    void tc05_annullato_ripristinaQuantitaProdotto() {
        int quantitaIniziale = p.getQuantita(); //
        o.addRigaOrdine(p, 3);
        int quantifaFinale = p.getQuantita() + 3;
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        o.setStatoOrdine(StatoOrdine.SPEDITO);
        o.setStatoOrdine(StatoOrdine.ANNULLATO);
        Assertions.assertEquals(quantifaFinale, p.getQuantita());
    }


    @Test
    void bb01_statoSuccessivo_modificatoCorrettamente() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        Assertions.assertEquals(StatoOrdine.IN_PREPARAZIONE, o.getStatoOrdine());
    }

    @Test
    void bb02_statoAntecedente_lancia_eccezione() {
        o.setStatoOrdine(StatoOrdine.IN_PREPARAZIONE);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> o.setStatoOrdine(StatoOrdine.INSERITO));
    }


    @Test
    void annullato_terminale_nonModificabile() {
        o.setStatoOrdine(StatoOrdine.ANNULLATO);
        Assertions.assertThrows(IllegalStateException.class,
                () -> o.setStatoOrdine(StatoOrdine.SPEDITO));
    }
}
