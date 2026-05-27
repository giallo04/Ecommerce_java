package GUI;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductInHome {
    private JPanel pane;
    private JLabel image;
    private JButton addToCart;
    private JLabel productName;
    private JLabel productPrice;
    private JLabel categoryLabel;
    private JPanel infoPanel;
    public ProductInHome(String name, String price, String category){
        //estetica
        Color customColor = new Color(79,70,229);
        infoPanel.setBackground(customColor);
        infoPanel.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 0, 20));
        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(104,113,207), 2, 20));


        //Set parametri
        productName.setText(name);
        productPrice.setText(price);
        categoryLabel.setText(category);
        image.setIcon(new ImageIcon(getClass().getResource("/products/img.png")));//just a test


        //codice per hover pane custom
        Color coloreNormale = new Color(245, 245, 245);
        Color coloreHover = new Color(224, 231, 255);
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pane.setBackground(coloreHover);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                pane.setBackground(coloreNormale);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                goToProduct();
            }
        });
    }
    public JPanel getPane(){
        return pane;
    }
    private void goToProduct(){
        //TODO implementare la pagina del prodotto
        System.out.println("prodotto cliccato");
    }
    private void addToCart(){
        //TODO implementare l'aggiunta al carrello' chiamando il controller
        System.out.println("prodotto aggiunto al carrello");
    }
}
