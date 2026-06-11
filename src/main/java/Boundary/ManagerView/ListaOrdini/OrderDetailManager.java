package Boundary.ManagerView.ListaOrdini;


import Boundary.Template.Container.OrderContainer;


import javax.swing.*;


public class OrderDetailManager extends OrderContainer {
    public OrderDetailManager(String orderId) {
        super(orderId);
        setModalityType(ModalityType.APPLICATION_MODAL);
        pack();
        setLocationRelativeTo(null);
    }


    @Override
    protected void doOnEmpty() {
        containerViewPanel.add(new JLabel("Nessun prodotto presente in questo ordine."));
    }

    @Override
    protected void onBtn() {
        if(oContr.modificaOrdine(this.orderId, comboBoxStato.getSelectedItem().toString())){
            JOptionPane.showMessageDialog(null, "Stato ordine modificato con successo!");
        }else{
            JOptionPane.showMessageDialog(null,oContr.getError_msg());
        }
        dispose();
    }
}