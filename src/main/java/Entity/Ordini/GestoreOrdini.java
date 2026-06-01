package Entity.Ordini;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestoreOrdini {
    //Singleton
    private static GestoreOrdini instance=new GestoreOrdini();
    private ArrayList<Ordine> ordini;

    private GestoreOrdini(){
        ordini = new ArrayList<Ordine>();
    }

    public static GestoreOrdini getInstance(){
        return instance;
    }
    public List<Ordine> getOrdini(){
        return Collections.unmodifiableList(ordini);
    }

    public void addOrdine(Ordine ordine){
        ordini.add(ordine);
    }
    //TODO metodi per gestire modifica ordine

}
