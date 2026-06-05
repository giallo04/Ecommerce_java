package Boundary.ManagerView.Catalogo;



import Controller.CatalogoController;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.File;
import java.util.List;

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
    private JComboBox categoriaBox;

    public ShowAggiungiProdottoDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        CatalogoController controller = new CatalogoController();
        List<String> categorie=controller.getCategorie();
        for(String c:categorie){
            categoriaBox.addItem(c);
        }
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                imgPath.setText(filePath);
            }
        });
        imgPath.setDropTarget(new DropTarget(imgPath, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            @SuppressWarnings("unchecked")
            public void drop(DropTargetDropEvent dtde) {
                try {
                    // Accetta il trascinamento come operazione di copia
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);

                    // Estrae i file rilasciati dal sistema operativo
                    List<File> droppedFiles = (List<File>) dtde.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    if (droppedFiles != null && !droppedFiles.isEmpty()) {
                        // Prendiamo il primo file rilasciato
                        File file = droppedFiles.get(0);
                        imgPath.setText(file.getAbsolutePath());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Errore durante il Drag & Drop del file.");
                }
            }
        }));

        qt.setModel(new SpinnerNumberModel(1, 0, 100, 1));
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
        String categoria = this.categoriaBox.getSelectedItem().toString();

        if(nome.isEmpty() || prezzo.isEmpty() || descrizione.isEmpty() || imgPath.isEmpty() || categoria.isEmpty()){
            JOptionPane.showMessageDialog(null, "Compila tutti i campi");
            return;
        }else{
            CatalogoController controller=new CatalogoController();
            if(controller.aggiungiProdotto(nome,prezzo,descrizione,categoria,String.valueOf(qt.getValue()),imgPath)){
                JOptionPane.showMessageDialog(null, "Prodotto aggiunto con successo");
            }else{
                JOptionPane.showMessageDialog(null, controller.getMsg());
            }

        }

        dispose();
    }

    private void onCancel() {
        // add your code here if necessary

        dispose();
    }

}
