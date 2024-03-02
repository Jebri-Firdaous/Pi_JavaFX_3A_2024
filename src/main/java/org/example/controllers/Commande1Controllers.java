package org.example.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.entites.Article;
import org.example.entites.Commande;
import org.example.services.ServiceCommande;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.control.ButtonType;

public class Commande1Controllers implements ModificationListener {

    @FXML
    private VBox itemsContainer; // Container pour afficher les éléments (articles et commandes)

    private final ServiceCommande serviceCommande = new ServiceCommande();

    @FXML
    void initialize() {
        afficherCommandes();
    }

    private void afficherCommandes() {
        try {
            itemsContainer.getChildren().clear(); // Efface les anciennes commandes pour éviter les duplications
            List<Commande> commandes = serviceCommande.afficherCommande();
            for (Commande commande : commandes) {
                VBox commandeBox = createCommandeBox(commande);
                itemsContainer.getChildren().add(commandeBox);
            }
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des commandes : " + e.getMessage());
        }
    }

    private VBox createCommandeBox(Commande commande) {
        VBox commandeBox = new VBox();
        commandeBox.setSpacing(10);
        commandeBox.setStyle("-fx-background-color: #f9f9f9; " +
                "-fx-border-color: #cccccc; " +
                "-fx-border-width: 1px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 12px;");

        Label idLabel = new Label("Référence Commande : " + commande.getId_Commande());
        idLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label nombreArticleLabel = new Label("Nombre d'articles : " + commande.getNombre_Article());
        nombreArticleLabel.setStyle("-fx-font-size: 14px;");

        Label prixLabel = new Label("Prix total : " + commande.getPrix_Totale() + "DT");
        prixLabel.setStyle("-fx-font-size: 14px;");

        Label delaisLabel = new Label("Date de livraison de commande : " + commande.getDelais_Commande());
        delaisLabel.setStyle("-fx-font-size: 14px;");

        Separator separator = new Separator();
        VBox.setMargin(separator, new Insets(5, 0, 10, 0)); // Espacement autour du séparateur

        VBox articlesBox = new VBox();
        articlesBox.setSpacing(5);
        articlesBox.setStyle("-fx-padding: 10px;");

        Label articlesLabel = new Label("Articles :");
        articlesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        for (Article article : commande.getArticles()) {
            HBox articleBox = createArticleBox(article);
            articlesBox.getChildren().add(articleBox);
        }

        Button modifierButton = new Button("Modifier");
        modifierButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        modifierButton.setOnAction(event -> {
            ouvrirPageModification(commande);
        });

        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px;");
        deleteButton.setOnAction(event -> {
            // Créer un dialogue de confirmation
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Suppression de commande");
            confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer cette commande ?");

            // Option pour confirmer ou annuler la suppression
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        serviceCommande.supprimerCommande(commande.getId_Commande());
                        afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "La commande a été supprimée avec succès.");
                        afficherCommandes();
                    } catch (SQLException e) {
                        afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression de la commande : " + e.getMessage());
                    }
                }
            });
        });

        HBox buttonBox = new HBox(modifierButton, deleteButton);
        buttonBox.setSpacing(10);

        commandeBox.getChildren().addAll(idLabel, nombreArticleLabel, prixLabel, delaisLabel, separator, articlesLabel, articlesBox, buttonBox);
        return commandeBox;
    }

    private HBox createArticleBox(Article article) {
        HBox articleBox = new HBox();
        articleBox.setSpacing(10);

        Label articleLabel = new Label(article.getNom_Article());
        articleLabel.setStyle("-fx-font-size: 14px;");

        articleBox.getChildren().add(articleLabel);
        return articleBox;
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private void ouvrirPageModification(Commande commande) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCommande.fxml"));
            Parent root = loader.load();
            ModifierCommandeControllers modifierCommandeController = loader.getController();
            modifierCommandeController.setCommande(commande);
            modifierCommandeController.setModificationListener(this);

            // Initialiser la référence stage
            modifierCommandeController.setStage(new Stage());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onModification() {
        afficherCommandes();
    }
}
