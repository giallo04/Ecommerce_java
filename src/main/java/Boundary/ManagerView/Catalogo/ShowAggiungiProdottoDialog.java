package Boundary.ManagerView.Catalogo;



import Boundary.Template.ProdottoDataForm;
import Controller.CatalogoController;

import javax.swing.*;
import java.awt.*;

public class ShowAggiungiProdottoDialog extends ProdottoDataForm {
    private JLabel quantitaLabel;

    public ShowAggiungiProdottoDialog() {
        super();
        quantitaLabel=new JLabel("Quantità ");
        Font newFont = new Font("Segoe UI Black", Font.BOLD, 16);
        quantitaLabel.setFont(newFont);
        quantityPane.add(quantitaLabel, BorderLayout.WEST);
    }

    @Override
    protected void onOK() {
        String nome = this.nome.getText();
        String prezzo = this.prezzo.getText();
        String descrizione = this.descrizione.getText();
        String imgPath = this.imgPath.getText();
        String categoria = this.categoriaBox.getSelectedItem().toString();
        String quantita = this.quantita.getText();
        String sconto=String.valueOf(scontoSlider.getValue());
        if(nome.isEmpty() || prezzo.isEmpty() || descrizione.isEmpty() || imgPath.isEmpty() || categoria.isEmpty()|| quantita.isEmpty()){
            JOptionPane.showMessageDialog(null, "Compila tutti i campi");
            return;
        }else{
            CatalogoController controller=new CatalogoController();
            if(controller.aggiungiProdotto(nome,prezzo,descrizione,categoria,quantita,imgPath,sconto)){
                JOptionPane.showMessageDialog(null, "Prodotto aggiunto con successo");
            }else{
                JOptionPane.showMessageDialog(null, controller.getMsg());
                return;
            }
        }
        dispose();
    }


}
