package Boundary.Template.Container;

import com.formdev.flatlaf.ui.FlatLineBorder;
import javax.swing.*;
import java.awt.*;

public abstract class ContainerRow {

    protected final String id;
    protected JLabel imgLabel;
    protected JLabel qtLabel;
    protected JLabel priceLabel;
    protected JLabel nomeLabel;
    private JPanel pane;
    protected JPanel infoPanel;

    public ContainerRow(String id, String qt) {
        this.id = id;

        Font newFont = new Font("Segoe UI", Font.BOLD, 16);
        qtLabel.setText("Quantità: " + qt + "   ");
        qtLabel.setFont(newFont);
        priceLabel.setFont(newFont);
        nomeLabel.setFont(newFont);
        pane.setBorder(new FlatLineBorder(
                new Insets(0, 0, 0, 0),
                new Color(204, 203, 207), 1, 10
        ));

    }


    protected abstract void init(String id);

    public JPanel getPane() { return pane; }
}