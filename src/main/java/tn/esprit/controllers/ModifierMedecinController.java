package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Medecin;
import tn.esprit.services.ServiceMedecin;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierMedecinController {

    @FXML
    private TextField prenom;
    @FXML
    private TextField numeroTel;
    @FXML
    private TextField nom;
    @FXML
    private TextField addresse;
    @FXML
    private TextField specialite;
    private int medecinId;
    private ServiceMedecin serviceMedecin = new ServiceMedecin();

    public void initializeValues(int id, String nomM, String prenomM, int numTelM,
                                 String addresseM, String specialiteM) {
        medecinId = id;
        nom.setText(nomM);
        prenom.setText(prenomM);
        numeroTel.setText(Integer.toString(numTelM));
        addresse.setText(addresseM);
        specialite.setText(specialiteM);

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
        try {
            serviceMedecin.modifier(medecinId, nom.getText(), prenom.getText(), Integer.parseInt(numeroTel.getText())
                    , addresse.getText(), specialite.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Medecin modifier avec succées!");
//          block the execution until the user closes the alert dialog.
            alert.showAndWait();
            switchToDisplayAllDoctorsPage();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
