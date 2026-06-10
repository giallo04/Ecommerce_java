package Boundary.ClientView;

import Boundary.FormsTemplate.UserDataForm;
import Boundary.Login.RegistrazioneForm;

import javax.swing.*;
import Boundary.Utils.ImageUtils;
import Controller.AccountController;
import Controller.Stub;

public class ShowModificaProfiloDialog extends UserDataForm {
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
        AccountController controller=new AccountController();
        String[] info=controller.caricaProfilo();
        nomeField.setText(info[AccountController.NOME]);
        cognomeField.setText(info[AccountController.COGNOME]);
        //carico tutti i campi
        imgLabel.setIcon(ImageUtils.getIconScaled(info[AccountController.IMMAGINE],150));
        imgLabel.setVisible(true);
        emailField.setText(info[AccountController.EMAIL]);
        emailField.setEnabled(false);
        boxCitta.setSelectedItem(info[AccountController.CITTA]);
        boxProvincia.setSelectedItem(info[AccountController.PROVINCIA]);
        viaField.setText(info[AccountController.VIA]);
        capField.setText(info[AccountController.CAP]);
    }
    @Override
    protected void onOk() {
        String nome=nomeField.getText();
        String cognome=cognomeField.getText();
        String citta=boxCitta.getSelectedItem().toString();
        String provincia=boxProvincia.getSelectedItem().toString();
        String via=viaField.getText();
        String cap=capField.getText();
        String passwordNuova=passwordField2.getText();
        String passwordVecchia=passwordField1.getText();
        if(checkFields()){
            AccountController controller=new AccountController();
            //chiamo modifica profilo con i campi dei label
            if(controller.modificaProfilo(nome,cognome,citta,provincia,via,cap,imgPath.getText(),passwordVecchia,passwordNuova)){
                JOptionPane.showMessageDialog(this, "Profilo modificato con successo");
            }else{
                JOptionPane.showMessageDialog(this, controller.get_msg());
                return;
            }

        }else{
            JOptionPane.showMessageDialog(this, "Compila tutti i campi");
            return;
        }
        dispose();
    }

    private boolean checkFields(){
        return !(nomeField.getText().isEmpty() || cognomeField.getText().isEmpty() || emailField.getText().isEmpty() || boxCitta.getSelectedItem().toString().isEmpty() || boxProvincia.getSelectedItem().toString().isEmpty() || viaField.getText().isEmpty() || capField.getText().isEmpty());
    }
}
