package tn.esprit.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import tn.esprit.entities.Medecin;
import tn.esprit.services.ServiceMedecin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.Stage;

public class AfficherMedecinsController {

    @FXML
    public Label selectedDoctor;
    @FXML
    private ListView<Medecin> listViewMedecins;
    private final ServiceMedecin serviceMedecin = new ServiceMedecin();
    Medecin currentDoctorSelected;


    //    To can populate our list view with data
    public void initialize() {
        ObservableList<Medecin> medecinList = FXCollections.observableArrayList();
        listViewMedecins.setItems(medecinList);
        try {
            List<Medecin> medecinsFromService = serviceMedecin.afficher();
            medecinList.addAll(medecinsFromService);
            listViewMedecins.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldIndex, Number newIndex) {
                    currentDoctorSelected = listViewMedecins.getSelectionModel().getSelectedItem();
//                    display selected item
                    /*if (currentDoctorSelected != null) {
                        selectedDoctor.setText(currentDoctorSelected.toString());
                    } else {
                        selectedDoctor.setText("");
                    }*/
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void BT_supprimer(ActionEvent actionEvent) {
        try {
            serviceMedecin.supprimer(currentDoctorSelected.getId_medecin());
            initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToUpdatePage() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierMedecin.fxml"));
            Parent newPageRoot = loader.load();
            // Get the controller instance created by the FXMLLoader
            ModifierMedecinController modifierMedecinController = loader.getController();
            modifierMedecinController.initializeValues(currentDoctorSelected.getId_medecin(),
                    currentDoctorSelected.getNom_medecin(),
                    currentDoctorSelected.getPrenom_medecin_medecin(), currentDoctorSelected.getNumero_telephone_medecin(),
                    currentDoctorSelected.getAddress_medecin(), currentDoctorSelected.getSpecialite_medecin()
            );

            // Create a new scene with the newPageRoot
            Scene newPageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) selectedDoctor.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchToAddPage() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMedecin.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            // Get the current stage and set the new scene
            Stage stage = (Stage) selectedDoctor.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void BT_Modifier(ActionEvent actionEvent) {
        switchToUpdatePage();
    }


    public void SwitchToAdd(ActionEvent actionEvent) {
        switchToAddPage();
    }
}
