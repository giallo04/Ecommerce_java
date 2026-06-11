package Boundary.Template.Container;

import com.formdev.flatlaf.ui.FlatLineBorder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public abstract class Container extends JDialog {

    private JPanel ContainerPanel;
    private JScrollPane containerScrollPanel;
    protected JPanel containerViewPanel;
    protected JButton salvaBtn;
    protected JLabel totaleLabel;
    protected JButton backButton;
    protected JPanel infoPanel;

    public Container() {
        setContentPane(ContainerPanel);

        int arc = 20;
        Color customColor = new Color(79, 70, 229);

        ContainerPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), customColor, 0, arc));
        containerViewPanel = new JPanel();
        containerScrollPanel.setViewportView(containerViewPanel);
        containerScrollPanel.getVerticalScrollBar().setUnitIncrement(20);
        containerViewPanel.setLayout(new GridLayout(0, 1, 0, 0));
        containerScrollPanel.getHorizontalScrollBar().setEnabled(false);
        infoPanel.setLayout(new GridLayout(0, 1, 0, 0));

       //Viene configurato secondo le necessità con gli hook
        salvaBtn.setVisible(isSaveBtnVisible());
        salvaBtn.setEnabled(false);
        salvaBtn.addActionListener(e -> onOK());

        backButton.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        ContainerPanel.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    // --- Methods  hooks ---

   //hook per il salvaBtn
    protected boolean isSaveBtnVisible() {
        return true;
    }

    //Hook per la comboBox
    protected boolean isStatoEditable() {
        return true;
    }

//default per quando cambia la combo box
    protected void onStatoChanged() {
        salvaBtn.setEnabled(true);
    }


    //Logica tasti

    private void onOK() {
        onBtn();
    }

    private void onCancel() {
        dispose();
    }

    // --- Metodi astratti

    protected abstract void doOnEmpty();
    protected abstract List<String[]> loadRows();
    protected abstract void addRow(List<String[]> rows);
    protected abstract void onBtn();


    public void refreshContainer() {
        List<String[]> rows = loadRows();
        containerViewPanel.removeAll();
        if (rows == null || rows.isEmpty()) {
            doOnEmpty();
            return;
        }
        addRow(rows);
        containerViewPanel.revalidate();
        containerViewPanel.repaint();
    }
}