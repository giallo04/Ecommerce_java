package Entity.client;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Embeddable
public class Indirizzo {


    private Long id;
    private String citta;
    private String provincia;
    private String via;
    private int cap;



    public Indirizzo(String citta, String provincia,String via,int cap)throws IllegalArgumentException
    {
        if(citta==null && provincia==null && via==null && cap==0)
        { throw new IllegalArgumentException("\nInserire citta', provincia, via e cap validi!\n");
        } else {
            this.citta = citta;
            this.provincia = provincia;
            this.via = via;
            this.cap = cap;
        }
    }

    public Indirizzo() {
        //COSTRUTTORE VUOTO RICHIESTO DA HIBERNATE
    }




    public String getCitta() {
        return citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getVia() {
        return via;
    }

    public int getCap() {
        return cap;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    @Override
    public String toString() {
        return citta+"in provincia di "+provincia+" via "+via+" cap "+cap;
    }
}
