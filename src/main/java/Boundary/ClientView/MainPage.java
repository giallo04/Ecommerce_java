package Boundary.ClientView;

import Boundary.App;
import Boundary.ClientView.Catalogo.ProductInHome;
import com.formdev.flatlaf.ui.FlatLineBorder;
import Boundary.Utils.StyleUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

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
        topPanel.setBorder(new FlatLineBorder(insets,customColor, 1, arc));
        queryTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void changedUpdate(DocumentEvent e)  { search(); }
            @Override public void removeUpdate(DocumentEvent e)  { search(); }
            @Override  public void insertUpdate(DocumentEvent e)  { search(); }
        });
        //Pulsanti pannello superiore
        ricercaButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {search();}});
        carrelloButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {goToCart();}});

        categoriaComboBox.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
            search();
        }});


        //Pannello laterale
        leftPanel.setBorder(new FlatLineBorder(insets,customColor, 1, arc));
        ordineComboBox.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
            int scelta=ordineComboBox.getSelectedIndex();
            switch(scelta){
                case 0:
                    Collections.shuffle(products);
                    search();
                    break;
                case 1:
                    products.sort((p1,p2)->p1.getName().compareTo(p2.getName()));
                    search();
                    break;
                case 2:
                    Collections.sort(products);
                    search();
                    break;
                case 3:
                    products.sort(Collections.reverseOrder());
                    search();
            }
        }});

        offerteCheckBox.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {
            search();
        }});
        //Pulsanti pannello laterale

        StyleUtils.styleButton(logoutButton);
        StyleUtils.styleButton(modificaProfiloButton);
        StyleUtils.styleButton(visualizzaOrdiniButton);
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
        products.add(new ProductInHome("Cmf phone 2","100", "Elettronica","20"));
        products.add(new ProductInHome("Ps4","200", "Elettronica","0"));
        products.add(new ProductInHome("Air Force 1","160", "Moda","10"));
        products.add(new ProductInHome("Maglia Napoli","120", "Moda","0"));
        products.add(new ProductInHome("Iphone 17","150", "Elettronica","0"));
        products.add(new ProductInHome("Iphone 17","150", "Elettronica","0"));
        products.add(new ProductInHome("Iphone 17","150", "Elettronica","0"));
        products.add(new ProductInHome("Iphone 17","150", "Elettronica","0"));
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
    private void goToProfile(){
        ShowModificaProfiloDialog dialog = new ShowModificaProfiloDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
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
            if(categoriaComboBox.getSelectedIndex()==0 || p.getCategory().equals(categoriaComboBox.getSelectedItem()))
                if(offerteCheckBox.isSelected()){
                    if(p.isOffer()){productsPanel.add(p.getPane());}
                }else{
                productsPanel.add(p.getPane());
                }
        }
        productsPanel.revalidate();
        productsPanel.repaint();

    }
}
