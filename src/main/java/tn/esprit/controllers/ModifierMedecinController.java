package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tn.esprit.entities.Medecin;
import tn.esprit.services.ServiceMedecin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ModifierMedecinController implements Initializable {
    @FXML
    public ComboBox<String> specialiteM;
    public Label msgErreur;
    @FXML
    private TextField prenom;
    @FXML
    private TextField numeroTel;
    @FXML
    private TextField nom;
    @FXML
    private TextField addresse;

    private int medecinId;
    private ServiceMedecin serviceMedecin = new ServiceMedecin();

    public void initializeValues(int id, String nomM, String prenomM, int numTelM,
                                 String addresseM, String specialityM) {
        medecinId = id;
        nom.setText(nomM);
        prenom.setText(prenomM);
        numeroTel.setText(Integer.toString(numTelM));
        addresse.setText(addresseM);
        specialiteM.setValue(specialityM);

    }


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


    public void ModifierMedecin(ActionEvent actionEvent) {
        String nomText = nom.getText();
        String prenomText = prenom.getText();
        String numeroTelText = numeroTel.getText();
        String addresseText = addresse.getText();

        // Check if all fields are filled
        if (nomText.isEmpty() || prenomText.isEmpty() || numeroTelText.isEmpty() || addresseText.isEmpty()) {
            // Show an alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information manquante");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs : Nom, Prénom, Numéro de téléphone et Adresse.");
            alert.showAndWait();
        } else if (numeroTelText.length() !=   8 || !numeroTelText.matches("\\d+") || !numeroTelText.startsWith("5") && !numeroTelText.startsWith("2") && !numeroTelText.startsWith("9")) {
            // Show an alert if the phone number is not valid
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Numéro de téléphone invalide");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit comporter exactement 8 caractères numériques et commencer par 5, 2 ou 9");
            alert.showAndWait();
        } else {
            try {
                serviceMedecin.modifier(medecinId, nom.getText(), prenom.getText(), Integer.parseInt(numeroTel.getText())
                        , addresse.getText(), specialiteM.getValue());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Boîte de dialogue d'information");
                alert.setContentText("Médecin modifié avec succès !");
//          block the execution until the user closes the alert dialog.
                alert.showAndWait();
                switchToDisplayAllDoctorsPage();

            } catch (SQLException e) {
                throw new RuntimeException(e);
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
        specialiteM.setItems(specialiteList);

        specialiteList.addAll(listSpecialite);

    }

    public void returnToDisplayM(ActionEvent actionEvent) {
        switchToDisplayAllDoctorsPage();
    }

    public void numTelTyped(KeyEvent keyEvent) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        numeroTel.setTextFormatter(formatter);
        if (!numeroTel.getText().isEmpty()) {
            if (numeroTel.getText().matches("\\d+") && numeroTel.getText().length() == 8) {
                msgErreur.setText("");
                numeroTel.setStyle("-fx-border-color: blue; -fx-border-width: 1px; -fx-border-style: solid");
            } else {
                msgErreur.setText("N°Tel invalide");
                msgErreur.setStyle("-fx-text-fill: red");
                numeroTel.setStyle("-fx-border-width: 1px;-fx-border-color: red;");
                numeroTel.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-border-style: solid");

            }
        }
        else {
            msgErreur.setText("");
        }
    }
}
