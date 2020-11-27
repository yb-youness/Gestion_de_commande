package sample.controller.UtulisateurController;

import animatefx.animation.*;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.assests.helper.Helper;
import sample.controller.MasterpageController;
import sample.model.Utulisateur;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends Utulisateur implements Initializable  {

    @FXML
    private JFXTextField email;

    @FXML
    private JFXPasswordField pass;


    @FXML
    void login(ActionEvent event) throws IOException {
        String msg="";
        email.resetValidation();
        pass.resetValidation();
        pass.validate();
        email.validate();
//     if(!email.getText().isEmpty()){
//        if(!pass.getText().isEmpty()){
        if(!pass.getText().isEmpty()||!email.getText().isEmpty()){
             int rep=verfierLogin(email.getText(),pass.getText());
             switch (rep){
                 case 1:
                     msg="Mot de passe Incorrect";
                     pass.clear();
                     break;
                 case -1:
                     msg="Mot de passe  et email/le nom d'utulisateur Incorrect ";
                     pass.clear();
                     email.clear();
                     break;
                 case 2:
                     email.getScene().getWindow().hide();
                     FXMLLoader loder=new FXMLLoader();
                     Stage master=new Stage();
                     loder.setLocation(getClass().getResource("../../views/Masterpage.fxml"));
                     loder.load();
                     Parent root =loder.getRoot();
                     Scene secene=new Scene(root, 1237, 592);
                     master.setTitle("Gestion Des Commandes");
                     MasterpageController m=loder.getController();
                     m.setname(email.getText().toString().trim());
                     master.setScene(secene);
                     master.centerOnScreen();
                     //master.setResizable(false);
                     master.show();
                     break;
             }}

//           }else
//            pass.validate();

//       }else
//            email.validate();
         if(!msg.isEmpty())
          Helper.Alert(msg);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Helper h=new Helper();
        h.validator(email,"Champ Email Obilgatore");
        h.validator(pass,"Champ Pass Obilgatore");
        email.setStyle("-fx-text-inner-color:white;-fx-prompt-text-fill:white;");
        pass.setStyle("-fx-text-inner-color:white;-fx-prompt-text-fill:white;");

    }


    @FXML
    void entre(KeyEvent event) throws Exception {
    }

}
