package Boundary.ManagerView;

import Controller.Stub;

import javax.swing.*;
import Boundary.Utils.TableUtils;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseWheelListener;
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
        JPanel card   = TableUtils.wrapInCard(title, table);

        disableWheelScrolling(card);

        return card;
    }

    //metodo per disabilitare lo scroll del mousewheel
    private void disableWheelScrolling(Container container) {
        for (Component c : container.getComponents()) {
            if (c instanceof JScrollPane scrollPane) {
                scrollPane.setWheelScrollingEnabled(false);
                for (MouseWheelListener l : scrollPane.getMouseWheelListeners()) {
                    scrollPane.removeMouseWheelListener(l);
                }
                return;
            }
            if (c instanceof Container child) {
                disableWheelScrolling(child);
            }
        }
    }

    public JPanel[] getSections() {
        return sections.toArray(new JPanel[0]);
    }
}