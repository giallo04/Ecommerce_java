package Boundary.ManagerView.ListaOrdini;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderDetails extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel dataLabel;
    private JLabel indirizzoLabel;
    private JComboBox comboBoxStato;
    private JLabel statoLabel;
    private boolean isConfirmed = false;

    public OrderDetails(Frame owner, String data, String indirizzo, String statoAttuale) {
        super(owner, true); // Imposta il frame proprietario e la modalità modale
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


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
        // add your code here
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
        return (String) comboBoxStato.getSelectedItem();
    }

    /*
    public static void main(String[] args) {
        OrderDetails dialog = new OrderDetails();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

     */
}
