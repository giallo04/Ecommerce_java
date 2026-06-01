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
        fillForm();
    }
    private void fillForm(){
        Stub stub = new Stub();
        String[] info=stub.getUserInfo();
        String imgUrl= stub.getUserImgUrl();
        nomeField.setText(info[0]);
        cognomeField.setText(info[1]);
        emailField.setText(info[2]);
        imgLabel.setIcon(ImageUtils.getIconScaled(imgUrl,100));
        passwordField1.setText(info[3]);
        passwordField2.setText(info[3]);
        boxCitta.setSelectedItem(info[4]);
        boxProvincia.setSelectedItem(info[5]);
        capField.setText(info[6]);
        viaField.setText(info[7]);


        imgLabel.setIcon(ImageUtils.getIconScaled("/users/dios@napoli.png",100));//just a test
    }
    @Override
    protected void onOk() {
        JOptionPane.showMessageDialog(null, "Profilo modificato con successo");//TODO chiamare il controller
        dispose();
    }
}
