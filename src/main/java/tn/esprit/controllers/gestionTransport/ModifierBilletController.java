package tn.esprit.controllers.gestionTransport;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.controllers.gestionMedecin.AfficherMedecinsController;
import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.services.gestionTransport.BilletService;
import tn.esprit.services.gestionTransport.StationService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifierBilletController implements Initializable {

    @FXML
    private TextField mddestination;

    @FXML
    private DatePicker mdateR;

    @FXML
    private ComboBox<Integer> mminuteCombobox;

    @FXML
    private MenuButton crudTransport;

    @FXML
    private TextField mprix;

    @FXML
    private ComboBox<Integer> mhourCombobox;

    @FXML
    private TextField mduree;

    @FXML
    private ComboBox<Station> mdepart;
    private int ref;
    private float duree;

    BilletService billetService = new BilletService();
    public void initialize () {
        initialize(null, null);
    }
    public void initialize (URL location, ResourceBundle resources) {
        // Initialize ComboBox specialiteCombobox

        ObservableList<Station> stationList = FXCollections.observableArrayList();
        mdepart.setItems(stationList);

        try {
            stationList.addAll(billetService.getStations());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Initialize the hour ComboBox with values from  0 to  23
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i =  8; i <  17; i++) {
            hours.add(i);
        }
        mhourCombobox.setItems(hours);

        // Initialize the minute ComboBox with values from  0 to  59
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i =  0; i <  60; i+=30) {
            minutes.add(i);
        }
        mminuteCombobox.setItems(minutes);



        // Create menu items and add them to the MenuButton
        // Créer des éléments de menu
        System.out.println("methode");
        MenuItem modifierItem = new MenuItem("Ajouter");
        MenuItem afficherItem = new MenuItem("Afficher");
        MenuItem supprimerItem = new MenuItem("Supprimer");

        // Associer des actions aux éléments de menu

        modifierItem.setOnAction(event -> ajouterAction());
        afficherItem.setOnAction(event -> afficherAction());
        supprimerItem.setOnAction(event -> supprimerAction());

        // Ajouter les éléments de menu au MenuButton
        //crudTransport.getItems().addAll( modifierItem, afficherItem, supprimerItem);
        System.out.println("item");
    }

    private void supprimerAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml")));
            mddestination.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml")));
            mddestination.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void ajouterAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AjoutBillet.fxml")));
            mddestination.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /*
        public void initializeValues(int ref, String destination, Timestamp date_voyage, float prix, float duree, Station station, String nom) throws SQLException {
            this.ref = ref;
            this.duree=duree;
            mdateR.setValue(date_voyage.toLocalDateTime().toLocalDate());
            mhourCombobox.setValue(date_voyage.getHours());
            mminuteCombobox.setValue(date_voyage.getMinutes());

            String dureeVoyage = mduree.getText().trim();
            System.out.println("Valeur de dureeVoyage : \"" + dureeVoyage + "\"");
            float dureeFloat;
            try {
                dureeFloat = Float.parseFloat(dureeVoyage);
            } catch (NumberFormatException e) {
                System.out.println("La chaîne dureeVoyage n'est pas un nombre flottant valide : " + dureeVoyage);
                e.printStackTrace(); // Afficher la trace de la pile pour un débogage supplémentaire
                // Gérer l'erreur, par exemple, en assignant une valeur par défaut
                dureeFloat = 0.0f; // ou une autre valeur par défaut
            }

            String prixVoyage = mprix.getText().trim();
            float prixFloat;
            try {
                prixFloat = Float.parseFloat(prixVoyage);
            } catch (NumberFormatException e) {
                System.out.println("La chaîne prixVoyage n'est pas un nombre flottant valide : " + prixVoyage);
                e.printStackTrace(); // Afficher la trace de la pile pour un débogage supplémentaire
                // Gérer l'erreur, par exemple, en assignant une valeur par défaut
                prixFloat = 0.0f;
            }
            this.duree=dureeFloat;
            mddestination.setText(destination);
            StationService stationService = new StationService();
            station = stationService.getStationByNom(nom);
            mdepart.setValue(station);
        }
    */
public void initializeValues(int ref, String destination, Timestamp date_voyage, String prix, String duree, int id_station) throws SQLException {
    this.ref = ref;
    mdateR.setValue(date_voyage.toLocalDateTime().toLocalDate());
    mhourCombobox.setValue(date_voyage.getHours());
    mminuteCombobox.setValue(date_voyage.getMinutes());
    mduree.setText(duree);
    mprix.setText(prix);
    mddestination.setText(destination);
    StationService stationService = new StationService();
    Station station= stationService.getStationById(id_station);
    mdepart.setValue(station);

}


 public void modifybillet(ActionEvent actionEvent) throws SQLException, IOException {
     String destination = mddestination.getText();
     String prix = mprix.getText();
     String duree = mduree.getText();
     Object selectedObject2 = mdateR.getValue();
     String dateString = (selectedObject2 != null) ? selectedObject2.toString() : "";
     Station selectedStation = mdepart.getValue();
     Timestamp dateFinal;
         LocalDate date = mdateR.getValue(); // Get the date from the DatePicker
         int hour = mhourCombobox.getValue(); // Get the hour from the ComboBox
         int minute = mminuteCombobox.getValue(); // Get the minute from the ComboBox

         // Combine the date, hour, and minute into a LocalDateTime
         LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
     if (dateTime.isBefore(LocalDateTime.now())) {
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.setTitle("Choisir un date convenable ");
         alert.setHeaderText(null);
         alert.setContentText("Choisir un date convenable");
         alert.showAndWait();
         return;
     }
         dateFinal = Timestamp.valueOf(dateTime);

     StationService stationService = new StationService();
     int id_station = stationService.getStationByNom(selectedStation.getNom_station()).getId_station();
     billetService.modifierBillet(ref, destination, dateFinal, id_station, prix, duree);
     FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
     Parent newRoot = loader.load();
     AfficherBilletController afficherBilletController = loader.getController();

     // Mettre à jour les statistiques de destination dans le contrôleur de la vue AfficherBillet.fxml
     afficherBilletController.updateDestinationStatsAfterModify(mddestination.getText(),destination);
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setTitle("SUCCES");
     alert.setHeaderText(null);
     alert.setContentText("Station modifiée avec succès!");
     alert.showAndWait();
     switchToDisplayAllbilletsPage();
 }
    public void switchToDisplayAllbilletsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
            Parent newPageRoot = loader.load();


            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) mddestination.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}