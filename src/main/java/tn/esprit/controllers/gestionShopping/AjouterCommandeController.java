package tn.esprit.controllers.gestionShopping;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Duration;


import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.util.converter.IntegerStringConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.gestionShopping.Article;
import tn.esprit.entities.gestionShopping.Commande;
import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionShopping.ServiceArticle;
import tn.esprit.services.gestionShopping.ServiceCommande;
import tn.esprit.services.gestionUserServices.ServiceClient;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.util.function.UnaryOperator;


public class AjouterCommandeController {

    @FXML
    private ComboBox<String> clientComboBox;

    @FXML
    public Button ajouterCommandeButton;
    @FXML
    public TextField nomCarteTextField;
    @FXML
    public Button paiementButton;
    @FXML
    public TextField numeroCarteTextField;
    public Button validerPaiementButton;
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
    List<Client> clients = new ArrayList<>();







    @FXML
    public void initialize() {

        try {
            List<User> clients = new ServiceUser().listClient(); // Crée une instance de ServiceUser et appelle afficherClient()
            ObservableList<String> observableClientNames = FXCollections.observableArrayList();
            for (User client : clients) {
                String clientInfo = client.getId() + " - " + client.getNom_personne() + " - " + client.getPrenom_personne();
                observableClientNames.add(clientInfo);
            }
            clientComboBox.setItems(observableClientNames);
            clientComboBox.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Impossible de charger les clients : " + e.getMessage());
        }

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

        Commande commande = null;
        try {
            // Vérifier si aucun client n'a été sélectionné

            if (clientComboBox.getSelectionModel().isEmpty()) {

                afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un client.");

                return;

            }

// Obtenir l'ID du client sélectionné

            String selectedClientInfo = clientComboBox.getSelectionModel().getSelectedItem();

            int clientId = Integer.parseInt(selectedClientInfo.split(" - ")[0]);

            int nombreArticle = selectedArticles.size();

            Date delaisCommande = Date.from(LocalDate.now().plusDays(2).atStartOfDay(ZoneId.systemDefault()).toInstant());
            // Créer une nouvelle commande avec les articles sélectionnés
            commande = new Commande();
            commande.setArticles(selectedArticles);
            commande.setNombre_Article(nombreArticle);
            commande.setPrix_Totale(prixTotal);
            commande.setDelais_Commande(delaisCommande);
            commande.setId_Personne(clientId);

            // Obtenez un token de carte de crédit (ici, nous utilisons un token fictif pour les tests)
            String token = "tok_visa"; // Utilisez le token de carte de crédit fourni par Stripe

            // Traiter le paiement en ligne (exemple avec Stripe)
            boolean paiementEffectue = processPayment(prixTotal, token);
            if (!paiementEffectue) {
                afficherAlerte(Alert.AlertType.ERROR, "Erreur de paiement", "Le paiement en ligne n'a pas pu être effectué. Veuillez réessayer.");
                return;
            }

            // Ajouter la commande à la base de données
            serviceCommande.ajouterCommande(commande);
            // Afficher une notification en bas à gauche de l'écran pendant 5 secondes pour indiquer que la commande a été ajoutée avec succès
            Notifications.create()
                    .title("E-City")
                    .text("Une commande a été ajoutée !")
                    .position(Pos.BOTTOM_RIGHT)
                    .hideAfter(Duration.seconds(3))
                    .show();

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





    @FXML
    void afficherChampsPaiement(javafx.event.ActionEvent event) {
        nomCarteTextField.setVisible(true);
        numeroCarteTextField.setVisible(true);
        validerPaiementButton.setVisible(true);
    }

    public boolean processPayment(double amount, String token) {
        // Configuration de la clé secrète de l'API Stripe
        Stripe.apiKey = "sk_test_51OpYQx2NiuGfqOunOniuM3aEnS7CHNRtCYLb2VZv4clC6ze1b6i1VXBAAlDUUpUZ5rrKqjwthZhFtleAjP8vllzp001ZtxoYFJ";

        try {
            // Création de la charge
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (amount * 100)) // Montant en centimes
                            .setCurrency("usd") // Devise
                            .setSource(token) // Token de carte de crédit
                            .build()
            );

            // Le paiement a réussi
            return true;
        } catch (StripeException e) {
            e.printStackTrace();
            // Le paiement a échoué
            return false;
        }
    }

    @FXML
    public void validerPaiement() {
        String nomCarte = nomCarteTextField.getText();
        String numeroCarte = numeroCarteTextField.getText();

        // Logique pour effectuer le paiement
        // Afficher une alerte de paiement réussi
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Paiement effectué");
        alert.setHeaderText(null);
        alert.setContentText("Paiement effectué avec succès.");
        alert.showAndWait();
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }
}