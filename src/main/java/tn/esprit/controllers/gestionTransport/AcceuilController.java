package tn.esprit.controllers.gestionTransport;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AcceuilController {
    @FXML
    private MenuButton crudTransport;
    @FXML
    private Label label;
    public void ajouterAction() {
        try {
            System.out.println("Chargement du fichier FXML : " + getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml"));

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml")));
            label.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void initialize () {
        initialize(null, null);
    }

    public void initialize (URL location, ResourceBundle resources) {
        // Create menu items and add them to the MenuButton
        // Créer des éléments de menu
        System.out.println("methode");
        MenuItem ajouterItem = new MenuItem("Ajouter");
        MenuItem modifierItem = new MenuItem("Modifier");
        MenuItem afficherItem = new MenuItem("Afficher");
        MenuItem supprimerItem = new MenuItem("Supprimer");

        // Associer des actions aux éléments de menu
        ajouterItem.setOnAction(event -> ajouterAction());
        modifierItem.setOnAction(event -> modifierAction());
        afficherItem.setOnAction(event -> afficherAction());
        supprimerItem.setOnAction(event -> supprimerAction());



        // Ajouter les éléments de menu au MenuButton
        crudTransport.getItems().addAll(ajouterItem, modifierItem, afficherItem, supprimerItem);
        System.out.println("item");
    }

    private void supprimerAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            label.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            label.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void modifierAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml")));
            label.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}

