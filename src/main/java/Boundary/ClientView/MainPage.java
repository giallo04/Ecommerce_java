package Boundary.ClientView;

import Boundary.App;
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
    private JScrollPane scrollPane;
    private JPanel productsPanel;
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
        ricercaButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {search();}});//TODO add search function
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
        products.add(new ProductInHome("Cmf phone 2","$100", "Elettronica"));
        products.add(new ProductInHome("Ps4","$200", "Elettronica"));
        products.add(new ProductInHome("Air Force 1","$160", "Moda"));
        products.add(new ProductInHome("Maglia Napoli","$120", "Moda"));
        products.add(new ProductInHome("Iphone 17","$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17","$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17","$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17","$150", "Elettronica"));
        viewPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                renderProducts(products);
            }
        });
        productsPanel = new JPanel();
        scrollPane.setViewportView(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        productsPanel.setLayout(new GridLayout(0,3,20,20));

    }





    public JPanel getPane(){
        return pane;
    }

    //Navigazione
    private void goToProduct(){}
    private void goToLogin(){
        App.mostraLogin();}
    private void goToCart(){}
    private void goToOrder(){}
    private void goToProfile(){}
    //Barra laterale

    //Barra di ricerca
    private void search(){
        String askFor=queryTextField.getText();
        if(askFor.isEmpty()) {
            renderProducts(products);
            return;
        };
        ArrayList<ProductInHome> askedProd=new ArrayList<>();
        for(ProductInHome p:products){
            if(p.getName().toLowerCase().contains(askFor.toLowerCase())){
                askedProd.add(p);
            }
        }

        renderProducts(askedProd);
    }
    //Renderizzazione
    private void renderProducts(ArrayList<ProductInHome> products) {
        productsPanel.removeAll();
        for(ProductInHome p:products){
            productsPanel.add(p.getPane());
        }
        productsPanel.revalidate();
        productsPanel.repaint();

    }
}
