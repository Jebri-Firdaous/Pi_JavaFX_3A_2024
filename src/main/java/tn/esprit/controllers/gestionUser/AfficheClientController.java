package tn.esprit.controllers.gestionUser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceClient;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficheClientController {
    private final ServiceUser sc = new ServiceUser();
    @FXML
    public TableView<User> tableViewClient;
    ObservableList<User> listeClients = FXCollections.observableArrayList();
    User currentClientSelected;
    @FXML
    private TableColumn<User, Integer> ageClient;
    @FXML
    private Label label;
    @FXML
    private TableColumn<User, String> mailClient;

    @FXML
    private TableColumn<User, String> prenomClient;

    @FXML
    private TableColumn<User, String> genreClient;

    @FXML
    private TableColumn<User, Integer> telClient;


    @FXML
    private TableColumn<User, String> nomClient;
    @FXML
    private TableColumn<User, Boolean> IS_VERIFIED;

    @FXML
    private TableColumn<User, Boolean> IS_BANNED;
@FXML
private TextField search;

    /*-----------------------------AFFICHE ET RECUPERATION DES DONNEE DANS LA TABEVIEW--------------------------------*/
    public void initialize() {
        try {
            //RechercherClientDansLaBase("");
            search.textProperty().addListener((observable, oldValue, newValue) -> {
                //RechercherClientDansLaBase(newValue);
            });
            List<User> ClientFromService = sc.afficherClient();
            nomClient.setCellValueFactory(new PropertyValueFactory<>("nom_personne"));
            prenomClient.setCellValueFactory(new PropertyValueFactory<>("prenom_personne"));
            telClient.setCellValueFactory(new PropertyValueFactory<>("numero_telephone"));
            mailClient.setCellValueFactory(new PropertyValueFactory<>("email"));
            genreClient.setCellValueFactory(new PropertyValueFactory<>("genre"));
            ageClient.setCellValueFactory(new PropertyValueFactory<>("age"));
            tableViewClient.setItems(FXCollections.observableArrayList(ClientFromService));
            IS_VERIFIED.setCellValueFactory(new PropertyValueFactory<>("is_verified"));
            IS_VERIFIED.setCellFactory(column -> new TableCell<User, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item ? "oui" : "non");
                    }
                }
            });
            IS_BANNED.setCellValueFactory(new PropertyValueFactory<>("is_banned"));
            IS_BANNED.setCellFactory(column -> new TableCell<User, Boolean>() {
                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item ? "oui" : "non");
                    }
                }
            });// Utilisez ClientFromService
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de récupérer les données de la base de données");
            alert.setContentText("Une erreur s'est produite lors de la récupération des données. Veuillez réessayer plus tard.");
            alert.showAndWait();
        }
    }

    @FXML
    void RechercherClientDansLaBase(String recherche) {
        tableViewClient.getItems().clear(); // Effacer les anciens résultats de la ListView
        try {
            List<User> clientTrouve = sc.recherClient(recherche);
            if (!clientTrouve.isEmpty()) {
                tableViewClient.getItems().addAll(clientTrouve);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Résultat de la recherche");
                alert.setHeaderText(null);
                alert.setContentText("Aucun Client correspondant trouvé.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la recherche des clients.");
            alert.showAndWait();
        }
    }
    public void recherche(ActionEvent event) throws SQLException {
        RechercherClientDansLaBase("");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            RechercherClientDansLaBase(newValue);
        });

    }
    @FXML
    void AjouterClient(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/AjouterCompteClient.fxml"));
            Parent root = loader.load();
            AjoutClientController AjoutClientController = loader.getController();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void supprimerAdmin(ActionEvent event) {
    }

    public void banne(ActionEvent event) {
        User adminSelectionne = tableViewClient.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sc.banner(adminSelectionne.getId());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le client a été banné avec succès !");
                alert.showAndWait();
                ToAfficherListeClient();
                initialize();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }
    public void ToAfficherListeClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherClient.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }
    public void annulerbanne(ActionEvent event) {
        User adminSelectionne = tableViewClient.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sc.annulerbann(adminSelectionne.getId());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le bann a ete ennulé avec succes !");
                alert.showAndWait();
                ToAfficherListeClient();
                initialize();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }
}



