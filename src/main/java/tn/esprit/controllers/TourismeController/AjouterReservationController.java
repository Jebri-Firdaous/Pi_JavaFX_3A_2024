package tn.esprit.controllers.TourismeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.TourismeEntities.Reservation;
import tn.esprit.entities.gestionMedecin.Client;
import tn.esprit.services.TourismeService.ServiceHotel;
import tn.esprit.services.TourismeService.ServiceReservation;
import tn.esprit.services.gestionMedecin.ServiceClient;
import tn.esprit.utils.HotelMail;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class AjouterReservationController implements Initializable {

    @FXML
    private ComboBox<Client>  client ;

    private final ServiceReservation sh = new ServiceReservation();
    private final ServiceHotel serviceHotel = new ServiceHotel();
    @FXML
    private ComboBox<Reservation.TypeChambre> type;
    @FXML
    private Label prix;
    @FXML
    private DatePicker date;
    @FXML
    private TextField duree;
    @FXML
    private ComboBox<String> nomhotel;

    /*--------------------------------------------------Choisir-Hotel------------------------------------------------------*/


    @FXML
    void choisirHotel(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/e_city_last_version", "root", "0000")) {
            String query = "SELECT `nom_hotel` FROM `hotel`";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    nomhotel.getItems().add(resultSet.getString("nom_hotel"));
                }
            }
        } catch (SQLException e) {
            afficherMessage("Erreur", "Une erreur s'est produite lors du chargement des hôtels : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    /*--------------------------------------------------Ajouter-Hotel------------------------------------------------------*/

    @FXML
    void ajouterReservation(ActionEvent event) {
        try {
            String dureeText = duree.getText();
            String prixText = prix.getText();
            LocalDate dateValue = date.getValue();

            Float dureeValue = Float.parseFloat(dureeText);
            Float prixValue = Float.parseFloat(prixText);
            String nomHotelSelectionne = nomhotel.getValue();
            Reservation.TypeChambre typeChambre = type.getValue();
            int idHotel = serviceHotel.getHotelIdByNom(nomHotelSelectionne);

            // Vérifier si la date est valide (aujourd'hui ou après un mois)
            LocalDate dateActuelle = LocalDate.now();
            LocalDate dateMax = dateActuelle.plusMonths(1); // Un mois à partir de maintenant

            if (dateValue == null || dateValue.isBefore(dateActuelle) || dateValue.isAfter(dateMax)) {
                // Afficher un message d'erreur si la date n'est pas valide
                afficherMessage("Erreur", "Veuillez saisir une date valide (aujourd'hui ou après un mois).", Alert.AlertType.ERROR);
                return;
            }
            ServiceReservation sr = new ServiceReservation(); // Créez une instance de ServiceReservation
            // Ajouter la réservation
            sh.ajouter(new Reservation(dureeValue, prixValue, dateValue, idHotel, typeChambre , client.getValue().getId_personne()));




            // Effacer les champs de saisie après l'ajout
            duree.setText("");
            prix.setText(""); // Efface le texte du champ prix après l'ajout
            date.setValue(null);
            type.setValue(null);
            nomhotel.setValue(null);
/////////////////////////////////////notif////////////////////////////////////////////
            // Afficher une notification en bas à gauche de l'écran pendant 5 secondes pour indiquer que la commande a été ajoutée avec succès
            Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/TourismeResources/ticke_icone.png")));
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(50); // Définir la largeur souhaitée de l'icône
            imageView.setFitHeight(50); // Définir la hauteur souhaitée de l'icône


            Notifications.create()
                    .graphic(imageView)
                    .title("E-City")
                    .text("Une reservation a été ajoutée !")
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(3))
                    .show();

            // Afficher un message de confirmation
     //       afficherMessage("Succès", "La réservation a été ajoutée avec succès.", Alert.AlertType.INFORMATION);

            String message = "Reservation de " + nomHotelSelectionne + " avec succès !";
            HotelMail.send("ecity.tunis2000@gmail.com", message);


            //  HotelMail.send("ali.ammari@esprit.tn","Reservation hotel avec succée !" );
        } catch (NumberFormatException e) {
            // Gérer les erreurs de conversion des prix en nombres décimaux
            afficherMessage("Erreur", "Veuillez saisir des nombres valides pour les prix et la durée.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            // Afficher un message d'erreur en cas d'échec de l'ajout de la réservation
            afficherMessage("Erreur", "Erreur lors de l'ajout de la réservation : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /*--------------------------------------------------Afficher-Message------------------------------------------------------*/

    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
    /*--------------------------------------------------Controle-De-Saisie------------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialize Combobox Client
        ServiceClient serviceClient = new ServiceClient();
        ObservableList<Client> clientsList = FXCollections.observableArrayList();
        client.setItems(clientsList);
        try {
            clientsList.addAll(serviceClient.afficher());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Ajoutez le code pour limiter la saisie à des valeurs numériques uniquement
        duree.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                duree.setText(oldValue);
            }
        });

        choisirHotel(null);
        type.getItems().addAll(Reservation.TypeChambre.values());
        // nomhotel.getItems().addAll(choisirHotel);


    }


    @FXML
    void choisirType(ActionEvent event) {
        Reservation.TypeChambre selectedType = type.getSelectionModel().getSelectedItem();


        try {


            if (selectedType != null) { // Vérification de nullité
                // Récupérer l'ID de l'hôtel sélectionné
                int idHotel = serviceHotel.getHotelIdByNom(nomhotel.getValue());

                // Mettre à jour le nombre de chambres disponibles
                switch (selectedType) {
                    case NORMAL:
                        LocalDate periodeMinimale = sh.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.NORMAL);
                        int nombreChambresDisponibles = serviceHotel.updateNombreChambres(idHotel, "numero1");

                        if (nombreChambresDisponibles == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                if (item.isAfter(periodeMinimale)) {
                                                    setStyle("-fx-background-color: green;");
                                                } else {
                                                    // Tous les chambres de ce type sont réservées
                                                    setStyle("-fx-background-color: red;");
                                                }

                                            }
                                        }
                                    };
                                }
                            });
                        } else if (nombreChambresDisponibles > 0) {
                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                // Il y a des chambres disponibles
                                                setStyle("-fx-background-color: green;");
                                            }
                                        }
                                    };
                                }
                            });
                        }
                        break;

                    case STANDARD:
                        LocalDate periodeMinimale2 = sh.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.STANDARD);
                        int nombreChambresDisponibles2 = serviceHotel.updateNombreChambres(idHotel, "numero2");

                        if (nombreChambresDisponibles2 == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                if (item.isAfter(periodeMinimale2)) {
                                                    setStyle("-fx-background-color: green;");
                                                } else {
                                                    // Tous les chambres de ce type sont réservées
                                                    setStyle("-fx-background-color: red;");
                                                }

                                            }
                                        }
                                    };
                                }
                            });
                        } else if (nombreChambresDisponibles2 > 0) {
                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                // Il y a des chambres disponibles
                                                setStyle("-fx-background-color: green;");
                                            }
                                        }
                                    };
                                }
                            });
                        }
                        break;
                    case LUXE:
                        LocalDate periodeMinimale3 = sh.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.LUXE);
                        int nombreChambresDisponibles3 = serviceHotel.updateNombreChambres(idHotel, "numero3");

                        if (nombreChambresDisponibles3 == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                if (item.isAfter(periodeMinimale3)) {
                                                    setStyle("-fx-background-color: green;");
                                                } else {
                                                    // Tous les chambres de ce type sont réservées
                                                    setStyle("-fx-background-color: red;");
                                                }

                                            }
                                        }
                                    };
                                }
                            });
                        } else if (nombreChambresDisponibles3 > 0) {
                            date.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                                @Override
                                public DateCell call(DatePicker param) {
                                    return new DateCell() {
                                        @Override
                                        public void updateItem(LocalDate item, boolean empty) {
                                            super.updateItem(item, empty);
                                            if (item.isBefore(LocalDate.now()) || item.isAfter(LocalDate.now().plusMonths(1))) {
                                                setDisable(true); // Désactive les jours passés et les jours après un mois
                                            } else {
                                                // Il y a des chambres disponibles
                                                setStyle("-fx-background-color: green;");
                                            }
                                        }
                                    };
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }

                // Récupérer et afficher le prix correspondant au type de chambre sélectionné
                double prixhotel = 0.0;
                switch (selectedType) {
                    case NORMAL:
                        prixhotel = serviceHotel.getPrix1ById(idHotel);
                        break;
                    case STANDARD:
                        prixhotel = serviceHotel.getPrix2ById(idHotel);
                        break;
                    case LUXE:
                        prixhotel = serviceHotel.getPrix3ById(idHotel);
                        break;
                    default:
                        break;
                }

                // Afficher le prix dans le champ prix
                prix.setText(String.valueOf(prixhotel));

                // Autres actions à effectuer en fonction du prix ou du type de chambre sélectionné
                System.out.println("Type de chambre : " + selectedType);
                System.out.println("Prix : " + prixhotel);
                // Ajouter le reste de la logique ici...
            } else {
                System.out.println("Aucun type de chambre sélectionné.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }


    @FXML
    void naviguezVersAffichage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherReservation.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void naviguerToChat(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/Chat.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Récupérer le stage actuel
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<LocalDate> getDatesIndisponibles(int idHotel, Reservation.TypeChambre typeChambre, LocalDate debutPeriode, float duree) {
        // Implémentez votre logique pour récupérer les dates indisponibles pour ce type de chambre et cette période spécifique
        // Cette méthode doit renvoyer une liste de LocalDate représentant les dates indisponibles
        // Vous pouvez interroger votre base de données pour obtenir ces informations
        return null; // Placeholder, remplacez-le par votre implémentation réelle
    }



    @FXML
    void choisirClient(ActionEvent event) {

    }

}
