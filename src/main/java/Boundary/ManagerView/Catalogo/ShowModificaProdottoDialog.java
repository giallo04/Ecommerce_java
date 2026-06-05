package Boundary.ManagerView.Catalogo;

import Controller.CatalogoController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.List;

public class ShowModificaProdottoDialog extends JDialog {
    private final Long id;
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
    private JComboBox categoriaBox;
    private boolean newImg=false;
    public ShowModificaProdottoDialog(String id) {
        this.id=Long.parseLong(id);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        CatalogoController controller = new CatalogoController();
        String[] data=controller.caricaProdotto(Long.parseLong(id));
        if(data==null){
            JOptionPane.showMessageDialog(null, controller.getMsg());
            dispose();
            return;
        }
        List<String> categorie=controller.getCategorie();
        for(String c:categorie){
            categoriaBox.addItem(c);
        }

        nome.setText(data[CatalogoController.NOME]);
        prezzo.setText(data[CatalogoController.PREZZO].replace("$ ",""));
        descrizione.setText(data[CatalogoController.DESCRIZIONE]);
        imgPath.setText(data[CatalogoController.IMG_PATH]);
        categoriaBox.setSelectedItem(data[CatalogoController.CATEGORIA]);
        quantita.setText("0");
        scontoSlider.setValue(Integer.parseInt(data[CatalogoController.SCONTO]));
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
                newImg=true;
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
        String imgPath ="" ;
        if(newImg) imgPath = this.imgPath.getText();
        String categoria = this.categoriaBox.getSelectedItem().toString();
        String quantita = this.quantita.getText();
        String sconto=String.valueOf(scontoSlider.getValue());
        if(choice.getSelectedItem().equals("Rimuovi")) {
            quantita = "-"+quantita;;
        }
        try {
            if (nome.isEmpty() || Float.parseFloat(prezzo)<0 || descrizione.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Compila tutti i campi");
                return;
            } else {
                CatalogoController controller = new CatalogoController();
                if (controller.modificaProdotto(id, nome, prezzo, descrizione, categoria, quantita,sconto,imgPath)) {
                    JOptionPane.showMessageDialog(null, "Prodotto modificato con successo");
                } else {
                    JOptionPane.showMessageDialog(null, controller.getMsg());
                    return;
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
