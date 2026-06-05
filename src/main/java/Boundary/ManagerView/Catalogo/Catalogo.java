package Boundary.ManagerView.Catalogo;
import Controller.CatalogoController;
import Boundary.Utils.TableUtils;

import java.util.List;
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
        //Static definition of the table
        //load products from database
        pane.removeAll();
        model = new DefaultTableModel(null, new String[]{"ID", "Nome", "Prezzo","Quantità","Categoria", "Sconto"}){
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table = buildTable(model, sorter);
        JPanel card = TableUtils.wrapInCard("Gestione Catalogo", table);

        card.add(buildControlPanel(sorter), BorderLayout.SOUTH);

        pane.add(card, BorderLayout.CENTER);
        CatalogoController controller=new CatalogoController();
        List <String[]> dati=controller.caricaCatalogo();
        if(dati!=null) {
            for (String[] d : dati) {
                String id=d[CatalogoController.ID];
                String nome=d[CatalogoController.NOME];
                String prezzo=d[CatalogoController.PREZZO];
                String quantita=d[CatalogoController.QUANTITA];
                String categoria=d[CatalogoController.CATEGORIA];
                String sconto=d[CatalogoController.SCONTO];
                model.addRow(new String[]{id,nome,prezzo,quantita,categoria,sconto});
            }
        }
        pane.revalidate();
        pane.repaint();
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
        if (viewRow == -1) {JOptionPane.showMessageDialog(pane, "Seleziona un prodotto da modificare"); return;}
        String id=model.getValueAt(viewRow,0).toString();
        ShowModificaProdottoDialog dialog = new ShowModificaProdottoDialog(id);
        dialog.pack();
        dialog.setVisible(true);
        loadCatalogo();
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