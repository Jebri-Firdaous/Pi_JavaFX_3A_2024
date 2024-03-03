package tn.pidev.controllers.TourismeController;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
public class AdminInterfaceControllers {

    @FXML
    private Pane contentPane;

    @FXML
    void afficherHotel() {
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

    @FXML
    void ajouterHotel() {

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
    @FXML
    void modifierHotel() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/ModifierHotel.fxml"));
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
    void AjouterReservation() {
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
    @FXML
    void ModifierReservation() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/ModifierReservation.fxml"));
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
    void AfficherReservation() {
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






    public void navigateToAcceuil(ActionEvent actionEvent) {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/Acceuil.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) contentPane.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
