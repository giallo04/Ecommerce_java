package Boundary.ManagerView;

import Controller.Stub;

import javax.swing.*;
import Boundary.Utils.TableUtils;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Boundary.Utils.TableUtils;
import java.util.ArrayList;
import java.util.List;

public class Statistiche {
    private final List<JPanel> sections = new ArrayList<>();

    public Statistiche() {
        sections.add(buildSection(
                "Prodotti in esaurimento",
                new String[]{"Prodotto", "Quantità residua"},
                Stub.getDati()   // TODO: replace with real controller call
        ));

        sections.add(buildSection(
                "Ordini in attesa di consegna",
                new String[]{"Ordine", "Cliente", "Indirizzo", "Totale"},
                Stub.getDati()   // TODO: replace with real controller call
        ));

        sections.add(buildSection(
                "Prodotti più venduti",
                new String[]{"Prodotto", "Vendite"},
                Stub.getDati()   // TODO: replace with real controller call
        ));
    }


    private JPanel buildSection(String title, String[] columns, Object[][] data) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        JTable table = TableUtils.createStyledTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JPanel card = TableUtils.wrapInCard(title, table);
        TableUtils.disableWheelScrolling(card);
        return card;
    }


    public JPanel[] getSections() {
        return sections.toArray(new JPanel[0]);
    }
}