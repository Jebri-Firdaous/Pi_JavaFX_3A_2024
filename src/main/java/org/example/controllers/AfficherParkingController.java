package org.example.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.example.entities.Parking;
import org.example.services.ParkingService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AfficherParkingController {
    ParkingService ps=new ParkingService();
    public Button addB;
    public ListView<Parking> listid;
    public Button modifB;
    public Button delB;
    Parking currentParkingSelected;
    @FXML
    void initialize(){
        try{
            List<Parking> list = ps.recuperer();
            //listid.getItems().addAll(list);
            ObservableList<Parking> listParking = FXCollections.observableArrayList();
            listid.setItems(listParking);
            try {
                List<Parking> parkingsFromService = ps.recuperer();
                listParking.addAll(list);

                // Set a custom CellFactory for the ListView
                listid.setCellFactory(new Callback<ListView<Parking>, ListCell<Parking>>() {
                    @Override
                    public ListCell<Parking> call(ListView<Parking> param) {
                        return new ListCell<Parking>() {
                            @Override
                            protected void updateItem(Parking parking, boolean empty) {
                                super.updateItem(parking, empty);
                                if (empty || parking == null) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    // Assuming this code is inside a method where you have access to the rendezVous object
                                    String parkingName = parking.getNom();
                                    String addresse = parking.getAddresse();
                                    int nbPlacesLibres = parking.getNbPlaceMax()-parking.getNbPlaceOcc();
                                    String etat = parking.getEtat();
// Create an HBox to hold the details
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(65); // Adjust spacing as needed

// Add details to the HBox with styled Labels
                                    Label nomPrenomDoctorLabel = new Label(parkingName);
                                    nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");

                                /*Label prenomLabel = new Label(doctorSurname);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");*/

                                    Label dateLabel = new Label(addresse);
                                    dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");

                                    Label specialiteLabel = new Label(Integer.toString(nbPlacesLibres));
                                    specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");

                                    Label adresseLabel = new Label(etat);
                                    adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");

// Add the date Label with the full month name

// Add the Labels to the HBox
                                    hbox.getChildren().addAll(nomPrenomDoctorLabel, specialiteLabel, dateLabel, adresseLabel);

// Assuming this is inside a ListCell or similar where you can set the graphic
                                    setGraphic(hbox);

                                }
                            }
                        };
                    }
                });

                listid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Parking>() {
                    @Override
                    public void changed(ObservableValue<? extends Parking> observableValue, Parking oldParking, Parking newParking) {
                        currentParkingSelected = newParking;
                        // Display the selected item
                        if (currentParkingSelected != null) {
                            // Assuming you have a Label to display the selected RendezVous
                            // selectedRendezVousLabel.setText(currentRendezVousSelected.toString());
                        } else {
                            // Assuming you have a Label to display the selected RendezVous
                            // selectedRendezVousLabel.setText("");
                        }
                    }
                });

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void naviguezVersModifier(ActionEvent event) {
        System.out.println(listid.getSelectionModel().getSelectedItem().getNom());
//        Node node = (Node) event.getSource();
//        Stage stage = (Stage) node.getScene().getWindow();
//        stage.setUserData(listid.getSelectionModel().getSelectedItem());
//        Stage s= (Stage) addB.getScene().getWindow();
//        s.getUserData();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierParking.fxml"));
            //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ModifierParking.fxml")));
            Parent root=loader.load();
            ModifierParkingController ctr= loader.getController();
            ctr.init(listid.getSelectionModel().getSelectedItem().getNom(),
                    listid.getSelectionModel().getSelectedItem().getAddresse(),
                    listid.getSelectionModel().getSelectedItem().getNbPlaceMax(),
                    listid.getSelectionModel().getSelectedItem().getRef());
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimerParking(ActionEvent actionEvent) {
        try {
            ps.supprimer(listid.getSelectionModel().getSelectedItem().getRef());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        refresh(actionEvent);
    }

    public void afficherPlace(ActionEvent actionEvent) {
        Node node = (Node) actionEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setUserData(listid.getSelectionModel().getSelectedItem());
//        Stage s= (Stage) addB.getScene().getWindow();
        System.out.println(stage.getUserData());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlace.fxml"));
            Parent root=loader.load();
            AfficherPlaceController ctr= loader.getController();
            ctr.init(listid.getSelectionModel().getSelectedItem().getRef());
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguezVersAjouter(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterParkings.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParking.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
