package tn.esprit.controllers.gestionUser;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.gestionUserEntities.Administrateur;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.PasswordEncryption;
import tn.esprit.services.gestionUserServices.ServiceAdmin;
import tn.esprit.services.gestionUserServices.ServiceUser;
import tn.esprit.services.gestionUserServices.Session;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.*;
import java.util.Random;


public class LoginController {
    /*************************** Partie déclaration *************************************************/
    private final String url = "jdbc:mysql://localhost:3306/e_city_final";
    private final String user = "root";
    private final String password = "";
    private String resetCode;
    @FXML
    private Label label;
    ServiceUser serviceUser = new ServiceUser();
    User Administrateur = new User();
    @FXML
    private TextField mdp;
    @FXML
    private TextField email;
    Boolean loginBlocked = false;

    private int loginAttempts = 0;

    /**
     * I-******************************* Logique de login + ban
     ***************************************/
    //-1- logique de login et ban:------------------------------------------------------------------/

    public void login(ActionEvent actionEvent) {

        String emailText = email.getText();
        String passwordText = mdp.getText();
        String hashedPassword = PasswordEncryption.hashPassword(passwordText);


        int authentificated = serviceUser.authentication(emailText, passwordText);
        System.out.println(authentificated);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Session sessionAdmin = Session.getInstance();

        if (authentificated > 0) {
            User currentUser = serviceUser.getOneById(authentificated);
            sessionAdmin.setCurrentUser(currentUser);
            // User authenticated successfully
            if (sessionAdmin.getCurrentUser() != null) {
                System.out.println("Session works: Current user set successfully.");
                // User authenticated successfully
                navigateTo("gestionUserRessources/Acceuil.fxml");
            } else {
                System.out.println("Session does not work: Current user not set.");
            }
        } else if (authentificated == -1) {
            System.out.println("Simulating authentication failure: Incorrect email or password.");

            loginAttempts++;
            if (loginAttempts >= 5) {
                loginBlocked = true;
                disableFieldsTemporarily();
                alert.setHeaderText(null);
                alert.setContentText("Vou ete banner. Veuillez réessayer dans 20 secondes.");
                alert.showAndWait();
            } else if (loginAttempts == 3) {
                // Afficher une alerte après trois tentatives infructueuses
                alert.setHeaderText(null);
                alert.setContentText("Tentative de connexion échouée. Vous avez encore deux tentatives.");
                alert.showAndWait();
            } else {
                alert.setHeaderText(null);
                alert.setContentText("Mot de passe ou mail incorrcte");
                alert.showAndWait();
            }
        } else if (authentificated == -2) {
            System.out.println("Simulating authentication failure: Not an administrator.");

            alert.setHeaderText(null);
            alert.setContentText("Vous n'ete pas un Administrateur");
            alert.showAndWait();
        } else {
            System.out.println("Simulating other authentication failure scenarios.");

            alert.setHeaderText(null);
            alert.setContentText("Vous ete banné");
            alert.showAndWait();
        }
    }


    //-2- bloquer les champs pendant 20 minutes----------------------------------------------------------/
    private void disableFieldsTemporarily() {
        // Désactiver les champs
        email.setDisable(true);
        mdp.setDisable(true);

        // Définir un délai de 20 secondes
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(20), event -> {
            // Réactiver les champs après le délai
            email.setDisable(false);
            mdp.setDisable(false);

            // Réinitialiser le compteur de tentatives et le statut de blocage
            loginAttempts = 0;
            loginBlocked = false;

        }));
        timeline.play();
    }


    /**
     * II-****************************** Rééinitialiser mot de passe
     ***************************************/
    String emailRecupered;

    //-1-verification d'existance de mail dans la base:--------------------------------------------------/
    private boolean emailExistsInDatabase(String mail) {
        // Déclaration d'une variable pour stocker le résultat de la vérification
        boolean emailExists = false;

        // Connexion à la base de données (vous devrez configurer cela en fonction de votre configuration de base de données)
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Préparation de la requête SQL
            String sql = "SELECT COUNT(*) FROM user WHERE email= ?  AND JSON_CONTAINS(roles, '[\"ADMIN\"]')";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                // Remplacement du paramètre dans la requête SQL par l'e-mail fourni
                statement.setString(1, mail);

                // Exécution de la requête SQL et récupération du résultat
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Si la requête renvoie au moins une ligne, cela signifie que l'e-mail existe
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        emailExists = count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            // Gestion des erreurs de base de données
            e.printStackTrace();
        }
        // Retourne true si l'e-mail existe dans la base de données, sinon false
        return emailExists;
    }


//-2-generation de code aléatoire:----------------------------------------------------------------------/

    private String generateRandomCode() {
        // Générer un code aléatoire de 6 chiffres
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }


//-3-processus d'envoi de mail de recupération mdp avec le code aléatoire:----------------------------------/

    public String reinitialiserMDP(ActionEvent actionEvent) {
        AdminLoginController adminLoginController = new AdminLoginController();
        // Vérifier si l'e-mail existe dans la base de données
        if (emailExistsInDatabase(email.getText())) {
            // Générer un code de réinitialisation aléatoire
            String code = generateRandomCode();


            // Envoyer l'e-mail avec le code de réinitialisation
            boolean emailSent = sendResetPasswordEmail(email.getText(), code);

            if (emailSent) {
                showAlertsuccce("Succès", "Réinitialisation du mot de passe",
                        "Un e-mail contenant un Code " + code + " de réinitialisation du mot de passe a été envoyé.");
                navigateToSaisirCode();
            } else {
                showAlertError("Erreur", "Erreur d'envoi d'e-mail",
                        "Une erreur s'est produite lors de l'envoi de l'e-mail. Veuillez réessayer plus tard.");
            }
        } else {
            // L'e-mail n'existe pas dans la base de données
            showAlertError("Erreur", "E-mail non trouvé",
                    "L'adresse e-mail spécifiée n'existe pas\n ou vous n'est pas un admin.");
        }
        System.out.println("im in reinitialiser psw");
        System.out.println(email.getText());
        emailRecupered = email.getText();
        return email.getText();
    }

    // Email envoyé:----------------------------------------------------------------------------------------/
    private boolean sendResetPasswordEmail(String email, String code) {
        try {
            String NomPrenom = serviceUser.getnombyEmail(email);
            System.out.println(NomPrenom);

            // Ici, vous devez implémenter le code pour envoyer l'e-mail de réinitialisation
            // Utilisez l'adresse ecity.tunis2000@gmail.com comme expéditeur
            // Le corps de l'e-mail doit contenir le code de réinitialisation
            // Utilisez une bibliothèque JavaMail ou une autre API de mailing pour envoyer l'e-mail
            resetCode = code;
            // Exemple avec JavaMail
            String subject = "Réinitialisation du mot de passe";
            String body = "Cher/chère " + NomPrenom + ",\n\n"
                    + "Nous vous informons que votre demande de réinitialisation de mot de passe a été traitée avec succès.\n\n"
                    + "Veuillez trouver ci-dessous votre code de réinitialisation :\n\n"
                    + code + "\n\n"
                    + "Veuillez utiliser ce code pour réinitialiser votre mot de passe. Si vous n'avez pas demandé cette réinitialisation, veuillez contacter notre équipe de support dès que possible.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe de E-city";
            EmailSender.sendEmail(email, subject, body); // Utilisez votre méthode d'envoi d'e-mail
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;

        }
    }

    //-4- naviguer vers la verification de code envoyé:--------------------------------------------------/
    private void initialiseEmailValue(String mail) {
        emailRecupered = mail;
    }

    public void initialiseresetCode(String code) {
        resetCode = code;
    }

    private void navigateToSaisirCode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/SaisieCode.fxml"));
            Parent root = loader.load();
            LoginController adminLoginController = loader.getController();
            adminLoginController.initialiseEmailValue(email.getText());
            adminLoginController.initialiseresetCode(resetCode);
            System.out.println(resetCode);
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : ");
            e.printStackTrace();
        }
    }

    @FXML
    private TextField code;

    //-5- Verification de code saisie avec le code envoyé:-----------------------------------------------/
    public void confirmercode(ActionEvent event) {
        System.out.println(resetCode);
        if (code.getText().equals(resetCode)) {
            System.out.println("code valide");
            navigateToSaisirMotDePasse();
        } else {
            System.out.println("code valide");
            showAlertError("Erreur", "Code invalide",
                    "Saisisser le code envoyé par mail");
        }
    }


    private void showAlertsuccce(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlertError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void confirmermail(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/confirmermail.fxml"));
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

    private void navigateTo(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = loader.load();

            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : " + fxmlFileName);
            e.printStackTrace();
        }

    }

    private void navigateToSaisirMotDePasse() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/modifierMdp.fxml"));
            Parent root = loader.load();
            LoginController adminLoginController = loader.getController();
            adminLoginController.initialiseEmailValue(emailRecupered);
            System.out.println("hello am here " + emailRecupered);
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) label.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : ");
            e.printStackTrace();
        }
    }

    @FXML
    void annuler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
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

    public void ToaddPage(ActionEvent event) {
        navigateTo("gestionUserRessources/creerCompte.fxml");
    }


    @FXML
    private PasswordField pwd1;

    @FXML
    private PasswordField pwd2;

    public void modifiermotdePasse(ActionEvent event) throws SQLException {
        String password1 = pwd1.getText();
        String password2 = pwd2.getText();
        if (!password1.isEmpty() && !password2.isEmpty() && password1.equals(password2)) {
            User userAdmin = serviceUser.getOneByEmail(emailRecupered);
            userAdmin.setMdp_personne(password1);
            serviceUser.modifierAdmin(userAdmin);
            boolean emailSent = compte_récuperer(emailRecupered);
            navigateTo("gestionUserRessources/pageConnexion.fxml");

        } else {
            System.out.println("doesnt much");
            showAlertError("error", "", "Veillez saisir le meme mot de passe");
        }
    }

    private boolean compte_récuperer(String email) {
        try {
            String NomPrenom = serviceUser.getnombyEmail(email);


            String subject = "Récupération de Compte Réussie";
            String body = "Cher/chère " + NomPrenom + "\n\n"
                    + "Nous sommes heureux de vous informer que la récupération de votre compte a été effectuée avec succès. Votre accès à E-city est désormais rétabli.\n\n"
                    + "Si vous avez des questions ou avez besoin d'une assistance supplémentaire, n'hésitez pas à nous contacter à tout moment. Nous sommes là pour vous aider.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe E-city";
            EmailSender.sendEmail(email, subject, body); // Utilisez votre méthode d'envoi d'e-mail
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;

        }
    }
    public void ToConnexion(ActionEvent event) {
        navigateTo("gestionUserRessources/pageConnexion.fxml");
    }
}