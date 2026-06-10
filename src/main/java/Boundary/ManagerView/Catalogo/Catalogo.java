package Boundary.ManagerView.Catalogo;
import Boundary.Template.TablePane;
import Controller.CatalogoController;
import Boundary.Utils.TableUtils;

import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Catalogo extends TablePane {

    public Catalogo() {
        super(new String[]{"ID", "Nome", "Prezzo","Quantità","Categoria", "Sconto"},"Gestione Catalogo");
    }
    @Override
    protected void loadTableData() {
        //Static definition of the table
        //load products from database
        CatalogoController controller=new CatalogoController();
        List <String[]> dati=controller.caricaCatalogo();
        if(dati!=null) {
            for (String[] d : dati) {
                String id=d[CatalogoController.ID];
                String nome=d[CatalogoController.NOME];
                String prezzo=d[CatalogoController.PREZZO];
                String quantita=d[CatalogoController.QUANTITA];
                String categoria=d[CatalogoController.CATEGORIA];
                String sconto=d[CatalogoController.SCONTO];
                model.addRow(new String[]{id,nome,prezzo,quantita,categoria,sconto});
            }
        }
    }

    @Override
    protected JPanel buildActionPanel() {
        JButton btnMod   = createButton("Modifica prodotto");
        JButton btnRemove = createButton("Rimuovi");
        JButton btnAdd    = createButton("Aggiungi prodotto");

        btnMod.addActionListener(e -> onMod());
        btnRemove.addActionListener(e -> onRemove());
        btnAdd.addActionListener(e -> onAdd());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panel.setOpaque(false);
        panel.add(btnMod);
        panel.add(btnRemove);
        panel.add(btnAdd);
        return panel;
    }

//Action handlers


    private void onMod() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {JOptionPane.showMessageDialog(pane, "Seleziona un prodotto da modificare"); return;}
        String id=model.getValueAt(viewRow,0).toString();
        ShowModificaProdottoDialog dialog = new ShowModificaProdottoDialog(id);
        dialog.pack();
        dialog.setLocationRelativeTo(pane);
        dialog.setVisible(true);
        loadTableData();
    }

    private void onRemove() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(pane, "Seleziona un prodotto.");   return; }
        String id=model.getValueAt(viewRow,0).toString();
        CatalogoController controller=new CatalogoController();
        if(controller.eliminaProdotto(Long.parseLong(id))){
            JOptionPane.showMessageDialog(pane, "Prodotto eliminato con successo");
        }else {
            JOptionPane.showMessageDialog(pane, controller.getMsg());
        }
        loadTableData();
    }

    private void onAdd() {
        ShowAggiungiProdottoDialog dialog = new ShowAggiungiProdottoDialog();
        dialog.pack();
        dialog.setLocationRelativeTo(pane);
        dialog.setVisible(true);
        loadTableData();
    }
}