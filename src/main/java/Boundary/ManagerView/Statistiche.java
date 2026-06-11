package Boundary.ManagerView;

import Controller.CatalogoController;
import Controller.OrdiniController;
import Controller.Stub;

import javax.swing.*;
import Boundary.Utils.TableUtils;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Boundary.Utils.TableUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistiche {
    private final List<JPanel> sections = new ArrayList<>();

    public Statistiche() {
        CatalogoController controller = new CatalogoController();
        List<String[]> data = controller.inEsaurimento();
        if(data==null) return;
        sections.add(buildSection(
                "Prodotti in esaurimento",
                new String[]{"Prodotto", "Quantità residua"},data

        ));
        OrdiniController ordiniController = new OrdiniController();
        data=ordiniController.reportOrdiniMensili();
        sections.add(buildSection(
                "Ordini nel corso del mese",
                new String[]{"Order ID", "Data", "Indirizzo", "Totale","stato Ordine"},
                data
        ));
        data=controller.prodottiPiuVenduti();
        sections.add(buildSection(
                "Prodotti più venduti",
                new String[]{"Prodotto", "Vendite"},
                data
        ));
    }


    private JPanel buildSection(String title, String[] columns, List<String[]> data) {
        DefaultTableModel model = new DefaultTableModel(null, columns) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        for (String[] row : data) {
            model.addRow(row);
        }

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