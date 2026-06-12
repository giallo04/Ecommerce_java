package Boundary.Template;

import Controller.OrdiniController;

import java.util.List;
import javax.swing.*;

import java.awt.*;

public  abstract class VisualizzaOrdini extends TablePane {

    public VisualizzaOrdini() {
        super(new String[]{"Data", "OrderID", "Indirizzo", "Totale", "Stato Ordine"},"Gestione Ordini");
    }

    @Override
    protected void loadTableData() {
        model.setRowCount(0);
        //Static definition of the table
        //load products from database
        List <String[]> dati=filter();
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
    protected List<String[]> filter(){
        OrdiniController controller=new OrdiniController();
        return controller.caricaOrdini();
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


    protected void onSelect() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {JOptionPane.showMessageDialog(pane, "Seleziona un ordine da visualizzare"); return;}
        String id=model.getValueAt(viewRow,1).toString();
        openWindow(id);
        loadTableData();
    }
    protected abstract  void openWindow(String id );

}