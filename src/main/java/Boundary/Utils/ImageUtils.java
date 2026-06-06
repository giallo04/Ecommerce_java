package Boundary.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;

public class ImageUtils {

    private ImageUtils() {}

    public static Icon getIconScaled(String imgUrl,int height) {
        if (imgUrl == null || imgUrl.trim().isEmpty()) {
            return getFallbackIcon(height);
        }
        try {
            File file = new File(imgUrl);
            if(!file.exists()) return getFallbackIcon(height);
            Image originalImage =new ImageIcon(file.getAbsolutePath()).getImage();
            Image scaledImage = originalImage.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
            }catch (Exception e){
            return getFallbackIcon(height);

        }
    }

    private static Icon getFallbackIcon(int height) {
        try {
            URL fallbackUrl = ImageUtils.class.getResource("/images/notFound.png");
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
