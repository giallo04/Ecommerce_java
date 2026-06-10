package Boundary.Template;

import Controller.OrdiniController;

import java.util.List;
import javax.swing.*;

import java.awt.*;

public  abstract class VisualizzaOrdini extends TablePane {

    public VisualizzaOrdini() {
        super(new String[]{"Data", "OrderID", "Indirizzo", "Totale", "Stato Ordine"},"Gestione Ordini"); // Esempio di colonne fisiche
    }
    @Override
    protected void loadTableData() {
        //Static definition of the table
        //load products from database
        OrdiniController controller=new OrdiniController();
        List <String[]> dati=controller.caricaOrdini();
        if(dati!=null) {
            for (String[] d : dati) {
                String id=d[OrdiniController.ORDER_ID];
                String data=d[OrdiniController.DATA];
                String indirizzo=d[OrdiniController.INDIRIZZO];
                String totale=d[OrdiniController.TOTALE];
                String stato=d[OrdiniController.STATO];
                model.addRow(new String[]{data,id,indirizzo,totale,stato});
            }
        }
    }

    @Override
    protected JPanel buildActionPanel() {
        JButton btnSeleziona   = createButton("Visualizza dettagli ordine");
        btnSeleziona.addActionListener(e -> onSelect());



        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panel.setOpaque(false);
        panel.add(btnSeleziona);
        return panel;
    }

//Action handlers


    private void onSelect() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {JOptionPane.showMessageDialog(pane, "Seleziona un ordine da visualizzare"); return;}
        String id=model.getValueAt(viewRow,1).toString();
        openWindow(id);
        loadTableData();
    }
    protected abstract  void openWindow(String id );

}