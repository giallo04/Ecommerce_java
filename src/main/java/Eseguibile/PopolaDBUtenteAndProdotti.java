package Eseguibile;

import Database.GestorePersistenza;
import Entity.Merce.Categoria;
import Entity.Merce.Prodotto;
import Entity.Merce.RegistroProdotti;
import Entity.Ordini.Ordine;
import Entity.Ordini.RegistroOrdini;
import Entity.client.Indirizzo;
import Entity.client.RegistroUtenti;
import Entity.client.Utente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PopolaDBUtenteAndProdotti {

    public static void main(String[] args) {
        //creo un utente di prova test@email.it e Password@1
        new RegistroUtenti().registraUtente(new Utente("Mario","Rossi","test@email.it","Password@1",new Indirizzo("Milano","Milano","Via Roma","23")));
        new RegistroUtenti().registraUtente(new Utente("Paolo","Cosco","test2@email.it","Password@2",new Indirizzo("Roma","Roma","Via Cascita","2")));
        new RegistroUtenti().registraUtente(new Utente("Elisa","Roni","guest@email.it","Password@3",new Indirizzo("Napoli","Napoli","Via Petroni","11")));
        //Popolo il database con prodotti
        RegistroProdotti catalogo1 = new RegistroProdotti();

        catalogo1.aggiungiProdotto("Cmf phone 2 pro", "Il medio gamma definitivo dal design modulare e l'interfaccia Glyph che fa invidia ai top di gamma.", 249.99F, Categoria.Elettronica, 70,0);
        catalogo1.aggiungiProdotto("Air Force 1", "Le sneaker immortali. Bianche, classiche, perfette per ogni outfit.", 79.99F, Categoria.Abbigliamento, 30,0);
        catalogo1.aggiungiProdotto("Ps4", "Una vecchia gloria che resiste al tempo. Ideale per recuperare capolavori senza svaligiare una banca.", 500F, Categoria.Elettronica, 35,10);
        catalogo1.aggiungiProdotto("Xbox Series X", "Il monolito nero della potenza videoludica. Pronta a divorare le tue ore di sonno insieme al Game Pass.", 1000F, Categoria.Elettronica, 40,30);
        catalogo1.aggiungiProdotto("iPhone 17 pro", "Il flagship del futuro con scocca in titanio alleggerito e un comparto fotografico che scatta foto anche ai tuoi pensieri.", 999.99F, Categoria.Elettronica, 90,0);
        catalogo1.aggiungiProdotto("Lego DC Batman", "Diventa la notte (e un ingegnere civile) costruendo l'iconica Batcaverna mattoncino dopo mattoncino.", 30, Categoria.Giochi, 10,0);
        catalogo1.aggiungiProdotto("Monopoly", "Il gioco da tavolo definitivo per distruggere amicizie storiche e litigare pesantemente con i parenti a Natale.", 40, Categoria.Giochi, 15,0);
        catalogo1.aggiungiProdotto("Pallone da basket", "Perfetto per fare tre passi, saltare sul cemento e sognare la NBA (o per finire dritto sul balcone dei vicini).", 19.99F, Categoria.Sport, 30,15);
        catalogo1.aggiungiProdotto("Maglia del Napoli", "Maglia azzurra ufficiale. Passione viscerale stampata su tessuto. 'Un giorno all'improvviso mi innamorai di te, batte il mio cuore non chiedermi il perché...'", 99F, Categoria.Abbigliamento, 100,0);
        catalogo1.aggiungiProdotto("Bicicletta", "Due ruote, zero emissioni e la promessa solenne di rimettersi in forma, che svanirà puntualmente alla prima salita.", 150, Categoria.Sport, 20,0);
        catalogo1.aggiungiProdotto("Tesi di laurea", "Un capolavoro accademico dal valore inestimabile, redatto all'ombra della meravigliosa Piazza d'Italia a Sassari, tra il profumo della favata e il fascino senza tempo della Sardegna. Vale ogni singolo centesimo.", 999999, Categoria.Giochi, 10,0);
        catalogo1.aggiungiProdotto("Love Songs", "Una raccolta di tracce strappalacrime per i momenti romantici o per quando vuoi fissare il vuoto ripensando al passato.", 10, Categoria.Musica, 30,0);
        catalogo1.aggiungiProdotto("Star boy", "L'album capolavoro di The Weeknd che ti farà sentire il re del mondo mentre guidi di notte in mezzo al traffico.", 60, Categoria.Musica, 30,5);

        RegistroUtenti registroUtenti = new RegistroUtenti();
        RegistroOrdini registroOrdini = new RegistroOrdini();
        RegistroProdotti registroProdotti = new RegistroProdotti();
        GestorePersistenza gestore = new GestorePersistenza();

        // Carica prodotti esistenti dal DB
        List<Prodotto> catalogo = registroProdotti.caricaCatalogo();
        if (catalogo == null || catalogo.isEmpty()) {
            System.out.println("Nessun prodotto nel catalogo");
            return;
        }
        List<Utente> users=gestore.cercaPerCampi(Utente.class, Collections.emptyMap());
        ArrayList<Long> users_id=new ArrayList<>();
        for(Utente u:users){
            users_id.add(u.getUser_id());
        }
        Random r= Random.from(new Random(32423));
        List<Indirizzo> indirizzi=new ArrayList<>();
        indirizzi.add(new Indirizzo("Milano","Milano","Via Roma","23"));
        indirizzi.add(new Indirizzo("Napoli","Napoli","Via Cascita","2"));
        indirizzi.add(new Indirizzo("Firenze","Firenze","Via Cinzia","10"));
        for (Long user_id : users_id) {
            int nOfOrder = r.nextInt(1, 6);

            Ordine order = new Ordine(indirizzi.get(r.nextInt(indirizzi.size())), user_id);

            for (int i = 0; i < nOfOrder; i++) {
                Prodotto prodotto = catalogo.get(r.nextInt(catalogo.size()));
                int quantita = r.nextInt(1, 11); // da 1 a 10
                order.addRigaOrdine(prodotto, quantita);
            }

            registroOrdini.registraOrdine(order);
        }
    }

    }


