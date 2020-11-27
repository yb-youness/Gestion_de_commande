package sample.controller.ClientController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.assests.helper.Helper;
import sample.model.Client;
import sample.model.Command;
import sample.model.Produit;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherClientController implements Initializable {

    @FXML
    private TableView<Client> Display;

    @FXML
    private TableColumn<Client, Integer> ID_Client;

    @FXML
    private TableColumn<Client, String> Telephone;

    @FXML
    private TableColumn<Client, String> Nom;

    @FXML
    private TableColumn<Client, String> Prenom;

    @FXML
    private TableColumn<Client, String> Sexe;
    @FXML
    private TableColumn<Client, String> Email;
    public Client c=new Client();

    @FXML
    private JFXTextField searchFX;

    @FXML
    private JFXButton rechrche;


    @FXML
    void Refresh(ActionEvent event){ Display.setItems(c.ShowAllClient()); }
    @FXML
    void search(ActionEvent event) {
            searchFX.resetValidation();
            if(searchFX.getText().isEmpty()) {
                searchFX.validate();
   }else
               if(!c.SearchMultiClient(searchFX.getText()).isEmpty()) {
                   Display.setItems(c.SearchMultiClient(searchFX.getText()));
               }
       else Helper.Alert("Elément N'existe Pas");
                searchFX.clear();
    }

    @FXML
    void DelClient(ActionEvent event) {
        if(Display.getSelectionModel().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner le client a supprimer !!");
            alert1.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Vous voullez Supprimer ??");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Client c = new Client();
                ArrayList<Client> l=new ArrayList<>(Display.getSelectionModel().getSelectedItems());
                for (Client res : l) {
                    c.Delete(res.getId_client());
                }
                //c.Delete(c.getId_client());
                Display.setItems(c.ShowAllClient()); }}
    }

    @FXML
    void UpdClient(ActionEvent event) throws IOException {
        int cont=0;
        ArrayList<Client> l=new ArrayList<>(Display.getSelectionModel().getSelectedItems());
        for (Client res : l) {
            cont++;
        }
        if(Display.getSelectionModel().isEmpty()||cont>1){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner le Client a Modifier !!");
            alert1.showAndWait();
        }
        else{
            FXMLLoader loder=new FXMLLoader();
            Stage master=new Stage();
            loder.setLocation(getClass().getResource(  "../../views/ClientView/AjouterClient.fxml"));
            loder.load();
            Parent root =loder.getRoot();
            Scene secene=new Scene(root, 800, 550);
            master.setTitle("Modifier Client");
            AjouterClientController m=loder.getController();
            Client c1 = Display.getSelectionModel().getSelectedItem();
            c1=c1.SelectClient(c1.getId_client());
            m.setUpdate("Modifier");
            m.setVisibilite(false);
            master.setResizable(false);
            m.setclient(c1);
            master.centerOnScreen();
            master.show();
            master.setScene(secene);
        }
    }


    @FXML
    public void  AddClient(ActionEvent event) throws IOException {
        Stage master=new Stage();
        FXMLLoader loder=new FXMLLoader();
        loder.setLocation(getClass().getResource(  "../../views/ClientView/AjouterClient.fxml"));
        loder.load();
        Parent root =loder.getRoot();
        Scene secene=new Scene(root, 800, 550);
        master.setTitle("Gestion Des Commandes");
        master.setScene(secene);
        master.setResizable(false);
        master.show();
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Display.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ID_Client.setCellValueFactory(new PropertyValueFactory<>("id_client"));
        Telephone.setCellValueFactory(new PropertyValueFactory<>("Num_tel"));
        Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        Sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        try { Display.setItems(c.ShowAllClient()); }
        catch (Exception ex){ System.out.println(ex.toString()); }

    }


}
