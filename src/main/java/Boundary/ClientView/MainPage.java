package Boundary.ClientView;

import Boundary.App;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import Boundary.Utils.StyleUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Locale;
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

    public MainPage() {

        //Estetica setup
        int arc = 20;
        Insets insets = new Insets(arc, arc, arc, arc);
        Color customColor = new Color(79, 70, 229);

        //Finestra
        pane.setBackground(new Color(240, 240, 240));
        pane.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));

        //Pannello superiore
        queryTextField.putClientProperty("JTextField.placeholderText", "Cerca prodotto...");
        topPanel.setBackground(customColor);
        topPanel.setBorder(new FlatLineBorder(insets, customColor, 0, arc));

        //Pulsanti pannello superiore
        ricercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });//TODO add search function
        carrelloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToCart();
            }
        });
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
        leftPanel.setBackground(customColor);
        leftPanel.setBorder(new FlatLineBorder(insets, customColor, 0, arc));
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

        modificaProfiloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToProfile();
            }
        });
        visualizzaOrdiniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToOrder();
            }
        });

        //Pannello centrale
        viewPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        products.add(new ProductInHome("Cmf phone 2", "$100", "Elettronica"));
        products.add(new ProductInHome("Ps4", "$200", "Elettronica"));
        products.add(new ProductInHome("Air Force 1", "$160", "Moda"));
        products.add(new ProductInHome("Maglia Napoli", "$120", "Moda"));
        products.add(new ProductInHome("Iphone 17", "$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17", "$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17", "$150", "Elettronica"));
        products.add(new ProductInHome("Iphone 17", "$150", "Elettronica"));
        viewPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                renderProducts(products);
            }
        });
        productsPanel = new JPanel();
        scrollPane.setViewportView(productsPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        productsPanel.setLayout(new GridLayout(0, 3, 20, 20));

    }


    public JPanel getPane() {
        return pane;
    }

    //Navigazione
    private void goToProduct() {
    }

    private void goToLogin() {
        App.mostraLogin();
    }

    private void goToCart() {
    }

    private void goToOrder() {
    }

    private void goToProfile() {
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
    private void search() {
        String askFor = queryTextField.getText();
        if (askFor.isEmpty()) {
            renderProducts(products);
            return;
        }
        ;
        ArrayList<ProductInHome> askedProd = new ArrayList<>();
        for (ProductInHome p : products) {
            if (p.getName().toLowerCase().contains(askFor.toLowerCase())) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        pane = new JPanel();
        pane.setLayout(new GridLayoutManager(2, 2, new Insets(50, 20, 20, 20), -1, -1));
        pane.setForeground(new Color(-1));
        pane.setMaximumSize(new Dimension(2560, 1440));
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayoutManager(9, 1, new Insets(0, 0, 0, 0), -1, -1));
        Font leftPanelFont = this.$$$getFont$$$(null, -1, 18, leftPanel.getFont());
        if (leftPanelFont != null) leftPanel.setFont(leftPanelFont);
        leftPanel.setForeground(new Color(-1));
        pane.add(leftPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        leftPanel.add(spacer1, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        offerteCheckBox = new JCheckBox();
        Font offerteCheckBoxFont = this.$$$getFont$$$("Segoe UI Black", -1, 20, offerteCheckBox.getFont());
        if (offerteCheckBoxFont != null) offerteCheckBox.setFont(offerteCheckBoxFont);
        offerteCheckBox.setText("Offerte");
        leftPanel.add(offerteCheckBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ordineComboBox = new JComboBox();
        Font ordineComboBoxFont = this.$$$getFont$$$("Segoe UI Black", -1, 18, ordineComboBox.getFont());
        if (ordineComboBoxFont != null) ordineComboBox.setFont(ordineComboBoxFont);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Ordina per: In evidenza");
        defaultComboBoxModel1.addElement("Ordina per: Nome");
        defaultComboBoxModel1.addElement("Ordina per: Prezzo crescente");
        defaultComboBoxModel1.addElement("Ordina per: Prezzo decrescente");
        ordineComboBox.setModel(defaultComboBoxModel1);
        leftPanel.add(ordineComboBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Segoe UI Black", -1, 20, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Utente");
        leftPanel.add(label1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaProfiloButton = new JButton();
        Font modificaProfiloButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 20, modificaProfiloButton.getFont());
        if (modificaProfiloButtonFont != null) modificaProfiloButton.setFont(modificaProfiloButtonFont);
        modificaProfiloButton.setText("Modifica profilo");
        leftPanel.add(modificaProfiloButton, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        visualizzaOrdiniButton = new JButton();
        Font visualizzaOrdiniButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 20, visualizzaOrdiniButton.getFont());
        if (visualizzaOrdiniButtonFont != null) visualizzaOrdiniButton.setFont(visualizzaOrdiniButtonFont);
        visualizzaOrdiniButton.setText("Visualizza ordini");
        leftPanel.add(visualizzaOrdiniButton, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        leftPanel.add(spacer2, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        Font label2Font = this.$$$getFont$$$("Segoe UI Black", -1, 20, label2.getFont());
        if (label2Font != null) label2.setFont(label2Font);
        label2.setText("Impostazioni ricerca");
        leftPanel.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logoutButton = new JButton();
        Font logoutButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 20, logoutButton.getFont());
        if (logoutButtonFont != null) logoutButton.setFont(logoutButtonFont);
        logoutButton.setText("Logout");
        leftPanel.add(logoutButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        pane.add(topPanel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        categoriaComboBox = new JComboBox();
        Font categoriaComboBoxFont = this.$$$getFont$$$("Segoe UI Black", -1, 20, categoriaComboBox.getFont());
        if (categoriaComboBoxFont != null) categoriaComboBox.setFont(categoriaComboBoxFont);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("Categorie");
        defaultComboBoxModel2.addElement("Elettronica");
        defaultComboBoxModel2.addElement("Moda");
        defaultComboBoxModel2.addElement("Cibo");
        categoriaComboBox.setModel(defaultComboBoxModel2);
        topPanel.add(categoriaComboBox, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        queryTextField = new JTextField();
        topPanel.add(queryTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ricercaButton = new JButton();
        Font ricercaButtonFont = this.$$$getFont$$$(null, -1, 18, ricercaButton.getFont());
        if (ricercaButtonFont != null) ricercaButton.setFont(ricercaButtonFont);
        ricercaButton.setHorizontalTextPosition(0);
        ricercaButton.setText("\uD83D\uDD0D");
        topPanel.add(ricercaButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        carrelloButton = new JButton();
        Font carrelloButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 19, carrelloButton.getFont());
        if (carrelloButtonFont != null) carrelloButton.setFont(carrelloButtonFont);
        carrelloButton.setText("\uD83D\uDED2");
        topPanel.add(carrelloButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(60, 40), new Dimension(60, 40), new Dimension(60, 40), 0, false));
        viewPanel = new JPanel();
        viewPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        pane.add(viewPanel, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(920, 720), null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        viewPanel.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        viewPanel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        Font label3Font = this.$$$getFont$$$("Segoe UI Black", -1, 36, label3.getFont());
        if (label3Font != null) label3.setFont(label3Font);
        label3.setText("SHOPFLOW \uD83C\uDF10");
        pane.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return pane;
    }
}
