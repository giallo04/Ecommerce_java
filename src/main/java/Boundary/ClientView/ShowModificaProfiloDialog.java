package Boundary.ClientView;

import Boundary.Login.RegistrazioneForm;

import javax.swing.*;
import Boundary.Utils.ImageUtils;
import Controller.Stub;

import java.util.Arrays;
import java.util.Iterator;

public class ShowModificaProfiloDialog extends RegistrazioneForm {
    public ShowModificaProfiloDialog() {
        super();
        setTitle("Modifica Profilo");
        this.registerButton.setText("Salva");
        this.backButton.setText("Annulla");
        passwordField1.putClientProperty("JTextField.placeholderText", "Almeno 8 caratteri");
        password1Label.setText("Vecchia password: ");
        passwordField2.putClientProperty("JTextField.placeholderText", "Almeno 8 caratteri");
        password2Label.setText("Nuova password: ");
        fillForm();
    }
    private void fillForm(){
        Stub stub = new Stub();
        String[] info=stub.getUserInfo();
        nomeField.setText(info[0]);
        cognomeField.setText(info[1]);
        emailField.setText(info[2]);
        imgLabel.setIcon(ImageUtils.getIconScaled("/users/dios@napoli.png",150));//just a test
        imgLabel.setVisible(true);
        boxCitta.setSelectedItem(info[4]);
        boxProvincia.setSelectedItem(info[5]);
        capField.setText(info[6]);
        viaField.setText(info[7]);

    }
    @Override
    protected void onOk() {
        JOptionPane.showMessageDialog(null, "Profilo modificato con successo");//TODO chiamare il controller
        dispose();
    }
}
