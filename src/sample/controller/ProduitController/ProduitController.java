package sample.controller.ProduitController;


import com.jfoenix.controls.JFXComboBox;
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
import javafx.util.Callback;
import sample.assests.helper.Helper;
import sample.model.Categorie;
import sample.model.Command;
import sample.model.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    Produit prod = new Produit();
    @FXML private TableView<Produit> Display;
    @FXML private TableColumn<Produit, Integer> ID_Produit;
    @FXML private TableColumn<Produit, String> Libele;
    @FXML private TableColumn<Produit, Integer> Quantite;
    @FXML private TableColumn<Produit, String> Categories;
    @FXML private TableColumn<Produit, Double> Prix;
    @FXML private Button SearchButton;
    @FXML private Button Supprimer;
    @FXML private Button Modifier;
   @FXML private JFXTextField search_text;

    public void  AddProd(ActionEvent event) throws IOException {
        Stage master=new Stage();
        FXMLLoader loder=new FXMLLoader();
        loder.setLocation(getClass().getResource("../../views/ProduitView/AjoutProduit.fxml"));
        loder.load();
        Parent root =loder.getRoot();
        Scene secene=new Scene(root, 800, 550);
        master.setTitle("Gestion Des Commandes");
        master.setScene(secene);
        master.setResizable(false);
        master.show();
    }
    @FXML
    void Refresh(ActionEvent event){ Display.setItems(prod.ShowAllProduct()); }

    @FXML
    void SearchMulti(ActionEvent event) {

        Produit P1=new Produit();
        search_text.resetValidation();
        if(search_text.getText().isEmpty()) {
            search_text.validate();
        }
        else{
            if(!P1.SearchMulti(search_text.getText()).isEmpty()){
            Display.setItems(P1.SearchMulti(search_text.getText()));}
            else
                Helper.Alert("Elément N'existe Pas");

            search_text.clear();
        }
    }

    @FXML
    void modicomm(ActionEvent event) throws IOException {
        int cont=0;
        ArrayList<Produit> l=new ArrayList<>(Display.getSelectionModel().getSelectedItems());
        for (Produit res : l) {
            cont++;
        }

        if(Display.getSelectionModel().isEmpty()|| cont>1){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner Un Produit a Modifier !!");
            alert1.showAndWait();
        }
        else {
            FXMLLoader loder=new FXMLLoader();
            Stage master=new Stage();
            loder.setLocation(getClass().getResource("../../views/ProduitView/AjoutProduit.fxml"));
            loder.load();
            Parent root =loder.getRoot();
            Scene secene=new Scene(root, 1150, 550);
            master.setTitle("Modifier Produit");
            AjoutProduitController m=loder.getController();
            Produit c1 = Display.getSelectionModel().getSelectedItem();//Recuperer le produit selectionneé apartir tableview
            c1=c1.searchob(c1.getId());//Recuperer l'Objet
            m.setUpdate("Modifier");//changement de button Enregister vers UPdate
            m.setcommd(c1);//passer l'objet trouvé vers la mehode setcommd
            m.setVisibilite(false);
            master.centerOnScreen();
            master.show();
            master.setResizable(false);
            master.setScene(secene);

        }


    }

    @FXML
    void DeleteProduct(ActionEvent event) {

        if(Display.getSelectionModel().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner le Produit a supprimer !!");
            alert1.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Vous voullez Supprimer  ??");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Produit P1 = new Produit();
                        //Display.getSelectionModel().getSelectedItem();
                ArrayList<Produit> l=new ArrayList<>(Display.getSelectionModel().getSelectedItems());
                for (Produit res : l) {
                    P1.SupprimerProd(res.getId());
                }
                Display.setItems(prod.ShowAllProduct());
            }


    }}
//!!lorsque la scene produit charge ,automatiquement(par defaut)toutes  les donnes  produit sera afficher
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Display.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        ID_Produit.setCellValueFactory(new PropertyValueFactory<>("id"));
        Libele.setCellValueFactory(new PropertyValueFactory<>("libele"));
        Quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        Categories.setCellValueFactory(new PropertyValueFactory<>("Libele_cat"));
        Prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

      try { Display.setItems(prod.ShowAllProduct()); }
      catch (Exception ex){ System.out.println(ex.toString()); }

    }


}