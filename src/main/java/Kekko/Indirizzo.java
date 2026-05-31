package Kekko;

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
}
