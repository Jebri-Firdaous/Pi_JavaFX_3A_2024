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

public class HomeController {
    @FXML
    private Label home;
    @FXML
    private MenuButton crudTransport;
    public void initialize () {
        initialize(null, null);
    }
@FXML
    public void initialize (URL location, ResourceBundle resources) {
        // Create menu items and add them to the MenuButton
        // Créer des éléments de menu
        System.out.println("methode");
        MenuItem modifierItem = new MenuItem("STATION");
        MenuItem afficherItem = new MenuItem("BILLET");


        // Associer des actions aux éléments de menu

        modifierItem.setOnAction(event -> stationAction());
        afficherItem.setOnAction(event -> billetAction());
        // Ajouter les éléments de menu au MenuButton
        crudTransport.getItems().addAll( modifierItem, afficherItem);
        System.out.println("item");
    }

    private void billetAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AjoutBillet.fxml")));
            home.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void stationAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/ajoutstation.fxml")));
            home.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
