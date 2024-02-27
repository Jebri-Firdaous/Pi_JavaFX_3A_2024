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
import org.example.entities.Place;
import org.example.services.ParkingService;
import org.example.services.PlaceService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AfficherPlaceController {
    public Button addB;
    public Button res;
    public Button cancelRes;
    PlaceService ps=new PlaceService();
    public ListView<Place> listid;
    public int ref;
    private Place currentPlaceSelected;
    public void init(int ref){
        this.ref=ref;
        System.out.println(ref);
        try{
        List<Place>list=ps.recupererFiltrer(ref);
        listid.getItems().addAll(list);
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        try{
            List<Place>list=ps.recupererFiltrer(ref);
            //listid.getItems().addAll(list);
            ObservableList<Place> listPlace = FXCollections.observableArrayList();
            listid.setItems(listPlace);
            try {
                List<Place> rendezvousFromService = ps.recuperer();
                listPlace.addAll(list);

                // Set a custom CellFactory for the ListView
                listid.setCellFactory(new Callback<ListView<Place>, ListCell<Place>>() {
                    @Override
                    public ListCell<Place> call(ListView<Place> param) {
                        return new ListCell<Place>() {
                            @Override
                            protected void updateItem(Place place, boolean empty) {
                                super.updateItem(place, empty);
                                if (empty || place == null) {
                                    setText(null);
                                    setGraphic(null);
                                } else {
                                    // Assuming this code is inside a method where you have access to the rendezVous object
                                    int numPlace = place.getNum_place();
                                    String typePlace = place.getType_place();
                                    String placeEtat = place.getEtat();
// Create an HBox to hold the details
                                    HBox hbox = new HBox();
                                    hbox.setSpacing(40); // Adjust spacing as needed

// Add details to the HBox with styled Labels
                                    Label numLabel = new Label(Integer.toString(numPlace));
                                    numLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");

                                /*Label prenomLabel = new Label(doctorSurname);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");*/

                                    Label typeLabel = new Label(typePlace);
                                    typeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");

                                    Label etatLabel = new Label(placeEtat);
                                    etatLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");
// Add the date Label with the full month name
                                    HBox hbox2 = new HBox();
                                    hbox.setSpacing(5); // Adjust spacing as needed
//
//                                    Button modifB = new Button("Modifier");
//                                    modifB.setOnAction(Eve);
//                                    Button suppB = new Button("Supprimer");

// Add the Labels to the HBox
//                                    hbox2.getChildren().addAll(modifB, suppB);
                                    hbox.getChildren().addAll(numLabel, typeLabel, etatLabel);

// Assuming this is inside a ListCell or similar where you can set the graphic
                                    setGraphic(hbox);

                                }
                            }
                        };
                    }
                });

                listid.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Place>() {
                    @Override
                    public void changed(ObservableValue<? extends Place> observableValue, Place oldPlace, Place newPlace) {
                        currentPlaceSelected = newPlace;
                        // Display the selected item
                        if (currentPlaceSelected != null) {
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
//    @FXML
//    void initialize(){
//        try{
//            List<Place> list = ps.recuperer();
//            listid.getItems().addAll(list);
//            listid.getItems().clear();
//            Node node =(Node) listid.getScene().lookup("#addB");
//            Stage stage= (Stage) node.getScene().getWindow();
//            Place place= (Place) stage.getUserData();
//            System.out.println(ref);
//            List<Place>list=ps.recupererFiltrer(ref);
//            listid.getItems().addAll(list);
//        }catch (SQLException e){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Error");
//            alert.setContentText(e.getMessage());
//            alert.showAndWait();
//        }
//    }
    public void naviguezVersModifier(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPlace.fxml"));
            Parent root=loader.load();
            ModifierPlaceController ctr= loader.getController();
            ctr.init(listid.getSelectionModel().getSelectedItem().getRef_place(),
                    listid.getSelectionModel().getSelectedItem().getNum_place(),
                    listid.getSelectionModel().getSelectedItem().getType_place());
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimerPlace(ActionEvent actionEvent) {
        try {
            ps.supprimer(listid.getSelectionModel().getSelectedItem().getRef_place());
            try {
                Stage stage = (Stage) addB.getScene().getWindow();
                Parking parking = (Parking) stage.getUserData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlace.fxml"));
                Parent root=loader.load();
                AfficherPlaceController ctr= loader.getController();
                ctr.init(parking.getRef());
                addB.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguerVersAjouter(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPlace.fxml"));
            Parent root=loader.load();
            listid.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguerVersParkings(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParking.fxml")));
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void reserverPlace(ActionEvent actionEvent) {
        try {
            ps.updateEtat(listid.getSelectionModel().getSelectedItem().getRef_place(), 0);
            try {
                Stage stage = (Stage) addB.getScene().getWindow();
                Parking parking = (Parking) stage.getUserData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlace.fxml"));
                Parent root=loader.load();
                AfficherPlaceController ctr= loader.getController();
                ctr.init(parking.getRef());
                addB.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void annulerReservation(ActionEvent actionEvent) {
        try {
            ps.updateEtat(listid.getSelectionModel().getSelectedItem().getRef_place(), 1);
            try {
                Stage stage = (Stage) addB.getScene().getWindow();
                Parking parking = (Parking) stage.getUserData();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlace.fxml"));
                Parent root=loader.load();
                AfficherPlaceController ctr= loader.getController();
                ctr.init(parking.getRef());
                addB.getScene().setRoot(root);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
