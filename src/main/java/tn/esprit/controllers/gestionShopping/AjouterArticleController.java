package tn.esprit.controllers.gestionShopping;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import tn.esprit.entities.gestionShopping.Article;
import tn.esprit.services.gestionShopping.ServiceArticle;

import java.io.File;
import java.sql.SQLException;
import java.util.function.UnaryOperator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AjouterArticleController {
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
    private final String CHEMIN_HTDOCS = "C:/xampp/htdocs"; // Chemin vers htdocs
    private final String URL_HTDOCS = "http://localhost/"; // URL de base pour accéder à htdocs


    @FXML
    public String insererPhoto() {
        if (!cheminPhotoSelectionne.isEmpty()) {
            return cheminPhotoSelectionne;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insérer une photo");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            // Copier l'image dans le répertoire htdocs
            String nomFichier = selectedFile.getName();
            Path destination = Paths.get(CHEMIN_HTDOCS, "img", nomFichier);
            try {
                Files.copy(selectedFile.toPath(), destination);
                cheminPhotoSelectionne = URL_HTDOCS + "img/" + nomFichier;
                return cheminPhotoSelectionne;
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        } else {
            return "";
        }
    }
}
