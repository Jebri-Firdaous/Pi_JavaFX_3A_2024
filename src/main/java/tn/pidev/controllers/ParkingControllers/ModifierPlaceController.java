package tn.pidev.controllers.ParkingControllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tn.pidev.entities.ParkingEntities.Parking;
import tn.pidev.entities.ParkingEntities.Place;
import tn.pidev.services.ParkingServices.PlaceService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierPlaceController  implements Initializable {
    private final PlaceService ps = new PlaceService();
    public TextField numTF;
    public ComboBox<String> typeCB;
    public TextField idTF;
    public Label errNum;
    public Label errType;
    private boolean test;
    int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCB.setItems(FXCollections.observableArrayList("normal", "moto", "handicap"));
    }
    public void init(int idTF, int num, String type){
        this.id=idTF;
        this.numTF.setText(Integer.toString(num));
        this.typeCB.setValue(type);
    }
    public void Update(ActionEvent actionEvent) {
        if(numTF.getText().isBlank()) {
            errNum.setText("Ce champ est obligatoire!");}
        if(typeCB.getSelectionModel().isEmpty()) {
            errType.setText("Ce champ est obligatoire!");}
        if (test && !typeCB.getSelectionModel().isEmpty()) {
            try {
                Place p = new Place(id,
                        Integer.parseInt(numTF.getText()),
                        typeCB.getValue());
                ps.modifier(p);
                try {
                    Stage stage = (Stage) typeCB.getScene().getWindow();
                    Parking parking = (Parking) stage.getUserData();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
                    Parent root = loader.load();
                    AfficherPlaceController ctr = loader.getController();
                    ctr.init(parking.getRef());
                    typeCB.getScene().setRoot(root);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void naviguerVersAffichage(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) typeCB.getScene().getWindow();
            Parking parking = (Parking) stage.getUserData();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherPlaces.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherParkingg.fxml"));
            Parent root = loader.load();
            typeCB.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
