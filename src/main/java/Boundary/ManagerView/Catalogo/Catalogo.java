package Boundary.ManagerView.Catalogo;

import Boundary.DTO.ProductDTO;
import Controller.CatalogoController;
import Controller.Stub;
import Boundary.Utils.TableUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

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
        //Static definition of the table
        CatalogoController controller = new CatalogoController();
        ArrayList<ProductDTO> products = controller.loadCatalogo();
        String[][] dati = new String[products.size()][4];
        for(int i=0;i<products.size();i++){
            dati[i][0]=Long.toString(products.get(i).getId());
            dati[i][1]=products.get(i).getNome();
            dati[i][2]=Float.toString(products.get(i).getPrezzo());
            dati[i][3]=Integer.toString(products.get(i).getQuantita());
        }
        model = new DefaultTableModel(dati, new String[]{"ID", "Nome", "Prezzo","Quantità"}){
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table = buildTable(model, sorter);

        JPanel card = TableUtils.wrapInCard("Gestione Catalogo", table);

        card.add(buildControlPanel(sorter), BorderLayout.SOUTH);

        pane.add(card, BorderLayout.CENTER);
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
        JLabel lblSearch = new JLabel("Cerca:");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(lblSearch);
        panel.add(txtSearch);
        return panel;
    }

    private JPanel buildActionPanel() {
        JButton btnMod   = createButton("Modifica prodotto");
        JButton btnRemove = createButton("Rimuovi");
        JButton btnAdd    = createButton("Aggiungi prodotto");

        btnMod.addActionListener(e -> onMod());
        btnRemove.addActionListener(e -> onRemove());
        btnAdd.addActionListener(e -> onAdd());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panel.setOpaque(false);
        panel.add(btnMod);
        panel.add(btnRemove);
        panel.add(btnAdd);
        return panel;
    }

//Action handlers


    private void onMod() {
        int viewRow = table.getSelectedRow();
        String id=(String) model.getValueAt(viewRow,0);
        ShowModificaProdottoDialog dialog = new ShowModificaProdottoDialog(id);
        dialog.pack();
        dialog.setVisible(true);
        // TODO: Controller.saveDati(model.getDataVector());
    }

    private void onRemove() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(pane, "Seleziona un prodotto.");
            return;
        }
        model.removeRow(table.convertRowIndexToModel(viewRow));//TODO call to controller
        loadCatalogo();
    }

    private void onAdd() {
        ShowAggiungiProdottoDialog dialog = new ShowAggiungiProdottoDialog();
        dialog.pack();
        dialog.setVisible(true);
        loadCatalogo();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public JPanel getPane() { return pane; }
}