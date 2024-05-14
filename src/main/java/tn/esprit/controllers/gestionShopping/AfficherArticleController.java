package tn.esprit.controllers.gestionShopping;

import javafx.animation.KeyFrame;


import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import tn.esprit.entities.gestionShopping.Article;
import tn.esprit.services.gestionShopping.ServiceArticle;


public class AfficherArticleController implements ModificationListener {


    @FXML
    private HBox articlesContainer;

    @FXML
    private TextField rechercheArticleTF;

    @FXML
    private ComboBox<String> triComboBox; // Ajout du ComboBox

    private final ServiceArticle serviceArticle = new ServiceArticle();
    private final List<Article> articles = new ArrayList<>();

    private Stage stage;
    @FXML
    private ImageView backgroundImageView;

    private final List<String> imagePaths = new ArrayList<>();
    private int currentImageIndex = 0;



    @FXML
    void initialize() {
        // Ajouter les chemins d'accès aux images
        imagePaths.add("src/main/resources/resourcesGestionShopping/AfficherArticle1.png");
        imagePaths.add("src/main/resources/resourcesGestionShopping/AfficherArticle2.png");
        imagePaths.add("src/main/resources/resourcesGestionShopping/AfficherArticle3.png");


        // Charger la première image
        loadImage();

        // Créer une timeline pour changer d'image toutes les 10 secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> changeImage()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        // Ajout des options de tri dans le ComboBox
        ObservableList<String> options = FXCollections.observableArrayList("Prix croissant", "Prix décroissant");
        triComboBox.setItems(options);

        // Gestion de la sélection dans le ComboBox pour le tri
        triComboBox.setOnAction(event -> {
            String selectedOption = triComboBox.getSelectionModel().getSelectedItem();
            if (selectedOption != null) {
                trierArticles(selectedOption);
            }
        });

        afficherArticles();
        rechercherArticles("");
        stage = new Stage();
        rechercheArticleTF.textProperty().addListener((observable, oldValue, newValue) -> {
            rechercherArticles(newValue);
        });
    }
    private void changeImage() {
        // Incrémenter l'index de l'image
        currentImageIndex++;
        if (currentImageIndex >= imagePaths.size()) {
            currentImageIndex = 0; // Revenir à la première image si on dépasse la dernière image
        }

        // Charger et afficher la nouvelle image
        loadImage();
    }

    private void loadImage() {
        String imagePath = imagePaths.get(currentImageIndex);
        File file = new File(imagePath);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            backgroundImageView.setImage(image);
        }
    }

    private void rechercherArticles(String recherche) {
        articlesContainer.getChildren().clear();
        articles.clear();
        try {
            articles.addAll(serviceArticle.rechercherArticles(recherche));
            afficherArticles();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la recherche des articles : " + e.getMessage());
        }
    }

    private void trierArticles(String option) {
        if (option.equals("Prix croissant")) {
            Collections.sort(articles, Comparator.comparingDouble(Article::getPrix_Article));
        } else if (option.equals("Prix décroissant")) {
            Collections.sort(articles, Comparator.comparingDouble(Article::getPrix_Article).reversed());
        }
        afficherArticles();
    }

    private ImageView generateQRCode(String text, int width, int height) {
        try {
            // Créer un objet MultiFormatWriter
            MultiFormatWriter writer = new MultiFormatWriter();

            // Paramètres pour la génération du code QR
            final int WHITE = 0xFFFFFFFF;
            final int BLACK = 0xFF000000;
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);

            // Créer une image WritableImage pour afficher le code QR
            WritableImage image = new WritableImage(width, height);
            PixelWriter pixelWriter = image.getPixelWriter();

            // Écrire les pixels en fonction de la matrice de bits
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelWriter.setArgb(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }

            // Créer un ImageView pour afficher l'image WritableImage
            ImageView imageView = new ImageView(image);
            return imageView;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

    }


    private void afficherArticles() {
        articlesContainer.getChildren().clear();
        for (Article article : articles) {
            Node card = createArticleCard(article);
            articlesContainer.getChildren().add(card);
        }
    }

    private Node createArticleCard(Article article) {
        String imagePath = "";
        HBox card = new HBox();
        card.setSpacing(10);
        card.setStyle("-fx-background-color: #ffffff; " +
                "-fx-border-color: rgba(37,33,33,0.4); " +
                "-fx-border-width: 2px; " + // Changer la largeur de la bordure ici
                "-fx-border-radius: 5px; " + // Changer le rayon de la bordure ici
                "-fx-padding: 35px;");

        // Création de la VBox pour les données de l'article
        VBox dataVBox = new VBox();
        dataVBox.setSpacing(10);
        // Création de l'ImageView pour le code QR
        ImageView qrCodeImageView = generateQRCode("ID de l'article : " + article.getId_Article() + ", Prix article : " + article.getPrix_Article()+ ",  Quantité article : " + article.getQuantite_Article()+ ", Type d'article : " + article.getType_article()+ ", Description : " + article.getDescription_article(), 200, 200);
        if (qrCodeImageView != null) {
            qrCodeImageView.setFitWidth(80);
            qrCodeImageView.setFitHeight(80);
            card.getChildren().add(qrCodeImageView);
        }



        Label nomLabel = new Label(article.getNom_Article());
        nomLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20px;");

        Label prixLabel = new Label("Prix: " + article.getPrix_Article() + " DT");
        prixLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");

        Label quantiteLabel = new Label("Quantité: " + article.getQuantite_Article());
        quantiteLabel.setStyle("-fx-font-size: 14px;");

        Label typeLabel = new Label("Type: " + article.getType_article());
        typeLabel.setStyle("-fx-font-size: 14px;");

        Label descriptionLabel = new Label("Description: " + article.getDescription_article());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 14px;");

        // Ajout des éléments à la VBox des données
        dataVBox.getChildren().addAll(nomLabel, prixLabel, quantiteLabel, typeLabel, descriptionLabel);

        // Création de la VBox pour les boutons
        VBox buttonsVBox = new VBox();
        buttonsVBox.setSpacing(10);

        // Bouton Modifier
        Button modifierButton = new Button("Modifier");
        modifierButton.setStyle("-fx-background-color: #3CB371; -fx-font-size: 14px; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        modifierButton.setOnAction(event -> navigateToModification(article));

        // Bouton Supprimer
        Button supprimerButton = new Button("Supprimer");
        supprimerButton.setStyle("-fx-background-color: #FF6347; -fx-font-size: 14px; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 30px;");
        supprimerButton.setOnAction(event -> supprimerArticle(article));

        // Ajout des boutons à la VBox des boutons
        buttonsVBox.getChildren().addAll(modifierButton, supprimerButton);


        ImageView imageView = null;
        if (article.getPhoto_article() != null && !article.getPhoto_article().isEmpty()) {
            imagePath = article.getPhoto_article().replace("\\", "/");
            if (!imagePath.startsWith("http://")) {
                imagePath = "http://localhost/img/" + imagePath;
            }

            Image image = new Image(imagePath);
            String finalImagePath = imagePath;
            image.errorProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    System.out.println("Erreur de chargement de l'image : " + finalImagePath);
                }
            });
            imageView = new ImageView(image);
            imageView.setFitWidth(140);
            imageView.setFitHeight(140);
        }

        // Ajout des éléments à la HBox de la carte
        if (imageView != null) {
            card.getChildren().addAll(imageView, dataVBox, buttonsVBox);
        } else {
            card.getChildren().addAll(dataVBox, buttonsVBox);
        }

        // Ajouter une marge à la carte pour créer un espace entre les articles
        card.setMargin(card, new Insets(10, 10, 10, 10));

        return card;
    }




    private void navigateToModification(Article article) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/ModifierArticle.fxml"));
            Pane root = loader.load();

            ModifierArticleController modifierArticleController = loader.getController();
            modifierArticleController.setArticle(article);
            modifierArticleController.setModificationListener(this);

            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(root);

            Dialog<Void> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.show();
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la page de modification : " + e.getMessage());
        }
    }

    // Méthode pour supprimer un article
    private void supprimerArticle(Article article) {
        try {
            serviceArticle.supprimerArticle(article.getId_Article());
            rechercherArticles(""); // Recharger les articles après la suppression
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
        rechercherArticles("");
    }
}
