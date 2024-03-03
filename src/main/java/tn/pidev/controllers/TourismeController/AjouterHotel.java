package tn.pidev.controllers.TourismeController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.pidev.entities.TourismeEntities.Hotel;
import tn.pidev.services.TourismeService.ServiceHotel;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterHotel implements Initializable {
    private final ServiceHotel sh = new ServiceHotel();

    @FXML
    private TextField adress;

    @FXML
    private TextField nom;

    @FXML

    private TextField prix1;

    @FXML
    private TextField prix2;

    @FXML
    private TextField prix3;

    @FXML
    private Button buttonAjout;
    @FXML
    private Button buttonAcceuil;


    @FXML
    private TextField nombre1;

    @FXML
    private TextField nombre2;

    @FXML
    private TextField nombre3;
    /*--------------------------------------------------Ajouter------------------------------------------------------*/

    @FXML
    void ajouterHotel(ActionEvent event) {
        try {


            String nomHotel = nom.getText();
            String adresse = adress.getText();
            String prix1Str = prix1.getText();
            String prix2Str = prix2.getText();
            String prix3Str = prix3.getText();
            String nombre1Str = nombre1.getText();
            String nombre2Str = nombre2.getText();
            String nombre3Str = nombre3.getText();
            // Vérifier si tous les champs sont remplis
            if (nomHotel.isEmpty() || adresse.isEmpty() || prix1Str.isEmpty() || prix2Str.isEmpty() || prix3Str.isEmpty() || nombre1Str.isEmpty() || nombre2Str.isEmpty() || nombre3Str.isEmpty()) {
                // Afficher un message d'erreur si un champ est vide
                afficherMessage("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
                return;
            }

            // Vérifier si le nom dépasse 10 caractères
            if (nomHotel.length() > 10) {
                afficherMessage("Erreur", "Le nom de l'hôtel ne doit pas dépasser 10 caractères.", Alert.AlertType.ERROR);
                return;
            }

            // Vérifier si l'adresse contient des caractères spéciaux
            if (!adresse.matches("[a-zA-Z0-9\\s]+")) {
                afficherMessage("Erreur", "L'adresse ne doit pas contenir de caractères spéciaux.", Alert.AlertType.ERROR);
                return;
            }


            // Vérifier si le nom de l'hôtel est déjà utilisé dans la base de données
            if (sh.nomHotelExisteDeja(nomHotel)) {
                // Afficher un message d'erreur si le nom de l'hôtel est déjà utilisé
                afficherMessage("Erreur", "Le nom de l'hôtel est déjà utilisé.", Alert.AlertType.ERROR);
            } else {
                // Ajouter l'hôtel uniquement si le nom n'est pas déjà utilisé
                sh.ajouter(new Hotel(nomHotel, adress.getText(), Integer.parseInt(prix1.getText()), Integer.parseInt(prix2.getText()), Integer.parseInt(prix3.getText()), Integer.parseInt(nombre1.getText()), Integer.parseInt(nombre2.getText()), Integer.parseInt(nombre3.getText())));

                // Effacer les champs de saisie après l'ajout
                nom.setText("");
                adress.setText("");
                prix1.setText("");
                prix2.setText("");
                prix3.setText("");
                nombre1.setText("");
                nombre2.setText("");
                nombre3.setText("");
                // Afficher un message de confirmation
                afficherMessage("Succès", "L'hôtel a été ajouté avec succès.", Alert.AlertType.INFORMATION);
                afficherNotificationSysteme("Un nouvel hôtel a été ajouté : " + nomHotel);
                // Après l'ajout de l'hôtel avec succès
                // afficherNotificationSysteme("L'hôtel a été ajouté avec succès.");


            }
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion des prix en entiers
            afficherMessage("Erreur", "Veuillez saisir des nombres valides pour les prix.", Alert.AlertType.ERROR);
        } catch (SQLException e) {

            // Afficher un message d'erreur en cas d'échec de l'ajout de l'hôtel
            afficherMessage("Erreur", "Erreur lors de l'ajout de l'hôtel : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    /*--------------------------------------------------Afficher_Message------------------------------------------------------*/
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Controle-saisie------------------------------------------------------*/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Ajoutez le code pour limiter la saisie à des valeurs numériques uniquement
        prix1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix1.setText(oldValue);
            }
        });

        prix2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix2.setText(oldValue);
            }
        });

        prix3.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix3.setText(oldValue);
            }
        });
    }


    /*--------------------------------------------------Naviguer-Vers-Affichage------------------------------------------------------*/
    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherHotel.fxml"));
            Parent root = loader.load();
            AfficherHotel AfficheController = loader.getController();
            Scene pageScene = new Scene(root);

            Stage stage = (Stage) nom.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*--------------------------------------------------Afficher-Notif------------------------------------------------------*/
    private void afficherNotification(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /*--------------------------------------------------Afficher-Notif------------------------------------------------------*/

    @FXML
    private void afficherNotificationSysteme(String message) {
        Toolkit.getDefaultToolkit().beep(); // Émettre un bip
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().getImage("C:/Users/DELL£/Bureau/999.ico");
        TrayIcon trayIcon = new TrayIcon(image, "Nouvel hôtel ajouté");
        trayIcon.setImageAutoSize(false); // Désactive le redimensionnement automatique
        trayIcon.setImage(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)); // Redimensionne l'image
        trayIcon.setToolTip("Nouvel hôtel ajouté");
        trayIcon.displayMessage("Nouvel hôtel ajouté", message, TrayIcon.MessageType.INFO);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.err.println("Impossible d'ajouter l'icône au système Tray");
        }
    }


    public void ClickAccueil(ActionEvent actionEvent) {

        buttonAcceuil.setStyle("-fx-background-color: #5CE1E6; -fx-text-fill: black;");

    }

    public void ClickTourisme(ActionEvent actionEvent) {
    }

    public void ClickTransport(ActionEvent actionEvent) {
    }

    public void ClickShopping(ActionEvent actionEvent) {
    }

    public void ClickSante(ActionEvent actionEvent) {
    }

    public void ClickParking(ActionEvent actionEvent) {
    }


}
