package Boundary.ClientView.Carrello;

import Boundary.Utils.ImageUtils;
import Controller.CarrelloController;
import Controller.CatalogoController;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;


public class ProductInCart {
    private final Runnable onCartChanged;
    private final String id;
    private JLabel imgLabel;
    private JLabel qtLabel;
    private JLabel priceLabel;
    private JLabel nomeLabel;
    private JPanel pane;
    private JButton removeButton;
    private JButton addButton;
    private CarrelloController  carrelloController=new CarrelloController();
    public ProductInCart(String id,String qt,Runnable onCartChanged) {
        this.onCartChanged=onCartChanged;
        this.id=id;
        CatalogoController controller = new CatalogoController();
        String[] data=controller.caricaProdotto(Long.parseLong(id));
        if(data==null){
            return;
        }
        Font newFont = new Font("Swing ui", Font.BOLD, 16);
        qtLabel.setFont(newFont);
        priceLabel.setFont(newFont);
        addButton.setFont(newFont);
        removeButton.setFont(newFont);
        nomeLabel.setFont(newFont);
        imgLabel.setIcon(ImageUtils.getIconScaled(data[CatalogoController.IMG_PATH],100));
        qtLabel.setText("Selezionato: "+qt);
        priceLabel.setText("Prezzo: "+data[CatalogoController.PREZZO_CON_SCONTO]);
        nomeLabel.setText(data[CatalogoController.NOME]);
        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(204,203,207), 1, 10));


        //bottoni

        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
    }


    private void addProduct() {
        if (carrelloController.aggiungiAlCarrello(Long.parseLong(id), 1)) {
            int updatedQt = Integer.parseInt(qtLabel.getText().replace("Selezionato: ", "")) + 1;
            qtLabel.setText("Selezionato: " + updatedQt);
            onCartChanged.run();
        } else {
            JOptionPane.showMessageDialog(null, carrelloController.getMsg());
        }
    }
    private void removeProduct(){
        if(carrelloController.rimuoviDalCarrello(Long.parseLong(id),1)){
            int updatedQt = Integer.parseInt(qtLabel.getText().replace("Selezionato: ", "")) - 1;
            qtLabel.setText("Selezionato: "+updatedQt);
            onCartChanged.run();
        }else{
            JOptionPane.showMessageDialog(null, carrelloController.getMsg());
        }
    }
    public JPanel getPane(){return  pane;}
}
