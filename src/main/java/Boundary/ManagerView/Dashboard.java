package Boundary.ManagerView;

import Boundary.App;
import Boundary.ManagerView.Catalogo.Catalogo;
import com.formdev.flatlaf.ui.FlatLineBorder;
import javax.swing.*;
import java.awt.*;

public class Dashboard {

    //Menu and View
    private JPanel pane;
    private JPanel leftPanel;
    private JScrollPane scrollPane;
    private JButton catalogoButton;
    private JButton statisticheButton;
    private JButton ordiniButton;
    private JButton logoutButton;


    private final JPanel contentPanel;//Hp:Non si aggiorna dinamicamente
    private JLabel labelNUtenti;
    private JLabel labelNOrdini;

    public Dashboard() {
        if (leftPanel != null) {
            leftPanel.setPreferredSize(new Dimension(250, 0));
        }


        JPanel viewPanel = new JPanel(new BorderLayout());
        viewPanel.setOpaque(false);

        scrollPane.setViewportView(viewPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        styleButton(catalogoButton);
        styleButton(ordiniButton);
        styleButton(statisticheButton);
        styleButton(logoutButton);

        // --- TOP PANEL ---
        if (labelNUtenti == null) labelNUtenti = new JLabel("1.248");
        if (labelNOrdini == null) labelNOrdini = new JLabel("342");//TODO CONTROLLER FOR DATA RECOVERY

        // TOP PANEL
        JPanel statsContainer = new JPanel(new GridLayout(1, 2, 25, 0));
        statsContainer.setOpaque(false);
        statsContainer.setPreferredSize(new Dimension(0, 140)); // Altezza fissa
        statsContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 5, 40));

        statsContainer.add(createStatCard("Utenti Registrati", labelNUtenti));
        statsContainer.add(createStatCard("Ordini in Lavorazione", labelNOrdini));

        viewPanel.add(statsContainer, BorderLayout.NORTH);

        //  CREAZIONE PANNELLO RELATIVO AL MENU
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 30, 40));

        viewPanel.add(contentPanel, BorderLayout.CENTER);


        // --- AZIONI MENU LATERALE ---
        statisticheButton.addActionListener(e -> {
            contentPanel.removeAll();

            Statistiche statistiche = new Statistiche();
            for (JPanel panel : statistiche.getSections()) {
                contentPanel.add(panel);
            }

            contentPanel.revalidate();
            contentPanel.repaint();
        });

        catalogoButton.addActionListener(e -> {
            contentPanel.removeAll();
            Catalogo catalogo = new Catalogo();
            contentPanel.add(catalogo.getPane());

            contentPanel.revalidate();
            contentPanel.repaint();
        });

        logoutButton.addActionListener(e -> {
            App.mostraLogin();});
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new FlatLineBorder(new Insets(0,0,0,0), new Color(79,70,229), 1, 12),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void styleButton(JButton button) {
        if (button == null) return;
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.putClientProperty("JButton.buttonType", "borderless");
        button.setMargin(new Insets(10, 15, 10, 15));
    }

    public JPanel getPane() {
        return pane;
    }
}