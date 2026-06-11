package Boundary.Login;

import Boundary.Template.UserDataForm;
import Controller.AccountController;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;

public class RegistrazioneForm extends UserDataForm {
    Map<String, String[]> citta;

    public RegistrazioneForm () {
        super();
    }



    private boolean checkPasswords() {
        return Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword());
    }


    @Override
    protected void onOk() {
            if(emailField.getText().contains("@") ){
                if(checkPasswords()){
                    //controllo se tutti i campi sono stati compilati
                    if(nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || emailField.getText().isEmpty() || capField.getText().isEmpty() || viaField.getText().isEmpty() || boxCitta.getSelectedItem() == null || boxProvincia.getSelectedItem() == null  || passwordField1.getPassword().length < 8){
                        JOptionPane.showMessageDialog(null,"Compila tutti i campi");
                    }else{
                        AccountController controller=new AccountController();
                        if(controller.register(emailField.getText(),passwordField1.getText(),nomeField.getText(),cognomeField.getText(),boxCitta.getSelectedItem().toString(),boxProvincia.getSelectedItem().toString(),viaField.getText(),capField.getText(),imgPath.getText())){
                            JOptionPane.showMessageDialog(null,controller.get_msg());
                            dispose();
                        }else{
                            JOptionPane.showMessageDialog(null,controller.get_msg());
                            return;
                        }
                    }

                }else {
                        JOptionPane.showMessageDialog(null,"Le password non coincidono");
                    }
            }else{
                JOptionPane.showMessageDialog(null,"Email non valida");
            }
            return;
    }
}
