package sample.controller.CategorieController;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.sun.org.apache.xerces.internal.impl.dv.dtd.StringDatatypeValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import sample.assests.helper.Dbhandeler;
import sample.assests.helper.Helper;
import sample.model.Categorie;
import sample.model.Command;


import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class CategorieController implements Initializable {
    Categorie cat = new Categorie();
    @FXML
    JFXTextField label;
    @FXML
    JFXButton add;
    @FXML
    private TableView<Categorie> data;
    @FXML
    private Button refre;

    @FXML
    private TableColumn<Categorie, Integer> id;

    @FXML
    private TableColumn<Categorie, String> lib;

    @FXML
    private JFXTextField rechrtex;


    @FXML
    private Button modicomm;

    @FXML
    private Button supcom;

    @FXML
    private Button SearchButton;
    Helper h=new Helper();
    @FXML
    void Addcat(ActionEvent event) {

             if (!label.getText().equals("")) {
                 if(add.getText()!="Modifier") {
                 Categorie c = new Categorie();
                 c.setLibele(label.getText());
                 c.insert(c);
                 label.clear();
                 label.resetValidation();
                 Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                 alert1.setHeaderText(null);
                 alert1.setContentText("Categorie Ajoutee avec reussite ");
                 alert1.showAndWait();
            }else {
                     Categorie cat=new Categorie();
                  Categorie idc= data.getSelectionModel().getSelectedItem();
                   cat.exq("update Categorie set LibeleCat=? where CatId=?",label.getText(),idc.getId());
                     add.setText("Ajouter");
                     label.clear();
                     label.resetValidation();
                     refre.fire();
                 }
             } else
               label.validate();



}

    @FXML
    void Refresh(ActionEvent event) {
     data.setItems(cat.showCategorienoannuler());
    }

    @FXML
    void Search(ActionEvent event) {
        rechrtex.resetValidation();
        if(rechrtex.getText().isEmpty()){
             rechrtex.validate();
        }else {

       if(!cat.SelectCatemul(rechrtex.getText()).isEmpty()){
           data.setItems(cat.SelectCatemul(rechrtex.getText()));
       }else {
           Helper.Alert("Élément n'existe pas");
       }
       rechrtex.clear();
        }
    }

    @FXML
    void modicomm(ActionEvent event) {
        int cont=0;
        ArrayList<Categorie> l=new ArrayList<>(data.getSelectionModel().getSelectedItems());
        for (Categorie res : l) {
            cont++;
        }

        if(data.getSelectionModel().isEmpty()||cont>1){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner Une Catgeroy a Modifier !!");
            alert1.showAndWait();
        }else {
        add.setText("Modifier");
        Categorie idc= data.getSelectionModel().getSelectedItem();
        //System.out.println(idc.getLibele());
        label.setText(idc.getLibele()+"");
        }
    }

    @FXML
    void supcom(ActionEvent event) {
        int cont=0;
        ArrayList<Categorie> l=new ArrayList<>(data.getSelectionModel().getSelectedItems());
        for (Categorie res : l) {
            cont++;
        }

        if(data.getSelectionModel().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);alert1.setHeaderText(null);
            alert1.setContentText("Veuillez Selectionner Une Catgeroy a Suprimer !!");
            alert1.showAndWait();
        }else {
           // if(Helper.Confirmmsg("La  catégorie sera annuler pour tous les produit qui appartient à cette catégorie ils seront attribuer   une catégorie annuler  Veuillez  les changer dans l’angle produit ")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("La  catégorie sera annuler pour tous les produit qui appartient à cette catégorie ils seront attribuer   une catégorie annuler  Veuillez  les changer dans l’angle produit");
            Optional<ButtonType> result = alert.showAndWait();
            Dbhandeler h =new Dbhandeler();
            if (result.get() == ButtonType.OK) {
                ArrayList<Categorie> ll=new ArrayList<>(data.getSelectionModel().getSelectedItems());
                for (Categorie res : ll) {

                    h.exequery("update Produit set CategorieId=? where CategorieId=?",1,res.getId());
                    h.exequery("delete from  Categorie where CatId=?",res.getId());
                }

                 refre.fire();

                //cat.exq("update Produit set LibeleCat=? where CatId=?",label.getText(),idc.getId());
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        lib.setCellValueFactory(new PropertyValueFactory<>("libele"));
        refre.fire();
        data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

}