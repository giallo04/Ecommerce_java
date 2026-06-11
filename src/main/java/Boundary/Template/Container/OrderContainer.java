package Boundary.Template.Container;

import Boundary.ManagerView.ListaOrdini.ProductInOrdine;
import Controller.OrdiniController;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class OrderContainer extends Container {

    protected final OrdiniController oContr = new OrdiniController();
    protected JComboBox<String> comboBoxStato;
    protected final String orderId;

    public OrderContainer(String orderId) {
        super();
        this.orderId = orderId;
        setTitle("Dettaglio Ordine #" + orderId);
        salvaBtn.setText("Salva modifiche");
        salvaBtn.setVisible(isSaveBtnVisible());
        salvaBtn.setEnabled(false);
        initInfoPanel(orderId);
        refreshContainer();
    }

    private void initInfoPanel(String orderId) {
        infoPanel.removeAll();

        Font font = new Font("Segoe UI", Font.BOLD, 18);
        String[] data = oContr.caricaOrdine(orderId);

        JLabel orderIdLabel = new JLabel("ID Ordine: " + orderId);
        orderIdLabel.setFont(font);

        totaleLabel.setText("Totale: $ " + data[OrdiniController.TOTALE]);

        JLabel dataLabel = new JLabel("Data: " + data[OrdiniController.DATA]);
        dataLabel.setFont(font);

        JLabel indirizzoLabel = new JLabel("Indirizzo: " + data[OrdiniController.INDIRIZZO]);
        indirizzoLabel.setFont(font);

        JPanel statoPanel = new JPanel(new BorderLayout());
        JLabel statoLabel = new JLabel("Stato Ordine: ");
        statoLabel.setFont(font);

        List<String> stati = oContr.getStatiOrdine();
        comboBoxStato = new JComboBox<>();
        for (String stato : stati) {
            comboBoxStato.addItem(stato);
        }
        comboBoxStato.setSelectedItem(data[OrdiniController.STATO]);
        comboBoxStato.setFont(font);
        comboBoxStato.setEnabled(isStatoEditable());
        comboBoxStato.addActionListener(e -> onStatoChanged());

        statoPanel.add(statoLabel, BorderLayout.WEST);
        statoPanel.add(comboBoxStato, BorderLayout.CENTER);

        infoPanel.add(orderIdLabel);
        infoPanel.add(dataLabel);
        infoPanel.add(indirizzoLabel);
        infoPanel.add(statoPanel);

        infoPanel.revalidate();
        infoPanel.repaint();
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

}