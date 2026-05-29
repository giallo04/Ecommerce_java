package Entity;

public class Prodotto {
    private String nome;
    private float prezzo;
    private final String descrizione;
    private final String categoria;
    private int sconto;
    int quantita;


    public  Prodotto(String nome, float prezzo, String descrizione, int quantita, String categoria ) throws IllegalArgumentException{
        if(!(nome.isEmpty() || prezzo<=0 || descrizione.isEmpty() || categoria.isEmpty()|| quantita<0)){
            throw new IllegalArgumentException();
        }
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.categoria = categoria;
        this.quantita = quantita;
    }
    public String getNome(){
        return nome;
    }
    public float  getCostoEffettivo(){
        return prezzo-prezzo*sconto/100;
    }
    public void setNome(String nome) throws IllegalArgumentException{
        if(nome.isEmpty()){
            throw new IllegalArgumentException();
        }else{
            this.nome=nome;
        }
    }
    public void setPrezzo(float prezzo) throws IllegalArgumentException{
        if(prezzo<=0){
            throw new IllegalArgumentException();
        }else{
            this.prezzo=prezzo;
        }
    }
    public float getPrezzo(){
        return prezzo;
    }
    public String getDescrizione(){
        return descrizione;
    }
    public String getCategoria(){
        return categoria;
    }
    public int getQuantita(){
        return quantita;
    }
    public void addQuantita(int quantita) throws IllegalArgumentException{
        if(quantita<=0){
            throw  new IllegalArgumentException();
        }else{
            this.quantita+=quantita;
        }
    }
    public void removeQuantita(int quantita) throws IllegalArgumentException{
        if(quantita<=0||quantita>this.quantita){
            throw  new IllegalArgumentException();
        }else{
            this.quantita-=quantita;
        }
    }
    public void setSconto(int sconto) throws IllegalArgumentException{
        if(sconto<0||sconto>100){
            throw  new IllegalArgumentException();
        }else{
            this.sconto=sconto;
        }
    }
    public  String getSconto(){
        return  sconto+"%";
    }
    public boolean isDisponibile(){
        return quantita>0;
    }
    public void setNotDisponibile(){
        quantita=0;
    }
}

