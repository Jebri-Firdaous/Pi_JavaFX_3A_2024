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
import tn.esprit.entities.gestionUserEntities.Administrateur;
import tn.esprit.entities.gestionUserEntities.Personne;
import tn.esprit.services.gestionUserServices.ServiceAdmin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficheAdministrateurController {
    private final ServiceAdmin sa = new ServiceAdmin();
    ObservableList<Administrateur> listeAdmins = FXCollections.observableArrayList();
    Administrateur currentAdminSelected;
    @FXML
    private ListView<Administrateur> listViewAdmin;
    @FXML
    private Label label;


    @FXML
    private TableColumn<Personne, String> nomAdmin;

    @FXML
    private TableColumn<Personne, String> prenomAdmin;
    @FXML
    private TableView<Administrateur> tableViewAdmin;


    @FXML
    private TableColumn<Administrateur, Integer> telAdmin;

    @FXML
    private TableColumn<Administrateur, String> mailAdmin;


    @FXML
    private TableColumn<Administrateur, String> role;
    @FXML
    private TextField search;

    /*-----------------------------AFFICHE ET RECUPERATION DES DONNEE DANS LA TABEVIEW--------------------------------*/
    public void initialize() {
        try {
            List<Administrateur> AdminsFromService = sa.afficher();
            nomAdmin.setCellValueFactory(new PropertyValueFactory<>("nom_personne"));
            prenomAdmin.setCellValueFactory(new PropertyValueFactory<>("prenom_personne"));
            telAdmin.setCellValueFactory(new PropertyValueFactory<>("numero_telephone"));
            mailAdmin.setCellValueFactory(new PropertyValueFactory<>("mail_personne"));
            role.setCellValueFactory(new PropertyValueFactory<>("role"));
            tableViewAdmin.setItems(FXCollections.observableArrayList(AdminsFromService));
            // Utilisez AdminsFromService
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de récupérer les données de la base de données");
            alert.setContentText("Une erreur s'est produite lors de la récupération des données. Veuillez réessayer plus tard.");
            alert.showAndWait();
        }
    }

    private void rechercherAdminDansLaBase(String recherche) {
        tableViewAdmin.getItems().clear(); // Effacer les anciens résultats du TableView
        try {
            List<Administrateur> administrateursTrouves = sa.rechercher(recherche);
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
    }

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

    public void supprimer(ActionEvent actionEvent) {

    }

    public void modifier(ActionEvent actionEvent) {
        Administrateur adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
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
        Administrateur adminSelectionne = tableViewAdmin.getSelectionModel().getSelectedItem();
        if (adminSelectionne != null) {
            try {
                sa.supprimer(adminSelectionne.getId_personne());
                // Afficher un message de confirmation
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("L'administrateur a été supprimé avec succès !");
                alert.showAndWait();
                initialize();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Affichez un message indiquant à l'utilisateur de sélectionner un administrateur
        }
    }

    public void recherche(ActionEvent event) throws SQLException {
        rechercherAdminDansLaBase("");
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherAdminDansLaBase(newValue);
        });

    }
}
