package tn.esprit.controllers.gestionMedecin;

//import com.twilio.rest.api.v2010.account.Message;
//import tn.esprit.entities.gestionMedecin.TwilioSendSms;
//import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.gestionMedecin.*;
import tn.esprit.services.gestionMedecin.ServiceClient;
import tn.esprit.services.gestionMedecin.ServiceMedecin;
import tn.esprit.services.gestionMedecin.ServiceRendezVous;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AfficherListRendezVousController {
    public ListView<RendezVous> listViewRendezVous;
    @FXML
    public Label labelListRV;


    public TextField textFieldSearch;
    public Label msgErreur;
    RendezVous currentRendezVousSelected;
    private final ServiceRendezVous serviceRendezVous = new ServiceRendezVous();
    ObservableList<RendezVous> listRndezVous;

    public void initialize() {
        listRndezVous = FXCollections.observableArrayList();
        listViewRendezVous.setItems(listRndezVous);
        try {
            List<RendezVous> rendezvousFromService = serviceRendezVous.afficher();
            listRndezVous.addAll(rendezvousFromService);

            // Set a custom CellFactory for the ListView
            listViewRendezVous.setCellFactory(new Callback<ListView<RendezVous>, ListCell<RendezVous>>() {
                @Override
                public ListCell<RendezVous> call(ListView<RendezVous> param) {
                    return new ListCell<RendezVous>() {
                        @Override
                        protected void updateItem(RendezVous rendezVous, boolean empty) {
                            super.updateItem(rendezVous, empty);
                            if (empty || rendezVous == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Assuming this code is inside a method where you have access to the rendezVous object
                                ServiceMedecin sm = new ServiceMedecin();
                                ServiceClient serviceClient = new ServiceClient();
                                Medecin medecin = sm.getMedecinById(rendezVous.getId_medecin());
                                Client client = serviceClient.getClientById(rendezVous.getId_personne());
                                String doctorName = medecin.getNom_medecin();
                                String doctorSurname = medecin.getPrenom_medecin_medecin();
                                String specialty = medecin.getSpecialite_medecin();
                                int phoneNumber = medecin.getNumero_telephone_medecin();
                                String address = medecin.getAddress_medecin();

                                Timestamp timestamp = rendezVous.getDate_rendez_vous();
// Format the timestamp to include the full month name
                                DateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm");

                                String timestampAsString = format.format(timestamp);
//                                just i need the first three letters of the month ( fev => not ferv, Mar not Mars)
                                String[] parts = timestampAsString.split("-");
                                String month = parts[1];
                                // Extract the first three letters of the month
                                String firstThreeLettersOfMonth = month.substring(0, 3);
                                String dateRvDisplayedInInterface = parts[0]+"-"+firstThreeLettersOfMonth+"-"+parts[2];

// Create an HBox to hold the details
                                HBox hbox = new HBox();
                                hbox.setSpacing(65); // Adjust spacing as needed

                                Label nomClientLabel = new Label(client.getNom_personne());
                                nomClientLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");

// Add details to the HBox with styled Labels
                                Label nomPrenomDoctorLabel = new Label("Dr. "+doctorName+" "+doctorSurname);
                                nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   250; -fx-pref-height:   21");

                                /*Label prenomLabel = new Label(doctorSurname);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   118; -fx-pref-height:   21");*/

                                Label dateLabel = new Label(dateRvDisplayedInInterface);
                                dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   200; -fx-pref-height:   21");

                                Label specialiteLabel = new Label(specialty);
                                specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");

                                Label adresseLabel = new Label(address);
                                adresseLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");

// Add the date Label with the full month name

// Add the Labels to the HBox
                                hbox.getChildren().addAll(nomClientLabel,nomPrenomDoctorLabel, specialiteLabel, dateLabel, adresseLabel);

// Assuming this is inside a ListCell or similar where you can set the graphic
                                setGraphic(hbox);

                            }
                        }
                    };
                }
            });

            listViewRendezVous.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<RendezVous>() {
                @Override
                public void changed(ObservableValue<? extends RendezVous> observableValue, RendezVous oldRendezVous, RendezVous newRendezVous) {
                    currentRendezVousSelected = newRendezVous;
                    // Display the selected item
                    if (currentRendezVousSelected != null) {
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
    }

    public void suprrimerRDBT(ActionEvent actionEvent) {
        if(currentRendezVousSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose one Rv ");
            alert.showAndWait();
            return;
        }
        try {
            // before delete the RV get its informations like date, idMedecin...
            if(currentRendezVousSelected.getDate_rendez_vous().after(Timestamp.valueOf(LocalDateTime.now()))){
                // Send msg to deleted doctor
                ServiceMedecin serviceMedecin = new ServiceMedecin();
                Medecin medecin = serviceMedecin.getMedecinById(currentRendezVousSelected.getId_medecin());
                TwilioSendSms twilioSendSms = new TwilioSendSms();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
                LocalDateTime dateTime = currentRendezVousSelected.getDate_rendez_vous().toLocalDateTime();
                // Sms for Doctor
                String msg = "Bonjour Dr. "+medecin.getNom_medecin()+" Votre rendez-vous le " + dateTime.format(myFormatObj) +" sera annulé ";
                Message.creator(new PhoneNumber("+4915510686794"), new PhoneNumber(twilioSendSms.getFromNumberMyTwillioNumber()), msg).create();

                // For email to client
                ServiceClient serviceClient = new ServiceClient();
                Client client = serviceClient.getClientById(currentRendezVousSelected.getId_personne());
                // 7ot email l client
                String receiverAdresse = "tavef44143@aersm.com";
                String subject = "supprimer Rendez-Vous";
                String body = "Bonjour Mr."+client.getNom_personne()+" votre rendezVous avec Dr."+medecin.getNom_medecin()+" à "+dateTime.format(myFormatObj) + " sera annulé";
                new SendEmail(receiverAdresse, subject, body);
            }
            serviceRendezVous.supprimer(currentRendezVousSelected.getRef_rendez_vous());
            initialize();
            // if RV date in future , Send msg to deleted doctor


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void ajouterRV(ActionEvent actionEvent) {

        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AjouterRendezVous.fxml"));
            Parent addPageRoot = loader.load();
            Scene newPageScene = new Scene(addPageRoot);
            // Get the current stage and set the new scene
            Stage stage = (Stage) labelListRV.getScene().getWindow();
            stage.setScene(newPageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void BT_Modifier(ActionEvent actionEvent) {
        if(currentRendezVousSelected == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Information");
            alert.setHeaderText(null);
            alert.setContentText("Choose one Rv ");
            alert.showAndWait();
            return;
        }
        switchToUpdatePage();
    }
    public void switchToUpdatePage() {
        try {
//           for load an FXML file and create a scene graph from it
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/MofidierRv.fxml"));
            Parent newPageRoot = loader.load();
            // Get the controller instance created by the FXMLLoader
            MofidierRvController mofidierRvController = loader.getController();
            mofidierRvController.initializeValues(currentRendezVousSelected.getRef_rendez_vous(),
                    currentRendezVousSelected.getDate_rendez_vous(),
                    currentRendezVousSelected.getId_medecin()
            );

            // Create a new scene with the newPageRoot
            Scene newPageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) labelListRV.getScene().getWindow();
            stage.setScene(newPageScene);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchByAttribut(KeyEvent keyEvent) throws SQLException {
        String valChamp = textFieldSearch.getText();
        String val = extractValue(valChamp);
        String attribut = extractAttribute(valChamp);
        msgErreur.setText("");
        msgErreur.setTextFill(Color.RED);
        if (textFieldSearch.getText().equals("")){
            initialize();
        }
        if (!attribut.toLowerCase().equals("client") && !attribut.toLowerCase().equals("dr.nom") && !attribut.toLowerCase().equals("specialite")
                && !attribut.toLowerCase().equals("n°tel") && !attribut.toLowerCase().equals("adresse")){
            msgErreur.setText("_attribut val");
            return;
        }
        msgErreur.setText("");
//        if(valChamp!=null){
//           TextField erreur = new TextField();
//           erreur.setText("tu dois repecter le régle");
//           return;
//        }
        if (attribut.toLowerCase().equals("client")){
            listRndezVous.clear();
            listRndezVous.addAll(serviceRendezVous.afficherByNomClient(val));
        }
        if (attribut.toLowerCase().equals("dr.nom")){
            listRndezVous.clear();
            listRndezVous.addAll(serviceRendezVous.afficherByNomDoctor(val));
        }
        if (attribut.toLowerCase().equals("specialite")){
            listRndezVous.clear();
            listRndezVous.addAll(serviceRendezVous.afficherBySpecialite(val));
        }
        if (attribut.toLowerCase().equals("daterv")){
            listRndezVous.clear();
            listRndezVous.addAll(serviceRendezVous.afficherByDate(val));
        }
        if (attribut.toLowerCase().equals("adresse")){
            listRndezVous.clear();
            listRndezVous.addAll(serviceRendezVous.afficherByAdresse(val));
        }
//        if (attribut.toLowerCase().equals("adresse")){
//            medecinList.clear();
//            medecinList.addAll(serviceMedecin.searchByAdresse(val));
//        }

    }
    public static String extractAttribute(String input) {
        Pattern pattern = Pattern.compile("_(.+?)\\s");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Retourne l'attribut
        } else {
            return "No attribute found.";
        }
    }

    public static String extractValue(String input) {
        Pattern pattern = Pattern.compile("\\s(.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1); // Retourne la valeur
        } else {
            return "No value found.";
        }
    }
}
