package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;


import javafx.stage.Modality;
import javafx.stage.Stage;
import tn.esprit.controllers.gestionMedecin.AfficherListRendezVousController;
import tn.esprit.controllers.gestionMedecin.AfficherMedecinsController;
import tn.esprit.controllers.gestionTransport.AfficherBilletController;
import tn.esprit.controllers.gestionTransport.AfficherStationController;
import tn.esprit.controllers.gestionUser.LoginController;

public class NavBarController {


    @FXML
    private Pane contentPane;



    @FXML
    void afficherMedecin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AfficherMedecins.fxml"));
            Parent newPageRoot = loader.load();
            AfficherMedecinsController afficherMedecinsController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherRV() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AfficherListRendezVous.fxml"));
            Parent newPageRoot = loader.load();
            AfficherListRendezVousController afficherListRendezVousController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherAfficherArticle() {
        navigateToPage("AfficherArticle.fxml");
    }

    @FXML
    void afficherModifierArticle() {
        navigateToPage("AfficherArticle.fxml");
    }

    @FXML
    void afficherSupprimerArticle() {
        navigateToPage("SupprimerArticle.fxml");
    }

    private void navigateToPage(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent newContentPane = loader.load();
            contentPane.getChildren().clear();
            contentPane.getChildren().add(newContentPane);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : " + fxmlFileName);
            e.printStackTrace();
        }
    }


    public void disconnect(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
            Parent root = loader.load();
            LoginController adminLoginController = loader.getController();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();

            // Save reference to the parent stage
            Stage parentStage = new Stage();
            parentStage.initModality(Modality.WINDOW_MODAL);
            parentStage.initOwner(stage);

            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


