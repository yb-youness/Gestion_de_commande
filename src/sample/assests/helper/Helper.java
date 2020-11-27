package sample.assests.helper;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.omg.CORBA.WStringSeqHelper;

import javax.swing.*;
import java.io.IOException;
import java.util.Optional;

public class Helper {
    private Pane View;


    public void NavRouter(String file, BorderPane pane){
        try {
            View = FXMLLoader.load(getClass().getResource("/sample/views/"+file+".fxml"));
            pane.setCenter(View);
            new SlideInRight(pane.getCenter()).play();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void NavRouternoamin (String file, BorderPane pane){
        try {
            View = FXMLLoader.load(getClass().getResource("/sample/views/"+file+".fxml"));
            pane.setCenter(View);
            pane.getCenter();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void  Exseptiongmsg(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception Dialog");
        alert.setContentText("Exception Dialog");
        Label label = new Label("The exception stacktrace was:");
        TextArea textArea = new TextArea(msg);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }


    public static boolean Confirmmsg(String msg){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Look, a Confirmation Dialog");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
           return true;
        }
        return false;
    }

    public static void Alert(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
     }

     //? To do
     public void loadViwe(String viwe) throws IOException {
         Stage master=new Stage();
         FXMLLoader loder=new FXMLLoader();
         loder.setLocation(getClass().getResource("../views/AjoutCommand.fxml"));
         loder.load();
         Parent root =loder.getRoot();
         Scene secene=new Scene(root, 800, 550);
         master.setTitle("Gestion Des Commandes");
         master.setScene(secene);
         master.show();
     }
    public void validator(JFXTextField text, String msg){

        RequiredFieldValidator Validator = new RequiredFieldValidator();
        text.getValidators().add(Validator);Validator.setMessage(msg);
        text.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){ text.validate(); } }});
    }

    public void validator(JFXPasswordField text,String msg){
        RequiredFieldValidator Validator = new RequiredFieldValidator();
        text.getValidators().add(Validator);Validator.setMessage(msg);
        text.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){ text.validate(); } }});
    }


    public void validator(JFXTextField text,String msg, String patren){
        RequiredFieldValidator Validator = new RequiredFieldValidator();
        text.getValidators().add(Validator);Validator.setMessage(msg);
        text.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue){
                    if(text.getText().matches(patren)){
                        text.validate();
                    }
                } }});
    }

     }





