package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
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
