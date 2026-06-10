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
        salvaBtn.addActionListener(e -> onOK());
        infoPanel.setLayout(new GridLayout(0, 1, 0, 0));
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

    private void onOK() {
        onBtn();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    protected abstract void doOnEmpty();
    protected abstract List<String[]> loadRows();
    protected abstract void addRow(List<String[]> rows);

    // Cambiato in public o protected così che la sottoclasse possa chiamarlo al momento giusto
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

    protected abstract void onBtn();
}