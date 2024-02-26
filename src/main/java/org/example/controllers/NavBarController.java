package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class NavBarController {


    @FXML
    private Pane contentPane;

    @FXML
    void afficherArticle() {
        navigateToPage("Article1.fxml");
    }
    @FXML
    void afficherMedecin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMedecins.fxml"));
            Parent newPageRoot = loader.load();
            //AfficherMedecinsController afficherMedecinsController = loader.getController();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherListRendezVous.fxml"));
            Parent newPageRoot = loader.load();
            //AfficherListRendezVousController afficherListRendezVousController = loader.getController();

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
}
