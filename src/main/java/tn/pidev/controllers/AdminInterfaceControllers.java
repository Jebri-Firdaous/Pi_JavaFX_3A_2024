package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminInterfaceControllers{
    @FXML
    private Label label;

    @FXML
    private MenuItem afficherListeArticlesMenuItem;

    @FXML
    private MenuItem action1MenuItem;

    @FXML
    private MenuItem listeUtilisateurMenuItem;

    @FXML
    void afficherAdmin() {

        navigateToPage("afficherAdmin.fxml");
    }



    @FXML
    void Connecter(ActionEvent event) {
        navigateToPage("pageConnexion.fxml");
    }


    public void reinitialisermdp(ActionEvent actionEvent) {
        navigateToPage("confirmermail.fxml");
    }

   /* @FXML
    void listeUtilisateur() {
        // Handle list utilisateur
    }*/

    private void navigateToPage(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : " + fxmlFileName);
            e.printStackTrace();
        }
    }



    public void ToAcceuil(ActionEvent actionEvent) {
        navigateToPage("Acceuil.fxml");

    }



    public void modifierAdmin(ActionEvent actionEvent) {
        navigateToPage("modifier.fxml");

    }

    public void SwitchToAcceuil(ActionEvent actionEvent) {
        navigateToPage("Acceuil.fxml");
    }

    public void ToaddPage(ActionEvent actionEvent) {
    }


//se connecter

}