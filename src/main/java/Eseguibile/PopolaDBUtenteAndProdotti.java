package Eseguibile;

import Entity.Merce.Categoria;
import Entity.Merce.RegistroProdotti;
import Entity.client.Indirizzo;
import Entity.client.RegistroUtenti;
import Entity.client.Utente;

public class PopolaDBUtenteAndProdotti {

    public static void main(String[] args) {
        //creo un utente di prova test@email.it e Password@1
        new RegistroUtenti().registraUtente(new Utente("Mario","Rossi","test@email.it","Password@1",new Indirizzo("Milano","Milano","Via Roma",23)));

        //Popolo il database con prodotti
        RegistroProdotti catalogo = new RegistroProdotti();

        catalogo.aggiungiProdotto("Cmf phone 2 pro", "Il medio gamma definitivo dal design modulare e l'interfaccia Glyph che fa invidia ai top di gamma.", 249.99F, Categoria.Elettronica, 7);
        catalogo.aggiungiProdotto("Air Force 1", "Le sneaker immortali. Bianche, classiche, perfette per ogni outfit.", 79.99F, Categoria.Abbigliamento, 3);
        catalogo.aggiungiProdotto("Ps4", "Una vecchia gloria che resiste al tempo. Ideale per recuperare capolavori senza svaligiare una banca.", 500F, Categoria.Elettronica, 1);
        catalogo.aggiungiProdotto("Xbox Series X", "Il monolito nero della potenza videoludica. Pronta a divorare le tue ore di sonno insieme al Game Pass.", 1000F, Categoria.Elettronica, 1);
        catalogo.aggiungiProdotto("iPhone 17 pro", "Il flagship del futuro con scocca in titanio alleggerito e un comparto fotografico che scatta foto anche ai tuoi pensieri.", 999.99F, Categoria.Elettronica, 90);
        catalogo.aggiungiProdotto("Lego DC Batman", "Diventa la notte (e un ingegnere civile) costruendo l'iconica Batcaverna mattoncino dopo mattoncino.", 30, Categoria.Giochi, 10);
        catalogo.aggiungiProdotto("Monopoly", "Il gioco da tavolo definitivo per distruggere amicizie storiche e litigare pesantemente con i parenti a Natale.", 40, Categoria.Giochi, 10);
        catalogo.aggiungiProdotto("Pallone da basket", "Perfetto per fare tre passi, saltare sul cemento e sognare la NBA (o per finire dritto sul balcone dei vicini).", 19.99F, Categoria.Sport, 30);
        catalogo.aggiungiProdotto("Maglia del Napoli", "Maglia azzurra ufficiale. Passione viscerale stampata su tessuto. 'Un giorno all'improvviso mi innamorai di te, batte il mio cuore non chiedermi il perché...'", 99F, Categoria.Abbigliamento, 100);
        catalogo.aggiungiProdotto("Bicicletta", "Due ruote, zero emissioni e la promessa solenne di rimettersi in forma, che svanirà puntualmente alla prima salita.", 150, Categoria.Sport, 3);
        catalogo.aggiungiProdotto("Tesi di laurea", "Un capolavoro accademico dal valore inestimabile, redatto all'ombra della meravigliosa Piazza d'Italia a Sassari, tra il profumo della favata e il fascino senza tempo della Sardegna. Vale ogni singolo centesimo.", 999999, Categoria.Giochi, 10);
        catalogo.aggiungiProdotto("Love Songs", "Una raccolta di tracce strappalacrime per i momenti romantici o per quando vuoi fissare il vuoto ripensando al passato.", 10, Categoria.Musica, 1);
        catalogo.aggiungiProdotto("Star boy", "L'album capolavoro di The Weeknd che ti farà sentire il re del mondo mentre guidi di notte in mezzo al traffico.", 60, Categoria.Musica, 10);
    }
}