package tn.esprit.controllers.gestionTransport;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.services.gestionTransport.StationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifieStationController {
    StationService ss = new StationService();
    @FXML
    private TextField mdadress;
    private String type;
    @FXML
    private ComboBox<String> mdtype1;

    @FXML
    private TextField mdnom;
    private int idstation;
    @FXML
    private Label labelnom;
    @FXML
    private MenuButton crudTransport;



    public void initializeValues(int idStation, String nomStation, String adressStation, String type) {
        idstation=idStation;
        mdnom.setText(nomStation);
        mdadress.setText(adressStation);
        mdtype1.setValue(type);
        mdtype1.getItems().addAll("bus", "metro", "louage","train");

        // Définition de la valeur sélectionnée en fonction de la valeur récupérée
       // Récupérez la valeur depuis votre base de données
       // mdtype1.setValue(typeRecupere);
    }
    public void switchToDisplayAllstationsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml"));
            Parent newPageRoot = loader.load();


            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) mdnom.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void modifierfinale(ActionEvent actionEvent) throws RuntimeException {
      /*  try {
            ss.modifier(idstation,mdnom.getText(),mdadress.getText(),mdtype.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Station modifiée avec succées!");
            alert.showAndWait();


        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }*/

        try {
            // Vérification des champs de saisie
            String nom = mdnom.getText();
            String adresse = mdadress.getText();
            Object selectedObject = mdtype1.getValue();
            String type = (selectedObject != null) ? selectedObject.toString() : "";
            if (nom.isEmpty() || adresse.isEmpty()) {
                // Affichage d'un message d'erreur si l'un des champs est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
            }

            // Vérification que l'adresse ne contient pas des entiers
            boolean containsDigit = false;
            for (char c : adresse.toCharArray()) {
                if (Character.isDigit(c)) {
                    containsDigit = true;
                    break;
                }
            }
            if (containsDigit) {
                // Affichage d'un message d'erreur si l'adresse contient des entiers
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("L'adresse ne doit pas contenir des entiers.");
                alert.showAndWait();
                return; // Arrêter l'exécution de la méthode si l'adresse contient des entiers
            }

            // Si tous les champs sont remplis, appeler la méthode de modification
            System.out.println("gfgfggfgfgfgfgfgfgfgfgf");
            ss.modifier(idstation, nom, adresse,type);
            mdtype1.setValue(type);
            // Affichage d'un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("SUCCES");
            alert.setHeaderText(null);
            alert.setContentText("Station modifiée avec succès!");
            switchToDisplayAllstationsPage();
            alert.showAndWait();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void backtoafficher(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            labelnom.getScene().setRoot(root);

        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


   /* public void initialize () {
        initialize(null, null);
    }*/
    public void initialize (URL location, ResourceBundle resources) {
        // Create menu items and add them to the MenuButton
        // Créer des éléments de menu
        System.out.println("methode");
        javafx.scene.control.MenuItem ajoutItem = new javafx.scene.control.MenuItem("Ajouter");
        javafx.scene.control.MenuItem afficherItem = new javafx.scene.control.MenuItem("Afficher");
        javafx.scene.control.MenuItem supprimerItem = new javafx.scene.control.MenuItem("Supprimer");


        // Associer des actions aux éléments de menu

        ajoutItem.setOnAction(event -> ajoutAction());
        afficherItem.setOnAction(event -> afficherAction());
        supprimerItem.setOnAction(event -> supprimerAction());


        // Ajouter les éléments de menu au MenuButton
        //crudTransport.getItems().addAll( ajoutItem,afficherItem,supprimerItem);
        System.out.println("item");
    }

    private void supprimerAction() {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            mdadress.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    private void afficherAction() {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            mdadress.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void ajoutAction() {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml")));
            mdadress.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void backtohome(ActionEvent actionEvent) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/home.fxml")));
            mdadress.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}

