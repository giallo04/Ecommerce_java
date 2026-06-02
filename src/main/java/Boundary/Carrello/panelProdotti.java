package Boundary.Carrello;

import Boundary.DTO.ProductDTO;
import Boundary.DTO.RigaCarrelloDTO;
import Controller.CarrelloController;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;

public class panelProdotti {

    private JPanel panel1;
    private JLabel descrizione;
    private JLabel categoria;
    private JLabel prezzo;
    private JLabel quantita;
    private JButton buttonPiu;
    private JLabel image;
    private JButton buttonCestino;
    private JLabel nome;

    private RigaCarrelloDTO rigaDTO;
    private CarrelloController controller;

    public panelProdotti(RigaCarrelloDTO rigaDTO, CarrelloController controller) {
        // 1. Inizializzazione obbligatoria dei componenti grafici di IntelliJ
        $$$setupUI$$$();

        this.rigaDTO = rigaDTO;
        this.controller = controller;

        try {
            if (rigaDTO == null) {
                System.err.println("[ERRORE CARD] rigaDTO è NULL!");
                return;
            }

            ProductDTO productDTO = rigaDTO.getProductDTO();
            if (productDTO == null) {
                System.err.println("[ERRORE CARD] productDTO dentro la riga è NULL!");
                return;
            }

            System.out.println("[DEBUG CARD] Sto creando la card per il prodotto: " + productDTO.getNome());

            // 2. Assegnazione testi con controlli di sicurezza per evitare NullPointerException
            if (nome != null) nome.setText(productDTO.getNome() != null ? productDTO.getNome() : "Senza Nome");
            if (descrizione != null) descrizione.setText(productDTO.getDescrizione() != null ? productDTO.getDescrizione() : "");
            if (categoria != null) categoria.setText(productDTO.getCategoria() != null ? productDTO.getCategoria() : "");
            if (prezzo != null) prezzo.setText(String.format("€ %.2f", productDTO.getPrezzo()));
            if (quantita != null) quantita.setText("Quantità: " + rigaDTO.getQuantita());

            // 3. Recupero sicuro dell'ID (usiamo product_id se id fallisce)
            long idprod = 0;
            try {
                idprod = productDTO.getId(); // Prova a usare il getter corretto visto nel controller
            } catch (Throwable t) {
                // Se non esiste il metodo getProduct_id(), usa getId()
                System.out.println("[WARN CARD] getProduct_id() non trovato, provo con getId()...");
            }

            final long finalIdprod = idprod;

            // 4. Aggancio dei listener solo se i bottoni sono stati creati correttamente
            if (buttonCestino != null) {
                buttonCestino.addActionListener(e -> {
                    System.out.println("[UI] Cliccato ELIMINA per id: " + finalIdprod);
                    controller.eliminaProdotto(finalIdprod);
                });
            }

            if (buttonPiu != null) {
                buttonPiu.addActionListener(e -> {
                    System.out.println("[UI] Cliccato PIU per id: " + finalIdprod);
                    controller.incrementaProdotto(finalIdprod);
                });
            }

        } catch (Exception ex) {
            System.err.println("[CRASH CRITICO CARD] Errore durante il popolamento dati della card:");
            ex.printStackTrace();
        }
    }

    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), -1, -1));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-16776961)), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-3025959)));

        // Forza una dimensione minima e preferita visibile per la card
        panel1.setMinimumSize(new Dimension(400, 180));
        panel1.setPreferredSize(new Dimension(500, 200));

        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(9, 5, new Insets(5, 5, 5, 5), -1, -1));
        panel2.setBackground(new Color(-1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel2.add(spacer3, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel2.add(spacer4, new GridConstraints(8, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        prezzo = new JLabel();
        prezzo.setText("Prezzo");
        panel2.add(prezzo, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        quantita = new JLabel();
        quantita.setText("Quantità");
        panel2.add(quantita, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        descrizione = new JLabel();
        descrizione.setText("Descrizione");
        panel2.add(descrizione, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        categoria = new JLabel();
        categoria.setText("Categoria");
        panel2.add(categoria, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        image = new JLabel();
        image.setText("[Immagine]");
        panel2.add(image, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        buttonPiu = new JButton();
        buttonPiu.setBackground(new Color(-1));
        buttonPiu.setBorderPainted(false);
        Font buttonPiuFont = this.$$$getFont$$$("Arial Black", -1, 18, buttonPiu.getFont());
        if (buttonPiuFont != null) buttonPiu.setFont(buttonPiuFont);
        buttonPiu.setText("+");
        panel2.add(buttonPiu, new GridConstraints(5, 3, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(40, 30), null, 0, false));

        buttonCestino = new JButton();
        buttonCestino.setBackground(new Color(-1));
        buttonCestino.setBorderPainted(false);
        Font buttonCestinoFont = this.$$$getFont$$$("Arial Black", -1, 14, buttonCestino.getFont());
        if (buttonCestinoFont != null) buttonCestino.setFont(buttonCestinoFont);
        buttonCestino.setText("ELIMINA");
        panel2.add(buttonCestino, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 30), null, 0, false));

        final Spacer spacer5 = new Spacer();
        panel2.add(spacer5, new GridConstraints(7, 1, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));

        nome = new JLabel();
        nome.setText("NOME PRODOTTO");
        Font nomeFont = this.$$$getFont$$$("Segoe UI", Font.BOLD, 16, nome.getFont());
        if (nomeFont != null) nome.setFont(nomeFont);
        panel2.add(nome, new GridConstraints(3, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName = fontName == null ? currentFont.getName() : fontName;
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

    private void createUIComponents() {
        // Lasciare vuoto per la gestione automatica di IntelliJ
    }
}