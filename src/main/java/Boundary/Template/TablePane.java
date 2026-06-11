package Boundary.Template;

import Boundary.Utils.TableUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public abstract class TablePane extends JPanel {
    protected  final JPanel pane;
    protected JTable table;
    protected DefaultTableModel model;
    protected final String[] columns;
    private final String title;
    protected TablePane(String[] columns, String title) {
        this.title=title;
        this.columns=columns;
        pane = new JPanel(new BorderLayout());
        pane.setOpaque(false);
        loadTable();
    }


    protected   void loadTable() {
        //Static definition of the table
        //load products from database
        pane.removeAll();
        model = new DefaultTableModel(null, columns){
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);

        table = buildTable(model, sorter);
        JPanel card = TableUtils.wrapInCard(title, table);

        card.add(buildControlPanel(sorter), BorderLayout.SOUTH);

        pane.add(card, BorderLayout.CENTER);
        loadTableData();
        pane.revalidate();
        pane.repaint();
    }
    protected abstract void loadTableData();

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

    protected abstract JPanel buildActionPanel();

    protected JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public JPanel getPane() { return pane; }
}
