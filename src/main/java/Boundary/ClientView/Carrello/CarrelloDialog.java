package Boundary.ClientView.Carrello;

import Controller.CarrelloController;
import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CarrelloDialog extends JDialog {

    //Carrello view
    private JPanel carrelloPanel;
    private JScrollPane carrelloScrollPane;
    private JPanel carrelloViewPanel;
    private JButton effettuaOrdineButton;
    private JLabel totaleLabel;
    private JButton backButton;

    public CarrelloDialog() {
        setContentPane(carrelloPanel);

        //Carrello view

        int arc = 20;
        Insets insets = new Insets(arc, arc, arc, arc);
        Color customColor = new Color(79, 70, 229);


        carrelloPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        carrelloViewPanel = new JPanel();
        carrelloScrollPane.setViewportView(carrelloViewPanel);
        carrelloScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        carrelloViewPanel.setLayout(new GridLayout(0, 1, 0, 0));
        carrelloScrollPane.getHorizontalScrollBar().setEnabled(false);
        effettuaOrdineButton.addActionListener(e ->makeOrder());

        backButton.addActionListener(new ActionListener() {
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
        carrelloPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        refreshCart();
    }

    private void onOK() {
        makeOrder();
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }



    private void refreshCart() {
        CarrelloController controller = new CarrelloController();
        List<String[]> prodInCart = controller.caricaCarrello();
        totaleLabel.setText(controller.caricaTotale());

        carrelloViewPanel.removeAll();
        if (prodInCart == null) {
            totaleLabel.setText("Carrello vuoto");
            effettuaOrdineButton.setEnabled(false);
            return;
        };

        for (String[] prodotto : prodInCart) {
            ProductInCart p = new ProductInCart(
                    prodotto[CarrelloController.ID],
                    prodotto[CarrelloController.QUANTITA],
                    this::refreshCart
            );
            carrelloViewPanel.add(p.getPane());
        }
        carrelloViewPanel.revalidate();
        carrelloViewPanel.repaint();
    }

    private void makeOrder(){
        CarrelloController controller = new CarrelloController();
        if(controller.effettuaOrdine()){
            JOptionPane.showMessageDialog(null, "Ordine effettuato con successo!");
        }else{
            JOptionPane.showMessageDialog(null, controller.getMsg());
        }
        return;

    }


}
