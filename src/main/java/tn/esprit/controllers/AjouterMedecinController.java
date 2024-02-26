package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Medecin;
import tn.esprit.services.ServiceMedecin;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterMedecinController implements Initializable {
    public ServiceMedecin serviceMedecin = new ServiceMedecin();
    public TextField nom;
    public TextField prenom;

    public TextField numeroTel;
    public TextField addresse;

    public ComboBox<String> specialiteR;



    public void switchToDisplayAllDoctorsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherMedecins.fxml"));
            Parent newPageRoot = loader.load();
            AfficherMedecinsController afficherMedecinsController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) nom.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void AjouterMedecin(ActionEvent actionEvent) {
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String numeroTelText = numeroTel.getText();
        String addresseText = addresse.getText();

        // Check if all fields are filled
        if (nomText.isEmpty() || prenomText.isEmpty() || numeroTelText.isEmpty() || addresseText.isEmpty()) {
            // Show an alert if any field is empty
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields: Nom, Prenom, Numero Tel, and Addresse.");
            alert.showAndWait();
        } else if (numeroTelText.length() !=   8 || !numeroTelText.matches("\\d+") || !numeroTelText.startsWith("5") && !numeroTelText.startsWith("2") && !numeroTelText.startsWith("9")) {
            // Show an alert if the phone number is not valid
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText(null);
            alert.setContentText("The phone number must be exactly   8 numeric characters and start with   5,   2, or   9.");
            alert.showAndWait();
        } else {
            try {
                serviceMedecin.ajouter(new Medecin(nomText, prenomText, Integer.parseInt(numeroTelText),
                        addresseText, specialiteR.getValue()));
                Alert successAlert = new Alert(AlertType.INFORMATION);
                successAlert.setTitle("Information Dialog");
                successAlert.setContentText("Medecin added successfully!");
                successAlert.showAndWait();
                switchToDisplayAllDoctorsPage();
            } catch (SQLException e) {
                // Handle the exception appropriately
                e.printStackTrace();
                // You may want to show an error message to the user
            }
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> listSpecialite = Arrays.asList(
                "Anesthesiology",
                "Cardiology",
                "Dermatology",
                "Endocrinology",
                "Gastroenterology",
                "Neurology",
                "Obstetrics and Gynecology",
                "Ophthalmology",
                "Orthopedics",
                "Pediatrics",
                "Psychiatry",
                "Radiology",
                "Urology"
        );
        ObservableList<String> specialiteList = FXCollections.observableArrayList();
        specialiteR.setItems(specialiteList);

            specialiteList.addAll(listSpecialite);

    }

    public void returnToDisplay(ActionEvent actionEvent) {
    }
}
