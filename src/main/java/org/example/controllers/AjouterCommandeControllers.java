package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.example.entites.Article;
import org.example.entites.Commande;
import org.example.services.ServiceArticle;
import org.example.services.ServiceCommande;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterCommandeControllers {

    public Button ajouterCommandeButton;
    @FXML
    private ComboBox<String> articleComboBox;

    @FXML
    private Label nombreArticleLabel;

    @FXML
    private Label prixTotalLabel;

    @FXML
    private Label delaisCommandeLabel;

    private final ServiceCommande serviceCommande = new ServiceCommande();
    private final ServiceArticle serviceArticle = new ServiceArticle();

    private double prixTotal = 0.0;
    private final List<Article> selectedArticles = new ArrayList<>();

    @FXML
    public void initialize() {
        // Charger les articles depuis la base de données
        try {
            List<Article> articles = serviceArticle.afficherArticle();
            ObservableList<String> observableArticleNames = FXCollections.observableArrayList();
            for (Article article : articles) {
                String articleInfo = article.getId_Article() + " - " + article.getNom_Article() + " - " + article.getPrix_Article();
                observableArticleNames.add(articleInfo);
            }
            articleComboBox.setItems(observableArticleNames);
            articleComboBox.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les articles : " + e.getMessage());
        }

        // Définir le délai de commande à deux jours à partir d'aujourd'hui
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
        String delaisCommande = dateFormat.format(date);
        delaisCommandeLabel.setText(" " + delaisCommande);
    }

    @FXML
    void updateTotalPrice(javafx.event.ActionEvent event) {
        String selectedArticleInfo = articleComboBox.getSelectionModel().getSelectedItem();
        if (selectedArticleInfo != null) {
            String[] parts = selectedArticleInfo.split(" - ");
            int articleId = Integer.parseInt(parts[0]);
            String articleName = parts[1];
            double articlePrice = Double.parseDouble(parts[2]);
            Article selectedArticle = new Article();
            selectedArticle.setId_Article(articleId);
            selectedArticle.setNom_Article(articleName);
            selectedArticle.setPrix_Article(articlePrice);

            if (!selectedArticles.contains(selectedArticle)) {
                selectedArticles.add(selectedArticle);
                nombreArticleLabel.setText("" + selectedArticles.size());
                prixTotal += articlePrice;
                prixTotalLabel.setText(" " + prixTotal);
            }
        }
    }

    @FXML
    void ajouterCommande(javafx.event.ActionEvent event) {
        try {
            if (selectedArticles.isEmpty()) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner au moins un article.");
                return;
            }

            int nombreArticle = selectedArticles.size();
            Date delaisCommande = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());

            // Créer une nouvelle commande avec les articles sélectionnés
            Commande commande = new Commande();
            commande.setArticles(selectedArticles);
            commande.setNombre_Article(nombreArticle);
            commande.setPrix_Totale(prixTotal);
            commande.setDelais_Commande(delaisCommande);

            // Ajouter la commande à la base de données
            serviceCommande.ajouterCommande(commande);

            // Effacer la sélection après l'ajout de la commande
            selectedArticles.clear();
            nombreArticleLabel.setText("Nombre d'articles sélectionnés : 0");
            prixTotal = 0.0;
            prixTotalLabel.setText("Prix total : 0.0");

            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "La commande a été ajoutée avec succès.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'ajout de la commande : " + e.getMessage());
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
