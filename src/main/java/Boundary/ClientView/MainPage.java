package Boundary.ClientView;

import Eseguibile.App;
import Boundary.ClientView.Catalogo.ProductInHome;
import Controller.CatalogoController;
import com.formdev.flatlaf.ui.FlatLineBorder;
import Boundary.Utils.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainPage {

    private final CatalogoController controller;
    private JPanel pane;

    // Pannello laterale
    private JPanel leftPanel;
    private JCheckBox offerteCheckBox;
    private JComboBox<String> ordineComboBox;
    private JButton modificaProfiloButton;
    private JButton visualizzaOrdiniButton;
    private JButton homeButton;

    // Pannello superiore
    private JPanel topPanel;
    private JComboBox<String> categoriaComboBox;
    private JTextField queryTextField;
    private JButton ricercaButton;
    private JButton carrelloButton;
    private JButton logoutButton;

    // Pannello centrale
    private JPanel viewPanel;
    private JScrollPane scrollPane;
    private JPanel productsPanel;

    //Carrello view
    private JPanel carrelloPanel;
    private JScrollPane carrelloScrollPane;
    private JPanel carrelloViewPanel;
    private JButton effettuaOrdineButton;
    private JLabel totaleLabel;

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
        controller = new CatalogoController();
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
        StyleUtils.styleButton(homeButton);

        logoutButton.addActionListener(e -> goToLogin());
        modificaProfiloButton.addActionListener(e -> goToProfile());
        visualizzaOrdiniButton.addActionListener(e -> goToOrder());
        homeButton.addActionListener(e -> goToHome());

        // Pannello centrale
        viewPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        productsPanel = new JPanel();
        scrollPane.setViewportView(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        productsPanel.setLayout(new GridLayout(0, 3, 20, 20));

        //Carrello view
        carrelloPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        carrelloViewPanel = new JPanel();
        carrelloScrollPane.setViewportView(carrelloViewPanel);
        carrelloViewPanel.setLayout(new GridLayout(0, 1, 20, 20));
        carrelloPanel.setVisible(false);
        updateView();
    }

    public JPanel getPane(){
        return pane;
    }

    // Navigazione
    private void goToProduct(){}
    private void goToLogin(){ App.mostraLogin(); }
    private void goToCart(){
        carrelloPanel.setVisible(true);
        ArrayList<ProductInHome> p=new ArrayList<>();
        p.add(new ProductInHome("Prodotto 1", "100", "Categoria", "10", "img/products/1.png"));
        p.add(new ProductInHome("Prodotto 1", "100", "Categoria", "10", "img/products/13.png"));
        renderCarrello(p);
    }
    private void goToOrder(){}
    private void goToProfile(){
        ShowModificaProfiloDialog dialog = new ShowModificaProfiloDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
    private void goToHome(){
        carrelloPanel.setVisible(false);
    }


    private void updateView() {
        //mi salvo lo stato della main page
        String askFor = queryTextField.getText().trim();
        String categoria = categoriaComboBox.getSelectedIndex() == 0
                ? null
                : categoriaComboBox.getSelectedItem().toString();
        boolean soloOfferte = offerteCheckBox.isSelected();
        int ordinamento = ordineComboBox.getSelectedIndex();

        SwingWorker<List<String[]>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<String[]> doInBackground() {
                return controller.caricaProdotti(askFor, categoria, soloOfferte, ordinamento);
            }

            @Override
            protected void done() {
                try {
                    List<String[]> prodottiGrezzi = get();
                    ArrayList<ProductInHome> prodotti = convertToProduct(prodottiGrezzi);
                    renderProducts(prodotti);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            pane,
                            "Errore durante il caricamento dei prodotti.",
                            "Errore",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
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
    private void renderCarrello(ArrayList<ProductInHome> products) {
        carrelloViewPanel.removeAll();
        if(products == null) return;
        for(ProductInHome p : products){
            carrelloViewPanel.add(p.getPane());
        }
        carrelloViewPanel.revalidate();
        carrelloViewPanel.repaint();
    }

    private ArrayList<ProductInHome> convertToProduct(List<String[]> prodotti){
        if(prodotti == null) return null;
        ArrayList<ProductInHome> products = new ArrayList<>();
        for(String[] p : prodotti){
            products.add(new ProductInHome(p[CatalogoController.NOME],p[CatalogoController.PREZZO],p[CatalogoController.CATEGORIA],p[CatalogoController.SCONTO],p[CatalogoController.IMG_PATH]));
        }
        return products;
    }
}