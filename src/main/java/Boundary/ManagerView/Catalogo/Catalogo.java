package Boundary.ManagerView.Catalogo;

import Controller.Stub;
import Boundary.Utils.TableUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Catalogo {
    private final JPanel pane;
    private JTable table;
    private DefaultTableModel model;
    public Catalogo() {
        pane = new JPanel(new BorderLayout());
        pane.setOpaque(false);
        loadCatalogo();
    }


    private void loadCatalogo() {
        model = buildTableModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table = buildTable(model, sorter);

        JPanel card = TableUtils.wrapInCard("Gestione Catalogo", table);

        card.add(buildControlPanel(sorter), BorderLayout.SOUTH);

        pane.add(card, BorderLayout.CENTER);
    }


    private DefaultTableModel buildTableModel() {
        return new DefaultTableModel(Stub.getDati(), new String[]{"ID", "Nome", "Prezzo"}) {
            @Override
            public boolean isCellEditable(int row, int col) { return col != 0; }
        };
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
        panel.add(buildActionPanel(),       BorderLayout.EAST);

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
                sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("^" + text, 1));
            }
        });

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        panel.add(new JLabel("Cerca:"));
        panel.add(txtSearch);
        return panel;
    }

    private JPanel buildActionPanel() {
        JButton btnSave   = createButton("Salva modifiche");
        JButton btnRemove = createButton("Rimuovi");
        JButton btnAdd    = createButton("Aggiungi prodotto");

        btnSave.addActionListener(e -> onSave());
        btnRemove.addActionListener(e -> onRemove());
        btnAdd.addActionListener(e -> onAdd());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panel.setOpaque(false);
        panel.add(btnSave);
        panel.add(btnRemove);
        panel.add(btnAdd);
        return panel;
    }

//Action handlers

    private void onEnter(){

    }
    private void onSave() {
        if (table.isEditing()) table.getCellEditor().stopCellEditing();
        // TODO: Controller.saveDati(model.getDataVector());
        JOptionPane.showMessageDialog(pane, "Catalogo aggiornato correttamente.");
    }

    private void onRemove() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(pane, "Seleziona un prodotto.");
            return;
        }
        model.removeRow(table.convertRowIndexToModel(viewRow));//TODO call to controller
    }

    private void onAdd() {
        ShowAggiungiProdottoDialog dialog = new ShowAggiungiProdottoDialog();
        dialog.pack();
        dialog.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public JPanel getPane() { return pane; }
}