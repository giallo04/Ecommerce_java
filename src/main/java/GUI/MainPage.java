package GUI;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainPage {
    private JPanel pane;

    //Pannello laterale
    private JPanel leftPanel;
    private JCheckBox offerteCheckBox;
    private JComboBox ordineComboBox;
    private JButton modificaProfiloButton;
    private JButton visualizzaOrdiniButton;
    //Pannello superiore
    private JPanel topPanel;
    private JComboBox categoriaComboBox;
    private JTextField queryTextField;
    private JButton ricercaButton;
    private JButton carrelloButton;
    private JButton logoutButton;

    //Pannello centrale
    private JPanel viewPanel;
    private JButton nextButton;
    private JButton backButton;
    private JPanel productsPanel;
    private int paginaCorrente = 0;
    private ArrayList<ProductInHome> products = new ArrayList<ProductInHome>();

    public MainPage(){

        //Estetica setup
        int arc=20;
        Insets insets=new Insets(arc,arc,arc,arc);
        Color customColor = new Color(79,70,229);

        //Finestra
        pane.setBackground(new Color(240,240,240));
        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 0, arc));

        //Pannello superiore
        queryTextField.putClientProperty("JTextField.placeholderText", "Cerca prodotto...");
        topPanel.setBackground(customColor);
        topPanel.setBorder(new FlatLineBorder(insets,customColor, 0, arc));

        //Pulsanti pannello superiore
        ricercaButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {smartRender();}});//TODO add search function
        carrelloButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {goToCart();}});

        //Pannello laterale
        leftPanel.setBackground(customColor);
        leftPanel.setBorder(new FlatLineBorder(insets,customColor, 0, arc));

        //Pulsanti pannello laterale

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLogin();
            }
        });

        modificaProfiloButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {goToProfile();}});
        visualizzaOrdiniButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {goToOrder();}});

        //Pannello centrale
        viewPanel.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 0, arc));
        for(int i=0;i<20;i++){ProductInHome p=new ProductInHome("Scarpe Nike","30","Moda");products.add(p);}
        viewPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                smartRender();
            }
        });


        //Pulsanti pannello centrale
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaCorrente++;
                smartRender();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paginaCorrente--;
                smartRender();
            }
        });
    }





    public JPanel getPane(){
        return pane;
    }

    //Navigazione
    private void goToProduct(){}
    private void goToLogin(){App.mostraLogin();}
    private void goToCart(){}
    private void goToOrder(){}
    private void goToProfile(){}

    //Renderizzazione
    private void smartRender() {
        productsPanel.removeAll();
        Dimension dim = productsPanel.getSize();

        if (dim.height <= 0 || dim.width <= 0) return;

        int larghezzaMinimaProdotto = 430;
        int altezzaMinimaProdotto = 400;

        int colonneCalcolate = Math.max(1, dim.width / larghezzaMinimaProdotto);
        int righeCalcolate = Math.max(1, dim.height / altezzaMinimaProdotto);

        productsPanel.setLayout(new GridLayout(righeCalcolate, colonneCalcolate, 12, 12));


        int prodottiPerPagina = colonneCalcolate * righeCalcolate;
        // --- Paginazione ---
        int indiceInizio = paginaCorrente * prodottiPerPagina;
        int indiceFine = indiceInizio + prodottiPerPagina;

        // Check ultima pagina
        if (indiceFine > products.size()) {
            indiceFine = products.size();
        }

        // Gestione attivazione/disattivazione visiva dei bottoni
        backButton.setEnabled(paginaCorrente > 0);
        nextButton.setEnabled(indiceFine < products.size());

        // Se l'indice di inizio supera i prodotti totali
        if (indiceInizio >= products.size() && paginaCorrente > 0) {
            paginaCorrente--;
            smartRender();
            return;
        }

        // --- Render pagina corrente ---
        for (int i = indiceInizio; i < indiceFine; i++) {
            productsPanel.add(products.get(i).getPane());
        }

        //Padding
        int prodottiDisegnati = indiceFine - indiceInizio;
        if (prodottiDisegnati < prodottiPerPagina) {
            int slotVuoti = prodottiPerPagina - prodottiDisegnati;
            for (int i = 0; i < slotVuoti; i++) {
                productsPanel.add(new JPanel());
            }
        }

        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
