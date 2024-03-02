package org.example.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.entites.Article;
import org.example.services.ServiceArticle;
import javafx.scene.control.ScrollPane;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Article1Controllers implements ModificationListener {

    @FXML
    private HBox articlesContainer;

    @FXML
    private TextField nomArticleTF;

    @FXML
    private TextField prixArticleTF;

    @FXML
    private TextField quantiteArticleTF;

    @FXML
    private ComboBox<Article.TypeArticle> typeArticleCB;

    @FXML
    private TextField descriptionArticleTF;

    private final ServiceArticle serviceArticle = new ServiceArticle();

    @FXML
    void initialize() {

        afficherArticles();
    }

    private void afficherArticles() {
        articlesContainer.getChildren().clear();
        try {
            List<Article> articles = serviceArticle.afficherArticle();
            for (Article article : articles) {
                Node articleCard = createArticleCard(article);
                articlesContainer.getChildren().add(articleCard);
            }
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des articles : " + e.getMessage());
        }
    }


    private HBox createArticleCard(Article article) {
        HBox card = new HBox();
        card.setSpacing(10);
        card.setStyle("-fx-background-color: #ffffff; " + // Couleur de fond
                "-fx-border-color: rgb(37,33,33); " +     // Couleur de la bordure
                "-fx-border-width: 1px; " +         // Largeur de la bordure
                "-fx-border-radius: 2px; " +        // Rayon de bordure arrondi
                "-fx-padding: 12px;" );               // Espacement intérieur

        VBox labelsAndButtons = new VBox();
        labelsAndButtons.setSpacing(10);

        Label nomLabel = new Label(article.getNom_Article());
        nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;"); // Texte en gras, taille de police 14px

        Label prixLabel = new Label("Prix: " + article.getPrix_Article() + " DT");
        prixLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        Label quantiteLabel = new Label("Quantité: " + article.getQuantite_Article());
        quantiteLabel.setStyle("-fx-font-size: 14px;");

        Label typeLabel = new Label("Type: " + article.getType_article());
        typeLabel.setStyle("-fx-font-size: 14px;");

        Label descriptionLabel = new Label("Description: " + article.getDescription_article());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        Button modifierButton = new Button("Modifier");
        modifierButton.setStyle("-fx-background-color: #4CAF50; " +  // Couleur de fond verte
                "-fx-text-fill: white;");             // Couleur du texte blanc
        modifierButton.setOnAction(event -> navigateToModification(article));

        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setStyle("-fx-background-color: #f44336; " +  // Couleur de fond rouge
                "-fx-text-fill: white;");            // Couleur du texte blanc
        supprimerButton.setOnAction(event -> supprimerArticle(article));

        labelsAndButtons.getChildren().addAll(nomLabel, prixLabel, quantiteLabel, typeLabel, descriptionLabel, modifierButton, supprimerButton);

        if (article.getPhoto_article() != null && !article.getPhoto_article().isEmpty()) {
            ImageView imageView = new ImageView(new Image(new File(article.getPhoto_article()).toURI().toString()));
            imageView.setFitWidth(120);
            imageView.setFitHeight(100);
            card.getChildren().addAll(imageView, labelsAndButtons);
        } else {
            card.getChildren().addAll(labelsAndButtons);
        }

        return card;
    }





    @FXML
    void retourPageAdmin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AdminInterface.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void navigateToModification(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierArticle.fxml"));
            Parent root = loader.load();

            ModifierArticleControllers modifierArticleController = loader.getController();
            modifierArticleController.setArticle(article);
            modifierArticleController.setModificationListener(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }
    }

    private void supprimerArticle(Article article) {
        try {
            serviceArticle.supprimerArticle(article.getId_Article());
            afficherArticles();
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "L'article a été supprimé avec succès.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la suppression de l'article : " + e.getMessage());
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }



    @Override
    public void onModification() {
        afficherArticles();
    }


}