package sample.controller.UtulisateurController;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.model.Produit;
import sample.model.Utulisateur;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterUtulisateur implements Initializable {
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField pass;
    @FXML private JFXPasswordField ConfirmPass;
    @FXML private JFXTextField email;
    @FXML
    public void vider(){username.clear();email.clear();pass.clear();ConfirmPass.clear();}
    @FXML
    void AddUSer(ActionEvent event) {
        Boolean valid=true;

        username.resetValidation(); email.resetValidation();ConfirmPass.resetValidation();
        pass.resetValidation();ConfirmPass.resetValidation();pass.resetValidation();

        if(username.getText().isEmpty() || email.getText().isEmpty()|| pass.getText().isEmpty()||ConfirmPass.getText().isEmpty()){
              valid=false;
            username.validate(); email.validate();
            pass.validate();ConfirmPass.validate();
           pass.clear();
           ConfirmPass.clear();
        }else {
          if(!email.getText().matches("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)")) {
              email.validate();
              valid=false;
          }
          if(!pass.getText().equals(ConfirmPass.getText())){
              Alert alert1 = new Alert(Alert.AlertType.WARNING);alert1.setHeaderText(null);
              alert1.setContentText("Mot de passe et confirmmation doit etre  identique !! ");
              alert1.showAndWait();
          }

    if(!pass.getText().equals(ConfirmPass.getText())){
          ConfirmPass.validate();
        valid=false;
        pass.clear();
        ConfirmPass.clear();
       }
        }
         if(valid){ //! Ajouter en cas de mod

            Utulisateur u = new Utulisateur();
            u.setUsername(username.getText());
            u.setEmail(email.getText());
            u.setPass(pass.getText());
            u.insert(u);vider();
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
        alert1.setContentText("Utilisateur Ajouté avec réussite");
        alert1.showAndWait();
        }
    }

    @FXML
    void Retour(ActionEvent event) throws IOException {
         email.getScene().getWindow().hide();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
