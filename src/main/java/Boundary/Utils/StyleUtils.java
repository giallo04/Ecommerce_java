package Boundary.Utils;

import com.formdev.flatlaf.ui.FlatLineBorder;

import javax.swing.*;
import java.awt.*;

public class StyleUtils {
    private StyleUtils() {}


    public static void styleButton(JButton button) {
        if (button == null) return;
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.putClientProperty("JButton.buttonType", "borderless");
        button.setMargin(new Insets(10, 15, 10, 15));
    }

    public static JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new FlatLineBorder(new Insets(0,0,0,0), new Color(79,70,229), 1, 12),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }
}
