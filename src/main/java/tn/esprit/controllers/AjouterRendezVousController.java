package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.Medecin;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.ServiceMedecin;
import tn.esprit.services.ServiceRendezVous;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterRendezVousController implements Initializable {

    public ComboBox<String> specialiteR;
    public Label adresse_TextField;
    public Label n_TelTextField;
    @FXML
    private ComboBox<Integer> hourComboBox;

    @FXML
    private ComboBox<Integer> minuteComboBox;

    @FXML
    private ComboBox<Medecin> medecinR;

    @FXML
    private DatePicker dateR;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize ComboBox specialiteCombobox
        ServiceMedecin serviceMedecin = new ServiceMedecin();
        ObservableList<String> specialiteList = FXCollections.observableArrayList();
        specialiteR.setItems(specialiteList);
        try {
            specialiteList.addAll(serviceMedecin.getAllSpecialié());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Listen for changes in specialiteR selection
        specialiteR.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                // Call method to initialize medecinR based on the selected speciality
                initialiserComboboxMedecin();
                n_TelTextField.setText("N°Tel");
                adresse_TextField.setText("Adresse");
            }
        });
        // Initialize the hour ComboBox with values from  0 to  23
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 8; i < 17; i++) {
            hours.add(i);
        }
        hourComboBox.setItems(hours);

        // Initialize the minute ComboBox with values from  0 to  59
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i += 30) {
            minutes.add(i);
        }
        minuteComboBox.setItems(minutes);

    }

    //    public void initialiserComboboxMedecin(){
//        ServiceMedecin serviceMedecin = new ServiceMedecin();
//        ObservableList<Medecin> medecinListBySpecialite = FXCollections.observableArrayList();
//        medecinR.setItems(medecinListBySpecialite);
//        medecinListBySpecialite.addAll(serviceMedecin.getMedecinBySpecialite(specialiteR.getValue()));
//
//    }
    @FXML
    public void initialiserComboboxMedecin() {
        ServiceMedecin serviceMedecin = new ServiceMedecin();
        ObservableList<Medecin> medecinListBySpecialite = FXCollections.observableArrayList();
        medecinR.setItems(medecinListBySpecialite);
        medecinListBySpecialite.addAll(serviceMedecin.getMedecinBySpecialite(specialiteR.getValue()));

        // Display the medecinR ComboBox items using toStringWithSpeciality() method
        medecinR.setCellFactory(listView -> new ListCell<Medecin>() {
            @Override
            protected void updateItem(Medecin item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    n_TelTextField.setText("N°Tel");
                    adresse_TextField.setText("Adresse");
                } else {
                    setText(item.toStringNomPrenom());
                    n_TelTextField.setText(Integer.toString(item.getNumero_telephone_medecin()));
                    adresse_TextField.setText(item.getAddress_medecin());
                }
            }
        });

    }

    public void switchToDisplayAllRVPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherListRendezVous.fxml"));
            Parent newPageRoot = loader.load();
            AfficherListRendezVousController afficherListRendezVousController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) adresse_TextField.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Timestamp> getUnavailableTimesForDoctor(Medecin doctor) {
        List<Timestamp> unavailableTimes = new ArrayList<>();

        return unavailableTimes;
    }


    @FXML
    public void reserverBT(ActionEvent actionEvent) throws SQLException {
        // Check if the medecinR, specialiteR, hourComboBox, and minuteComboBox fields are filled
        if (medecinR.getValue() == null || specialiteR.getValue() == null || hourComboBox.getValue() == null || minuteComboBox.getValue() == null
                || dateR.getValue() == null) {
            // Show an alert if any field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields: Medecin, Specialite, Hour, and Minute.");
            alert.showAndWait();
            return;
        }
        LocalDate date = dateR.getValue(); // Get the date from the DatePicker
        int hour = hourComboBox.getValue(); // Get the hour from the ComboBox
        int minute = minuteComboBox.getValue(); // Get the minute from the ComboBox

        // Combine the date, hour, and minute into a LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        ServiceRendezVous serviceRendezVous = new ServiceRendezVous();
        List<LocalDateTime> listRVbyidMedecin = serviceRendezVous.getAllDateRendezVousByidMedeicn(medecinR.getValue().getId_medecin());
        if (listRVbyidMedecin.contains(dateTime)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("the doctor have an appoitment in this date");
            alert.setHeaderText(null);
            alert.setContentText("Choose an other date or time ");
            alert.showAndWait();
            return;
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Choose a date that after now ");
            alert.setHeaderText(null);
            alert.setContentText("Choose a date that after now");
            alert.showAndWait();
            return;

        }
        try {
            serviceRendezVous.ajouter(new RendezVous(dateTime, medecinR.getValue().id_medecin));
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Information Dialog");
            successAlert.setContentText("Rendez-vous reserved successfully!");
            successAlert.showAndWait();
            switchToDisplayAllRVPage();
        } catch (SQLException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            // You may want to show an error message to the user
        }


    }

    public void backOneMonth(ActionEvent actionEvent) {
    }

    public void forwardOneMonth(ActionEvent actionEvent) {
    }
}
