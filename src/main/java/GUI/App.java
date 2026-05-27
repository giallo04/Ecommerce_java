package GUI;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import themes.MyTheme;

public class App {

    private static JFrame frame;

    public static void main(String[] args) {
        if (!MyTheme.setup()) {
            System.err.println("Impossibile caricare MyTheme, fallback ");
            FlatMacLightLaf.setup();
        }
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);

        //serializzo modifiche GUI
        EventQueue.invokeLater(() -> {
            frame = new JFrame("E-Commerce");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setIconImage(new ImageIcon(App.class.getResource("/immages/deskIcon.png")).getImage());

            mostraLogin();

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Metodo pubblico per mostrare il Login
    public static void mostraLogin() {
        frame.setResizable(false);
        MioForm form = new MioForm();
        cambiaPannello(form.getPane());
    }
    public static void mostraHome(){
        frame.setResizable(true);
        MainPage page = new MainPage();
        cambiaPannello(page.getPane());
    }
    // Metodo pubblico per mostrare la Registrazione
    public static void mostraRegistrazione() {
        frame.setResizable(false);
        RegistrazioneForm form = new RegistrazioneForm();
        cambiaPannello(form.getPane());
    }
    // Metodo di utility per cambiare il pannello di visualizzazione
    private static void cambiaPannello(JPanel nuovoPannello) {
        frame.setContentPane(nuovoPannello);
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }
}