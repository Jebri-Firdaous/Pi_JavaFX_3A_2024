package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.pidev.entities.Administrateur;
import tn.pidev.services.ServiceAdmin;

import java.sql.*;
import java.io.IOException;
import java.util.regex.*;



public class AjoutAdministrateurController {
    public ServiceAdmin sa = new ServiceAdmin();


    @FXML
    private ComboBox<String> role;

    @FXML
    private TextField mail;

    @FXML
    private TextField mdp;

    @FXML
    private TextField tel;

    @FXML
    private Button creercpt;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;
    @FXML
    private AnchorPane background;
    @FXML
    private Button button;
    @FXML
    private Connection connection;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView backgroundImage;

    /*public void initialize() {
        // Lier la largeur de l'image à celle de la fenêtre
        backgroundImage.fitWidthProperty().bind(rootPane.widthProperty());

        // Lier la hauteur de l'image à celle de la fenêtre
        backgroundImage.fitHeightProperty().bind(rootPane.heightProperty());
    }*/
    @FXML
    void ToConnexion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/creercpt.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   public void ToAfficherListeAdmine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAdmin.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) nom.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }
    private boolean validateTelephone(String telephone) {
        // Vérifier si le numéro de téléphone est composé de 8 chiffres et qu'il s'agit d'un entier
        return telephone != null && telephone.matches("\\d{8}");
    }

    @FXML
    void AjouterAdmin(ActionEvent event) {

            String roleSelected = role.getValue();
            String nomSaisi = nom.getText();

            if (!nom.getText().isEmpty() && !prenom.getText().isEmpty()&& !mail.getText().isEmpty()&& !mdp.getText().isEmpty()&& !tel.getText().isEmpty()&& !roleSelected.isEmpty())
            {
                try {
                sa.ajouter(new Administrateur(nom.getText(), prenom.getText(), Integer.parseInt(tel.getText()),
                        mail.getText(),mdp.getText(), roleSelected));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Admin insérée avec succées!");
            alert.showAndWait();
            nom.setText("");
                prenom.setText("");
                tel.setText("");
                mail.setText("");
                mdp.setText("");
                role.setValue(null);
                ToAfficherListeAdmine();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs.");
                alert.show();
            }


    }

   /* @FXML
    void ModifierAdmin(ActionEvent event) {
        try {
            sa.modifier(new Administrateur(nom.getText(),prenom.getText(),Integer.parseInt(tel.getText()),mail.getText(),mdp.getText(),role.getValue() ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Medecin modifier avec succées!");
//          block the execution until the user closes the alert dialog.
            alert.showAndWait();
            //ToAfficherListeAdmine();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/
    /*@FXML
    void supprimerAdmin(ActionEvent event) {

    }*/



}
