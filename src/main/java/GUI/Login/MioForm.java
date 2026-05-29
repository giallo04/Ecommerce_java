package GUI.Login;

import GUI.App;
import GUI.PasswordValidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MioForm {
    private JPanel pane;
    private JTextField textField1;
    private JButton signInButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel labelImmagine;
    private JPasswordField passwordField1;
    private JButton nonHaiUnAccountButton;
    private JButton showButton;

    public MioForm() {
        textField1.putClientProperty("JTextField.placeholderText", "name@email.com");
        nonHaiUnAccountButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signInButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showButton.setIcon(new ImageIcon(getClass().getResource("/immages/show.png")));
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
                    showButton.setIcon(new ImageIcon(getClass().getResource("/immages/hide.png")));
                }else{
                    passwordField1.setEchoChar(echoChar);
                    showButton.setIcon(new ImageIcon(getClass().getResource("/immages/show.png")));
                }
            }
        });
    }

    public JPanel getPane() {
        return pane;
    }
    private void createUIComponents() {
        labelImmagine = new JLabel();
        java.net.URL imgUrl = getClass().getResource("/immages/logo.png");
        labelImmagine.setOpaque(false);
        if (imgUrl != null) {
            labelImmagine.setIcon(new ImageIcon(imgUrl));
        }
    }
    private void enterData(){
        //validate data entered TODO
        PasswordValidator validator = new PasswordValidator(passwordField1.getText());
        if(validator.isValid()){
            //mando al controller
            App.mostraHome();
        }else{
            JOptionPane.showMessageDialog(null, validator.getErrorMessage());
        }
    }
    private void loadRegisterGUI(){
        App.mostraRegistrazione();
    }
}
