package Boundary.Utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageUtils {

    private ImageUtils() {}

    public static Icon getIconScaled(String imgUrl,int height) {
        if (imgUrl == null || imgUrl.trim().isEmpty()) {
            return getFallbackIcon(height);
        }
        try {
            URL url = ImageUtils.class.getResource(imgUrl);
            if (url == null) {
                return getFallbackIcon(height);
            }
            ImageIcon originalIcon = new ImageIcon(url);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(-1, 250, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            return getFallbackIcon(height);
        }
    }

    private static Icon getFallbackIcon(int height) {
        try {
            URL fallbackUrl = ImageUtils.class.getResource("/products/notFound.png");
            if (fallbackUrl != null) {
                ImageIcon fallbackIcon = new ImageIcon(fallbackUrl);
                Image scaledFallback = fallbackIcon.getImage().getScaledInstance(-1, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledFallback);
            }
        } catch (Exception e) {
            System.err.println("Impossibile caricare  l'immagine di fallback!");
        }
        return null;
    }
}
