package Boundary.Login;

import Boundary.App;
import Boundary.Utils.ImageUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Map;

public class RegistrazioneForm extends JDialog {
    protected JLabel imgLabel;
    protected JPanel pane;
    protected JTextField nomeField;
    protected JTextField cognomeField;
    protected JComboBox boxCitta;
    protected JComboBox boxProvincia;
    protected JTextField capField;
    protected JTextField viaField;
    protected JTextField emailField;
    protected JPasswordField passwordField1;
    protected JPasswordField passwordField2;
    protected JButton backButton;
    protected JButton registerButton;
    protected JButton showButton;
    protected JTextField imgPath;
    protected JButton openButton;

    Map<String, String[]> citta;

    public RegistrazioneForm () {
        setContentPane(pane);
        imgLabel.setVisible(false);
        setModalityType(ModalityType.APPLICATION_MODAL);
        getRootPane().setDefaultButton(registerButton);

        imgLabel.setIcon(ImageUtils.getIconScaled("/products/notFound.png",100));//just a test
        citta = new java.util.HashMap<String, String[]>();
        citta.put("Napoli", new String[]{"Napoli", "Pozzuoli", "Giugliano in Campania", "Torre del Greco", "Casoria", "Castellammare di Stabia", "Afragola", "Pompei"});
        citta.put("Milano", new String[]{"Milano", "Sesto San Giovanni", "Cinisello Balsamo", "Legnano", "Rho", "Cologno Monzese", "Paderno Dugnano"});
        citta.put("Firenze", new String[]{"Firenze", "Scandicci", "Sesto Fiorentino", "Empoli", "Campi Bisenzio"});
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
        showButton.setIcon(new ImageIcon(getClass().getResource("/images/show.png")));
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                imgPath.setText(filePath);
            }//TODO carica immagine
        });
        boxCitta.setRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1 && value == null) {
                    setText("Città");
                    setForeground(java.awt.Color.GRAY);
                }
                return this;
            }
        });

        boxCitta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cittaSelezionata = (String) boxCitta.getSelectedItem();
                if (cittaSelezionata != null) {
                    setProvince(cittaSelezionata);
                }
            }
        });
        boxProvincia.setRenderer(new DefaultListCellRenderer() {
            @Override
           public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (index == -1 && value == null) {
                    setText("Comune");
                    setForeground(java.awt.Color.GRAY);
                }
                return this;
            }
        });
        boxCitta.setSelectedIndex(-1);
        boxProvincia.setSelectedIndex(-1);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOk();
            }
        });
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

    private void setProvince(String key) {
        boxProvincia.removeAllItems();
        String[] provincia = citta.get(key);
        for (String prov : provincia) {
            boxProvincia.addItem(prov);
        }
    }


    private boolean checkPasswords() {
        return Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword());
    }

    private boolean checkEmail() {
        return emailField.getText().contains("@");
    }

    protected void onOk() {
        if (!checkEmail()) {
            JOptionPane.showMessageDialog(null, "Email non valida");
            emailField.setText("");
            emailField.putClientProperty("JComponent.outline", "error");
            emailField.requestFocus();


        } else {
            emailField.putClientProperty("JComponent.outline", null);
            if (passwordField1.getPassword().length < 8) {
                JOptionPane.showMessageDialog(null, "Password troppo corta, minimo 8 caratteri");
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
                    dispose();
                }
            }
        }
    }
}
