package Boundary.ClientView;

import com.formdev.flatlaf.ui.FlatLineBorder;
import Boundary.Utils.StyleUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Boundary.Utils.ImageUtils;
public class ProductInHome implements Comparable<ProductInHome>{
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
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setForeground(customColor);
        infoPanel.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 3, 20));
        StyleUtils.styleButton(addToCart);
        addToCart.setBackground(customColor);

        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(104,113,207), 2, 20));


        //Set parametri
        productName.setText(name);
        productPrice.setText("$"+price);
        categoryLabel.setText(category);

        String imgUrl = "/products/" + name + ".png";
        imgUrl = imgUrl.replace(" ", "");
       image.setIcon(ImageUtils.getIconScaled(imgUrl,250));

        //codice per hover pane custom
        Color coloreNormale = new Color(245, 245, 245);
        Color coloreHover = new Color(224, 231, 255);
        addToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();
            }
        });
        pane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                pane.setBackground(coloreHover);
                image.setForeground(coloreHover);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                pane.setBackground(coloreNormale);
                image.setForeground(coloreHover);
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
        JOptionPane.showMessageDialog(null, "Prodotto aggiunto al carrello");
    }
    public String getName(){
        return productName.getText();
    }
    public String getCategory(){
        return categoryLabel.getText();
    }

    @Override
    public int compareTo(ProductInHome o) {
        return Integer.compare(Integer.parseInt(productPrice.getText().replace("$","")), Integer.parseInt(o.productPrice.getText().replace("$","")));
    }

}
