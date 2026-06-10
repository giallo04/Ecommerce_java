package Boundary.ManagerView.ListaOrdini;

import Controller.OrdiniController;
import Entity.Ordini.Ordine;
import Entity.Ordini.RigaOrdine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class OrderDetails extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel dataLabel;
    private JLabel indirizzoLabel;
    private JComboBox comboBoxStato;
    private JLabel statoLabel;
    private JPanel productInOrdinePanel;
    private JLabel orderIdLabel;
    private JLabel totaleLabel;
    private boolean isConfirmed = false;

    public OrderDetails(Frame owner,String order_id, String data, String indirizzo, String statoAttuale) {
        super(owner, true); // Imposta il frame proprietario e la modalità modale
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        OrdiniController oContr = new OrdiniController();

        //-- Info --
        orderIdLabel = new JLabel("Order ID: " + order_id);
        orderIdLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        dataLabel = new JLabel("Data: " + data);
        dataLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        indirizzoLabel = new JLabel("Indirizzo: " + indirizzo);
        indirizzoLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        totaleLabel = new JLabel("Totale: €" + oContr.totaleOrdine(order_id));
        totaleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        contentPane.add(orderIdLabel);
        contentPane.add(dataLabel);
        contentPane.add(indirizzoLabel);
        contentPane.add(totaleLabel);

        //-- Pannello RigaOrdine --
        productInOrdinePanel.setLayout(new BoxLayout(productInOrdinePanel, BoxLayout.Y_AXIS));
        List<String[]> righeOrdine = oContr.caricaRigheOrdine(order_id);
        for(String[] riga: righeOrdine){
            productInOrdinePanel.add(new ProductInOrdine(owner, riga));
            productInOrdinePanel.add(Box.createVerticalStrut(5)); //spaziatura
        }


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(oContr, order_id);
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

    private void onOK(OrdiniController oContr, String order_id) {
        oContr.modificaOrdine(order_id, getNuovoStato());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public String getNuovoStato() {
        //aggiungere qui codice da copiare da visualizzaOrdini
        return (String) comboBoxStato.getSelectedItem();
    }

}
