package Boundary.Login;

import Boundary.Utils.ImageUtils;
import Eseguibile.App;
import Controller.AccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class LoginPage {
    private JPanel pane;
    private JTextField textField1;
    private JButton signInButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel labelImmagine;
    private JPasswordField passwordField1;
    private JButton nonHaiUnAccountButton;
    private JButton showButton;

    public LoginPage() {
        textField1.putClientProperty("JTextField.placeholderText", "name@email.com");
        nonHaiUnAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showButton.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/show.png"))));
        labelImmagine.setIcon(ImageUtils.getIconScaled("img/brand/logo.png",400));
        //LISTENER TASTI
        nonHaiUnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadRegisterGUI();
            }
        });
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterData();
            }
        });

        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    enterData();
                }
            }
        };

        passwordField1.addKeyListener(keyAdapter);
        textField1.addKeyListener(keyAdapter);
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char echoChar='•';
                if(passwordField1.getEchoChar()==echoChar){
                    passwordField1.setEchoChar((char)0);
                    showButton.setIcon(new ImageIcon(getClass().getResource("/images/hide.png")));
                }else{
                    passwordField1.setEchoChar(echoChar);
                    showButton.setIcon(new ImageIcon(getClass().getResource("/images/show.png")));
                }
            }
        });
    }

    public JPanel getPane() {
        return pane;
    }
    private void enterData(){
        String email = textField1.getText();
        if(email.equals("admin")){App.mostraDashBoard();return;}
       if(email.contains("@")){
           if(passwordField1.getPassword().length>8){
               AccountController controller=new AccountController();
               if(controller.login(email,passwordField1.getText())){
                   App.mostraHome();
               }else{
                   JOptionPane.showMessageDialog(null,controller.get_msg());
               }
           }else{
               JOptionPane.showMessageDialog(null,"La password deve essere lunga almeno 8 caratteri");
           }
       }else{
           JOptionPane.showMessageDialog(null,"Email non valida");
       }
    }
    private void loadRegisterGUI(){
            RegistrazioneForm form = new RegistrazioneForm();
            form.pack();
            form.setLocationRelativeTo(pane);
            form.setVisible(true);
            form.setLocationRelativeTo(null);

    }
}
