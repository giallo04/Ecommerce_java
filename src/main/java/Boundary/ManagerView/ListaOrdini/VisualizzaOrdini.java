package Boundary.ManagerView.ListaOrdini;
import Boundary.Utils.TableUtils;
import Controller.OrdiniController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

//contentpane.removeAll()
public class VisualizzaOrdini {
    private JPanel contentPane;
    private JTable tabellaOrdini;
    private DefaultTableModel model;
    private JPanel upperPanel;

    // COSTRUTTORE: Inizializza il pannello e costruisce la UI
    public VisualizzaOrdini() {
        contentPane = new JPanel(new BorderLayout(20,0));
        upperPanel = new JPanel(new BorderLayout());
        VisualizzaTabella();

    }

    // Creazione tabella
    private void VisualizzaTabella() {
        String[] col = new String[]{"OrderID", "Data", "Indirizzo", "Totale", "Stato Ordine"}; // Esempio di colonne fisiche
        OrdiniController ordController = new OrdiniController();

        // BUG RISOLTO: Assegnato correttamente a model (variabile di istanza)
        model = new DefaultTableModel(col, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tabellaOrdini = buildTable(model, sorter);

        JPanel card = TableUtils.wrapInCard("Ordini Effettuati", tabellaOrdini);

        upperPanel.add(buildControlPanel(sorter), BorderLayout.WEST);

        //inserisco gli ordini all'interno della tabella
        List<String[]> ordini = ordController.caricaOrdini();
        if(ordini!=null){
            for (String[] o: ordini){
                String order_id = o[0];
                String data  =o[1];
                String indirizzo = o[2];
                String totale = o[3];
                String stato = o[4];
                model.addRow(new String[]{order_id, data, indirizzo, totale, stato});
            }
        }
        JButton mioBottone = new JButton("Modifica Ordine");
        mioBottone.setPreferredSize(new Dimension(200, 20));
        mioBottone.setFont(new Font("Segoe UI", Font.BOLD, 15));

        contentPane.revalidate();
        contentPane.repaint();

        mioBottone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Controlla se è stata selezionata una riga
                int selectedRow = tabellaOrdini.getSelectedRow();
                //ERRORE RIGA NON SELEZIONATA
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(contentPane,
                            "Seleziona un ordine dalla tabella per visuallizzarne i dettagli.",
                            "Nessuna selezione",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int modelRow = tabellaOrdini.convertRowIndexToModel(selectedRow);

                //Recupera i dati dalle colonne del modello
                // Colonne: 0 = OrderId, 1 = Data, 2 = Indirizzo, 3 = Totale, 4 = stato ordine
                String order_id = model.getValueAt(modelRow, 0).toString();
                String data = model.getValueAt(modelRow, 1).toString();
                String indirizzo = model.getValueAt(modelRow, 2).toString();
                String totale = model.getValueAt(modelRow, 3).toString();
                String statoAttuale = model.getValueAt(modelRow, 4).toString();

                //da spostare in order details
                //ERRORE ORDINE SELEZIONATO ANNULLATO O CONSEGNATO
                if (ordController.isTerminale(order_id)) {
                    JOptionPane.showMessageDialog(contentPane,
                            "Non è possibile modificare un ordine che si trova in stato: " + statoAttuale,
                            "Modifica non consentita",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Trova il Frame principale per passarlo al JDialog
                Frame topFrame = (Frame) SwingUtilities.getWindowAncestor(contentPane);

                //Crea e mostra il Dialog OrderDetails
                OrderDetails dialog = new OrderDetails(topFrame, order_id, data, indirizzo, statoAttuale);
                dialog.pack();
                dialog.setLocationRelativeTo(contentPane); // Centra il dialog rispetto alla tabella
                dialog.setVisible(true); // Questa chiamata è bloccante finché il dialog non si chiude

                //Se l'utente ha premuto OK, aggiorna la tabella
                if (dialog.isConfirmed()) {
                    String nuovoStato = dialog.getNuovoStato();
                    ordController.modificaOrdine(order_id, nuovoStato);
                    // Aggiorna il modello della tabella alla colonna dell'Id dello Stato (indice 3)
                    model.setValueAt(nuovoStato, modelRow, 4);
                    JOptionPane.showMessageDialog(contentPane, "Stato dell'ordine aggiornato con successo!");
                }
            }
        });

        upperPanel.add(mioBottone, BorderLayout.EAST); // NOTA: Cambiato in EAST o CENTER, vedi sotto
        upperPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        contentPane.add(upperPanel, BorderLayout.NORTH);
        contentPane.add(card, BorderLayout.CENTER);
    }


    private JTable buildTable(DefaultTableModel model, TableRowSorter<DefaultTableModel> sorter) {
        JTable t = TableUtils.createStyledTable(model);
        t.setRowSorter(sorter);
        return t;
    }

    private JPanel buildControlPanel(TableRowSorter<DefaultTableModel> sorter) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(buildSearchPanel(sorter), BorderLayout.WEST);
        return panel;
    }

    private JPanel buildSearchPanel(TableRowSorter<DefaultTableModel> sorter) {
        JTextField txtSearch = new JTextField(15);
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { applyFilter(); }
            public void removeUpdate(DocumentEvent e)  { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }

            private void applyFilter() {
                String text = txtSearch.getText().trim();
                // Nota: filtra sulla colonna 1. Assicurati che l'indice esista.
                sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("^" + text, 1));
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        JLabel lblSearch = new JLabel("Cerca:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(lblSearch);
        panel.add(txtSearch);
        return panel;
    }

    public JPanel getContentPane() {
        return contentPane;
    }
}