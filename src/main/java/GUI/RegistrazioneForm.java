package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class RegistrazioneForm {
    private JButton backButton;
    private JPanel pane;
    private JTextField nomeField;
    private JTextField cognomeField;
    private JComboBox boxCitta;
    private JComboBox boxProvincia;
    private JTextField capField;
    private JTextField viaField;
    private JTextField emailField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton returnToLoginButton;
    private JButton registerButton;
    private JButton showButton;

    Map<String, String[]> citta;

    public RegistrazioneForm() {
        citta = new java.util.HashMap<String, String[]>();
        citta.put("Napoli", new String[]{"Napoli", "Pozzuoli", "Giugliano in Campania", "Torre del Greco", "Casoria", "Castellammare di Stabia", "Afragola", "Pompei"});
        citta.put("Milano", new String[]{"Milano", "Sesto San Giovanni", "Cinisello Balsamo", "Legnano", "Rho", "Cologno Monzese", "Paderno Dugnano"});
        citta.put("Firenze", new String[]{"Firenze", "Scandicci", "Sesto Fiorentino", "Empoli", "Campi Bisenzio"});
        //Carico prima questo per farlo uscire come placeholder (il dictionary non è ordinato)
        boxCitta.addItem("Città");
        boxProvincia.addItem("Provincia/Comune");
        for (String key : citta.keySet()) {
            boxCitta.addItem(key);
        }
        //Configurazione tasti flatlaf
        passwordField1.putClientProperty("JTextField.placeholderText", "Almeno 8 caratteri");
        nomeField.putClientProperty("JTextField.placeholderText", "Nome");
        cognomeField.putClientProperty("JTextField.placeholderText", "Cognome");
        emailField.putClientProperty("JTextField.placeholderText", "name@email.com");
        capField.putClientProperty("JTextField.placeholderText", "CAP");
        viaField.putClientProperty("JTextField.placeholderText", "Via");
        showButton.setIcon(new ImageIcon(getClass().getResource("/immages/show.png")));
        boxCitta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cittaSelezionata = (String) boxCitta.getSelectedItem();
                if (cittaSelezionata != null && !cittaSelezionata.equals("Città")) {
                    boxCitta.removeItem("Città");
                    setProvince(cittaSelezionata);
                }
            }
        });
        returnToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLogin();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
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

    private void setProvince(String key) {
        boxProvincia.removeAllItems();
        String[] provincia = citta.get(key);
        for (String prov : provincia) {
            boxProvincia.addItem(prov);
        }
    }

    private void goToLogin() {
        App.mostraLogin();
    }

    private boolean checkPasswords() {
        return Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword());
    }

    private boolean checkEmail() {
        return emailField.getText().contains("@");
    }

    private void register() {
        if (!checkEmail()) {
            JOptionPane.showMessageDialog(null, "Email non valida");
            emailField.setText("");
            emailField.putClientProperty("JComponent.outline", "error");
            emailField.requestFocus();


        } else {
            emailField.putClientProperty("JComponent.outline", null);
            PasswordValidator validator = new PasswordValidator(new String(passwordField1.getPassword()));
            if (!validator.isValid()) {
                JOptionPane.showMessageDialog(null, validator.getErrorMessage());
                passwordField1.setText("");
                passwordField1.putClientProperty("JComponent.outline", "error");
                passwordField1.requestFocus();
            } else {
                passwordField1.putClientProperty("JComponent.outline", null);
                if (!checkPasswords()) {
                    JOptionPane.showMessageDialog(null, "Le password non coincidono");
                    passwordField1.setText("");
                    passwordField2.setText("");
                    passwordField1.putClientProperty("JComponent.outline", "error");
                    passwordField2.putClientProperty("JComponent.outline", "error");
                    passwordField1.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Registrazione effettuata con successo");
                    goToLogin();
                }
            }
        }
    }
}
