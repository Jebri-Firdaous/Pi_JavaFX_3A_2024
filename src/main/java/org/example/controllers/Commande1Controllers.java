package org.example.controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.entites.Article;
import org.example.entites.Commande;
import org.example.services.ServiceCommande;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class Commande1Controllers implements ModificationListener {

    @FXML
    private VBox itemsContainer; // Container pour afficher les éléments (articles et commandes)

    @FXML
    private ComboBox<String> comboBoxTri; // ComboBox pour trier les commandes

    private final ServiceCommande serviceCommande = new ServiceCommande();

    @FXML
    void initialize() {
        // Initialisation de la ComboBox de tri
        comboBoxTri.getItems().addAll("Date de livraison (récent)", "Date de livraison (ancien)");

        afficherCommandes();
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

    @FXML
    void trierCommandes() {
        String triSelectionne = comboBoxTri.getValue();
        if (triSelectionne.equals("Date de livraison (récent)")) {
            // Tri des commandes par date de livraison la plus récente
            afficherCommandesTriees(Comparator.comparing(Commande::getDelais_Commande).reversed());
        } else if (triSelectionne.equals("Date de livraison (ancien)")) {
            // Tri des commandes par date de livraison la moins récente
            afficherCommandesTriees(Comparator.comparing(Commande::getDelais_Commande));
        }
    }

    private void afficherCommandesTriees(Comparator<Commande> comparator) {
        try {
            itemsContainer.getChildren().clear(); // Efface les anciennes commandes pour éviter les duplications
            List<Commande> commandes = serviceCommande.afficherCommande();
            commandes.stream()
                    .sorted(comparator)
                    .forEach(commande -> {
                        VBox commandeBox = createCommandeBox(commande);
                        itemsContainer.getChildren().add(commandeBox);
                    });
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la récupération des commandes : " + e.getMessage());
        }
    }

    private void afficherCommandes() {
        afficherCommandesTriees(Comparator.comparing(Commande::getDelais_Commande).reversed());
    }

    private void genererBonDeCommandePDF(Commande commande) {
        try {
            String fileName = "BonDeCommande_" + commande.getId_Commande() + ".pdf"; // Nom du fichier avec l'ID de commande
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Ajoutez votre contenu PDF ici
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(0, 0, 0); // Couleur du texte : Noir
            contentStream.newLineAtOffset(100, 700);

            contentStream.showText("Référence Commande : " + commande.getId_Commande());
            contentStream.newLineAtOffset(0, -15); // Retour à la ligne

            contentStream.showText("Nombre des articles commandés : " + commande.getNombre_Article());
            contentStream.newLineAtOffset(0, -15); // Retour à la ligne

            contentStream.showText("Prix total de la commande : " + commande.getPrix_Totale() + "DT");
            contentStream.newLineAtOffset(0, -15); // Retour à la ligne

            contentStream.showText("Delais de livraison : " + commande.getDelais_Commande());
            contentStream.newLineAtOffset(0, -15); // Retour à la ligne

            contentStream.showText("Articles :");
            contentStream.newLineAtOffset(0, -15); // Retour à la ligne

            for (Article article : commande.getArticles()) {
                contentStream.showText(" - " + article.getNom_Article() + " : " + article.getPrix_Article());
                contentStream.newLineAtOffset(0, -15); // Retour à la ligne pour chaque article
            }

            contentStream.endText();

            contentStream.close();
            document.save("C:\\Users\\lenovo\\Desktop\\" + fileName); // Chemin du fichier avec le nom spécifique
            document.close();

            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Le bon de commande a été téléchargé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de la génération du bon de commande PDF.");
        }
    }

    private VBox createCommandeBox(Commande commande) {
        VBox commandeBox = new VBox();
        commandeBox.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 12px;");

        // Création du code QR pour la commande
        ImageView qrCodeImageView = generateQRCode("ID de la commande : " + commande.getId_Commande()+"Prix totale : " +commande.getPrix_Totale()+"Delais commande : "+commande.getDelais_Commande()+"Nombre des articles : "+commande.getNombre_Article(), 200, 200);
        qrCodeImageView.setFitWidth(100);
        qrCodeImageView.setFitHeight(100);

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

        Button genererPDFButton = new Button("Télécharger PDF");
        genererPDFButton.setStyle("-fx-background-color: #0059ff; -fx-text-fill: white; -fx-font-size: 14px;");
        genererPDFButton.setOnAction(event -> {
            genererBonDeCommandePDF(commande);
        });
        VBox.setMargin(genererPDFButton, new Insets(10, 0, 0, 0)); // Ajout de marge en haut pour le bouton PDF

        HBox buttonBox = new HBox(modifierButton, deleteButton, genererPDFButton);
        buttonBox.setSpacing(10);

        commandeBox.getChildren().addAll(qrCodeImageView, idLabel, nombreArticleLabel, prixLabel, delaisLabel, separator, articlesLabel, articlesBox, buttonBox);
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
