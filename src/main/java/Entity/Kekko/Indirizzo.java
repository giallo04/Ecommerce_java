package Entity.Kekko;

import java.util.Objects;

public class Indirizzo {

    private String citta;
    private String provincia;
    private String via;
    private int cap;



    public Indirizzo(String citta, String provincia,String via,int cap)
    {
        this.citta=citta;
        this.provincia=provincia;
        this.via=via;
        this.cap=cap;
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
        return "Indirizzo{" +
                "citta='" + citta + '\'' +
                ", provincia='" + provincia + '\'' +
                ", via='" + via + '\'' +
                ", cap=" + cap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Indirizzo indirizzo = (Indirizzo) o;
        return cap == indirizzo.cap && Objects.equals(citta, indirizzo.citta) && Objects.equals(provincia, indirizzo.provincia) && Objects.equals(via, indirizzo.via);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citta, provincia, via, cap);
    }
}
