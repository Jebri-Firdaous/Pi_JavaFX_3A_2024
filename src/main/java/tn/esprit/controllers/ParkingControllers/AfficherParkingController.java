package tn.esprit.controllers.ParkingControllers;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.MapView;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.ParkingEntities.Parking;
import tn.esprit.services.ParkingServices.ParkingService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class AfficherParkingController {
    public Label nomL;
    public Label etatL;
    public Label addresseL;
    public Label nbL;
    public Group grp2;
    public VBox listVB;
    public Pane grp1;
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
        System.out.println(mapView.getStylesheets());
        grp1.getChildren().add(1, mapView);
        grp1.getChildren().get(1).setTranslateX(7);
        grp1.getChildren().get(1).setTranslateY(14);
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_TOPOGRAPHIC);
        mapView.setMap(map);
        try {
            List<Parking> parkings = new ArrayList<>();
            parkings= ps.recuperer();

            ObservableList<Parking> data1 = FXCollections.observableArrayList(parkings);

            listid = new ListView<>(data1);
            listid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    grp1.setVisible(true);
                    details(newValue);
                }
                else{
                    grp1.setVisible(false);
                }
            });

            Pagination pagination = new Pagination((int) Math.ceil((double) data1.size() / 5), 0);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    int fromIndex = pageIndex * 5;
                    int toIndex = Math.min(fromIndex + 5, data1.size());
                    listid.setItems(FXCollections.observableArrayList(data1.subList(fromIndex, toIndex)));
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
                                        int nbPlacesLibres = parking.getNbPlaceMax() - parking.getNbPlaceOcc();
                                        String etat = parking.getEtat();
                                        // Create an HBox to hold the details
                                        HBox hbox = new HBox();
                                        hbox.setSpacing(20); // Adjust spacing as needed

                                        // Add details to the HBox with styled Labels
                                        Label nomPrenomDoctorLabel = new Label("Parking  " + parkingName);
                                        nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");

                                        Label dateLabel = new Label(addresse);
                                        dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");

                                        Label specialiteLabel = new Label(Integer.toString(nbPlacesLibres));
                                        specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");

                                        Label adresseLabel = new Label(etat);
                                        adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");

                                        // Add the Labels to the HBox
                                        hbox.getChildren().addAll(nomPrenomDoctorLabel);

                                        // Assuming this is inside a ListCell or similar where you can set the graphic
                                        setGraphic(hbox);
                                    }
                                }
                            };
                        }
                    });
                    return new VBox(listid);
                }
            });
            listVB.getChildren().add(pagination);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/ModifierParking.fxml"));
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
            Parking parking = listid.getSelectionModel().getSelectedItem();
            ps.supprimer(listid.getSelectionModel().getSelectedItem().getRef());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Java\\PIDEV\\ProjetPDEV3A8-Smart-City-Codemasters\\src\\main\\resources\\data.txt", true))) {
                StringJoiner joiner = new StringJoiner(",");
                joiner.add(Integer.toString(parking.getRef()));
                joiner.add(parking.getNom());
                joiner.add(parking.getAddresse());
                joiner.add(Integer.toString(parking.getNbPlaceMax()));
                joiner.add(Integer.toString(parking.getNbPlaceOcc()));
                joiner.add(Float.toString(parking.getLati()));
                joiner.add(Float.toString(parking.getLongi()));
                joiner.add(parking.getEtat());
                writer.write(joiner.toString());
                writer.newLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AjouterParkings.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void refresh(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherParkingg.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
//            WebEngine webEngine = mapp.getEngine();
//            webEngine.load(Objects.requireNonNull(getClass().getResource("map.html")).toExternalForm());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void details(Parking newValue) {
        if (newValue.getEtat().equals("Plein"))
            etatL.setVisible(true);
        else
            etatL.setVisible(false);
        etatL.setText(newValue.getEtat());
        nomL.setText("Parking  "+newValue.getNom());
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

    public void naviguerVersBin(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/RecycleBin.fxml"));
            Parent root=loader.load();
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
