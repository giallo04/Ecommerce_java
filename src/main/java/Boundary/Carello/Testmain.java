package Boundary.Carello;
import  Entity.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Testmain {


    public class TestMain {
        public static void main(String[] args) {
            // 1. Impostiamo il Look & Feel del sistema operativo per rendere la grafica più moderna
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 2. Creiamo dei dati di prova (Mock Data)
            Prodotto p1 = new Prodotto("Scarpe Nike Air", "Scarpe sportive total black", 120.50f, "Calzature");
            Prodotto p2 = new Prodotto("Felpa Adidas", "Felpa con cappuccio e logo", 69.99f, "Abbigliamento");
            Prodotto p3 = new Prodotto("Calzini Tecnici", "Confezione da 3 paia anti-vesciche", 15.00f, "Accessori");

            // Creiamo il "sacchetto" di prodotti simulando il carrello
            List<InCarrello> carrelloSimulato = new ArrayList<>();
            carrelloSimulato.add(new InCarrello(p1, 1));
            carrelloSimulato.add(new InCarrello(p2, 2)); // 2 felpe
            carrelloSimulato.add(new InCarrello(p3, 5)); // 5 paia di calzini

            // 3. Inizializziamo l'interfaccia grafica del carrello
            ProdottiNelCarrello schermataCarrello = new ProdottiNelCarrello();

            // Passiamo i dati simulati al metodo che abbiamo creato per far comparire i quadratini
            schermataCarrello.mostraProdotti(carrelloSimulato);

            // 4. Creiamo il Frame (la finestra) che conterrà tutto
            JFrame frame = new JFrame("E-Commerce - Il Tuo Carrello (Test Vista)");
            frame.setContentPane(schermataCarrello.getMainPanelCarrello());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Impostiamo una dimensione di partenza per la finestra
            frame.setSize(500, 600);
            frame.setLocationRelativeTo(null); // Centra la finestra sullo schermo
            frame.setVisible(true);
        }
    }

    // =========================================================================
// CLASSI DI SUPPORTO TEMPORANEE (Rimuovile o adattale se hai già le tue)
// =========================================================================
    class Prodotto {
        private String nome;
        private String descrizione;
        private float prezzo;
        private String categoria;

        public Prodotto(String nome, String descrizione, float prezzo, String categoria) {
            this.nome = nome;
            this.descrizione = descrizione;
            this.prezzo = prezzo;
            this.categoria = categoria;
        }

        public String getNome() { return nome; }
        public String getDescrizione() { return descrizione; }
        public float getPrezzo() { return prezzo; }
        public String getCategoria() { return categoria; }
        public int getQuantita() { return 100; } // Finto magazzino
    }

    class InCarrello {
        private Prodotto prodotto;
        private int quantita;

        public InCarrello(Prodotto prodotto, int quantita) {
            this.prodotto = prodotto;
            this.quantita = quantita;
        }

        public Prodotto getProdotto() { return prodotto; }
        public int getQuantita() { return quantita; }
    }
}
