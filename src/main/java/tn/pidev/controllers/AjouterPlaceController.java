package tn.pidev.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tn.pidev.entities.Parking;
import tn.pidev.entities.Place;
import tn.pidev.services.ParkingService;
import tn.pidev.services.PlaceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterPlaceController implements Initializable {
    private final PlaceService ps = new PlaceService();
    private final ParkingService parkS = new ParkingService();
    public TextField numTF;
    public ComboBox<String> typeCB;
    public Label errNum;
    public Label errType;
    private boolean test;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCB.setItems(FXCollections.observableArrayList("normal", "moto", "handicap"));
    }
    public void ajouterPlace(ActionEvent actionEvent) {
        if(numTF.getText().isBlank()) {
            errNum.setText("Ce champ est obligatoire!");}
        if(typeCB.getSelectionModel().isEmpty()) {
            errType.setText("Ce champ est obligatoire!");}
        if (test && !typeCB.getSelectionModel().isEmpty()) {
            try {
                System.out.println(typeCB.getValue());
                Stage stage = (Stage) typeCB.getScene().getWindow();
                Parking parking = (Parking) stage.getUserData();
                System.out.println(parking.getRef());
                if (parking.getNbPlaceMax()>parkS.calculNbPlace(parking.getRef())) {
                    ps.ajouter(new Place(Integer.parseInt(numTF.getText()), typeCB.getValue(), parking.getRef()));
//                    parkS.updateNbOcc(parking, 0);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Nombre place maximal atteint!");
                    alert.showAndWait();
                }
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
                    Parent root = loader.load();
                    AfficherPlaceController ctr = loader.getController();
                    ctr.init(parking.getRef());
                    typeCB.getScene().setRoot(root);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void naviguezVersAffichage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) typeCB.getScene().getWindow();
            Parking parking = (Parking) stage.getUserData();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPlaces.fxml"));
            Parent root=loader.load();
            AfficherPlaceController ctr= loader.getController();
            ctr.init(parking.getRef());
            typeCB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void testNum(KeyEvent keyEvent) {
        Stage stage = (Stage) typeCB.getScene().getWindow();
        Parking parking = (Parking) stage.getUserData();
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        numTF.setTextFormatter(formatter);
        if(!numTF.getText().isEmpty()) {
            if (numTF.getText().matches("[0-9]+")) {
                errNum.setText("");
                if (Integer.parseInt(numTF.getText())<=parking.getNbPlaceMax()) {
                    errNum.setText("");
                    try {
                        if (!ps.chercher(Integer.parseInt(numTF.getText()), parking.getRef())) {
                            errNum.setText("");
                            test = true;
                        }
                        else{
                            errNum.setText("Nombre existe deja");
                            test=false;
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    errNum.setText("Nombre doit etre <="+parking.getNbPlaceMax()+"!");
                    test=false;
                }
            } else {
                errNum.setText("Caractere non numerique!");
                test=false;
            }
        }else {
            errNum.setText("");
            test = false;
        }
    }

    public void naviguezVersParking(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParkingsss.fxml"));
            Parent root = loader.load();
            typeCB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
