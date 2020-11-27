package sample.controller.CommandeController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.assests.helper.Helper;
import sample.model.Categorie;
import sample.model.Client;
import sample.model.Command;
import sample.model.Produit;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AjouterCommandeController implements Initializable {
    public Command li=null;
    private String sta;
    public int filed;

    public void setUpdate(String re){
      add.setText(re.toString());
    }
    public void SetVisibilite(boolean b){Annuler.setVisible(b);}
    public void SetStatus(String b){
        if(b.equals("En attend")){
            //System.out.println(b);
            status.getSelectionModel().select(0);
        }if(b.equals("Livree")){

            status.getSelectionModel().select(1);
        }if(b.equals("Annulee")) {
            status.getSelectionModel().select(2);
        }
    }
    @FXML
    private JFXComboBox<Produit> allproduit;

    @FXML
    private JFXTextField quantity;

    @FXML
    private JFXComboBox<Client> allClient;

    @FXML
    private JFXComboBox<String> status;

    @FXML
    private JFXDatePicker datech;

    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXButton add;
    @FXML
    private JFXButton Annuler;

    public void setcommd(Command c){
        this.li=c;
        Client c1 =new Client();
        Produit p= new Produit();
        allproduit.setItems(p.Selectproduit(li.id_prod));
        allClient.setItems(c1.rechercheroneClient(li.id_client));
        allClient.getSelectionModel().select(1);
        quantity.setText(li.getQuantite()+"");
        adresse.setText(li.getAdresse());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(li.getDate(), formatter);
        datech.setValue(localDate);
        allClient.setDisable(true);
        allClient.getSelectionModel().select(0);
        allproduit.setDisable(true);
        allproduit.getSelectionModel().select(0);
    }
        @FXML
    void AddCommand(ActionEvent event) {
        Command C = new Command();
            adresse.resetValidation();
            quantity.resetValidation();
            allClient.resetValidation();
            datech.resetValidation();
            allproduit.resetValidation();
            status.resetValidation();
          boolean v=true;
                if(adresse.getText().isEmpty()||quantity.getText().isEmpty()||allClient.getSelectionModel().isEmpty()||allClient.getSelectionModel().isEmpty()||datech.getValue().toString().isEmpty()||status.getSelectionModel().isEmpty()){
                v=false;
                adresse.validate();
                quantity.validate();
                allClient.validate();
                datech.validate();
                allproduit.validate();
                status.validate();
            }else{
                if (!quantity.getText().matches("^[1-9]\\d*$")) {
                       quantity.validate();
                       v=false;
                }
            }
        if(add.getText()!="Modifier"&& v){
            Produit p =allproduit.getSelectionModel().getSelectedItem();
            Client c =allClient.getSelectionModel().getSelectedItem();
              if(p.SelectQunt(p.getId(),Integer.parseInt(quantity.getText()))) {
                  C.setId_client(c.getId_client());
                  C.setId_prod(p.getId());
                  C.setQuantite(Integer.parseInt(quantity.getText()));
                  C.setDate(datech.getValue().toString());
                  C.setAdresse(adresse.getText());
                  C.setStatus(status.getValue());
                  C.insert(C);
                  vider();
                  Annuler.setVisible(true);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setHeaderText(null);
                  alert.setContentText("Commande Ajouté avec réussite  ");
                  alert.showAndWait();
              }else {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setHeaderText(null);
                  alert.setContentText("Stock Epuisé ");
                  alert.showAndWait();
              }

            }else if(add.getText()=="Modifier"&& v) {
            Produit p =allproduit.getSelectionModel().getSelectedItem();
            Client c =allClient.getSelectionModel().getSelectedItem();
            if(p.SelectQunt(p.getId(),Integer.parseInt(quantity.getText()))) {
            C.Upadate(this.li.id,Integer.parseInt(quantity.getText()),adresse.getText(),status.getValue(),datech.getValue().toString());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);alert.setHeaderText(null);
            alert.setContentText("Commande Modifieé avec réussite ");alert.showAndWait();}
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Stock Epuisé ");
                alert.showAndWait();
            }
       }

    }

    @FXML
    void Retour(ActionEvent event) throws IOException {

        add.getScene().getWindow().hide();
    }

    @FXML
    void vider() {
        allproduit.getSelectionModel().clearSelection();quantity.clear();adresse.clear();allClient.getSelectionModel().clearSelection();status.getSelectionModel().clearSelection();
        adresse.resetValidation();
        quantity.resetValidation();
        allClient.resetValidation();
        datech.resetValidation();
        allproduit.resetValidation();
        status.resetValidation();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
              datech.setValue(LocalDate.now());
              Client C=new Client();
              allClient.setItems(C.ShowAllClient());
              Produit P=new Produit();
              allproduit.setItems(P.ShowAllProduct()); //! A consulter
              status.getItems().addAll("En attend","Livree","Annulee");
//        SetStatus("En attend");
    }
}
