package Boundary.ClientView.OrderDetail;

import Boundary.Template.Container.OrderContainer;

import javax.swing.*;

public class OrderDetailClient extends OrderContainer {
    public OrderDetailClient(String orderId) {
        super(orderId);

    }

    @Override
    protected boolean isSaveBtnVisible() { return false; }

    @Override
    protected boolean isStatoEditable() { return false; }

    @Override
    protected void onStatoChanged() {  }


    @Override
    protected void doOnEmpty() {
        containerViewPanel.add(new JLabel("Nessun prodotto presente in questo ordine."));
    }

    @Override
    protected void onBtn() {
    }
}