package sample.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.assests.helper.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MasterpageController implements Initializable {
      Helper navl =new Helper();
    @FXML
    private Text bon;
    @FXML
    private Label usern;
    public void setname(String name){
        bon.setText("Bonjour  "+name);
    }
    public void router(String viwe){
      //  System.out.println("test");

//        if(viwe.equals("Commande"))
//            navl.NavRouternoamin("Commande",Parent);
//        if(viwe.equals("Produit"))
//            navl.NavRouternoamin("Produit",Parent);
//        if(viwe.equals("Categorie"))
//            navl.NavRouternoamin("Categorie",Parent);
//        if(viwe.equals("AfficherClient"))
//            navl.NavRouternoamin("AfficherClient",Parent);
//        if(viwe.equals("Searchcommande"))
//            navl.NavRouter("Searchcommande",Parent);
//        if(viwe.equals("Statistique"))
//            navl.NavRouternoamin("Statistique",Parent);
//        if(viwe.equals("Info"))
//            navl.NavRouternoamin("Info",Parent);
    }
    @FXML
    private Button commande;
    @FXML
    private Button searchclient;
    @FXML
    private Button categorie;
    @FXML
    private Button produit;
    @FXML
    private Button chart;
    @FXML
    private Button info;
    @FXML
    private Button Setting;
    @FXML
    private Button logout;
    @FXML
    private BorderPane Parent;
    @Override
    public void initialize(URL URL, ResourceBundle rb){
        navl.NavRouter("CommandeView/Commande",Parent);

    }
    @FXML
    void Commande(ActionEvent event) {
        navl.NavRouter("CommandeView/Commande",Parent);
    }
    @FXML
    void Produit(ActionEvent event) { navl.NavRouter("ProduitView/Produit",Parent);}
    @FXML
    void categorie(ActionEvent event) {
        navl.NavRouter("CatégorieView/Categorie",Parent);
    }

    @FXML
    void RecherClient(ActionEvent event) { navl.NavRouter("ClientView/AfficherClient",Parent); }
    @FXML
    void Statistique(ActionEvent event) {
        navl.NavRouter("StatistiqueView/Statistique",Parent);
    }
    @FXML
    void Info(ActionEvent event) {
        navl.NavRouter("InfoView/Info",Parent);
    }
    @FXML
    void Settings(ActionEvent event) { navl.NavRouter("UtulisateurView/AfficherUtulisateurs",Parent);}

    public void  Logout(ActionEvent event) throws IOException {
        javafx.scene.Parent ProductParent= FXMLLoader.load(getClass().getResource("../views/UtulisateurView/login.fxml"));
        Scene ProductScene=new Scene(ProductParent);
        Stage window =(Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(ProductScene);
        window.show(); }


}
