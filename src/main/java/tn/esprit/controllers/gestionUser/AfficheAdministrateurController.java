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
import tn.esprit.services.gestionUserServices.ServiceAdmin;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficheAdministrateurController {
    private final ServiceUser sa = new ServiceUser();
   ServiceUser serviceUser = new ServiceUser();
    ObservableList<User> listeAdmins = FXCollections.observableArrayList();
    @FXML
    private ListView<User> listViewAdmin;
    @FXML
    private Label label;


    @FXML
    private TableColumn<User, String> nomAdmin;

    @FXML
    private TableColumn<User, String> prenomAdmin;
    @FXML
    private TableView<User> tableViewAdmin;


    @FXML
    private TableColumn<User, Integer> telAdmin;

    @FXML
    private TableColumn<User, String> mailAdmin;


    @FXML
    private TableColumn<User, String> role;
    @FXML
    private TextField search;

    @FXML
    private TableColumn<User, Boolean> IS_VERIFIED;

    @FXML
    private TableColumn<User, Boolean> IS_BANNED;

    /*-----------------------------AFFICHE ET RECUPERATION DES DONNEE DANS LA TABEVIEW--------------------------------*/

    public void initialize() {
        try {
            // Clear the TableView before reloading data
            tableViewAdmin.getItems().clear();

            // Get the updated list of admins from the service
            List<User> adminsFromService = sa.afficherAdmin();

            // Set cell value factories for each column
            nomAdmin.setCellValueFactory(new PropertyValueFactory<>("nom_personne"));
            prenomAdmin.setCellValueFactory(new PropertyValueFactory<>("prenom_personne"));
            telAdmin.setCellValueFactory(new PropertyValueFactory<>("numero_telephone"));
            mailAdmin.setCellValueFactory(new PropertyValueFactory<>("email"));
            role.setCellValueFactory(new PropertyValueFactory<>("role_admin"));
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
            });
            // Populate the TableView with the updated list of admins
            tableViewAdmin.setItems(FXCollections.observableArrayList(adminsFromService));
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de récupérer les données de la base de données");
            alert.setContentText("Une erreur s'est produite lors de la récupération des données. Veuillez réessayer plus tard.");
            alert.showAndWait();
        }
    }
/*
    private void rechercherAdminDansLaBase(String recherche) {
        tableViewAdmin.getItems().clear(); // Effacer les anciens résultats du TableView
        try {
            List<User> administrateursTrouves = sa.rechercher(recherche);
            if (!administrateursTrouves.isEmpty()) {
                // Si des administrateurs ont été trouvés, les ajouter au TableView
                tableViewAdmin.getItems().addAll(administrateursTrouves);
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Résultat de la recherche");
                alert.setContentText("Aucun administrateur correspondant trouvé.");
                alert.showAndWait();
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur lors de la recherche des administrateurs" + e.getMessage());
            alert.showAndWait();
        }
    }*/
    public void ToAjouterAdmin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/AjouterCompteAdmin.fxml"));
            Parent root = loader.load();
            AjoutAdministrateurController AjoutAdministrateurController = loader.getController();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void toAddpage(ActionEvent event) {
        ToAjouterAdmin();
    }


    public void modifier(ActionEvent actionEvent) {
        User adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/modifier.fxml"));
                Parent root = loader.load();
                Scene pageScene = new Scene(root);

                ModifierAdminController modifierController = loader.getController();
                modifierController.initData(adminSelectionne); // Passez l'administrateur sélectionné au contrôleur de la vue de modification

                Stage stage = (Stage) label.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }

    public void supprimerAdmin(ActionEvent actionEvent) {
        User adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sa.supprimerUser(adminSelectionne.getId());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("L'administrateur a été supprimé avec succès !");
                alert.showAndWait();
                ToAfficherListeAdmine();
                initialize();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }
    public void ToAfficherListeAdmine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
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

    public void recherche(ActionEvent event) {
    }

    public void banne(ActionEvent event) {
        User adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sa.banner(adminSelectionne.getId());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("L'administrateur a été banné avec succès !");
                alert.showAndWait();
                ToAfficherListeAdmine();
                initialize();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }

    public void annulerbanne(ActionEvent event) {
        User adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sa.annulerbann(adminSelectionne.getId());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Le bann a ete ennulé avec succes !");
                alert.showAndWait();
                ToAfficherListeAdmine();
                initialize();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }

    /*public void recherche(ActionEvent event) throws SQLException {
        rechercherAdminDansLaBase("");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherAdminDansLaBase(newValue);
        });

    }*/
}
