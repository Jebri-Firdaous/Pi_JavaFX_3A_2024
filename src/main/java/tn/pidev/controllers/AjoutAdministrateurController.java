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

    private boolean isValidEmail(String email) {
        // Diviser l'e-mail en parties distinctes
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; // L'e-mail doit contenir exactement un symbole '@'
        }

        // Vérifier la partie locale de l'e-mail
        String localPart = parts[0];
        if (localPart.isEmpty()) {
            return false; // La partie locale ne peut pas être vide
        }

        // Vérifier le domaine de l'e-mail
        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2) {
            return false; // Le domaine doit contenir exactement un point séparant le nom de domaine et l'extension
        }

        // Vérifier le domaine et l'extension
        String domain = domainParts[0];
        String extension = domainParts[1];
        return (domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo")|| domain.equals("esprit")) &&
                (extension.equals("com") || extension.equals("tn"));
    }
    @FXML
    void ToConnexion(ActionEvent event) {
        try
        {FXMLLoader loader = new FXMLLoader(getClass().getResource("/pageConnexion.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) mail.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (
                IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
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





}
