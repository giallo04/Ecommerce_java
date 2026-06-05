package Boundary.ClientView;

import Boundary.App;
import Boundary.ClientView.Catalogo.ProductInHome;
import Controller.CatalogoController;
import com.formdev.flatlaf.ui.FlatLineBorder;
import Boundary.Utils.StyleUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class MainPage {
    private JPanel pane;

    // Pannello laterale
    private JPanel leftPanel;
    private JCheckBox offerteCheckBox;
    private JComboBox<String> ordineComboBox; // Aggiunto parametro di tipo generico
    private JButton modificaProfiloButton;
    private JButton visualizzaOrdiniButton;

    // Pannello superiore
    private JPanel topPanel;
    private JComboBox<String> categoriaComboBox; // Aggiunto parametro di tipo generico
    private JTextField queryTextField;
    private JButton ricercaButton;
    private JButton carrelloButton;
    private JButton logoutButton;

    // Pannello centrale
    private JPanel viewPanel;
    private JScrollPane scrollPane;
    private JPanel productsPanel;

    public MainPage(){

        // Estetica setup
        int arc = 20;
        Insets insets = new Insets(arc, arc, arc, arc);
        Color customColor = new Color(79, 70, 229);

        // Finestra
        pane.setBackground(new Color(240, 240, 240));
        pane.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));

        // Pannello superiore
        queryTextField.putClientProperty("JTextField.placeholderText", "Cerca prodotto...");
        topPanel.setBorder(new FlatLineBorder(insets, customColor, 1, arc));

     queryTextField.addKeyListener(new java.awt.event.KeyAdapter() {
         @Override
         public void keyReleased(java.awt.event.KeyEvent evt) {
             if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER)
                 updateView();
         }
     });

        // Caricamento categorie
        CatalogoController controller = new CatalogoController();
        List<String> categorie = controller.getCategorie();
        for(String c : categorie){
            categoriaComboBox.addItem(c);
        }

        // Listener Pannello Superiore
        ricercaButton.addActionListener(e -> updateView());
        categoriaComboBox.addActionListener(e -> updateView());
        carrelloButton.addActionListener(e -> goToCart());

        // Pannello laterale
        leftPanel.setBorder(new FlatLineBorder(insets, customColor, 1, arc));
        ordineComboBox.addActionListener(e -> updateView());
        offerteCheckBox.addActionListener(e -> updateView());

        // Pulsanti pannello laterale e stile
        StyleUtils.styleButton(logoutButton);
        StyleUtils.styleButton(modificaProfiloButton);
        StyleUtils.styleButton(visualizzaOrdiniButton);

        logoutButton.addActionListener(e -> goToLogin());
        modificaProfiloButton.addActionListener(e -> goToProfile());
        visualizzaOrdiniButton.addActionListener(e -> goToOrder());

        // Pannello centrale
        viewPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        productsPanel = new JPanel();
        scrollPane.setViewportView(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        productsPanel.setLayout(new GridLayout(0, 3, 20, 20));

        updateView();
    }

    public JPanel getPane(){
        return pane;
    }

    // Navigazione
    private void goToProduct(){}
    private void goToLogin(){ App.mostraLogin(); }
    private void goToCart(){}
    private void goToOrder(){}
    private void goToProfile(){
        ShowModificaProfiloDialog dialog = new ShowModificaProfiloDialog();
        dialog.pack();
        dialog.setVisible(true);
    }


    private void updateView() {

        List<String[]> prodottiGrezzi = search();

        ArrayList<ProductInHome> prodotti = convertToProduct(prodottiGrezzi);
        if(prodotti == null) return;
        // 3. Filtra per offerte:
        if (offerteCheckBox.isSelected()) {
            prodotti.removeIf(p -> !p.isOffer());
        }

        int scelta = ordineComboBox.getSelectedIndex();
        switch(scelta){
            case 1: // Nome (A-Z)
                prodotti.sort((p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName()));
                break;
            case 2: // Prezzo crescente (Presume che ProductInHome implementi Comparable)
                Collections.sort(prodotti);
                break;
            case 3: // Prezzo decrescente
                prodotti.sort(Collections.reverseOrder());
                break;
            default:
                break;
        }
        renderProducts(prodotti);
    }

    private List<String[]> search(){
        String askFor = queryTextField.getText().trim();
        CatalogoController controller = new CatalogoController();

        if(askFor.isEmpty() && categoriaComboBox.getSelectedIndex() == 0){
            return controller.caricaCatalogo();
        } else if(categoriaComboBox.getSelectedIndex() == 0 && !askFor.isEmpty()){
            return controller.caricaProdottiStringa(askFor);
        } else if(askFor.isEmpty() && categoriaComboBox.getSelectedIndex() != 0){
            return controller.caricaProdottiCategoria(categoriaComboBox.getSelectedItem().toString());
        } else {
            return controller.caricaPerCategoriaAndStringa(categoriaComboBox.getSelectedItem().toString(), askFor);
        }
    }

    private void renderProducts(ArrayList<ProductInHome> products) {
        productsPanel.removeAll();
        if(products == null) return;
        for(ProductInHome p : products){
            productsPanel.add(p.getPane());
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }
//String name, String price, String category, String offer,String imgPath){
    private ArrayList<ProductInHome> convertToProduct(List<String[]> prodotti){
        if(prodotti == null) return null;
        ArrayList<ProductInHome> products = new ArrayList<>();
        for(String[] p : prodotti){
            products.add(new ProductInHome(p[2], p[3], p[5], p[6], p[1]));
        }
        return products;
    }
}