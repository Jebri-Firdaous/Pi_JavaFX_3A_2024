package tn.pidev.controllers;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

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
    @FXML
    public void modifierAdmin(ActionEvent actionEvent) {
        navigateToPage("modifier.fxml");

    }
    @FXML
    void Connecter(ActionEvent event) {
        navigateToPage("pageConnexion.fxml");
    }


    public void reinitialisermdp(ActionEvent actionEvent) {
        navigateToPage("confirmermail.fxml");
    }

    public void AjouterAdmin(ActionEvent actionEvent) {
        navigateToPage("AjouterCompte.fxml");

    }
    @FXML
    public void Acceuil(ActionEvent actionEvent) {
        navigateToPage("Acceuil.fxml");

    }
    @FXML
    void afficherAdmin() {

        navigateToPage("afficherAdmin.fxml");
    }


    public void goBack(ActionEvent actionEvent) {
    }
    @FXML
    private Label bienvenue;

    public void initialize() {
        // Créer une transition de translation
        TranslateTransition transition = new TranslateTransition(Duration.seconds(15), bienvenue);
        transition.setFromX(1312); // Position de départ en X (hors de l'écran à droite)
        transition.setToX(-1312);
        transition.setCycleCount(Animation.INDEFINITE); // Répéter indéfiniment
// Position finale en X (0)
        transition.play(); // Démarrer la transition
    }


}