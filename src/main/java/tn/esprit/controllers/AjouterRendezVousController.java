package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import tn.esprit.entities.Medecin;
import tn.esprit.entities.RendezVous;
import tn.esprit.services.ServiceMedecin;
import tn.esprit.services.ServiceRendezVous;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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



    public void reserverBT(ActionEvent actionEvent) {
        ServiceRendezVous serviceRendezVous = new ServiceRendezVous();
        LocalDate date = dateR.getValue(); // Get the date from the DatePicker
        int hour = hourComboBox.getValue(); // Get the hour from the ComboBox
        int minute = minuteComboBox.getValue(); // Get the minute from the ComboBox

        // Combine the date, hour, and minute into a LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
        try {
            serviceRendezVous.ajouter(new RendezVous(dateTime,medecinR.getValue().id_medecin));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Medecin insérée avec succées!");
            alert.showAndWait();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
