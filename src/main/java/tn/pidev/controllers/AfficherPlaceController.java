package tn.pidev.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.pidev.entities.Parking;
import tn.pidev.entities.Place;
import tn.pidev.services.ParkingService;
import tn.pidev.services.PlaceService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;

public class AfficherPlaceController {
    public Button addB;
    public Button res;
    public Button cancelRes;
    public ImageView qr;
    public Label numL;
    public Label etatL;
    public Label addresseL;
    public ComboBox<Integer> idCli;
    public Group grp2;
    PlaceService ps=new PlaceService();
    private final ParkingService parkS = new ParkingService();
    public ListView<Place> listid;
    public int ref;
    private Place currentPlaceSelected;
    public void init(int ref){
        this.ref=ref;
        System.out.println(ref);
        listid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                details(newValue);
            }
        });
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
                                    Label numLabel = new Label("Place Numero " + Integer.toString(numPlace));
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
                                    hbox.getChildren().addAll(numLabel);

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
        Stage stage = (Stage) addB.getScene().getWindow();
        Parking parking = (Parking) stage.getUserData();
        try {
            ps.supprimer(listid.getSelectionModel().getSelectedItem().getRef_place());
            parkS.updateNbOcc(parking, 1);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
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
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherParkingsss.fxml")));
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void reserverPlace(ActionEvent actionEvent) {
        Stage stage = (Stage) addB.getScene().getWindow();
        Parking parking = (Parking) stage.getUserData();
        if(listid.getSelectionModel().getSelectedItem().getEtat().equals("Libre")) {
//            // GENERATE QR CODE
//            ByteArrayOutputStream out = QRCode.from("1234test").to(ImageType.PNG).withSize(100, 100).stream();
//            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
//
//            // SHOW QR CODE
//            Image image = new Image(in);
//            qr.setImage(image);
            try {
                listid.getSelectionModel().getSelectedItem().setIdCli(idCli.getValue());
                ps.updateEtat(listid.getSelectionModel().getSelectedItem(), 0);
                parkS.updateNbOcc(parkS.recupererById(parking.getRef()), 0);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
                    Parent root = loader.load();
                    AfficherPlaceController ctr = loader.getController();
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

    public void annulerReservation(ActionEvent actionEvent) {
        Stage stage = (Stage) addB.getScene().getWindow();
        Parking parking = (Parking) stage.getUserData();
        if(listid.getSelectionModel().getSelectedItem().getEtat().equals("Reservee")) {
            try {
                ps.updateEtat(listid.getSelectionModel().getSelectedItem(), 1);
                parkS.updateNbOcc(parkS.recupererById(parking.getRef()), 1);
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
                    Parent root = loader.load();
                    AfficherPlaceController ctr = loader.getController();
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

    private void details(Place newValue) {
        numL.setText(Integer.toString(newValue.getNum_place()));
        addresseL.setText(newValue.getEtat());
        idCli.getItems().clear();
        try {
            idCli.getItems().addAll(ps.refUser());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (newValue.getEtat().equals("Libre")) {
            cancelRes.setVisible(false);
            res.setVisible(true);
            qr.setVisible(false);
        }else {
            cancelRes.setVisible(true);
            res.setVisible(false);
            qr.setVisible(true);
            // GENERATE QR CODE
            ByteArrayOutputStream out = QRCode.from(Integer.toString(newValue.getRef_place())+" , "+Integer.toString(newValue.getIdCli())).to(ImageType.PNG).withSize(100, 100).stream();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            // SHOW QR CODE
            Image image = new Image(in);
            qr.setImage(image);
        }
        System.out.println(newValue.getIdCli());
        idCli.setValue(newValue.getIdCli());
        grp2.setStyle("-fx-border-width: 2px;");
        grp2.setVisible(true);
    }
}
