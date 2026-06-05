package Boundary.ClientView.Catalogo;

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
    private JPanel offerPane;
    private JLabel offerLabel;
    private JLabel image;
    private JButton addToCart;
    private JLabel productName;
    private JLabel productPrice;
    private JLabel categoryLabel;
    private JPanel infoPanel;
    public ProductInHome(String name, String price, String category, String offer,String imgPath){
        //estetica
        Color customColor = new Color(79,70,229);
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setForeground(customColor);
        infoPanel.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 3, 20));
        StyleUtils.styleButton(addToCart);
        addToCart.setBackground(customColor);

        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(104,113,207), 2, 20));
        if(Integer.parseInt(offer)>0){
            offerPane.setVisible(true);
            offerPane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(242, 126, 91), 2, 20));
            offerLabel.setText("Prodotto scontato del "+offer+"%");
        }
        //Set parametri
        productName.setText(name);
        productPrice.setText(price);
        categoryLabel.setText(category);

       image.setIcon(ImageUtils.getIconScaled(imgPath,250));

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
            ProductDetail productDetail=new ProductDetail(getName());
            productDetail.pack();
            productDetail.setVisible(true);
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
public boolean isOffer(){
        return offerPane.isVisible();
}
    @Override
    public int compareTo(ProductInHome o) {
        return Float.compare(Float.parseFloat(productPrice.getText().replace("$ ","")), Float.parseFloat(o.productPrice.getText().replace("$","")));
    }

}
