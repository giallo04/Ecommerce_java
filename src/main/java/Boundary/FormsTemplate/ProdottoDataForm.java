package Boundary.FormsTemplate;

import Controller.CatalogoController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.List;

public abstract class ProdottoDataForm extends JDialog {
    protected JPanel contentPane;
    protected JButton buttonOK;
    protected JButton buttonCancel;
    protected JTextField nome;
    protected JTextField prezzo;
    protected JTextArea descrizione;
    protected JTextField imgPath;
    protected JButton openButton;
    protected JTextField categoria;
    protected JSlider scontoSlider;
    protected JPanel quantityPane;
    protected JTextField  quantita;
    protected JLabel labelQuantita;
    protected JComboBox categoriaBox;
    protected ProdottoDataForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        CatalogoController controller = new CatalogoController();
        List<String> categorie=controller.getCategorie();
        for(String c:categorie){
            categoriaBox.addItem(c);
        }
        scontoSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelQuantita.setText(String.valueOf(scontoSlider.getValue()));
            }
        });
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOpen();
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

    protected abstract  void onOK() ;

    protected void onCancel() {
        // add your code here if necessary

        dispose();
    }
    protected void onOpen(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        imgPath.setText(filePath);
    }

}
