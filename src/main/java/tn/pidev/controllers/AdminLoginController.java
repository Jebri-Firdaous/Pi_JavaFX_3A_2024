package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.pidev.entities.Administrateur;
import tn.pidev.entities.Personne;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminLoginController {
    @FXML
    private Label label;
    @FXML
    private Label mailInvalide;

    @FXML
    private TextField mdp;


    @FXML
    private TextField email;

    @FXML
    private Label mdpInvalide;



    public void reinitialisermdp(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/confirmermail.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }

    }

    public void ToaddPage(ActionEvent actionEvent) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/creerCompte.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (
                IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }
    private boolean isValidEmail(String email) {
        // Diviser l'e-mail en parties distinctes
        String[] parts = email.split("@");
        if (parts.length != 2) {
            return false; // L'e-mail doit contenir exactement un symbole '@'
        }

        // Vérifier la partie locale de l'e-mail
        String localPart = parts[0];
        if (localPart.isEmpty()) {
            return false; // La partie locale ne peut pas être vide
        }

        // Vérifier le domaine de l'e-mail
        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2) {
            return false; // Le domaine doit contenir exactement un point séparant le nom de domaine et l'extension
        }

        // Vérifier le domaine et l'extension
        String domain = domainParts[0];
        String extension = domainParts[1];
        return (domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo")|| domain.equals("esprit")) &&
                (extension.equals("com") || extension.equals("tn"));
    }
    private boolean checkAdminExistence(String email) {
        // Requête SQL pour vérifier si l'e-mail existe dans la table des administrateurs
        String sql = "SELECT COUNT(*) FROM administrateur WHERE mail_personne = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-city", "root", "");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private boolean checkAdminPassword(String email, String password) {
        // Requête SQL pour récupérer l'administrateur à partir de l'e-mail
        String sql = "SELECT * FROM administrateur WHERE mail_personne = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-city", "root", "");
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer les informations de l'administrateur depuis le ResultSet
                    int id_personne = resultSet.getInt("id_personne");
                    String nom_personne = resultSet.getString("nom_personne");
                    String prenom_personne = resultSet.getString("prenom_personne");
                    int numero_telephone = resultSet.getInt("numero_telephone");
                    String mail_personne = resultSet.getString("mail_personne");
                    String mdp_personne = resultSet.getString("mdp_personne");

                    // Créer un objet Personne avec les données récupérées
                    Personne admin = new Personne(id_personne, nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);

                    // Comparer le mot de passe fourni avec celui de l'administrateur
                    return password.equals(admin.getMdp_personne());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    private void redirectToAdminDashboard(String fxmlFileName) {
        try {
            // Charger le fichier FXML du tableau de bord de l'interface administrateur
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Récupérer la scène actuelle et définir la nouvelle scène
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : " + fxmlFileName);
            e.printStackTrace();
        }
    }
    public void ToFirdaousCpt(ActionEvent actionEvent) {
        redirectToAdminDashboard("FirdaousCpt.fxml");

    }
    public void login(ActionEvent actionEvent) {
        String emailText = email.getText();
        String passwordText = mdp.getText();

        if (!email.getText().isEmpty() && !mdp.getText().isEmpty()) {
            // Vérifier si l'e-mail est valide
            if (!isValidEmail(emailText)) {
                // Afficher un message d'erreur sur le format de l'e-mail
                mailInvalide.setVisible(true);
            } else {
                // L'e-mail est valide, masquer le message d'erreur
                mailInvalide.setVisible(false);

                // Vérifier si le mail et le mot de passe existent et correspondent
                if (checkAdminPassword(emailText, passwordText)) {
                    // Rediriger vers la page d'accueil si l'authentification est réussie
                    redirectToAdminDashboard("/Accueil.fxml");
                } else {
                    // Afficher un message d'erreur si l'authentification échoue
                    Alert alert = new Alert(Alert.AlertType.ERROR, "L'e-mail ou le mot de passe est incorrect.");
                    alert.show();
                }
            }
        } else {
            // Afficher un message d'erreur si l'un des champs est vide
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs.");
            alert.show();
        }

        }

    public void goBack(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML de la page précédente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pageConnexion.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la page précédente
            Scene previousScene = new Scene(root);

            // Obtenir la scène actuelle et changer son contenu
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(previousScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement de la page précédente
        }
    }

    public void ToAcceuil(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Acceuil.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }
}