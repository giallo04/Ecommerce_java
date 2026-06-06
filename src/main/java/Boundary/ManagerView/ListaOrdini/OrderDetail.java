package Boundary.ManagerView.ListaOrdini;

import Entity.Ordini.RigaOrdine;
import jakarta.persistence.Entity;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderDetail {
    private JPanel OrderPanel;
    private JComboBox statoOrdineButton;
    private JPanel modificaLowPanel;
    private JLabel dataLabel;
    private JLabel indirizzoLabel;
    private JLabel prodottiLabel;
    private JLabel totaleLabel;

    //costruttore
    public OrderDetail(ArrayList<RigaOrdine> rigaOrdine){
        OrderPanel = new JPanel(new BorderLayout(20, 0));
        modificaLowPanel = new JPanel(new BorderLayout());
        //aggiungere xLabel.setText...
    }

    public void VisualizzaResocontoOrdine(){
        statoOrdineButton.setPreferredSize(new Dimension(200, 20));
        statoOrdineButton.setFont(new Font("Segoe UI" ,Font.BOLD, 15));
        modificaLowPanel.add(statoOrdineButton, BorderLayout.EAST);
        modificaLowPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        OrderPanel.add(modificaLowPanel, BorderLayout.SOUTH);
    }
}


