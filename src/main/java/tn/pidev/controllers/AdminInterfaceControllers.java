    /*---------------------------------------------------- Import -----------------------------------------------------------*/
    package tn.pidev.controllers;

    import javafx.animation.Animation;
    import javafx.animation.TranslateTransition;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.ContextMenu;
    import javafx.scene.control.Label;
    import javafx.scene.control.MenuItem;
    import javafx.scene.image.ImageView;
    import javafx.stage.Stage;
    import javafx.util.Duration;

    import java.io.IOException;

    /*------------------------------------------------- Conntroller  --------------------------------------------------------*/
    public class AdminInterfaceControllers {
        /*--------------------------------------------------- Attribut ----------------------------------------------------------*/

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pageConnexion.fxml"));
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

        @FXML
        public void modifierAdmin(ActionEvent actionEvent) {
            navigateToPage("modifier.fxml");

        }

        @FXML
        void Connecter(ActionEvent event) {
            navigateToPage("pageConnexion.fxml");
        }

        public void reinitialisermdp(ActionEvent actionEvent) {
            navigateToPage("confirmermail.fxml");
        }

        public void AjouterAdmin(ActionEvent actionEvent) {
            navigateToPage("AjouterCompteAdmin.fxml");

        }

        @FXML
        public void Acceuil(ActionEvent actionEvent) {
            navigateToPage("Acceuil.fxml");

        }

        @FXML
        void afficherAdmin() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAdmin.fxml"));
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
            ContextMenu contextMenu = new ContextMenu();
            MenuItem voirProfilItem = new MenuItem("Voir le profil");
            MenuItem parametresItem = new MenuItem("Paramètres");
            contextMenu.getItems().addAll(voirProfilItem, parametresItem);

//        // Associez le menu contextuel à l'ImageView
//        ProfileImage.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.SECONDARY) { // Clic droit
//                contextMenu.show(ProfileImage, event.getScreenX(), event.getScreenY());
//            }
//        });
            // Créer une transition de translation
            TranslateTransition transition = new TranslateTransition(Duration.seconds(15), bienvenue);
            transition.setFromX(1312); // Position de départ en X (hors de l'écran à droite)
            transition.setToX(-1312);
            transition.setCycleCount(Animation.INDEFINITE); // Répéter indéfiniment
// Position finale en X (0)
            transition.play(); // Démarrer la transition

        }


        public void ToConnexion(ActionEvent actionEvent) {
            navigateToPage("pageConnexion.fxml");

        }

        @FXML
        void afficherClient() {

            navigateToPage("afficherClient.fxml");
        }
    }