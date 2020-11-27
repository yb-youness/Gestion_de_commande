package sample.controller.CommandeController;

import com.jfoenix.controls.JFXTextField;
import com.opencsv.CSVWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.assests.helper.Helper;
import sample.model.Command;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class CommandeController  implements Initializable {
    private Command C=new Command();
    @FXML
    private TableView<Command> tab_Commandes;

    @FXML
    private TableColumn<Command, Integer> id_commande;

    @FXML
    private TableColumn<Command, String> client_col;

    @FXML
    private TableColumn<Command, String> produit_col;

    @FXML
    private TableColumn<Command, Double> prix_col;

    @FXML
    private TableColumn<Command, String> adress_col;

    @FXML
    private TableColumn<Command, Integer> quantite_col;

    @FXML
    private TableColumn<Command, Double> total_col;

    @FXML
    private TableColumn<Command, String> status_col;
    @FXML
    private TableColumn<Command, String> Date_col;
    @FXML
    private JFXTextField search;
    @FXML
    private Label Total;


    @FXML
    void exportcsv(ActionEvent event) {

       //File file = new File(System.getProperty("user.home") + "/Desktop/raport.csv");
        FileChooser fileChooser = new FileChooser();

        //Set extension filter for text files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Csv files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
       File file = fileChooser.showSaveDialog(Total.getScene().getWindow());

        if (file != null) {


            try {
                // create FileWriter object with file as parameter
                FileWriter outputfile = new FileWriter(file);

                // create CSVWriter object filewriter object as parameter
                CSVWriter writer = new CSVWriter(outputfile);

                // adding header to csv
           String[] header = { "IDCommande", "ClientName", "Produit","Prix","Adress","Quantite","Status","Total","Date" };
                writer.writeNext(header);

                ObservableList<Command> list =C.ShowAllcommand();
               list.forEach((data) -> {
                   String donnes[] = {data.getId()+"", data.getClient(), data.getProduit(),data.getPrix()+"",data.getQuantite()+"",data.getStatus(),data.getTotal()+"",data.getDate()};
                   writer.writeNext(donnes);
         });

                writer.close();
                Helper.Alert("Fichier Csv Sauvegardé avec réussite ");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }





    @FXML
    void modicomm(ActionEvent event) throws IOException {
       int cont=0;
        ArrayList<Command> l=new ArrayList<>(tab_Commandes.getSelectionModel().getSelectedItems());
        for (Command res : l) {
            cont++;
        }

        if(tab_Commandes.getSelectionModel().isEmpty()||cont>1){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner Une Commande a Modifier !!");
            alert1.showAndWait();
        }
        else{
            FXMLLoader loder=new FXMLLoader();
            Stage master=new Stage();
            loder.setLocation(getClass().getResource(  "../../views/CommandeView/AjoutCommand.fxml"));
            loder.load();
            Parent root =loder.getRoot();
            Scene secene=new Scene(root, 800, 550);
            master.setTitle("Modifier Commande");
            AjouterCommandeController m=loder.getController();
            Command c1 = tab_Commandes.getSelectionModel().getSelectedItem();
            c1=c1.searchob(c1.getId()+"");
            m.setUpdate("Modifier");
            m.setcommd(c1);
            tab_Commandes.getSelectionModel().getSelectedItem();
            m.SetStatus(tab_Commandes.getSelectionModel().getSelectedItem().getStatus());
            m.SetVisibilite(false);
            master.centerOnScreen();
            master.setResizable(false);
            master.show();
            master.setScene(secene);
        }

    }

    @FXML
    void Refresh(ActionEvent event){ tab_Commandes.setItems(C.ShowAllcommand());
                          Total.setText("Total Commandes : "+total(C.ShowAllcommand())+" DH");
                          search.clear();
    }

    @FXML
    void searchB(ActionEvent event) {
        Command C1=new Command();
         search.resetValidation();
        if(search.getText().isEmpty()) {
            search.validate();
        }else {
            if(!C1.search(search.getText()).isEmpty()){
            tab_Commandes.setItems(C1.search(search.getText()));
            Total.setText("Total Commandes : "+total(C1.search(search.getText()))+" DH");
            search.clear();
            }else {
                Helper.Alert("élément n'existe pas");
            }
            search.clear();
        }

    }

    @FXML
    void supcom(ActionEvent event) {
        if(tab_Commandes.getSelectionModel().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner la Commande a supprimer !!");
            alert1.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Vous voullez Supprimer  ??");
            Optional<ButtonType> result = alert.showAndWait();
            Command c1=new Command();
            if (result.get() == ButtonType.OK) {
             ArrayList<Command> l=new ArrayList<>(tab_Commandes.getSelectionModel().getSelectedItems());
                for (Command res : l) {
                    c1.SupprimerComm(res.getId());
                }
                tab_Commandes.setItems(C.ShowAllcommand());
                Total.setText("Total Commandes : "+total(C.ShowAllcommand())+" DH");


            }
        }
    }


    @FXML
    void newcommandes(ActionEvent event) throws IOException {
        Stage master=new Stage();
        FXMLLoader loder=new FXMLLoader();
        loder.setLocation(getClass().getResource("../../views/CommandeView/AjoutCommand.fxml"));
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
//        Helper h=new Helper();
           tab_Commandes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//        h.validator(search,"Veuillez Entrer Un Critère A Rechercher");
        id_commande.setCellValueFactory(new PropertyValueFactory<>("id"));
        client_col.setCellValueFactory(new PropertyValueFactory<>("client"));
        produit_col.setCellValueFactory(new PropertyValueFactory<>("produit"));
        prix_col.setCellValueFactory(new PropertyValueFactory<>("prix"));
        adress_col.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        quantite_col.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        Date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        total_col.setCellValueFactory(new PropertyValueFactory<>("total"));
        try { tab_Commandes.setItems(C.ShowAllcommand());
              Total.setText("Total Commandes : "+total(C.ShowAllcommand())+" DH");
        }
         catch (Exception ex){ System.out.println(ex.toString()); }
    }
    private String total(ObservableList<Command> list){
        AtomicReference<Double> t= new AtomicReference<>(0.0);
        list.forEach((tab) -> {
           t.updateAndGet(v -> v + tab.getTotal());
         });
        return t+"";
    }
}


