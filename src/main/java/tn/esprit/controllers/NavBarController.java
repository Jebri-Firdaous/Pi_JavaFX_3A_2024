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


import javafx.stage.Stage;
import tn.esprit.controllers.gestionMedecin.AfficherListRendezVousController;
import tn.esprit.controllers.gestionMedecin.AfficherMedecinsController;
import tn.esprit.controllers.gestionTransport.AfficherBilletController;
import tn.esprit.controllers.gestionTransport.AfficherStationController;

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

    public void afficherBillet(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
            Parent newPageRoot = loader.load();
            AfficherBilletController afficherBilletController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void afficherStation(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml"));
            Parent newPageRoot = loader.load();
            AfficherStationController afficherStationController = loader.getController();

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
    void afficherArticle() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AfficherArticle.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ajouterArticle() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AjouterArticle.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void ajouterCommande() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AjouterCommande.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void afficherCommande() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AfficherCommande.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterHotel(ActionEvent actionEvent) {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AjouterHotel.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void afficherHotel(ActionEvent actionEvent) {

        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherHotel.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void AjouterReservation(ActionEvent actionEvent) {

        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AjouterReservation.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void AfficherReservation(ActionEvent actionEvent) {


        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherReservation.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void afficherParking(ActionEvent event) {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherParkingg.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void recupererParking(ActionEvent event) {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/RecycleBin.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private Label label;
    @FXML
    void afficherAdmin() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (
                IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }

    public void afficherClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherClient.fxml"));
           Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (
                IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }
}


