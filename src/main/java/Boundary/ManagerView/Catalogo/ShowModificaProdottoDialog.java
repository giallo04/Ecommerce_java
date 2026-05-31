package Boundary.ManagerView.Catalogo;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class ShowModificaProdottoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nome;
    private JTextField prezzo;
    private JTextArea descrizione;
    private JTextField imgPath;
    private JButton openButton;
    private JTextField categoria;
    private JSlider scontoSlider;
    private JComboBox choice;
    private JTextField  quantita;
    private JLabel labelQuantita;

    public ShowModificaProdottoDialog(String nome) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //TODO Load the card with the products data


        scontoSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelQuantita.setText(String.valueOf(scontoSlider.getValue()));
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                imgPath.setText(filePath);
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
        String nome = this.nome.getText();
        String prezzo = this.prezzo.getText();
        String descrizione = this.descrizione.getText();
        String imgPath = this.imgPath.getText();
        String categoria = this.categoria.getText();
        String quantita = this.quantita.getText();
        if(choice.getSelectedItem().equals("Rimuovi")) {
            quantita = "-"+quantita;;
        }
        try {


            if (nome.isEmpty() || Integer.parseInt(prezzo)<0 || descrizione.isEmpty() || imgPath.isEmpty() || categoria.isEmpty() | Integer.parseInt(quantita) < 0) {
                JOptionPane.showMessageDialog(null, "Compila tutti i campi");
                return;
            } else {
                if (Controller.Stub.addProduct(imgPath, nome, prezzo, descrizione)) {//TODO controller modify product
                    JOptionPane.showMessageDialog(null, "Prodotto modificato con successo");
                } else {
                    JOptionPane.showMessageDialog(null, "Errore durante la modifica del prodotto");
                }
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Inserisci un numero valido");
            return;
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }

}
