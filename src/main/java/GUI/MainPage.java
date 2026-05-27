package GUI;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage {
    private JPanel pane;
    private JCheckBox offerteCheckBox;
    private JComboBox categoriaComboBox;
    private JComboBox ordineComboBox;
    private JTextField queryTextField;
    private JButton ricercaButton;
    private JPanel leftPanel;
    private JPanel topPanel;
    private JButton carrelloButton;
    private JButton modificaProfiloButton;
    private JButton visualizzaOrdiniButton;
    private JPanel productsPanel;
    private JButton logoutButton;
    private JButton nextButton;
    private JButton backButton;

    public MainPage(){
        Color customColor = new Color(79,70,229);
        queryTextField.putClientProperty("JTextField.placeholderText", "Cerca prodotto...");
        leftPanel.setBackground(customColor);
        topPanel.setBackground(customColor);
        int arc=20;
        Insets insets=new Insets(arc,arc,arc,arc);
        leftPanel.setBorder(new FlatLineBorder(insets,customColor, 0, arc));
        topPanel.setBorder(new FlatLineBorder(insets,customColor, 0, arc));
        pane.setBackground(new Color(240,240,240));
        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 0, arc));
        productsPanel.setBorder(new FlatLineBorder(new Insets(0,0,0,0),customColor, 0, arc));

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.mostraLogin();
            }
        });
    }
    public JPanel getPane(){
        return pane;
    }
}
