package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        try {
            serviceMedecin.ajouter(new Medecin(nom.getText(), prenom.getText(), Integer.parseInt(numeroTel.getText()),
                    addresse.getText(), specialiteR.getValue()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Medecin insérée avec succées!");
            alert.showAndWait();
            switchToDisplayAllDoctorsPage();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
