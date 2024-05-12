    /*---------------------------------------------------- Import -----------------------------------------------------------*/
    package tn.esprit.controllers.gestionUser;

    import javafx.animation.Animation;
    import javafx.animation.TranslateTransition;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Label;
    import javafx.scene.control.MenuItem;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.stage.Modality;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import tn.esprit.services.gestionUserServices.ServiceAdmin;
    import tn.esprit.services.gestionUserServices.ServiceUser;

    import java.io.IOException;

    /*------------------------------------------------- Conntroller  --------------------------------------------------------*/
    public class AdminInterfaceControllers {
        /*--------------------------------------------------- Attribut ----------------------------------------------------------*/

        ServiceUser sa = new ServiceUser();
        @FXML
        private Label label;
        @FXML
        private MenuItem afficherListeArticlesMenuItem;
        @FXML
        private MenuItem action1MenuItem;
        @FXML
        private MenuItem listeUtilisateurMenuItem;
        @FXML
        private ImageView pdp;
        @FXML
        private ImageView ProfileImage;
        @FXML
        private Label bienvenue;
        private ImageView ImageProfile;
        private String connectedAdminEmail;
        private String connectedAdminPassword;
        @FXML
        private ImageView adminImage;

        /*------------------------------------------------------------------------------------------------------------------------*/
        /*------------------------------------Methode generale pour naviguer dans le meme stage-----------------------------------*/
        private void navigateToPage(String fxmlFileName) {
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

        public void ToLogin(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
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

        public void setProfileImage(String imageUrl) {
            ImageProfile.setImage(new Image(imageUrl));
        }

        @FXML
        public void modifierAdmin(ActionEvent actionEvent) {
            navigateToPage("gestionUserRessources/modifier.fxml");

        }

        @FXML
        void Connecter(ActionEvent event) {
            navigateToPage("gestionUserRessources/pageConnexion.fxml");
        }

        public void reinitialisermdp(ActionEvent actionEvent) {
            navigateToPage("gestionUserRessources/confirmermail.fxml");
        }

        public void AjouterAdmin(ActionEvent actionEvent) {
            navigateToPage("gestionUserRessources/AjouterCompteAdmin.fxml");

        }

        @FXML
        public void Acceuil(ActionEvent actionEvent) {
            navigateToPage("gestionUserRessources/Acceuil.fxml");

        }

        @FXML
        void afficherAdmin() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
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

        public void goBack(ActionEvent actionEvent) {
        }

        public void initialize() {

            TranslateTransition transition = new TranslateTransition(Duration.seconds(15), bienvenue);
            transition.setFromX(1312); // Position de départ en X (hors de l'écran à droite)
            transition.setToX(-1312);
            transition.setCycleCount(Animation.INDEFINITE); // Répéter indéfiniment
// Position finale en X (0)
            transition.play();

        }

        public void setAdminImage(String imagePath) {
            Image image = new Image("file:" + imagePath);
            adminImage.setImage(image);
        }

        public void ToConnexion(ActionEvent actionEvent) {
            navigateToPage("gestionUserRessources/pageConnexion.fxml");

        }

        @FXML
        void afficherClient() {

            navigateToPage("gestionUserRessources/afficherClient.fxml");
        }


        public void setConnectedAdminEmailPwd(String email, String pwd) {
            this.connectedAdminEmail = email;
            this.connectedAdminPassword = pwd;
        }

        @FXML
        void disconnect(ActionEvent event) {
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
    }