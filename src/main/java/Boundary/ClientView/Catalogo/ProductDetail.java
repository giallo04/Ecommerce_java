package Boundary.ClientView.Catalogo;

import Boundary.Utils.ImageUtils;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductDetail extends JDialog {
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

    public ProductDetail(String name) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //Get the product info from the controller
//TODO Load the card with the products data
        offerPane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(242, 126, 91), 2, 20));
        categoryPane.setBorder(new FlatLineBorder(new Insets(10,10,10,10),new Color(104,113,207), 2, 20));

        quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        descrizione.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        productName.setFont(new Font("Segoe UI", Font.BOLD, 24));
        productPrice.setFont(new Font("Segoe UI", Font.BOLD, 24));
        inMagazzinoLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        categoryLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        addToCart.setFont(new Font("Segoe UI", Font.BOLD, 16));
        removeFromCart.setFont(new Font("Segoe UI", Font.BOLD, 16));



        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quantityLabel.setText(String.valueOf(Integer.parseInt(quantityLabel.getText())+1));
            }
        });
        removeFromCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(quantityLabel.getText())==0) quantityLabel.setText("0");
                else quantityLabel.setText(String.valueOf(Integer.parseInt(quantityLabel.getText())-1));
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        // add your code here TODO aggiungere al carrello
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
