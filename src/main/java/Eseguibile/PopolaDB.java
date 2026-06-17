package Eseguibile;

import Database.GestorePersistenza;
import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;
import Entity.Ordini.Ordine;
import Entity.Ordini.RegistroOrdini;
import Entity.client.Indirizzo;
import Entity.client.RegistroUtenti;
import Entity.client.Utente;
import Entity.Merce.Categoria;

import java.util.*;

public class PopolaDB {

    public static void main(String[] args) {

        // ── Mappa province → città disponibili ──────────────────────────────
        Map<String, String[]> citta = new LinkedHashMap<>();
        citta.put("Napoli",  new String[]{"Napoli", "Pozzuoli", "Giugliano in Campania",
                "Torre del Greco", "Casoria", "Castellammare di Stabia", "Afragola", "Pompei"});
        citta.put("Milano",  new String[]{"Milano", "Sesto San Giovanni", "Cinisello Balsamo",
                "Legnano", "Rho", "Cologno Monzese", "Paderno Dugnano"});
        citta.put("Firenze", new String[]{"Firenze", "Scandicci", "Sesto Fiorentino",
                "Empoli", "Campi Bisenzio"});

        List<String> province = new ArrayList<>(citta.keySet());

        // ── Nomi/cognomi di base per generare utenti realistici ──────────────
        String[] nomi     = {"Mario", "Luca", "Giulia", "Sara", "Francesco",
                "Elena", "Andrea", "Chiara", "Davide", "Marta",
                "Roberto", "Federica"};
        String[] cognomi  = {"Rossi", "Ferrari", "Esposito", "Romano", "Colombo",
                "Ricci", "Marino", "Greco", "Bruno", "Conti",
                "De Luca", "Mancini"};
        String[] vie      = {"Via Roma", "Via Garibaldi", "Corso Italia", "Via Mazzini",
                "Via Verdi", "Piazza Dante", "Via Nazionale", "Via Po",
                "Via Leopardi", "Corso Vittorio Emanuele"};

        // ── Registrazione utenti ─────────────────────────────────────────────
        RegistroUtenti registroUtenti = new RegistroUtenti();

        registroUtenti.registraUtente(new Utente("Mario",  "Rossi",  "test@email.it",
                "Password@1", new Indirizzo("Milano",  "Milano",             "Via Roma",    "23")));
        registroUtenti.registraUtente(new Utente("Paolo",  "Cosco",  "test2@email.it",
                "Password@2", new Indirizzo("Napoli",  "Napoli",             "Via Cascita", "2")));
        registroUtenti.registraUtente(new Utente("Elisa",  "Roni",   "guest@email.it",
                "Password@3", new Indirizzo("Napoli",  "Napoli",             "Via Petroni", "11")));

        // Utenti generati proceduralmente
        Random rUsers = new Random(99887);
        for (int i = 0; i < 9; i++) {
            String nome     = nomi   [i % nomi.length];
            String cognome  = cognomi[i % cognomi.length];
            String email    = nome.toLowerCase() + "." + cognome.toLowerCase()
                    .replace(" ", "") + i + "@email.it";
            String password = "Pass@" + (1000 + i);
            String via      = vie[rUsers.nextInt(vie.length)];
            String civico   = String.valueOf(rUsers.nextInt(1, 200));
            String provincia = province.get(rUsers.nextInt(province.size()));
            String[] comuniDisponibili = citta.get(provincia);
            String comune = comuniDisponibili[rUsers.nextInt(comuniDisponibili.length)];

            registroUtenti.registraUtente(
                    new Utente(nome, cognome, email, password,
                            new Indirizzo(provincia, comune, via, civico)));
        }

        // ── Catalogo prodotti  ────────────────────────────────────
        RegistroProdotti catalogo = new RegistroProdotti();

        catalogo.aggiungiProdotto("Cmf phone 2 pro", "Il medio gamma definitivo dal design modulare e l'interfaccia Glyph che fa invidia ai top di gamma.", 249.99F, Categoria.Elettronica, 70, 0);
        catalogo.aggiungiProdotto("Air Force 1", "Le sneaker immortali. Bianche, classiche, perfette per ogni outfit.", 79.99F, Categoria.Abbigliamento, 30, 0);
        catalogo.aggiungiProdotto("Ps4", "Una vecchia gloria che resiste al tempo. Ideale per recuperare capolavori senza svaligiare una banca.", 500F, Categoria.Elettronica, 35, 10);
        catalogo.aggiungiProdotto("Xbox Series X", "Il monolito nero della potenza videoludica. Pronta a divorare le tue ore di sonno insieme al Game Pass.", 1000F, Categoria.Elettronica, 40, 30);
        catalogo.aggiungiProdotto("iPhone 17 pro", "Il flagship del futuro con scocca in titanio alleggerito e un comparto fotografico che scatta foto anche ai tuoi pensieri.", 999.99F, Categoria.Elettronica, 90, 0);
        catalogo.aggiungiProdotto("Lego DC Batman", "Diventa la notte  costruendo l'iconica Batcaverna mattoncino dopo mattoncino.", 30, Categoria.Giochi, 10, 0);
        catalogo.aggiungiProdotto("Monopoly", "Il gioco da tavolo definitivo per distruggere amicizie storiche e litigare pesantemente con i parenti a Natale.", 40, Categoria.Giochi, 15, 0);
        catalogo.aggiungiProdotto("Pallone da basket", "Perfetto per fare tre passi, saltare sul cemento e sognare la NBA .", 19.99F, Categoria.Sport, 30, 15);
        catalogo.aggiungiProdotto("Maglia del Napoli", "Maglia azzurra ufficiale. Passione viscerale stampata su tessuto. 'Un giorno all'improvviso mi innamorai di te, batte il mio cuore non chiedermi il perché...'", 99F, Categoria.Abbigliamento, 100, 0);
        catalogo.aggiungiProdotto("Bicicletta", "Due ruote, zero emissioni e la promessa solenne di rimettersi in forma, che svanirà puntualmente alla prima salita.", 150, Categoria.Sport, 20, 0);
        catalogo.aggiungiProdotto("Tesi di laurea", "Un capolavoro accademico dal valore inestimabile, redatto all'ombra della meravigliosa Piazza d'Italia a Sassari, tra il profumo della favata e il fascino senza tempo della Sardegna. Vale ogni singolo centesimo.", 999, Categoria.Giochi, 10, 0);
        catalogo.aggiungiProdotto("Love Songs", "Una raccolta di tracce strappalacrime per i momenti romantici o per quando vuoi fissare il vuoto ripensando al passato.", 10, Categoria.Musica, 30, 0);
        catalogo.aggiungiProdotto("Star boy", "L'album capolavoro di The Weeknd che ti farà sentire il re del mondo mentre guidi di notte in mezzo al traffico.", 60, Categoria.Musica, 30, 5);

        // ── Generazione ordini ───────────────────────────────────────────────
        RegistroOrdini registroOrdini = new RegistroOrdini();
        RegistroProdotti registroProdotti = new RegistroProdotti();
        GestorePersistenza gestore = new GestorePersistenza();

        List<Prodotto> prodotti = registroProdotti.caricaCatalogo();
        if (prodotti == null || prodotti.isEmpty()) {
            System.out.println("Nessun prodotto nel catalogo");
            return;
        }

        List<Utente> users = gestore.cercaPerCampi(Utente.class, Collections.emptyMap());

        Random r = new Random(32423);

        for (Utente utente : users) {
            // Indirizzo di spedizione: provincia casuale → città casuale tra quelle della provincia
            String provincia = province.get(r.nextInt(province.size()));
            String[] comuniDisponibili = citta.get(provincia);
            String comune  = comuniDisponibili[r.nextInt(comuniDisponibili.length)];
            String via     = vie[r.nextInt(vie.length)];
            String civico  = String.valueOf(r.nextInt(1, 200));
            Indirizzo indirizzoSpedizione = new Indirizzo(provincia, comune, via, civico);

            int nRighe = r.nextInt(1, 6);
            Ordine ordine = new Ordine(indirizzoSpedizione, utente.getUser_id());

            List<Prodotto> catalogoMescolato = new ArrayList<>(prodotti);
            Collections.shuffle(catalogoMescolato, r);
            int righeEffettive = Math.min(nRighe, catalogoMescolato.size());

            for (int i = 0; i < righeEffettive; i++) {
                int quantita = r.nextInt(1, 11);
                ordine.addRigaOrdine(catalogoMescolato.get(i), quantita);
            }

            registroOrdini.registraOrdine(ordine);

        }
        //Aggiungo dei prodotti non disponibili (qt=0)
        catalogo.aggiungiProdotto("Masterpiece Volume 2", "Questo Volume è dedicato al fumettista italiano Silver e al suo personaggio piú famoso, Lupo Alberto.", 59.0f, Categoria.Giochi, 0, 10);
        catalogo.aggiungiProdotto("Nothing", "Il nulla più assoluto", 15.5f, Categoria.Giochi, 0, 0);
        catalogo.aggiungiProdotto("Sony Ultra Bass", "Cuffie con bassi pazzeschi", 89.9f, Categoria.Elettronica, 0, 20);

        System.out.println("Database popolato con utenti, prodotti e ordini");
    }
}
