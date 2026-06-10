package Entity.client;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Embeddable
public class Indirizzo {


    private String citta;
    private String provincia;
    private String via;
    private int cap;



    public Indirizzo(String citta, String provincia,String via,String cap)throws IllegalArgumentException
    {
        if(citta==null && provincia==null && via==null && cap==null)
        { throw new IllegalArgumentException("\nInserire citta', provincia, via e cap validi!\n");
        } else {
            setCitta(citta);
            setProvincia(provincia);
            setVia(via);
            try{
                setCap(Integer.parseInt(cap));
            }catch (NumberFormatException e){
                throw new IllegalArgumentException("Cap deve essere un numero");
            }
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
        if(citta.isEmpty()) throw new IllegalArgumentException("Citta non puo essere vuota");
        if(citta.contains(" ")) throw new IllegalArgumentException("Citta non puo contenere spazi");
        this.citta = citta;
    }

    public void setProvincia(String provincia) {
        if(provincia.isEmpty()) throw new IllegalArgumentException("Provincia non puo essere vuota");
        this.provincia = provincia;
    }

    public void setVia(String via) {
        if(via.isEmpty()) throw new IllegalArgumentException("Via non puo essere vuota");
        this.via = via;
    }

    public void setCap(int cap) {
        if(cap<0) throw new IllegalArgumentException("Cap non puo essere negativo");
        this.cap = cap;
    }

    @Override
    public String toString() {
        return citta+" in provincia di "+provincia+"  "+via+"  "+cap;
    }
}
