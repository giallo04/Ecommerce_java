package Boundary.ManagerView.ListaOrdini;

import Boundary.Template.Container.Container;

import Controller.OrdiniController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderDetails extends Container {

    private final OrdiniController oContr=new OrdiniController();
    private final String orderId;


    private JComboBox<String> comboBoxStato;

    public OrderDetails(String orderId) {
        super();

        this.orderId = orderId;


        setTitle("Dettagli Ordine #" + orderId);
        salvaBtn.setText("Salva modifiche");
        salvaBtn.setEnabled(false);


        initInfoPanel(orderId);

        // Carica e mostra le righe d'ordine sfruttando il meccanismo di Container
        refreshContainer();
    }

    private void initInfoPanel(String orderId) {
        infoPanel.removeAll();

        String[] data=oContr.caricaOrdine(orderId);
        JLabel orderIdLabel = new JLabel("ID Ordine: " + orderId);
        orderIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totaleLabel.setText("totale: $ "+data[OrdiniController.TOTALE]);


        JLabel dataLabel = new JLabel("Data: " + data[OrdiniController.DATA]);
        dataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel indirizzoLabel = new JLabel("Indirizzo: " + data[OrdiniController.INDIRIZZO]);
        indirizzoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JPanel statoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JLabel statoLabel = new JLabel("Stato Ordine: ");
        statoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        List<String> stati = oContr.getStatiOrdine();
        comboBoxStato = new JComboBox<>();
        for (String stato : stati) {
            comboBoxStato.addItem(stato);
        }
        comboBoxStato.setSelectedItem(data[OrdiniController.STATO]);

        statoPanel.add(statoLabel);
        statoPanel.add(comboBoxStato);
        comboBoxStato.addActionListener(e -> salvaBtn.setEnabled(true));
        infoPanel.add(orderIdLabel);
        infoPanel.add(dataLabel);
        infoPanel.add(indirizzoLabel);
        infoPanel.add(statoPanel);
    }



    @Override
    protected List<String[]> loadRows() {
        // Recupero le righe dal controller
        return oContr.caricaRigheOrdine(this.orderId);
    }

    @Override
    protected void addRow(List<String[]> rows) {
        for (String[] prodotto : rows) {
            ProductInOrdine p = new ProductInOrdine(
                    prodotto[OrdiniController.PRODUCT_ID],
                    prodotto[OrdiniController.QUANTITA],
                    prodotto[OrdiniController.PREZZO]
            );
            containerViewPanel.add(p.getPane());
        }
    }

    @Override
    protected void doOnEmpty() {
        containerViewPanel.add(new JLabel("Nessun prodotto presente in questo ordine."));
    }

    @Override
    protected void onBtn() {
        if(oContr.modificaOrdine(this.orderId, getNuovoStato())){
            JOptionPane.showMessageDialog(null, "Stato ordine modificato con successo!");
        }else{
            JOptionPane.showMessageDialog(null,oContr.getError_msg());
        }
        dispose();
    }

    // --- Getter utili ---

    public String getNuovoStato() {
        return (String) comboBoxStato.getSelectedItem();
    }

}