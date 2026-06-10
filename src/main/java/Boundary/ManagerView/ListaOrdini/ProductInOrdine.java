package Boundary.ManagerView.ListaOrdini;

import Controller.CatalogoController;
import Entity.Ordini.RigaOrdine;

import javax.swing.*;
import java.awt.*;

public class ProductInOrdine extends JPanel {
    private JPanel contentPane;
    private JLabel quantitàLabel;
    private JLabel prezzoLabel;
    private JPanel pannelloInfo;
    private JPanel pannelloImg;
    private JLabel imgLabel;
    private JLabel nomeProdLabel;
    private JButton buttonOK;
    private JButton buttonCancel;

    public ProductInOrdine(Frame owner, String[] rigaOrdine) {

        // Recupera dati dalla RigaOrdine
        String qtaProdotto = rigaOrdine[1];
        String prezzo      = rigaOrdine[2];
        long productId   = Long.parseLong(rigaOrdine[0]);

        // Usa CatalogoController per ottenere nome e immagine
        CatalogoController catController = new CatalogoController();
        String[] prodotto = catController.caricaProdotto(productId);

        String nomeProd = (prodotto != null) ? prodotto[CatalogoController.NOME]     : "N/D";
        String imgPath  = (prodotto != null) ? prodotto[CatalogoController.IMG_PATH] : null;

        // --- Pannello immagine ---
        pannelloImg = new JPanel(new BorderLayout());
        pannelloImg.setPreferredSize(new Dimension(120, 120));

        if (imgPath != null) {
            ImageIcon icon = new ImageIcon(imgPath);
            Image scaled = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            imgLabel = new JLabel(new ImageIcon(scaled), SwingConstants.CENTER);
        } else {
            imgLabel = new JLabel("(img non disponibile)", SwingConstants.CENTER);
        }
        pannelloImg.add(imgLabel, BorderLayout.CENTER);

        // --- Pannello info ---
        pannelloInfo = new JPanel(new GridLayout(3, 1, 5, 5));
        pannelloInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nomeProdLabel = new JLabel("Prodotto: " + nomeProd);
        nomeProdLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));

        quantitàLabel = new JLabel("Quantità: " + qtaProdotto);
        prezzoLabel   = new JLabel("Prezzo: € " + prezzo);

        pannelloInfo.add(nomeProdLabel);
        pannelloInfo.add(quantitàLabel);
        pannelloInfo.add(prezzoLabel);



        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.add(pannelloImg,  BorderLayout.WEST);
        contentPane.add(pannelloInfo, BorderLayout.CENTER);

    }
}
