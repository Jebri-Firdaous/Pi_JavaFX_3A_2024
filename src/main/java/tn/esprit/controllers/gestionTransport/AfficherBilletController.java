package tn.esprit.controllers.gestionTransport;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionMedecin.ServiceClient;
import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.entities.gestionTransport.billet;
import tn.esprit.services.gestionTransport.BilletService;
import tn.esprit.services.gestionTransport.StationService;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AfficherBilletController {
BilletService bs=new BilletService();
    billet currentBilletSelected;
    @FXML
    private Button promoo;
    @FXML
    private Button promoo50;
    @FXML
    private Button promoo70;
    @FXML
    private Button nopromoo;
    @FXML
    private MenuButton crudTransport;
    @FXML
    private ListView<billet> listbillets;
    @FXML
    private Label billetlabel;
    Double initalPrix;
    double nouveauPrix;
    @FXML
    private Label client;
    @FXML
    private TextField searchfield;
    private Map<String, Integer> destinationStats = new HashMap<>();
    @FXML
    private PieChart statistiquesPieChart;

    @FXML
    void initialize() {
        promoo.setOnAction(this::promo);
        promoo50.setOnAction(this::promo50);
        promoo70.setOnAction(this::promo70);
        nopromoo.setOnAction(this::nopromo);
        updateStatisticsLabel();
        statistiquesPieChart.setData(FXCollections.observableArrayList());
        ObservableList<billet> observableList = FXCollections.observableArrayList();
        listbillets.setItems(observableList);
        try {
            BilletService bs = new BilletService();
            List<billet> billets = bs.recuperer();

            Set<String> uniqueDestinations = new HashSet<>(); // Utiliser un ensemble pour stocker les destinations uniques

            for (billet bi : billets) {
                uniqueDestinations.add(bi.getDestination_voyage()); // Ajouter chaque destination unique à l'ensemble
            }

            for (String destination : uniqueDestinations) {
                updateDestinationStats(destination); // Mettre à jour les statistiques pour chaque destination unique
            }

            observableList.addAll(billets);
            // Le reste du code pour configurer les cellules de la liste, les écouteurs d'événements, etc.


            listbillets.setCellFactory(new Callback<ListView<billet>, ListCell<billet>>() {
                @Override
                public ListCell<billet> call(ListView<billet> param) {
                    return new ListCell<billet>() {
                        @Override
                        protected void updateItem(billet bi, boolean empty) {
                            Station station;
                            super.updateItem(bi, empty);
                            if (empty || bi == null) {
                                setText(null);
                                setGraphic(null);
                            } else {
                                // Assuming this code is inside a method where you have access to the rendezVous object
                                StationService sm = new StationService();
                                try {
                                    station = sm.getStationById(bi.getId_station());
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                                ServiceUser ServiceUser = new ServiceUser();
                                String destination = bi.getDestination_voyage();
                                String prix = bi.getPrix() + "DT";
                                String duree = bi.getDuree();
                                String show = station.getNom_station() + "/" + station.getAdress_station() + "/" + station.getType();
                                Timestamp timestamp = bi.getDate_depart();
                                User user = ServiceUser.getOneById(bi.getId_personne());
// Format the timestamp to include the full month name
                                DateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
                                String timestampAsString = format.format(timestamp);

// Create an HBox to hold the details
                                HBox hbox = new HBox();
                                hbox.setSpacing(35); // Adjust spacing as needed


// Add details to the HBox with styled Labels
                                Label clientlabel = new Label(user.getNom_personne());
                                clientlabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 150; -fx-pref-height:   21");

                                Label nomPrenomDoctorLabel = new Label(destination);
                                nomPrenomDoctorLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width: 90; -fx-pref-height:   21");

                                Label prenomLabel = new Label(prix);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   30; -fx-pref-height:   21");
                                Label dureelabel = new Label(duree);
                                prenomLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   50; -fx-pref-height:   21");


                                Label dateLabel = new Label(timestampAsString);
                                dateLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");

                                Label specialiteLabel = new Label(show);
                                specialiteLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: black; -fx-pref-width:   150; -fx-pref-height:   21");


// Add the date Label with the full month name

// Add the Labels to the HBox
                                hbox.getChildren().addAll(clientlabel, nomPrenomDoctorLabel, dateLabel, specialiteLabel, dureelabel, prenomLabel);

// Assuming this is inside a ListCell or similar where you can set the graphic
                                setGraphic(hbox);

                            }
                        }
                    };
                }
            });
            listbillets.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    currentBilletSelected = listbillets.getSelectionModel().getSelectedItem();
                }
            });
            MenuItem ajouterItem = new MenuItem("Ajouter");
            // Associer des actions aux éléments de menu
            ajouterItem.setOnAction(event -> ajouterAction());
//            crudTransport.getItems().addAll( ajouterItem);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
/*        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Afficher tous les éléments si le champ de recherche est vide
                listbillets.setItems(observableList);
            } else {
                // Filtrer les éléments en fonction de la recherche avancée
                ObservableList<billet> filteredList = FXCollections.observableArrayList();
                String[] searchTerms = newValue.toLowerCase().split("\\s+"); // Séparer les termes de recherche par des espaces

                for (billet billet : observableList) {
                    boolean matchesAllCriteria = true;

                    // Convertir le timestamp en une date
                    LocalDateTime billetDateTime = Instant.ofEpochMilli(billet.getDate_depart().getTime())
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();

                    // Vérifier chaque terme de recherche
                    for (String term : searchTerms) {
                        // Vérifier si le terme correspond à la destination du billet
                        if (billet.getDestination_voyage().toLowerCase().contains(term)) {
                            continue; // Passer au terme suivant s'il correspond à la destination
                        }

                        // Vérifier si le terme correspond à une date
                        try {
                            LocalDate searchTermDate = LocalDate.parse(term, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            if (billetDateTime.toLocalDate().isEqual(searchTermDate)) {
                                continue; // Passer au terme suivant s'il correspond à la date exacte (sans l'heure)
                            }
                        } catch (DateTimeParseException e) {
                            // Ignorer les termes qui ne sont pas des dates valides
                        }

                        // Aucun des critères ne correspond, définir matchesAllCriteria sur false
                        matchesAllCriteria = false;
                        break;
                    }

                    if (matchesAllCriteria) {
                        filteredList.add(billet);
                    }
                }
                listbillets.setItems(filteredList);
            }
        });

    }*/
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                // Afficher tous les éléments si le champ de recherche est vide
                listbillets.setItems(observableList);
            } else {
                // Filtrer les éléments en fonction de la première lettre de la recherche
                ObservableList<billet> filteredList = FXCollections.observableArrayList();
                String[] searchTerms = newValue.toLowerCase().split("\\s+"); // Séparer les termes de recherche par des espaces

                for (billet billet : observableList) {
                    boolean matchesAnyCriteria = false;

                    // Convertir le timestamp en une date
                    LocalDateTime billetDateTime = Instant.ofEpochMilli(billet.getDate_depart().getTime())
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();

                    // Vérifier chaque terme de recherche
                    for (String term : searchTerms) {
                        // Vérifier si le terme correspond au début de la destination du billet
                        if (billet.getDestination_voyage().toLowerCase().startsWith(term)) {
                            matchesAnyCriteria = true; // Si un terme correspond, marquer comme correspondant
                            break; // Pas besoin de vérifier les autres termes
                        }

                        // Vérifier si le terme correspond à une date
                        try {
                            LocalDate searchTermDate = LocalDate.parse(term, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            if (billetDateTime.toLocalDate().isEqual(searchTermDate)) {
                                matchesAnyCriteria = true; // Si un terme correspond, marquer comme correspondant
                                break; // Pas besoin de vérifier les autres termes
                            }
                        } catch (DateTimeParseException e) {
                            // Ignorer les termes qui ne sont pas des dates valides
                        }
                    }

                    if (matchesAnyCriteria) {
                        filteredList.add(billet);
                    }
                }
                listbillets.setItems(filteredList);
            }
        });

    }
        private void ajouterAction() {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/AjoutBillet.fxml")));
            billetlabel.getScene().setRoot(root);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

   private void refreshPieChart() {
    ObservableList<PieChart.Data> newData = FXCollections.observableArrayList();

    // Accéder aux statistiques de destination actuelles
    for (Map.Entry<String, Integer> entry : destinationStats.entrySet()) {
        newData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
    }

    // Rafraîchir le PieChart avec les nouvelles données
    statistiquesPieChart.setData(newData);
    System.out.println("PieChart refreshed");}
   public void deletebillet(ActionEvent actionEvent) {
    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
    confirmationDialog.setTitle("Confirmation");
    confirmationDialog.setHeaderText(null);
    confirmationDialog.setContentText("Êtes-vous sûr de supprimer cette Billet ?");

    // Option pour confirmer ou annuler la suppression
    ButtonType confirmButton = new ButtonType("Confirmer");
    ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
    confirmationDialog.getButtonTypes().setAll(confirmButton, cancelButton);

    // Récupération de la réponse de l'utilisateur
    Optional<ButtonType> result = confirmationDialog.showAndWait();

    // Si l'utilisateur confirme la suppression
    if (result.isPresent() && result.get() == confirmButton) {
        try {
            String destinationtodelete=currentBilletSelected.getDestination_voyage();
            // Suppression de la station
            bs.supprimer(currentBilletSelected.getRef_voyage());
            updateDestinationStatsAfterDeletion(destinationtodelete);
            refreshPieChart();
            initialize(); // Met à jour la liste des billets et les statistiques
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
    @FXML
    void modifybillet(ActionEvent event) throws SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/ModifierBillet.fxml"));
        Parent newPageRoot = null;
        try {
            newPageRoot = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        // Get the controller instance created by the FXMLLoader
        ModifierBilletController modifierBilletController = loader.getController();
        modifierBilletController.initializeValues(currentBilletSelected.getRef_voyage(),
                currentBilletSelected.getDestination_voyage(),
                currentBilletSelected.getDate_depart(),
                currentBilletSelected.getPrix(),
                currentBilletSelected.getDuree(),
                currentBilletSelected.getId_station()

        );

        // Create a new scene with the newPageRoot
        Scene newPageScene = new Scene(newPageRoot);

        // Get the current stage and set the new scene
        Stage stage = (Stage) billetlabel.getScene().getWindow();
        stage.setScene(newPageScene);
    }
    public void backhome(ActionEvent actionEvent) {
        try {


            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resourcesGestionTransport/home.fxml")));
            billetlabel.getScene().setRoot(root);


        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private void updateDestinationStatsAfterDeletion(String destination) {
        int count = destinationStats.getOrDefault(destination, 0);
        if (count > 1) {
            destinationStats.put(destination, count - 1); // Décrémenter le compteur
        } else {
            destinationStats.remove(destination); // Supprimer la destination si le compteur est égal à 1
        }
        updateStatisticsLabel();
    }
    public void updateDestinationStats(String destination) {
        destinationStats.put(destination, destinationStats.getOrDefault(destination, 0) + 1);
        System.out.println("Stats updated for destination: " + destination);
        // Mettez à jour l'étiquette des statistiques dans l'interface utilisateur
        updateStatisticsLabel();
    }
    public void updateDestinationStatsAfterModify(String oldDestination, String newDestination) {
        if (oldDestination.equals(newDestination)) {
            // Pas de changement de destination, pas besoin de mise à jour
            return;
        }

        // Décrémenter le compteur de l'ancienne destination
        int oldCount = destinationStats.getOrDefault(oldDestination, 0);
        if (oldCount > 1) {
            destinationStats.put(oldDestination, oldCount - 1);
        } else {
            destinationStats.remove(oldDestination);
        }

        // Incrémenter le compteur de la nouvelle destination
        destinationStats.put(newDestination, destinationStats.getOrDefault(newDestination, 0) + 1);

        System.out.println("Stats updated after modification for old destination: " + oldDestination + " and new destination: " + newDestination);
        updateStatisticsLabel();
    }

    private void updateStatisticsLabel() {
        int totalDestinations = destinationStats.values().stream().mapToInt(Integer::intValue).sum();
        // Construisez une chaîne représentant les statistiques de destination
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : destinationStats.entrySet()) {
            double percentage = (double) entry.getValue() / totalDestinations * 100;
            pieChartData.add(new PieChart.Data(entry.getKey() + " (" + String.format("%.2f", percentage) + "%)", entry.getValue()));
        }
        statistiquesPieChart.setData(pieChartData);
        System.out.println("Statistics label updated with percentages");
    }

    public void addbillet(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AjoutBillet.fxml"));
            Parent newPageRoot = loader.load();


            // Create a new scene with the newPageRoot
            Scene pageScene = new Scene(newPageRoot);

            // Get the current stage and set the new scene
            Stage stage = (Stage) billetlabel.getScene().getWindow();
            stage.setScene(pageScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void sortButton(MouseEvent mouseEvent) {
        ObservableList<billet> billets = listbillets.getItems();
        billets.sort(Comparator.comparing(billet::getDate_depart));
    }

    public void promo(ActionEvent event) {
        {
            billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();
            if (billetSelectionne != null) {
                // Obtenez le prix du billet sélectionné
                initalPrix = Double.parseDouble(billetSelectionne.getPrix());


                // Appliquer la réduction de 70%
                nouveauPrix = initalPrix * 0.8; // Par exemple, appliquez une réduction de 20%

                // Mettez à jour le prix du billet dans l'objet billet
                billetSelectionne.setPrix(Double.toString(nouveauPrix));

                // Rafraîchissez la vue de la liste pour refléter le changement de prix
                listbillets.refresh();
            }
        }}

    public void promo50(ActionEvent event) {
        // Obtenez le billet sélectionné dans la liste
        billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();
        if (billetSelectionne != null) {
            // Obtenez le prix du billet sélectionné
            initalPrix = Double.parseDouble(billetSelectionne.getPrix());


            // Appliquer la réduction de 70%
            nouveauPrix = initalPrix * 0.5; // Réduction de 70%
            BilletService billetService = new BilletService();
            try {
                billetService.changePrixBilletByRef(billetSelectionne.getRef_voyage(),String.valueOf(nouveauPrix));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Mettre à jour le prix du billet dans l'objet billet
            billetSelectionne.setPrix(Double.toString(nouveauPrix));

            // Rafraîchir la vue de la liste pour refléter le changement de prix
            listbillets.refresh();
        }
    }

    public void promo70(ActionEvent event) {
        // Obtenez le billet sélectionné dans la liste
        billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();
        if (billetSelectionne != null) {
            // Obtenez le prix du billet sélectionné
            initalPrix = Double.parseDouble(billetSelectionne.getPrix());


            // Appliquer la réduction de 70%
            nouveauPrix = initalPrix * 0.3; // Réduction de 70%

            // Mettre à jour le prix du billet dans l'objet billet
            billetSelectionne.setPrix(Double.toString(nouveauPrix));

            // Rafraîchir la vue de la liste pour refléter le changement de prix
            listbillets.refresh();
        }
    }
billet bi =new billet();

    public void nopromo(ActionEvent event) {
        billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();

        if (billetSelectionne != null) {
            // Check if the current price is not null or meets a specific condition to reset
            if (billetSelectionne.getPrix() != null) {
                // Hypothetically, reset the price to a default value
//                 initalPrix = billetSelectionne.getPrix(); // Example default price
               // Formats to 2 decimal places
                billetSelectionne.setPrix(String.valueOf(initalPrix));
                // Optionally, provide feedback to the user
                System.out.println("Ticket price reset to default price.");
            } else {
                // Handle case where the current price is not available
                System.out.println("No current price available to reset.");
            }

            // Refresh the list view to reflect the change
            listbillets.refresh();
        } else {
            // Optionally, provide feedback if no ticket is selected
            System.out.println("Please select a ticket.");
        }
    }
    }
   /*     billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();
        if (billetSelectionne != null) {
            // Hypothetically, assume there's an originalPrice property to reset to
            if (billetSelectionne.getPrix() != null) {
                billetSelectionne.setPrix(billetSelectionne.getPrix());
                // Optionally, provide feedback to the user
                System.out.println("Ticket price reset to original price.");
            } else {
                // Handle case where original price is not available
                System.out.println("No original price available to reset.");
            }

            // Refresh the list view to reflect the change
            listbillets.refresh();
        } else {
            // Optionally, provide feedback if no ticket is selected
            System.out.println("Please select a ticket.");
        }
    }*/
    /*    billet billetSelectionne = listbillets.getSelectionModel().getSelectedItem();
        if (billetSelectionne != null) {
            // Rétablissez le prix initial du billet
            billetSelectionne.setPrix(billetSelectionne.getPrix());

            // Rafraîchissez la vue de la liste pour refléter le changement de prix
            listbillets.refresh();
        }
    }*/



