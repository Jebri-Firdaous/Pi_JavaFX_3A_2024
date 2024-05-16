package tn.esprit.controllers.TourismeController;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.TourismeEntities.Hotel;

import tn.esprit.entities.TourismeEntities.Reservation;
import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.TourismeService.ServiceHotel;
import tn.esprit.services.TourismeService.ServiceReservation;
import tn.esprit.services.gestionMedecin.ServiceClient;
import tn.esprit.services.gestionUserServices.ServiceUser;
import tn.esprit.utils.ReservationPDF;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AfficherReservationController {


    private final ServiceReservation sr = new ServiceReservation();
    private final ServiceHotel serviceHotel = new ServiceHotel();
    private final Hotel hotel = new Hotel();
    @FXML
    private ComboBox<String> triComboBox;
    @FXML
    private Text myText;
    @FXML
    private Button reservationPDF;
    @FXML
    private ListView<Reservation> listeView;

    @FXML
    private Label LAbel;

    public void initialize() {
        try {
            // Ajout des options de tri dans le ComboBox
            ObservableList<String> options = FXCollections.observableArrayList("Prix croissant", "Prix décroissant");
            triComboBox.setItems(options);

            // Gestion de la sélection dans le ComboBox pour le tri
            triComboBox.setOnAction(event -> {
                String selectedOption = triComboBox.getSelectionModel().getSelectedItem();
                if (selectedOption != null) {
                    trierReservation(selectedOption);
                }
            });

            // Récupère la liste des réservations depuis le service
            List<Reservation> reservations = sr.afficher();

            // Crée une cellule personnalisée pour la ListView
            listeView.setCellFactory(param -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);

                    if (empty || reservation == null) {
                        // Si la cellule est vide ou l'objet réservation est null, ne rien afficher
                        setText(null);
                    } else {
                        ServiceUser serviceClient = new ServiceUser();
                        User client = serviceClient.getOneById(reservation.getId_personne());

                        // Crée un GridPane pour aligner les valeurs horizontalement
                        GridPane gridPane = new GridPane();
                        gridPane.setHgap(10);
                        gridPane.setVgap(5);

                        // Ajoute les valeurs des attributs dans le GridPane
                        gridPane.addRow(0, new Label("Durée:"), new Label(Float.toString(reservation.getDuree_reservation())));
                        gridPane.addRow(1, new Label("Prix:"), new Label(reservation.getPrix_reservation() + " DT"));
                        gridPane.addRow(2, new Label("Date:"), new Label(reservation.getDate_reservation().toString()));

                        try {
                            gridPane.addRow(3, new Label("Nom Hôtel:"), new Label(serviceHotel.getNomHotelById(reservation.getId_hotel())));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        gridPane.addRow(4, new Label("Type de chambre:"), new Label(reservation.getType_chambre().toString()));
                        // Vérifie si l'ID de la personne dans la réservation est valide

                        gridPane.addRow(5, new Label("Nom de client:"), new Label(client.getNom_personne()));


                        // Affiche le GridPane dans la cellule
                        setGraphic(gridPane);
                    }
                }
            });

            // Ajoute les réservations à la ListView
            listeView.getItems().addAll(reservations);

            // Ajoute un gestionnaire d'événements pour gérer les clics sur les éléments de la liste
            listeView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Reservation selectedReservation = listeView.getSelectionModel().getSelectedItem();
                    if (selectedReservation != null) {
                        try {
                            switchToUpdatePage(selectedReservation); //code
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        } catch (SQLException e) {
            // Affiche une alerte en cas d'erreur
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des réservations : " + e.getMessage());
        }
    }


/////////////////////////////////////////////////////TrierReservation////////////////////////////////////////////////////////////

    private void trierReservation(String option) {
        if (option.equals("Prix croissant")) {
            Collections.sort(listeView.getItems(), Comparator.comparingDouble(Reservation::getPrix_reservation));
        } else if (option.equals("Prix décroissant")) {
            Collections.sort(listeView.getItems(), Comparator.comparingDouble(Reservation::getPrix_reservation).reversed());
        }
    }


    private void switchToUpdatePage(Reservation reservation) throws IOException {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/ModifierReservation.fxml"));

            Parent root = loader.load();
            ModifierReservationController modifierReservationController = loader.getController();
            modifierReservationController.afficherDetailsHotel(reservation); // Initialise les champs de modification
            Scene pageScene = new Scene(root);

            Stage stage = (Stage) LAbel.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        }catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    /////////////////////////////////////////////////AfficherAlerte//////////////////////////////////////////////////////////////
    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    //////////////////////////////////////////AfficherMessage//////////////////////////////////////////////////////////
    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    //////////////////////////////////////////Recuperation de Modification//////////////////////////////////////////////////////////

    @FXML
    void reservationModifier(ActionEvent event) throws IOException {

        // Récupérer l'objet Hotel sélectionné dans la ListView
        Reservation selectedReservation = listeView.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            // L'objet Hotel est sélectionné, vous pouvez maintenant l'utiliser comme vous le souhaitez
            switchToUpdatePage(selectedReservation);
        } else {
            // Aucun hôtel sélectionné, afficher un message d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une reservation à modifier.");
            alert.showAndWait();
            //  ToModifierListeHotel() ;
        }

    }

    //////////////////////////////////////////supprition//////////////////////////////////////////////////////////


    @FXML
    void reservationSupprimer(ActionEvent event) {

        Reservation reservationASupprimer = listeView.getSelectionModel().getSelectedItem();

        if (reservationASupprimer != null) {
            // Supprimer l'hôtel de la base de données et de la liste affichée
            try {
                sr.supprimer(reservationASupprimer.getId_hotel());
                listeView.getItems().remove(reservationASupprimer);

                // Afficher un message de confirmation
                afficherMessage("Suppression réussie", "L'hôtel a été supprimé avec succès.", Alert.AlertType.INFORMATION);
            } catch (SQLException e) {
                // Afficher un message d'erreur en cas d'échec de la suppression
                afficherMessage("Erreur", "Erreur lors de la suppression de l'hôtel : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            // Si aucun hôtel n'est sélectionné, afficher un message d'avertissement
            afficherMessage("Aucune sélection", "Veuillez sélectionner un hôtel à supprimer.", Alert.AlertType.WARNING);
        }

    }

    public void trierReservation(ActionEvent actionEvent) {
    }

    ////////////////////////////////////////////////PDF//////////////////////////////////////////////////////////////
    @FXML
    void clickPDF(ActionEvent event) {
        //  List<Reservation> reservationData = fetchReservationDataFromDatabase();
        List<Reservation> reservationData = null;
        try {
            reservationData = new ServiceReservation().afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ReservationPDF generator = new ReservationPDF();

        generator.generate(reservationData);
        try {
            Desktop.getDesktop().open(new File("iTextTable.pdf"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}