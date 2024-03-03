package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.pidev.entities.Reservation;
import tn.pidev.services.ServiceHotel;
import tn.pidev.services.ServiceReservation;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierReservationController implements Initializable {


    private final ServiceReservation serviceReservation = new ServiceReservation();
    private final ServiceHotel servicehotel = new ServiceHotel();
    int ref_reservation;
    @FXML
    private ComboBox<Reservation.TypeChambre> typeModif;
    @FXML
    private TextField dureeModif;
    @FXML
    private Label prixModif;
    @FXML
    private ComboBox<String> nomhotelModif;
    @FXML
    private DatePicker dateModif;
    private Reservation reservation;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /*--------------------------------------------------Afficher-Daitails------------------------------------------------------*/

    public void afficherDetailsHotel(Reservation reservation) throws SQLException {
        this.reservation = reservation;
        dureeModif.setText(String.valueOf(reservation.getDuree_reservation()));
        prixModif.setText(String.valueOf(reservation.getPrix_reservation()));
        dateModif.setValue(reservation.getDate_reservation());
        String hotelIdAsString = String.valueOf(servicehotel.getNomHotelById(reservation.getId_hotel()));
        nomhotelModif.setValue(hotelIdAsString);
        typeModif.setValue(reservation.getType_chambre());

    }

    public void setRef_reservation(int ref_reservation) {
        this.ref_reservation = ref_reservation;
    }


    public void someMethod() {
        // Assurez-vous que l'objet hotel est initialisé avant son utilisation
        if (reservation != null) {
            // Utilisez l'objet hotel ici
        } else {
            System.out.println("L'objet reservation n'est pas initialisé. Assurez-vous d'appeler initData pour l'initialiser.");
        }
    }

    /*--------------------------------------------------Controle-Saisie------------------------------------------------------*/


    @FXML
    void initialize() {
        // Ajout d'un événement pour vérifier la saisie dans le champ prix1Modif
        dureeModif.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                dureeModif.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }


    /*--------------------------------------------------Cffichage------------------------------------------------------*/

    public void Affichage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
            Parent newPageRoot = loader.load();
            AfficherHotel afficherHotelsController = loader.getController();

            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) dureeModif.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*--------------------------------------------------Naviguer-Affichage------------------------------------------------------*/

    @FXML
    void naviguezVersAffichage(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherReservation.fxml"));
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
    void modifierReservation(ActionEvent event) {
        try {


            float dureeReservation = Float.parseFloat(dureeModif.getText());
            float prixReservation = Float.parseFloat(prixModif.getText());
            LocalDate dateReservation = dateModif.getValue(); // Récupère la valeur du DatePicker
            String nomHotel = nomhotelModif.getValue(); // Récupère la valeur sélectionnée dans le ComboBox
            Reservation.TypeChambre selectedType = typeModif.getValue(); // Récupère la valeur sélectionnée dans le ComboBox

            // Appeler la méthode getHotelIdByNom à partir du serviceHotel pour obtenir l'ID de l'hôtel
            int hotelId = servicehotel.getHotelIdByNom(nomHotel);

            // Vérifier si la date est valide (aujourd'hui ou après un mois)
            LocalDate dateActuelle = LocalDate.now();
            LocalDate dateMax = dateActuelle.plusMonths(1); // Un mois à partir de maintenant

            if (dateReservation == null || dateReservation.isBefore(dateActuelle) || dateReservation.isAfter(dateMax)) {
                // Afficher un message d'erreur si la date n'est pas valide
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir une date valide (aujourd'hui ou après un mois).");
                return;
            }

            Reservation nouvelReservation = new Reservation(reservation.getRef_reservation(), dureeReservation, prixReservation, dateReservation, hotelId, selectedType);
            serviceReservation.modifier(nouvelReservation);

            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "L'article a été modifié avec succès.");
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs valides pour le prix et la quantité.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification de l'article : " + e.getMessage());
        }
    }

    /*--------------------------------------------------Afficher-Alerte-----------------------------------------------------*/

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Choisir-Type-Reservatio---------------------------------------------------*/

    @FXML
    void choisirType(ActionEvent event) {

        Reservation.TypeChambre selectedType = typeModif.getSelectionModel().getSelectedItem();


        try {


            if (selectedType != null) { // Vérification de nullité
                // Récupérer l'ID de l'hôtel sélectionné
                int idHotel = servicehotel.getHotelIdByNom(nomhotelModif.getValue());

                // Mettre à jour le nombre de chambres disponibles
                switch (selectedType) {
                    case NORMAL:
                        LocalDate periodeMinimale = serviceReservation.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.NORMAL);
                        int nombreChambresDisponibles = servicehotel.updateNombreChambres(idHotel, "numero1");

                        if (nombreChambresDisponibles == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                        LocalDate periodeMinimale2 = serviceReservation.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.STANDARD);
                        int nombreChambresDisponibles2 = servicehotel.updateNombreChambres(idHotel, "numero2");

                        if (nombreChambresDisponibles2 == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                        LocalDate periodeMinimale3 = serviceReservation.calculerDateFinMinimale(idHotel, Reservation.TypeChambre.LUXE);
                        int nombreChambresDisponibles3 = servicehotel.updateNombreChambres(idHotel, "numero3");

                        if (nombreChambresDisponibles3 == 0) {
                            // Afficher une alerte si toutes les chambres de ce type sont réservées
                            afficherMessage("Réservation impossible", "Toutes les chambres de ce type sont réservées. Veuillez sélectionner un autre type de chambre ou une autre date.", Alert.AlertType.WARNING);

                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                            dateModif.setDayCellFactory(new Callback<DatePicker, DateCell>() {
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
                        prixhotel = servicehotel.getPrix1ById(idHotel);
                        break;
                    case STANDARD:
                        prixhotel = servicehotel.getPrix2ById(idHotel);
                        break;
                    case LUXE:
                        prixhotel = servicehotel.getPrix3ById(idHotel);
                        break;
                    default:
                        break;
                }

                // Afficher le prix dans le champ prix
                prixModif.setText(String.valueOf(prixhotel));

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

    /*--------------------------------------------------Afficher-Message-----------------------------------------------------*/

    private void afficherMessage(String titre, String contenu, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    /*--------------------------------------------------Choisir-Hotel----------------------------------------------------*/

    @FXML
    void choisirHotel(ActionEvent event) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-city", "root", "")) {
            String query = "SELECT `nom_hotel` FROM `hotel`";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    nomhotelModif.getItems().add(resultSet.getString("nom_hotel"));
                }
            }
        } catch (SQLException e) {
            afficherMessage("Erreur", "Une erreur s'est produite lors du chargement des hôtels : " + e.getMessage(), Alert.AlertType.ERROR);
        }


    }


    /*--------------------------------------------------Initialisation-----------------------------------------------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choisirHotel(null);
        typeModif.getItems().addAll(Reservation.TypeChambre.values());
        // nomhotel.getItems().addAll(choisirHotel);


    }


}