package Boundary.ManagerView.Catalogo;

import Boundary.Template.ProdottoDataForm;
import Controller.CatalogoController;

import javax.swing.*;
import java.awt.*;

public class ShowModificaProdottoDialog extends ProdottoDataForm {
    private final String id;
    private boolean newImg=false;
    private JComboBox choice;
    public ShowModificaProdottoDialog(String id) {
        super();
        this.id = id;
        fillForm();
    }

    private void fillForm(){
        CatalogoController controller = new CatalogoController();
        String[] data = controller.caricaProdotto(Long.parseLong(id));
        if (data == null) {
            JOptionPane.showMessageDialog(null, controller.getMsg());
            dispose();
            return;
        }
        nome.setText(data[CatalogoController.NOME]);
        prezzo.setText(data[CatalogoController.PREZZO].replace("$ ", "").replace(",", "."));
        descrizione.setText(data[CatalogoController.DESCRIZIONE]);
        imgPath.setText(data[CatalogoController.IMG_PATH]);
        categoriaBox.setSelectedItem(data[CatalogoController.CATEGORIA]);
        quantita.setText("0");
        scontoSlider.setValue(Integer.parseInt(data[CatalogoController.SCONTO]));
        choice=new JComboBox();
        choice.addItem("Aggiungi");
        choice.addItem("Rimuovi");
        quantityPane.add(choice, BorderLayout.WEST);
        Font newFont = new Font("Segoe UI Black", Font.BOLD, 16);
        choice.setFont(newFont);
    }
    @Override
    protected void onOK() {
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
            if (nome.isEmpty() || prezzo.isEmpty() || descrizione.isEmpty() || categoria.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Compila tutti i campi");
                return;
            } else {
                CatalogoController controller = new CatalogoController();
                if (controller.modificaProdotto(Long.parseLong(id), nome, prezzo, descrizione, categoria, quantita,sconto,imgPath)) {
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
    @Override
    protected void onOpen(){
        super.onOpen();
        newImg=true;
    }


}
