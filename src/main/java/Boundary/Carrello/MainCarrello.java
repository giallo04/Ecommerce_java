package Boundary.Carrello;

import Boundary.DTO.RigaCarrelloDTO;
import Controller.CarrelloController;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.List;
import java.util.Locale;

public class MainCarrello {

    private JPanel mainPanel;
    private JLabel prezzoTotale;
    private JButton buttonEffettuaOrdine;
    private JLabel nameUtente;
    private JButton modificaProfiloButton;
    private JButton visualizzaOrdiniButton;
    private JLabel photoProfilo;
    private JScrollPane scrollpanel;
    private JPanel catalogo;

    public MainCarrello() {
        // Assicuriamo l'esistenza dei pannelli fondamentali
        if (mainPanel == null) mainPanel = new JPanel(new BorderLayout());
        if (catalogo == null) catalogo = new JPanel();
        if (scrollpanel == null) scrollpanel = new JScrollPane(catalogo);

        initListeners();
    }

    private void initListeners() {
        if (modificaProfiloButton != null) {
            modificaProfiloButton.addActionListener(e -> System.out.println("Bottone Modifica Profilo cliccato!"));
        }
        if (visualizzaOrdiniButton != null) {
            visualizzaOrdiniButton.addActionListener(e -> System.out.println("Bottone Ordini cliccato!"));
        }
        if (buttonEffettuaOrdine != null) {
            buttonEffettuaOrdine.addActionListener(e -> System.out.println("Bottone Effettua Ordine cliccato!"));
        }
    }

    /**
     * Sostituito e corretto per forzare il refresh del Viewport dello scrollpanel
     */
    public void popolaCatalogoProdotti(List<RigaCarrelloDTO> listaProdotti, CarrelloController controller) {
        // 1. Svuota completamente il pannello catalogo da vecchi elementi
        catalogo.removeAll();

        // 2. Forza il layout a griglia (0 righe = dinamiche, 1 colonna, 10px di spazio verticale)
        catalogo.setLayout(new GridLayout(0, 1, 0, 10));

        // 3. Ciclo sui prodotti reali passati dal controller
        for (RigaCarrelloDTO prod : listaProdotti) {
            panelProdotti cardProdotto = new panelProdotti(prod, controller);

            // Inseriamo la radice visiva della singola card nel catalogo
            catalogo.add(cardProdotto.$$$getRootComponent$$$());
        }

        // 4. CORREZIONE COSTRUTTIVA: Riaffida il catalogo modificato allo scrollpanel
        // Questo ricalcola l'altezza delle card (evita che collassino a 0 pixel)
        scrollpanel.setViewportView(catalogo);

        // 5. Chiediamo a Swing di ricalcolare i componenti e ridisegnare la grafica
        catalogo.revalidate();
        catalogo.repaint();
        scrollpanel.revalidate();
        scrollpanel.repaint();
        mainPanel.revalidate();
        mainPanel.repaint();

        System.out.println("[DEBUG UI] Prodotti inseriti a schermo. Totale card: " + listaProdotti.size());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    {
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-16776961));
        mainPanel.setForeground(new Color(-16776961));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Segoe UI Black", -1, 36, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-1));
        label1.setText("SHOPFLOW \uD83C\uDF10");
        mainPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollpanel = new JScrollPane();
        mainPanel.add(scrollpanel, new GridConstraints(1, 1, 5, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        catalogo = new JPanel();
        catalogo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        scrollpanel.setViewportView(catalogo);
        prezzoTotale = new JLabel();
        prezzoTotale.setForeground(new Color(-1));
        prezzoTotale.setText("Label");
        mainPanel.add(prezzoTotale, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonEffettuaOrdine = new JButton();
        buttonEffettuaOrdine.setBackground(new Color(-1));
        buttonEffettuaOrdine.setForeground(new Color(-16776961));
        buttonEffettuaOrdine.setText("Button");
        mainPanel.add(buttonEffettuaOrdine, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        nameUtente = new JLabel();
        nameUtente.setForeground(new Color(-1));
        nameUtente.setText("Label");
        mainPanel.add(nameUtente, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        modificaProfiloButton = new JButton();
        modificaProfiloButton.setBackground(new Color(-1));
        modificaProfiloButton.setContentAreaFilled(true);
        Font modificaProfiloButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 18, modificaProfiloButton.getFont());
        if (modificaProfiloButtonFont != null) modificaProfiloButton.setFont(modificaProfiloButtonFont);
        modificaProfiloButton.setForeground(new Color(-16776961));
        modificaProfiloButton.setMargin(new Insets(0, 0, 0, 0));
        modificaProfiloButton.setText("Modifica profilo1");
        mainPanel.add(modificaProfiloButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        visualizzaOrdiniButton = new JButton();
        visualizzaOrdiniButton.setBackground(new Color(-1));
        visualizzaOrdiniButton.setContentAreaFilled(true);
        Font visualizzaOrdiniButtonFont = this.$$$getFont$$$("Segoe UI Black", -1, 18, visualizzaOrdiniButton.getFont());
        if (visualizzaOrdiniButtonFont != null) visualizzaOrdiniButton.setFont(visualizzaOrdiniButtonFont);
        visualizzaOrdiniButton.setForeground(new Color(-16776961));
        visualizzaOrdiniButton.setText("Ordini");
        mainPanel.add(visualizzaOrdiniButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        photoProfilo = new JLabel();
        photoProfilo.setBackground(new Color(-16776961));
        photoProfilo.setIcon(new ImageIcon(getClass().getResource("/users/dios@napoli_1.png")));
        photoProfilo.setText("");
        mainPanel.add(photoProfilo, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(150, 150), new Dimension(150, 150), 0, false));
    }

    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    public void aggiornaTotale(float totale) {
        prezzoTotale.setText(String.format("Totale: € %.2f", totale));
    }
}