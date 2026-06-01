package Boundary.Utils;

import com.formdev.flatlaf.ui.FlatLineBorder;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseWheelListener;

public class TableUtils {

    // Colore standard dell'applicazione
    public static final Color PRIMARY_COLOR = new Color(79, 70, 229);

    private TableUtils() {}//Classe di utilità non deve essere istanziata
    public static JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        table.setRowHeight(38);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 18));
        header.setBackground(Color.WHITE);

        return table;
    }

    public static JPanel wrapInCard(String titolo, JComponent tableComponent) {
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), PRIMARY_COLOR, 1, 20));

        if (titolo != null) {
            JLabel labelTitolo = new JLabel(titolo);
            labelTitolo.setFont(new Font("Segoe UI", Font.BOLD, 16));
            labelTitolo.setForeground(PRIMARY_COLOR);
            labelTitolo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
            cardPanel.add(labelTitolo, BorderLayout.NORTH);
        }

        JScrollPane scroll = new JScrollPane(tableComponent);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        cardPanel.add(scroll, BorderLayout.CENTER);
        return cardPanel;
    }
    public static void disableWheelScrolling(Container container) {
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

}