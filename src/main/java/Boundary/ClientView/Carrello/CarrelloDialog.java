package Boundary.ClientView.Carrello;

import Boundary.Template.Container.Container;
import Controller.CarrelloController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CarrelloDialog extends Container {

    public CarrelloDialog() {
        super();
        setTitle("Carrello");
        salvaBtn.setText("Effettua Ordine");
        salvaBtn.setEnabled(true);
        refreshContainer();
    }


    @Override
    protected   List<String[]> loadRows(){
        CarrelloController controller = new CarrelloController();
        List<String[]> prodInCart = controller.caricaCarrello();
        totaleLabel.setText(controller.caricaTotale());
        containerViewPanel.removeAll();
        return prodInCart;
    }
    @Override
    protected   void addRow(List<String[]> prodInCart){
        for (String[] prodotto : prodInCart) {
            ProductInCart p = new ProductInCart(
                    prodotto[CarrelloController.ID],
                    prodotto[CarrelloController.QUANTITA],
                    this::refreshContainer
            );
            containerViewPanel.add(p.getPane());
        }
    }


    @Override
    protected void doOnEmpty() {
        totaleLabel.setText("Carrello vuoto");
        salvaBtn.setEnabled(false);
    }
    @Override
    protected void onBtn() {
        CarrelloController controller = new CarrelloController();
        if (controller.effettuaOrdine()) {
            JOptionPane.showMessageDialog(null, "Ordine effettuato con successo!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, controller.getMsg());
        }
        return;
    }


}
