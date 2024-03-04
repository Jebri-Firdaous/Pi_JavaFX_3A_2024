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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.entities.gestionTransport.billet;
import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.services.gestionMedecin.ServiceClient;
import tn.esprit.services.gestionTransport.BilletService;
import tn.esprit.services.gestionTransport.StationService;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class AjoutBilletController implements Initializable {
    public ComboBox<Integer> hourCombobox;
    public ComboBox<Client> comboboxclient;
    public ComboBox<Integer> minuteCombobox;
    BilletService bs = new BilletService();
    @FXML
    private DatePicker dateR;

    @FXML
    private TextField prix;

    @FXML
    private TextField destination;

    @FXML
    private MenuButton crudTransport;

    @FXML
    private TextField duree;
    @FXML
    private Label statistiquesLabel;
@FXML
private Label client;
    @FXML
    private ComboBox<Station> depart;
    public Connection connection;
    @FXML
    private TextField stat;


    private Map<String, Integer> destinationStats = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ServiceClient serviceClient = new ServiceClient();
        ObservableList<Client> clientsList = FXCollections.observableArrayList();
        comboboxclient.setItems(clientsList);
        try {
            clientsList.addAll(serviceClient.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Station> stationList = FXCollections.observableArrayList();
        depart.setItems(stationList);
        try {

            stationList.addAll(bs.getStations());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//        // Listen for changes in specialiteR selection
//        depart.valueProperty().addListener(new ChangeListener<Station>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
//                // Call method to initialize medecinR based on the selected speciality
//
//            }
//        });
        // Initialize the hour ComboBox with values from  0 to  23
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        for (int i = 8; i < 24; i++) {
            hours.add(i);
        }
        hourCombobox.setItems(hours);

        // Initialize the minute ComboBox with values from  0 to  59
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        for (int i = 0; i < 60; i += 15) {
            minutes.add(i);
        }
        minuteCombobox.setItems(minutes);
        System.out.println("methode");
        MenuItem modifierItem = new MenuItem("Modifier");
        MenuItem afficherItem = new MenuItem("Afficher");
        MenuItem supprimerItem = new MenuItem("Supprimer");

        // Associer des actions aux éléments de menu

        modifierItem.setOnAction(event -> modifierAction());
        afficherItem.setOnAction(event -> afficherAction());
        supprimerItem.setOnAction(event -> supprimerAction());

        // Ajouter les éléments de menu au MenuButton
        //crudTransport.getItems().addAll(modifierItem, afficherItem, supprimerItem);
        System.out.println("item");
    }

    private void supprimerAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml")));
            destination.getScene().setRoot(root);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void modifierAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml")));
            destination.getScene().setRoot(root);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void afficherAction() {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml")));
            destination.getScene().setRoot(root);


        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    /*    public void addbillet(ActionEvent actionEvent) throws SQLException {
            LocalDate date = dateR.getValue(); // Get the date from the DatePicker
            int hour = hourCombobox.getValue(); // Get the hour from the ComboBox
            int minute = minuteCombobox.getValue(); // Get the minute from the ComboBox
            // Combine the date, hour, and minute into a LocalDateTime
            LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
            String des = destination.getText();
           // Utiliser la variable existante prix déclarée en haut de la méthode
            String duration = duree.getText();
            duree.setText(duration);
            String prixx = prix.getText(); // Récupérer le texte du champ prix
            prix.setText(prixx);
            float prixFloat = Float.parseFloat(prixx);
            float dureeFloat = Float.parseFloat(duration);
           // Cette ligne ne stocke pas la valeur convertie dans une variable, elle peut être supprimée
            // Utiliser la variable existante duree déclarée en haut de la méthode
            Object selectedObject = depart.getValue();
            StationService stationService = new StationService();
            Station stationNom = stationService.getStationByNom(selectedObject.toString());

            bs.ajouter(new billet(des, dateTime, stationNom, prixFloat, dureeFloat));
        }*/
    public void addbillet(ActionEvent actionEvent) throws SQLException, IOException {

        LocalDate date = dateR.getValue(); // Get the date from the DatePicker
        int hour = hourCombobox.getValue(); // Get the hour from the ComboBox
        int minute = minuteCombobox.getValue(); // Get the minute from the ComboBox
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
        String des = destination.getText();

        // Vérification que l'adresse ne contient pas des entiers
        boolean containsDigit = false;
        for (char c : des.toCharArray()) {
            if (Character.isDigit(c)) {
                containsDigit = true;
                break;
            }
        }
        if (containsDigit) {
            // Affichage d'un message d'erreur si l'adresse contient des entiers
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La destination ne doit pas contenir des entiers.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si la destination contient des entiers
        }
        String prixx = prix.getText();
        if (!prixx.matches("[0-9]+")) {
            // Affichage d'un message d'erreur si le champ "prix" contient des caractères autres que des chiffres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ 'prix' ne doit contenir que des chiffres.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si le champ "prix" contient des caractères autres que des chiffres
        }
        String duration = duree.getText();
        if (!duration.matches("\\d{2}:\\d{2} H")) {
            // Affichage d'un message d'erreur si le format de "duration" n'est pas correct
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ 'duration' doit être au format 'hh:mm H '.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si le format de "duration" n'est pas correct
        }

        if (des.isEmpty() || prixx.isEmpty() || duration.isEmpty()) {
            // Affichage d'un message d'erreur si l'un des champs est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode si l'un des champs est vide
        }
        Station selectedObject = depart.getValue();
        System.out.println(selectedObject.getId_station());
        StationService stationService = new StationService();
        bs.ajouter(new billet(des, Timestamp.valueOf(dateTime), selectedObject.getId_station(), prixx, duration,comboboxclient.getValue().getId_personne()));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
        Parent newRoot = loader.load();
        AfficherBilletController afficherBilletController = loader.getController();
        billet bi=new billet(des, dateTime, selectedObject.getId_station(), prixx, duration);
        String recipientPhoneNumber = "+21692701943";
        String messageBody = "Bonjour, un nouveau billet a été ajouté :\n"
                + "Destination: " + bi.getDestination_voyage() + "\n"
                + "Date de départ: " + bi.getDate_depart() + "\n"
                + "Prix: " + bi.getPrix() + "\n"
                + "Durée: " + bi.getDuree();
        SmsController.sendSms(recipientPhoneNumber, messageBody);

        // Mettre à jour les statistiques de destination dans le contrôleur de la vue AfficherBillet.fxml
        afficherBilletController.updateDestinationStats(destination.getText());

        // Remplacer le contenu de la scène par la vue chargée
        destination.getScene().setRoot(newRoot);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("SUCCES");
        alert.setHeaderText(null);
        alert.setContentText("Billet ajoutée avec succès!");
        switchToDisplayAllbilletsPage();
        alert.showAndWait();
    }
    public void switchToDisplayAllbilletsPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
            Parent newPageRoot = loader.load();


            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) destination.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void backtohome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/home.fxml")));
            destination.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void test(KeyEvent keyEvent) {
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), 0, change ->
        {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });

        prix.setTextFormatter(formatter);
    }


}