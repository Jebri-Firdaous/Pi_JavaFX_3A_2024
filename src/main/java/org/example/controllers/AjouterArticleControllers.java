package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import org.example.entites.Article;
import org.example.services.ServiceArticle;

import java.io.File;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class AjouterArticleControllers {
    @FXML
    private VBox articlesContainer;

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

        typeArticleCB.getItems().addAll(Article.TypeArticle.values());
        // Limiter la saisie de quantité à des entiers uniquement
        quantiteArticleTF.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                if (change.isContentChange()) {
                    if (!change.getControlNewText().matches("\\d*")) {
                        change.setText("");
                    }
                }
                return change;
            }
        }));
        prixArticleTF.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change change) {
                if (change.isContentChange()) {
                    if (!change.getControlNewText().matches("\\d*")) {
                        change.setText("");
                    }
                }
                return change;
            }
        }));
    }

    @FXML
    void ajouterArticle() {
        if (nomArticleTF.getText().isEmpty() || prixArticleTF.getText().isEmpty() || quantiteArticleTF.getText().isEmpty() || typeArticleCB.getValue() == null || descriptionArticleTF.getText().isEmpty()) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier si l'élément sélectionné n'est pas null
        Article.TypeArticle typeArticle = typeArticleCB.getValue();
        if (typeArticle == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un type d'article.");
            return;
        }

        try {
            String nomArticle = nomArticleTF.getText();
            double prixArticle = Double.parseDouble(prixArticleTF.getText());
            int quantiteArticle = Integer.parseInt(quantiteArticleTF.getText());
            String descriptionArticle = descriptionArticleTF.getText();
            String photo_article = insererPhoto();

            Article nouvelArticle = new Article(nomArticle, prixArticle, quantiteArticle, typeArticle, descriptionArticle, photo_article);

            serviceArticle.ajouterArticle(nouvelArticle);

            nomArticleTF.clear();
            prixArticleTF.clear();
            quantiteArticleTF.clear();
            descriptionArticleTF.clear();

            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "L'article a été ajouté avec succès.");
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez saisir des valeurs valides pour le prix et la quantité.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de l'article : " + e.getMessage());
        }
    }



    private void afficherAlerte(Alert.AlertType type, String titre, String contenu) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    private String cheminPhotoSelectionne = "";

    @FXML
    private String insererPhoto() {
        if (!cheminPhotoSelectionne.isEmpty()) {
            return cheminPhotoSelectionne;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insérer une photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            cheminPhotoSelectionne = selectedFile.getAbsolutePath();
            return cheminPhotoSelectionne;
        } else {
            return "";
        }
    }
}
