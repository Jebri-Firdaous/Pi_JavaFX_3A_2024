package tn.pidev.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.pidev.entities.Administrateur;
import tn.pidev.entities.Personne;
import tn.pidev.services.ServiceAdmin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AfficheAdministrateurController {

    private final  ServiceAdmin serviceAdmin=new ServiceAdmin();
    @FXML
    private ListView<Administrateur> listViewAdmin;
    Administrateur currentAdminSelected;

    @FXML
    private Label label;


    @FXML
    private TableColumn<Personne, String> nomAdmin;

    @FXML
    private TableColumn<Personne, String> prenomAdmin;
    @FXML
    private TableView<Administrateur> tableViewAdmin;


    @FXML
    private TableColumn<Administrateur, Integer> telAdmin;

    @FXML
    private TableColumn<Administrateur, String> mailAdmin;

    @FXML
    private TableColumn<Administrateur, String> mdpAdmin;

    @FXML
    private TableColumn<Administrateur, String> role;
/*-----------------------------AFFICHE ET RECUPERATION DES DONNEE DANS LA TABEVIEW--------------------------------*/
    public void initialize() {
        try {
            List<Administrateur> AdminsFromService = serviceAdmin.afficher();
            nomAdmin.setCellValueFactory(new PropertyValueFactory<>("nom_personne"));
            prenomAdmin.setCellValueFactory(new PropertyValueFactory<>("prenom_personne"));
            telAdmin.setCellValueFactory(new PropertyValueFactory<>("numero_telephone"));
            mailAdmin.setCellValueFactory(new PropertyValueFactory<>("mail_personne"));
            mdpAdmin.setCellValueFactory(new PropertyValueFactory<>("mdp_personne"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            tableViewAdmin.setItems(FXCollections.observableArrayList(AdminsFromService)); // Utilisez AdminsFromService
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de récupérer les données de la base de données");
            alert.setContentText("Une erreur s'est produite lors de la récupération des données. Veuillez réessayer plus tard.");
            alert.showAndWait();
        }
    }

    /*-----------------------------AFFICHE ET RECUPERATION DES DONNEE DANS LA LISTVIEW--------------------------------*/


/*
    public void initialize() {
        ObservableList<Administrateur> AdminList=FXCollections.observableArrayList();
        listViewAdmin.setItems(AdminList);
        try {
            List<Administrateur> AdminsFromService=serviceAdmin.afficher();
            AdminList.addAll(AdminsFromService);
            listViewAdmin.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentAdminSelected=listViewAdmin.getSelectionModel().getSelectedItem();
                }
            });
        }catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

*/

    public void ToAjouterAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/creercpt.fxml"));
            Parent root = loader.load();
            AjoutAdministrateurController AjoutAdministrateurController = loader.getController();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void ToModifierPage() {
        try {
            // Récupérez l'administrateur sélectionné dans TableView
            Administrateur adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();

            if (adminSelectionne != null) {
                // Chargez la vue de la page de modification
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifier.fxml"));
                Parent root = loader.load();
                Scene pageScene = new Scene(root);

                // Accédez au contrôleur de la page de modification
                ModifierAdminController modifierAdminController = loader.getController();

                // Initialisez les champs de formulaire avec les données de l'administrateur sélectionné
                modifierAdminController.initialize(adminSelectionne);

                // Affichez la scène de la page de modification dans une nouvelle fenêtre
                Stage stage = (Stage) label.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void toAddpage(ActionEvent event) {
        ToAjouterAdmin();
    }
/*
    @FXML
    void toUpdatePage(ActionEvent event) {

    }

    @FXML
    void ToDeletePage(ActionEvent event) {
    }*/

}
