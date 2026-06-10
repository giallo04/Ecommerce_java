package Entity.Ordini;

public enum StatoOrdine {
    INSERITO(1, false),
    IN_PREPARAZIONE(2, false),
    SPEDITO(3, false),
    CONSEGNATO(4, true),
    ANNULLATO(5, true);

    private final int livello;
    private final boolean terminale;

    StatoOrdine(int livello, boolean terminale){
        this.livello = livello;
        this.terminale = terminale;
    }

    public boolean isTerminale() {
        return terminale;
    }
}
