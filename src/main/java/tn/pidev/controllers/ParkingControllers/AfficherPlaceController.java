package tn.pidev.controllers.ParkingControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.pidev.entities.ParkingEntities.Parking;
import tn.pidev.entities.ParkingEntities.Place;
import tn.pidev.services.ParkingServices.ParkingService;
import tn.pidev.services.ParkingServices.PlaceService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import net.glxn.qrgen.javase.QRCode;
import net.glxn.qrgen.core.image.ImageType;

import javax.mail.MessagingException;

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
    public VBox listVB;
    PlaceService ps=new PlaceService();
    private final ParkingService parkS = new ParkingService();
    public ListView<Place> listid;
    public int ref;
    private Place currentPlaceSelected;
    public void init(int ref){
        this.ref=ref;
        System.out.println(ref);
        try {
            List<Place> places =ps.recupererFiltrer(ref);
            ObservableList<Place> data1 = FXCollections.observableArrayList(places);

            listid = new ListView<>(data1);
            listid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    details(newValue);
                }
            });
            listid.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    details(newValue);
                }
            });

            Pagination pagination = new Pagination((int) Math.ceil((double) data1.size() / 5), 0);
            pagination.setPageFactory(new Callback<Integer, Node>() {
                @Override
                public Node call(Integer pageIndex) {
                    int fromIndex = pageIndex * 5;
                    int toIndex = Math.min(fromIndex + 5, data1.size());
                    listid.setItems(FXCollections.observableArrayList(data1.subList(fromIndex, toIndex)));
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
    public void naviguezVersModifier(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/ModifierPlace.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AjouterPlace.fxml"));
            Parent root=loader.load();
            listid.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void naviguerVersParkings(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/ParkingResources/AfficherParkingg.fxml")));
            addB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void reserverPlace(ActionEvent actionEvent) {
        Stage stage = (Stage) addB.getScene().getWindow();
        Parking parking = (Parking) stage.getUserData();
        Place p = listid.getSelectionModel().getSelectedItem();
        if(p.getEtat().equals("Libre")) {
            // GENERATE QR CODE
            ByteArrayOutputStream out = QRCode.from(Integer.toString(p.getRef_place())+" , "+Integer.toString(p.getIdCli())).to(ImageType.PNG).withSize(100, 100).stream();
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

            // SHOW QR CODE
            Image image = new Image(in);
            try {
                listid.getSelectionModel().getSelectedItem().setIdCli(idCli.getValue());
                ps.updateEtat(listid.getSelectionModel().getSelectedItem(), 0);
                parkS.updateNbOcc(parkS.recupererById(parking.getRef()), 0);
                try {
                    // Exemple avec JavaMail
                    String subject = "Parking QRC";
                    String body = "Bonjour,\n\nVotre QR code de la place reserve est : ";
                    EmailSender.sendEmail("f.felhi2016@gmail.com", subject, body, image); // Utilisez votre méthode d'envoi d'e-mail
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
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
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
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
