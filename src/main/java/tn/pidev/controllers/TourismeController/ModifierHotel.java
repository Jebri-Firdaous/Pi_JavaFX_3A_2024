package tn.pidev.controllers.TourismeController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.pidev.services.TourismeService.ServiceHotel;

import java.io.IOException;
import java.sql.*;

import tn.pidev.entities.TourismeEntities.Hotel;

public class ModifierHotel  {


    @FXML
    private TextField nomModif;

    @FXML
    private TextField prix3Modif;

    @FXML
    private TextField adressModif;

    @FXML
    private TextField prix2Modif;

    @FXML
    private TextField prix1Modif;

    @FXML
    private TextField nombre1Modif;

    @FXML
    private TextField nombre2Modif;

    @FXML
    private TextField nombre3Modif;


    @FXML
    private Label idModif;
    private int hotelid;
    private Hotel hotel;


    int id_hotel ;



    private  final  ServiceHotel serviceHotel = new ServiceHotel() ;

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
        afficherDetailsHotel() ;
    }

    public void setId(int id) {

        id_hotel = id;
        System.out.println("her id " + id_hotel);
    }

    /*--------------------------------------------------Controle_saisie------------------------------------------------------*/


    @FXML
    void initialize() {
        // Ajout d'un événement pour vérifier la saisie dans le champ prix1Modif
        prix1Modif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix1Modif.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Ajout d'un événement pour vérifier la saisie dans le champ prix2Modif
        prix2Modif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix2Modif.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Ajout d'un événement pour vérifier la saisie dans le champ prix3Modif
        prix3Modif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                prix3Modif.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });



        // Ajout d'un événement pour vérifier la saisie dans le champ adressModif
        adressModif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                adressModif.setText(oldValue);
            }
        });

        // Ajout d'un événement pour vérifier la saisie dans le champ adressModif
        adressModif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 20) {
                // Si la longueur du texte dépasse 20 caractères, afficher une alerte
                afficherMessage("Erreur", "L'adresse ne peut pas dépasser 20 caractères.", Alert.AlertType.ERROR);
                // Réinitialiser le texte du champ à sa valeur précédente
                adressModif.setText(oldValue);
            }
        });
    }

    /*--------------------------------------------------Afficher_Alerte------------------------------------------------------*/


    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Afficher-Detail_hotel------------------------------------------------------*/

    private  void  afficherDetailsHotel(){
        nomModif.setText(hotel.getNom_hotel());
        adressModif.setText(hotel.getAdress_hotel());
        prix1Modif.setText(String.valueOf(hotel.getPrix1()));
        prix2Modif.setText(String.valueOf(hotel.getPrix2()));
        prix3Modif.setText(String.valueOf(hotel.getPrix3()));
        nombre1Modif.setText(String.valueOf(hotel.getNumero1()));
        nombre2Modif.setText(String.valueOf(hotel.getNumero2()));
        nombre3Modif.setText(String.valueOf(hotel.getNumero3()));
    }










    /*--------------------------------------------------Modifier_hotel------------------------------------------------------*/


    @FXML
    private void modifierHotel(ActionEvent event) {

        try {



            // Vérifier si tous les champs sont remplis
            if (adressModif.getText().isEmpty() || nomModif.getText().isEmpty() ||
                    prix1Modif.getText().isEmpty() || prix2Modif.getText().isEmpty() || prix3Modif.getText().isEmpty() || nombre1Modif.getText().isEmpty() || nombre2Modif.getText().isEmpty()  || nombre3Modif.getText().isEmpty()) {
                afficherMessage("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
                return; // Arrêter l'exécution de la méthode si un champ est vide
            }




            String nomHotel = nomModif.getText();
            String adressHotel = adressModif.getText();
            float prix1Hotel = Float.parseFloat(prix1Modif.getText());
            float prix2Hotel = Float.parseFloat(prix2Modif.getText());
            float prix3Hotel = Float.parseFloat(prix3Modif.getText());
            int nombre1Hotel = Integer.parseInt(nombre1Modif.getText());
            int nombre2Hotel = Integer.parseInt(nombre2Modif.getText());
            int nombre3Hotel = Integer.parseInt(nombre3Modif.getText());


            Hotel nouvelHotel = new Hotel(hotel.getId_hotel(), nomHotel, adressHotel, prix1Hotel, prix2Hotel , prix3Hotel , nombre1Hotel , nombre2Hotel ,nombre3Hotel );
            serviceHotel.modifier(nouvelHotel);

            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "L'article a été modifié avec succès.");
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs valides pour le prix et la quantité.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification de l'article : " + e.getMessage());
        }


    }


    /*--------------------------------------------------Message_Alerte------------------------------------------------------*/

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }


    /*--------------------------------------------------Naviguer_Affichage------------------------------------------------------*/


    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherHotel.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}