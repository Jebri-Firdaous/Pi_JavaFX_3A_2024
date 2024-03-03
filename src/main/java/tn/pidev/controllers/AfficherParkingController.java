package tn.pidev.controllers;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.pidev.entities.Parking;
import tn.pidev.services.ParkingService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.scene.web.WebView;

public class AfficherParkingController {
    public Label nomL;
    public Label etatL;
    public Label addresseL;
    public Label nbL;
    public Group grp2;
    public WebView mapp;
    public ToggleGroup etat;
    ParkingService ps=new ParkingService();
    public Button addB;
    public ListView<Parking> listid;
    public Button modifB;
    public Button delB;
    Parking currentParkingSelected;
    private MapView mapView;

    @FXML
    void initialize(){
        String yourApiKey = "AAPK35cf4b021e7a4c019b63243490179ac2jBL4IBgUxMIrLCpGR5HmtaB-FMYQ85qBAkLF6T5MDlW9AIDXn06SI6mVpyKvGmuy";
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);
        mapView = new MapView();
        mapView.setPrefWidth(534);
        mapView.setPrefHeight(226);
        grp2.getChildren().add(1, mapView);
        grp2.getChildren().get(1).setTranslateX(32);
        grp2.getChildren().get(1).setTranslateY(8);
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);
        mapView.setMap(map);
        listid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                details(newValue);
            }
        });
        try{
            List<Parking> list = ps.recuperer();
            if(etat.getSelectedToggle().toString()!=null) {
                list = ps.recupererByEtat(etat.getSelectedToggle().toString());
            }
            ObservableList<Parking> listParking = FXCollections.observableArrayList();
            listid.setItems(listParking);
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
                                hbox.setSpacing(20); // Adjust spacing as needed

// Add details to the HBox with styled Labels
                                Label nomPrenomDoctorLabel = new Label("Parking  "+parkingName);
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
                                hbox.getChildren().addAll(nomPrenomDoctorLabel);

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
                    listid.getSelectionModel().getSelectedItem().getRef(),
                    listid.getSelectionModel().getSelectedItem().getLati(),
                    listid.getSelectionModel().getSelectedItem().getLongi());
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
//        System.out.println(stage.getUserData());
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParkingsss.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
//            WebEngine webEngine = mapp.getEngine();
//            webEngine.load(Objects.requireNonNull(getClass().getResource("map.html")).toExternalForm());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void details(Parking newValue) {
        nomL.setText(newValue.getNom());
        addresseL.setText(newValue.getAddresse());
        try {
            nbL.setText(Integer.toString(ps.calculNbPlace(newValue.getRef())-newValue.getNbPlaceOcc()));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        grp2.setStyle("-fx-border-width: 2px;");
        grp2.setVisible(true);
        mapView.setViewpoint(new Viewpoint(newValue.getLati(), newValue.getLongi(), 5000));
    }
}
