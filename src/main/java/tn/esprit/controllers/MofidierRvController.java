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
import tn.esprit.services.ServiceMedecin;
import tn.esprit.services.ServiceRendezVous;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MofidierRvController implements Initializable {
    public ComboBox<String> specialiteR;
    public ComboBox<Medecin> medecinR;
    public Label adresse_TextField;
    public Label n_TelTextField;
    public DatePicker dateR;
    public ComboBox<Integer> hourComboBox;
    public ComboBox<Integer> minuteComboBox;
    int ref_rendez_vous;
    ServiceRendezVous serviceRendezVous = new ServiceRendezVous();

    public void initializeValues(int ref_rendez_vous , Timestamp date_rendez_vous, int id_medecin) {
        this.ref_rendez_vous = ref_rendez_vous;
        dateR.setValue(date_rendez_vous.toLocalDateTime().toLocalDate());
        hourComboBox.setValue(date_rendez_vous.getHours());
        minuteComboBox.setValue(date_rendez_vous.getMinutes());
        ServiceMedecin serviceMedecin = new ServiceMedecin();
        Medecin medecin = serviceMedecin.getMedecinById(id_medecin);
        n_TelTextField.setText(Integer.toString(medecin.getNumero_telephone_medecin()));
        adresse_TextField.setText(medecin.getAddress_medecin());
        specialiteR.setValue(medecin.getSpecialite_medecin());
        medecinR.setValue(medecin);

    }

    public void modiferRvBT(ActionEvent actionEvent) {
        try {
            LocalDate date = dateR.getValue(); // Get the date from the DatePicker
            int hour = hourComboBox.getValue(); // Get the hour from the ComboBox
            int minute = minuteComboBox.getValue(); // Get the minute from the ComboBox

            // Combine the date, hour, and minute into a LocalDateTime
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            Timestamp timestamp = Timestamp.valueOf(dateTime);
            serviceRendezVous.modifier(ref_rendez_vous,timestamp,medecinR.getValue().id_medecin);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Medecin modifier avec succées!");
//          block the execution until the user closes the alert dialog.
            alert.showAndWait();
            switchToDisplayAllRV();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

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
        for (int i =  8; i <  17; i++) {
            hours.add(i);
        }
        hourComboBox.setItems(hours);

        // Initialize the minute ComboBox with values from  0 to  59
        ObservableList<Integer> minutes = FXCollections.observableArrayList();

        for (int i =  0; i <  60; i+=30) {
            minutes.add(i);
        }
        minuteComboBox.setItems(minutes);
}
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
    public void switchToDisplayAllRV() {
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
    public void forwardOneMonth(ActionEvent actionEvent) {
    }

    public void backOneMonth(ActionEvent actionEvent) {
    }}

