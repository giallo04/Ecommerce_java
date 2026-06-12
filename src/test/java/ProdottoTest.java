import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;
import org.junit.jupiter.api.*;

public class ProdottoTest {


    private final String nome= "Computer";
    private final float prezzo= 105.9f;
    private final String descrizione= "Computer per la scuola";
    private final Categoria categoria= Categoria.Elettronica;
    private final int quantita= 10;
    private Prodotto p;

    @BeforeEach
    void setUp() {
        p=new Prodotto(nome,prezzo,descrizione,quantita,categoria);
    }
    @Test
    void costruttoreTest() {
        Assertions.assertEquals(nome,p.getNome());
        Assertions.assertEquals(prezzo,p.getPrezzo());
        Assertions.assertEquals(descrizione,p.getDescrizione());
        Assertions.assertEquals(categoria,p.getCategoria());
        Assertions.assertEquals(quantita,p.getQuantita());
        Assertions.assertEquals(0,p.getSconto());
    }

    @Test
    void setNomeTest() {
        p.setNome("Telefono");
        Assertions.assertEquals("Telefono",p.getNome());
    }
    @Test
    void setNomeTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setNome(""));
    }
    @Test
    void setNomeTest3() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setNome(null));
    }
    @Test
    void setDescrizioneTest() {
        p.setDescrizione("Telefono per la scuola");
        Assertions.assertEquals("Telefono per la scuola",p.getDescrizione());
    }
    @Test
    void setDescrizioneTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setDescrizione(""));
    }
    @Test
    void incrementaQuantitaTest() {
        p.incrementQt(5);
        Assertions.assertEquals(quantita+5,p.getQuantita());
    }
    @Test
    void incrementaQuantitaTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.incrementQt(-1));
    }
    @Test
    void decrementaQuantitaTest() {
        p.decrementQt(quantita);
        Assertions.assertEquals(0,p.getQuantita());
    }
    @Test
    void decrementaQuantitaTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.decrementQt(quantita+1));
    }
    @Test
    void decrementaQuantitaTest3() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.decrementQt(-1));
    }

    @Test
    void setScontoTest() {
        p.setSconto(20);
        Assertions.assertEquals(20,p.getSconto());
    }
    @Test
    void setScontoTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setSconto(-1));
    }
    @Test
    void setScontoTest3() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setSconto(110));
    }

    @Test
    void setPrezzoTest() {
        p.setPrezzo(100);
        Assertions.assertEquals(100,p.getPrezzo());
    }
    @Test
    void setPrezzoTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setPrezzo(-1));
    }

    @Test
    void categoriaTest() {
        p.setCategoria(Categoria.Giochi);
        Assertions.assertEquals(Categoria.Giochi,p.getCategoria());
    }
    @Test
    void categoriaTest2() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->p.setCategoria(null));
    }
    @Test
    void categoriaTest3() {
        p.setCategoria(Categoria.Abbigliamento);
        Assertions.assertEquals(Categoria.Abbigliamento,p.getCategoria());
    }
    @Test
    void categoriaTest4() {
        p.setCategoria(Categoria.Musica);
        Assertions.assertEquals(Categoria.Musica,p.getCategoria());
    }
    @Test
    void categoriaTest5() {
        p.setCategoria(Categoria.Sport);
        Assertions.assertEquals(Categoria.Sport,p.getCategoria());
    }


}
