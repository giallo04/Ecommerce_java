package Boundary.ManagerView;

import Boundary.ManagerView.ListaOrdini.OrderDetailManager;
import Controller.AccountController;
import Controller.OrdiniController;
import Eseguibile.App;
import Boundary.ManagerView.Catalogo.Catalogo;
import Boundary.Template.VisualizzaOrdini;
import Boundary.Utils.StyleUtils;
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


    private final JPanel contentPanel;
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

        StyleUtils.styleButton(catalogoButton);
        StyleUtils.styleButton(ordiniButton);
        StyleUtils.styleButton(statisticheButton);
        StyleUtils.styleButton(logoutButton);

// --- TOP PANEL ---
        if (labelNUtenti == null) {
            labelNUtenti = new JLabel();
        }
        if (labelNOrdini == null) {
            labelNOrdini = new JLabel();
        }
        aggiornaStatistiche();

        // TOP PANEL
        JPanel statsContainer = new JPanel(new GridLayout(1, 2, 25, 0));
        statsContainer.setOpaque(false);
        statsContainer.setPreferredSize(new Dimension(0, 140)); // Altezza fissa
        statsContainer.setBorder(BorderFactory.createEmptyBorder(30, 40, 5, 40));

        statsContainer.add(StyleUtils.createStatCard("Utenti Registrati", labelNUtenti));
        statsContainer.add(StyleUtils.createStatCard("Ordini in Preparazione", labelNOrdini));

        viewPanel.add(statsContainer, BorderLayout.NORTH);

        //  CREAZIONE PANNELLO RELATIVO AL MENU
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 0, 20));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 30, 40));

        viewPanel.add(contentPanel, BorderLayout.CENTER);

        // --- AZIONI MENU LATERALE ---
        statisticheButton.addActionListener(e -> {aggiornaStatistiche();onStatistiche();});

        catalogoButton.addActionListener(e -> {aggiornaStatistiche();onCatalogo();});


        ordiniButton.addActionListener(e -> {
            aggiornaStatistiche();
            contentPanel.removeAll();
            VisualizzaOrdini pane=new VisualizzaOrdini(){
                @Override
                protected void openWindow(String id) {
                   OrderDetailManager pane=new OrderDetailManager(id);
                   pane.setVisible(true);
                   pane.pack();
                   pane.setLocationRelativeTo(contentPanel);
                }
                @Override
                protected void onSelect(){//Mi serve per aggiornare le statistiche dinamicamente
                    super.onSelect();
                    Dashboard.this.aggiornaStatistiche();
                }
            };
            contentPanel.add(pane.getPane());
            contentPanel.revalidate();
            contentPanel.repaint();
        });
        logoutButton.addActionListener(e -> {App.mostraLogin();});

        //main menu catalogo
        catalogoButton.doClick();
    }

    //metodi bottoni
    private void onCatalogo() {
        contentPanel.removeAll();
        Catalogo catalogo = new Catalogo();
        contentPanel.add(catalogo.getPane());

        contentPanel.revalidate();
        contentPanel.repaint();
    }
    private void onStatistiche() {
        contentPanel.removeAll();

        Statistiche statistiche = new Statistiche();
        for (JPanel panel : statistiche.getSections()) {
            contentPanel.add(panel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
    public JPanel getPane() {
        return pane;
    }

    //Aggiorna le top card dinamicamente
    public void aggiornaStatistiche() {
        AccountController accountController = new AccountController();
        if (labelNUtenti != null) {
            labelNUtenti.setText(accountController.getNumberUtenti());
        }

        OrdiniController ordiniController = new OrdiniController();
        if (labelNOrdini != null) {
            labelNOrdini.setText(ordiniController.getNumberOrderInLavorazione());
        }
    }
}