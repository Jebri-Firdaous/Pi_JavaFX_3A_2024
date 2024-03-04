package tn.esprit.controllers.gestionShopping;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.gestionShopping.Article;
import tn.esprit.entities.gestionShopping.Commande;
import tn.esprit.services.gestionShopping.ServiceCommande;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ModifierCommandeController {

    @FXML
    private TextField idCommandeTextField;

    @FXML
    private DatePicker delaisCommandeDatePicker;

    @FXML
    private ComboBox<Article> articlesDejaInseresComboBox;

    @FXML
    private Button modifierCommandeButton;

    private ServiceCommande serviceCommande = new ServiceCommande();
    private ModificationListener modificationListener;
    private Commande commande;
    private Stage stage;


    @FXML
    private Button accepterButton;



    public void initialize() {
        modifierCommandeButton.setOnAction(event -> modifierCommande());
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
        afficherDetailsCommande();
        chargerArticlesDejaInseres(); // Charger les articles déjà insérés lors de l'initialisation
    }

    @FXML
    void modifierCommande() {
        try {
            if (commande != null) {

                java.util.Date nouveauDelai = java.sql.Date.valueOf(delaisCommandeDatePicker.getValue());


                commande.setDelais_Commande(nouveauDelai);

                serviceCommande.modifierCommande(commande); // Mettre à jour la commande

                afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "La commande a été modifiée avec succès.");
                if (modificationListener != null) {
                    modificationListener.onModification(); // Appeler le listener pour indiquer la modification
                }
                // Fermer la fenêtre de modification après la réussite de la modification
                Stage stage = (Stage) idCommandeTextField.getScene().getWindow();
                stage.close();
            } else {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Aucune commande sélectionnée.");
            }
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs valides pour les champs numériques.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la modification de la commande : " + e.getMessage());
        }
    }

    private void afficherDetailsCommande() {
        if (commande != null) {
            idCommandeTextField.setText(String.valueOf(commande.getId_Commande()));
            java.sql.Date dateSql = (Date) commande.getDelais_Commande();
            java.util.Date utilDate = new java.util.Date(dateSql.getTime());
            LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            delaisCommandeDatePicker.setValue(localDate);
        }
    }


    private void chargerArticlesDejaInseres() {
        try {
            // Charger les articles déjà insérés dans la commande
            List<Article> articlesDejaInseres = serviceCommande.getArticlesDejaInseres(commande);
            articlesDejaInseresComboBox.getItems().addAll(articlesDejaInseres);
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors du chargement des articles déjà insérés : " + e.getMessage());
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    public void setModificationListener(ModificationListener modificationListener) {
        this.modificationListener = modificationListener;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
