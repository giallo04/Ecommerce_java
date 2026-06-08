package Boundary.ClientView.Catalogo;

import Boundary.Utils.ImageUtils;
import Boundary.Utils.StyleUtils; // Assicurati di aver importato la tua utility
import Controller.CarrelloController;
import Controller.CatalogoController;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ProductDetail extends JDialog {
    private Runnable onCartChanged;
    private long productId;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea descrizione;
    private JLabel productName;
    private JLabel productPrice;
    private JButton addToCart;
    private JButton removeFromCart;
    private JLabel imgLabel;
    private JPanel offerPane;
    private JLabel offerLabel;
    private JLabel categoryLabel;
    private JLabel quantityLabel;
    private JLabel inMagazzinoLabel;
    private JPanel categoryPane;
    private JPanel generalInfoPane;
    private JLabel discountPrice;
    private int quantitaDisponibile;


    public ProductDetail(String name, Runnable onCartChanged) {
        this.onCartChanged = onCartChanged;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        CatalogoController controller = new CatalogoController();
        String[] data = controller.caricaProdotto(name);

        if (data == null) {
            JOptionPane.showMessageDialog(null, controller.getMsg());
            dispose();
            return;
        }

        initDetail(data);
        initListeners();
    }

    private void initDetail(String[] data) {

        productId = Long.parseLong(data[CatalogoController.ID]);
        productName.setText(data[CatalogoController.NOME]);
        productPrice.setText(data[CatalogoController.PREZZO]);
        descrizione.setText(data[CatalogoController.DESCRIZIONE]);
        offerLabel.setText(data[CatalogoController.SCONTO] + "% OFF");
        categoryLabel.setText(data[CatalogoController.CATEGORIA]);
        discountPrice.setText("New: "+data[CatalogoController.PREZZO_CON_SCONTO]);
        quantitaDisponibile = Integer.parseInt(data[CatalogoController.QUANTITA]);
        quantityLabel.setText("0");

        if (quantitaDisponibile > 0) {
            inMagazzinoLabel.setText("In magazzino (" + quantitaDisponibile + ")");
        } else {
            inMagazzinoLabel.setText("Non disponibile");
            inMagazzinoLabel.setForeground(new Color(150, 0, 0));
            addToCart.setEnabled(false);
            removeFromCart.setEnabled(false);
            buttonOK.setEnabled(false);
        }


        imgLabel.setIcon(ImageUtils.getIconScaled(data[CatalogoController.IMG_PATH], 250));
        imgLabel.setVisible(true);

        boolean hasDiscount = Integer.parseInt(data[CatalogoController.SCONTO]) > 0;
        offerPane.setVisible(hasDiscount);
        discountPrice.setVisible(hasDiscount);
        if(hasDiscount){
            productPrice.setText("Old: "+data[CatalogoController.PREZZO]);
        }

        offerPane.setBorder(new FlatLineBorder(new Insets(4, 10, 4, 10), new Color(242, 126, 91), 1, 12));
        offerPane.setBackground(new Color(242, 126, 91));
        categoryPane.setBorder(new FlatLineBorder(new Insets(4, 10, 4, 10), new Color(104, 113, 207), 1, 12));
        categoryPane.setBackground(new Color(100, 130, 207));
        generalInfoPane.setBorder(new FlatLineBorder(new Insets(4, 10, 4, 10), new Color(30, 30, 30), 1, 20));
        generalInfoPane.setBackground(new Color(50, 50, 50));



        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        descrizione.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        productName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        productPrice.setFont(new Font("Segoe UI", Font.BOLD, 24));
        discountPrice.setFont(new Font("Segoe UI", Font.BOLD, 24));
        inMagazzinoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        offerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        descrizione.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        productPrice.setBorder(null);
        discountPrice.setBorder(null);

        StyleUtils.styleButton(addToCart);
        StyleUtils.styleButton(removeFromCart);
        StyleUtils.styleButton(buttonOK);
        StyleUtils.styleButton(buttonCancel);


        addToCart.setHorizontalAlignment(SwingConstants.CENTER);
        removeFromCart.setHorizontalAlignment(SwingConstants.CENTER);
        buttonOK.setHorizontalAlignment(SwingConstants.CENTER);
        buttonCancel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initListeners() {
        addToCart.addActionListener(e -> updateQuantity(1));
        removeFromCart.addActionListener(e -> updateQuantity(-1));

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });


        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    private void updateQuantity(int delta) {
        int currentQuantity = Integer.parseInt(quantityLabel.getText());
        int newQuantity = currentQuantity + delta;

        if (newQuantity < 0) {
            newQuantity = 0;
        }
        if (newQuantity > quantitaDisponibile) {
            newQuantity =quantitaDisponibile;
        }

        quantityLabel.setText(String.valueOf(newQuantity));
    }

    private void onOK() {
        CarrelloController controller = new CarrelloController();
        if(quantityLabel.getText().equals("0")) {
            JOptionPane.showMessageDialog(null,"Aggiungi al carrello prima di procedere");
            return;
        }else {
            if (controller.aggiungiAlCarrello(productId, Integer.parseInt(quantityLabel.getText()))){
                onCartChanged.run();
                JOptionPane.showMessageDialog(null, "Prodotto aggiunto al carrello");
                dispose();
            }else{
                JOptionPane.showMessageDialog(null, controller.getMsg());
                return;
            }
        }
    }

    private void onCancel() {
        dispose();
    }
}