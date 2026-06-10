package Boundary.Template.Container;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;


public  abstract class ContainerRow {
    protected final String id;
    protected JLabel imgLabel;
    protected JLabel qtLabel;
    protected JLabel priceLabel;
    protected JLabel nomeLabel;
    private JPanel pane;
    protected JPanel infoPanel;
    public ContainerRow(String id,String qt) {
        Font newFont = new Font("Swing ui", Font.BOLD, 16);
        this.id=id;
        qtLabel.setText(qt);
        qtLabel.setFont(newFont);
        priceLabel.setFont(newFont);
        nomeLabel.setFont(newFont);
        pane.setBorder(new FlatLineBorder(new Insets(0,0,0,0),new Color(204,203,207), 1, 10));
        init(id);
    }
    protected abstract void init(String id);

    public JPanel getPane(){return  pane;}
}
