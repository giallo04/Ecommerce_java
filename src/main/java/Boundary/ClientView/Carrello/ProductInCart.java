package Boundary.ClientView.Carrello;

import Boundary.Template.Container.ContainerRow;
import Boundary.Utils.ImageUtils;
import Controller.CarrelloController;
import Controller.CatalogoController;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;


public class ProductInCart extends ContainerRow {
    private Runnable onCartChanged;
    private JButton removeButton;
    private JButton addButton;
    private CarrelloController  carrelloController=new CarrelloController();

    public ProductInCart(String id,String qt,Runnable onCartChanged) {
        super(id,qt);
        this.onCartChanged=onCartChanged;
        init(id);
    }
    @Override
    protected void init(String id){
        CatalogoController controller = new CatalogoController();
        String[] data=controller.caricaProdotto(Long.parseLong(id));
        if(data==null){
            return;
        }
        Font newFont = new Font("Swing ui", Font.BOLD, 16);
        imgLabel.setIcon(ImageUtils.getIconScaled(data[CatalogoController.IMG_PATH],100));
        priceLabel.setText("Prezzo: "+data[CatalogoController.PREZZO_CON_SCONTO]);
        nomeLabel.setText(data[CatalogoController.NOME]);

        //bottoni
        addButton=new JButton("+");
        removeButton=new JButton("-");
        addButton.setFont(newFont);
        removeButton.setFont(newFont);
        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        JPanel btnPanel=new JPanel(new BorderLayout());
        btnPanel.add(addButton,BorderLayout.NORTH);
        btnPanel.add(removeButton,BorderLayout.SOUTH);
        infoPanel.add(btnPanel,BorderLayout.SOUTH);

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
}
