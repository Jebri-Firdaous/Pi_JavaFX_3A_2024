    /*---------------------------------------------------- Import -----------------------------------------------------------*/
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
    import javafx.stage.Modality;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import tn.esprit.entities.gestionUserEntities.Administrateur;
    import tn.esprit.services.gestionUserServices.ServiceAdmin;

    import javax.mail.MessagingException;
    import java.io.IOException;
    import java.sql.*;
    import java.util.Random;


    /*------------------------------------------------- Controller  --------------------------------------------------------*/
    public class AdminLoginController {
        private final String url = "jdbc:mysql://localhost:3307/e_city_final";
        private final String user = "root";
        private final String password = "0000";
        public TextField mail;
        ServiceAdmin sa = new ServiceAdmin();
        Administrateur adminConnected = new Administrateur();
        String emailRecupered;
        /*--------------------------------------------------- Attribute ----------------------------------------------------------*/
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
        private String resetCode;
        @FXML
        private PasswordField pwd1;
        @FXML
        private PasswordField pwd2;
        private int adminId;
        private int loginAttempts = 0;
        private boolean loginBlocked = false;
        private String connectedAdminEmail;
        private String connectedAdminPassword;

        private void highlightPasswordFields(boolean highlight) {
            if (highlight) {
                pwd1.setStyle("-fx-border-color: red;");
                pwd2.setStyle("-fx-border-color: red");
            } else {
                pwd2.setStyle("-fx-border-color: transparent;");
                pwd1.setStyle("-fx-border-color: transparent;");
            }
        }

        public void mailrecuperer() {
            emailRecupered = mail.getText();
        }


        @FXML
        void modifiermotdePasse(ActionEvent event) throws SQLException {
            String password1 = pwd1.getText();
            String password2 = pwd2.getText();
            System.out.println(emailRecupered + "I m in modfier psd");
            ServiceAdmin serviceAdmin = new ServiceAdmin();
            if (!password1.isEmpty() && !password2.isEmpty() && password1.equals(password2)) {
                System.out.println(emailRecupered);
                Administrateur administrateur = serviceAdmin.getAdminByEmail(emailRecupered);
                administrateur.setMdp_personne(password1);
                serviceAdmin.modifier(administrateur);
                boolean emailSent = compte_récuperer(emailRecupered);
                navigateTo("gestionUserRessources/pageConnexion.fxml");
            }
        }

        private boolean compte_récuperer(String email) {
            try {
                String NomPrenom = sa.getnombyEmail(email);


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

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        /*---------------------------------------------------------------------------------------------------------------------------*/
        /*--------------------------------------------- Navigate to confirmer mail --------------------------------------------------*/
        public void confirmermail(ActionEvent actionEvent) {
            navigateTo("gestionUserRessources/confirmermail.fxml");


        }

        /*------------------------------------------ Navigate to Creer un Compte page -------------------------------------------*/
        public void ToaddPage(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/creerCompte.fxml"));
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

        /*----------------------------------------------------------------------------------------------------------------------------*/
        /*------------------------- Navigate to Interface dans le meme stage methode general a appelé --------------------------------*/

        /*-----------------------------------------------------------------------------------------------------------------------*/
        /*-------------------------------------------Méthode de controle de saisie-----------------------------------------------*/
//------------>Mail valide :
        //1- L'e-mail doit contenir exactement un symbole '@'
        //2- Vérifier la partie avant '@' de l'e-mail ne peut pas être vide
        //3- Le domaine doit contenir exactement un point séparant le nom de domaine et l'extension
        //4- Vérifier le domaine et l'extension
        private boolean isValidEmail(String email) {
            // 1-
            String[] parts = email.split("@");
            if (parts.length != 2) {
                return false;
            }

            // 2-
            String localPart = parts[0];
            if (localPart.isEmpty()) {
                return false;
            }

            // 3-
            String[] domainParts = parts[1].split("\\.");
            if (domainParts.length != 2) {
                return false;
            }

            // 4-
            String domain = domainParts[0];
            String extension = domainParts[1];
            return (domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo") || domain.equals("esprit")) &&
                    (extension.equals("com") || extension.equals("tn"));
        }

        //---------->vérifier si le mail existe dans la table des administrateurs et si le mot de passe correspond à ce mail
        public boolean verifierConnexion(String email, String motDePasse) {
            String query = "SELECT COUNT(*) FROM Administrateur a JOIN Personne p ON a.id_personne = p.id_personne WHERE p.mail_personne = ? AND p.mdp_personne = ?";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, email);
                stmt.setString(2, motDePasse);

                ResultSet rs = stmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                return count > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        /*-------------------------------------- Action Bouton Connecter qui controle le saisie----------------------------------------*/
        public void login(ActionEvent actionEvent) {
            if (!loginBlocked) { // Vérifier si le login n'est pas bloqué
                String emailText = email.getText();
                String passwordText = mdp.getText();
                boolean connexionReussie = verifierConnexion(emailText, passwordText);
                Alert alert = new Alert(Alert.AlertType.ERROR);

                // Incrémenter le compteur de tentatives
                loginAttempts++;

                // Vérifier le nombre de tentatives
                if (loginAttempts >= 5) {
                    // Bloquer le login et désactiver les champs temporairement
                    loginBlocked = true;
                    disableFieldsTemporarily();
                    alert.setHeaderText(null);
                    alert.setContentText("Trop de tentatives de connexion. Veuillez réessayer dans 20 secondes.");
                    alert.showAndWait();
                    return;
                } else if (loginAttempts == 3) {
                    // Afficher une alerte après trois tentatives infructueuses
                    alert.setHeaderText(null);
                    alert.setContentText("Tentative de connexion échouée. Vous avez encore deux tentatives.");
                    alert.showAndWait();
                }

                // Vérifier les informations de connexion
                if (!emailText.isEmpty() && !passwordText.isEmpty()) {
                    if (connexionReussie) {
                        navigateTo("gestionUserRessources/Acceuil.fxml");
                    } else {
                        alert.setHeaderText(null);
                        alert.setContentText("E-mail ou mot de passe incorrect.");
                        alert.showAndWait();
                    }
                } else {
                    alert.setHeaderText(null);
                    alert.setContentText("Veuillez remplir tous les champs.");
                    alert.showAndWait();
                }
            } else {
                // Afficher un message si le login est bloqué
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Trop de tentatives de connexion. Veuillez réessayer dans 20 secondes.");
                alert.showAndWait();
            }
        }


        /*--------------------- Navigate to Acceuil page optionnel just pour voir l'acceuil --------------------------------------*/
        public void ToAcceuil(ActionEvent actionEvent) {
            navigateTo("gestionUserRessources/Acceuil.fxml");
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

        private void navigateToSaisirCode() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/SaisieCode.fxml"));
                Parent root = loader.load();
                AdminLoginController adminLoginController = loader.getController();
                adminLoginController.initialiseEmailValue(mail.getText());
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

        private void navigateToSaisirMotDePasse() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/modifierMdp.fxml"));
                Parent root = loader.load();
                AdminLoginController adminLoginController = loader.getController();
                System.out.println(emailRecupered);
                adminLoginController.initialiseEmailValue(emailRecupered);
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

        void initialiseEmailValue(String mail) {
            emailRecupered = mail;
        }

        public String reinitialiserMDP(ActionEvent actionEvent) {
            AdminLoginController adminLoginController = new AdminLoginController();
            // Récupérer l'e-mail saisi par l'utilisateur
            String email = mail.getText();


            // Vérifier si l'e-mail existe dans la base de données
            if (emailExistsInDatabase(email)) {
                // Générer un code de réinitialisation aléatoire
                String code = generateRandomCode();


                // Envoyer l'e-mail avec le code de réinitialisation
                boolean emailSent = sendResetPasswordEmail(email, code);

                if (emailSent) {
                    showAlertsuccce("Succès", "Réinitialisation du mot de passe",
                            "Un e-mail contenant un Code de réinitialisation du mot de passe a été envoyé.");
                    navigateToSaisirCode();
                } else {
                    showAlertError("Erreur", "Erreur d'envoi d'e-mail",
                            "Une erreur s'est produite lors de l'envoi de l'e-mail. Veuillez réessayer plus tard.");
                }
            } else {
                // L'e-mail n'existe pas dans la base de données
                showAlertError("Erreur", "E-mail non trouvé",
                        "L'adresse e-mail spécifiée n'existe pas dans la base de données.");
            }
            System.out.println("im in reinitialiser psw");
            System.out.println(mail.getText());
            emailRecupered = mail.getText();
            return email;
        }

        private boolean sendResetPasswordEmail(String email, String code) {
            try {
                String NomPrenom = sa.getnombyEmail(email);
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

        public boolean verifyResetCode(String enteredCode) {
            // Vérifier si le code saisi par l'utilisateur correspond au code de réinitialisation stocké
            return resetCode != null && resetCode.equals(enteredCode);
        }

        private String generateRandomCode() {
            // Générer un code aléatoire de 6 chiffres
            Random random = new Random();
            int code = 100000 + random.nextInt(900000);
            return String.valueOf(code);
        }

        private boolean emailExistsInDatabase(String email) {
            // Déclaration d'une variable pour stocker le résultat de la vérification
            boolean emailExists = false;

            // Connexion à la base de données (vous devrez configurer cela en fonction de votre configuration de base de données)
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/e_city_final", "root", "0000")) {
                // Préparation de la requête SQL
                String sql = "SELECT COUNT(*) FROM Administrateur a JOIN Personne p ON a.id_personne = p.id_personne WHERE p.mail_personne = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    // Remplacement du paramètre dans la requête SQL par l'e-mail fourni
                    statement.setString(1, email);

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

        @FXML
        void codeconfirmer(ActionEvent event) {
            if (!verifyResetCode(resetCode)) {
                navigateToSaisirMotDePasse();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Code Invalid");
                alert.setContentText("Veuiller saisir le code correct");
                alert.showAndWait();

            }

        }

        public void back(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
                Parent root = loader.load();
                AdminLoginController adminLoginController = loader.getController();
                Scene pageScene = new Scene(root);

                // Get the current stage and set the new scene
                Stage stage = (Stage) label.getScene().getWindow();

                // Save reference to the parent stage
                Stage parentStage = new Stage();
                parentStage.initModality(Modality.WINDOW_MODAL);
                parentStage.initOwner(stage);

                stage.setScene(pageScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        public void capturePhoto(ActionEvent event) {
        }

        public void OpenCamera(ActionEvent event) {
        }

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

        @FXML
        void FacialRecognition(ActionEvent event) {

        }

    }
