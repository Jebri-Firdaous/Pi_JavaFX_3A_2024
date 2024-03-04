package tn.esprit.controllers.ParkingControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import tn.esprit.entities.ParkingEntities.Parking;
import tn.esprit.services.ParkingServices.ParkingService;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class RecycleBinController implements Initializable {
    public ListView<Parking> listBin;
    private ObservableList<Parking> data;
    private ParkingService ps = new ParkingService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        listBin.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                details(newValue);
//            }
//        });
            data = FXCollections.observableArrayList();
            listBin.setItems(data);
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Java\\PIDEV\\ProjetPDEV3A8-Smart-City-Codemasters\\src\\main\\resources\\data.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    data.add(new Parking(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Float.parseFloat(parts[5]), Float.parseFloat(parts[6]), parts[7]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//            List<Parking> list = ps.recuperer();
//            ObservableList<Parking> listParking = FXCollections.observableArrayList();
//            listid.setItems(listParking);
//            listParking.addAll(list);

            // Set a custom CellFactory for the ListView
//            listBin.setCellFactory(new Callback<ListView<Parking>, ListCell<Parking>>() {
//                @Override
//                public ListCell<Parking> call(ListView<Parking> param) {
//                    return new ListCell<Parking>() {
//                        @Override
//                        protected void updateItem(Parking parking, boolean empty) {
//                            super.updateItem(parking, empty);
//                            if (empty || parking == null) {
//                                setText(null);
//                                setGraphic(null);
//                            } else {
//                                // Assuming this code is inside a method where you have access to the rendezVous object
//                                String parkingName = parking.getNom();
//                                String addresse = parking.getAddresse();
//                                int nbPlacesLibres = parking.getNbPlaceMax()-parking.getNbPlaceOcc();
//                                String etat = parking.getEtat();
//// Create an HBox to hold the details
//                                HBox hbox = new HBox();
//                                hbox.setSpacing(20); // Adjust spacing as needed
//
//// Add details to the HBox with styled Labels
//                                Label nomPrenomDoctorLabel = new Label("Parking  "+parkingName);
//                                nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");
//
//                            /*Label prenomLabel = new Label(doctorSurname);
//                            prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");*/
//
//                                Label dateLabel = new Label(addresse);
//                                dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");
//
//                                Label specialiteLabel = new Label(Integer.toString(nbPlacesLibres));
//                                specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");
//
//                                Label adresseLabel = new Label(etat);
//                                adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");
//
//// Add the date Label with the full month name
//
//// Add the Labels to the HBox
//                                hbox.getChildren().addAll(nomPrenomDoctorLabel);
//
//// Assuming this is inside a ListCell or similar where you can set the graphic
//                                setGraphic(hbox);
//
//                            }
//                        }
//                    };
//                }
//            });

//            listBin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Parking>() {
//                @Override
//                public void changed(ObservableValue<? extends Parking> observableValue, Parking oldParking, Parking newParking) {
//                    currentParkingSelected = newParking;
//                    // Display the selected item
//                    if (currentParkingSelected != null) {
//                        // Assuming you have a Label to display the selected RendezVous
//                        // selectedRendezVousLabel.setText(currentRendezVousSelected.toString());
//                    } else {
//                        // Assuming you have a Label to display the selected RendezVous
//                        // selectedRendezVousLabel.setText("");
//                    }
//                }
//            });
    }

    public void naviguerVersParking(ActionEvent actionEvent) {
    }

    public void refresh(ActionEvent actionEvent) {
    }

    public void returnP(ActionEvent actionEvent) {
        Parking parking = listBin.getSelectionModel().getSelectedItem();
        try {
            ps.ajouterFull(parking);
            updateFile(parking);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addToFile(Parking parking){
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
    }
    public void updateFile(Parking p){
        ObservableList<Parking> data = listBin.getItems();
        data.remove(p);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File("D:\\Java\\PIDEV\\ProjetPDEV3A8-Smart-City-Codemasters\\src\\main\\resources\\data.txt"), "rw")) {
            randomAccessFile.setLength(0); // Truncates the file to size 0
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Java\\PIDEV\\ProjetPDEV3A8-Smart-City-Codemasters\\src\\main\\resources\\data.txt", true))) {
            for (Parking parking : data) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
