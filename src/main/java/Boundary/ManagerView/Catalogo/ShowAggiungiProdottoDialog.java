package Boundary.ManagerView.Catalogo;

import javax.swing.*;
import java.awt.event.*;

public class ShowAggiungiProdottoDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nome;
    private JTextField prezzo;
    private JTextArea descrizione;
    private JSpinner qt;
    private JTextField imgPath;
    private JButton openButton;
    private JTextField categoria;

    public ShowAggiungiProdottoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        if(nome.isEmpty() || prezzo.isEmpty() || descrizione.isEmpty() || imgPath.isEmpty() || categoria.isEmpty()){
            JOptionPane.showMessageDialog(null, "Compila tutti i campi");
            return;
        }else{
          if(Controller.Stub.addProduct(imgPath,nome,prezzo,descrizione) ){
              JOptionPane.showMessageDialog(null, "Prodotto aggiunto con successo");
          }else {
              JOptionPane.showMessageDialog(null, "Errore durante l'aggiunta del prodotto");
          }
        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }

}
