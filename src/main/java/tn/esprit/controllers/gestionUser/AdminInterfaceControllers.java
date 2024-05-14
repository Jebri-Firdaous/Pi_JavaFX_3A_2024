    /*---------------------------------------------------- Import -----------------------------------------------------------*/
    package tn.esprit.controllers.gestionUser;

    import javafx.animation.Animation;
    import javafx.animation.TranslateTransition;
    import javafx.event.ActionEvent;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.geometry.Side;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.ContextMenu;
    import javafx.scene.control.Label;
    import javafx.scene.control.MenuItem;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.MouseEvent;
    import javafx.stage.Modality;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import tn.esprit.controllers.gestionMedecin.AfficherListRendezVousController;
    import tn.esprit.controllers.gestionMedecin.AfficherMedecinsController;
    import tn.esprit.controllers.gestionTransport.AfficherBilletController;
    import tn.esprit.controllers.gestionTransport.AfficherStationController;
    import tn.esprit.services.gestionUserServices.ServiceAdmin;
    import tn.esprit.services.gestionUserServices.ServiceUser;
    import tn.esprit.services.gestionUserServices.Session;

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
        @FXML
        private ImageView profilePicture;


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
        private Label labeldisconnect;
        @FXML
        private void disconnect() {
            Session session = Session.getInstance();
            session.setCurrentUser(null);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
                Parent root = loader.load();
                LoginController adminLoginController = loader.getController();
                Scene pageScene = new Scene(root);

                // Get the current stage and set the new scene
                Stage stage = (Stage) profilePicture.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void afficherBillet(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherBillet.fxml"));
                Parent newPageRoot = loader.load();
                AfficherBilletController afficherBilletController = loader.getController();

                // Create a new scene with the newPageRoot
                Scene pageScene = new Scene(newPageRoot);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void afficherStation(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionTransport/AfficherStation.fxml"));
                Parent newPageRoot = loader.load();
                AfficherStationController afficherStationController = loader.getController();

                // Create a new scene with the newPageRoot
                Scene pageScene = new Scene(newPageRoot);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        @FXML
        void afficherArticle() {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AfficherArticle.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @FXML
        void ajouterArticle() {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AjouterArticle.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        @FXML
        void ajouterCommande() {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AjouterCommande.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void afficherCommande() {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resourcesGestionShopping/AfficherCommande.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/resourcesGestionShopping/StyleShopping.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void ajouterHotel(ActionEvent actionEvent) {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AjouterHotel.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void afficherHotel(ActionEvent actionEvent) {

            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherHotel.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void AjouterReservation(ActionEvent actionEvent) {

            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AjouterReservation.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void AfficherReservation(ActionEvent actionEvent) {


            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/TourismeResources/AfficherReservation.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void afficherParking(ActionEvent event) {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/AfficherParkingg.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void recupererParking(ActionEvent event) {
            try {
//           for load an FXML file and create a scene graph from it
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParkingResources/RecycleBin.fxml"));
                Parent addPageRoot = loader.load();
                Scene newPageScene = new Scene(addPageRoot);
                newPageScene.getStylesheets().add(getClass().getResource("/TourismeResources/style.css").toExternalForm());

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(newPageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML
        void afficherAdmin() {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
                Parent root = loader.load();
                Scene pageScene = new Scene(root);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            } catch (
                    IOException e) {
                System.err.println("Erreur lors du chargement de la page ");
                e.printStackTrace();
            }
        }

        public void afficherClient(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherClient.fxml"));
                Parent root = loader.load();
                Scene pageScene = new Scene(root);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            } catch (
                    IOException e) {
                System.err.println("Erreur lors du chargement de la page ");
                e.printStackTrace();
            }
        }

        public void afficherMedecin(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AfficherMedecins.fxml"));
                Parent newPageRoot = loader.load();
                AfficherMedecinsController afficherMedecinsController = loader.getController();

                // Create a new scene with the newPageRoot
                Scene pageScene = new Scene(newPageRoot);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void afficherRV(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionMedecin/AfficherListRendezVous.fxml"));
                Parent newPageRoot = loader.load();
                AfficherListRendezVousController afficherListRendezVousController = loader.getController();

                // Create a new scene with the newPageRoot
                Scene pageScene = new Scene(newPageRoot);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }






        @FXML
        private void handleProfilePictureClicked() {
            ContextMenu contextMenu = new ContextMenu();

            MenuItem modifyProfileMenuItem = new MenuItem("Modifier le profil");
            MenuItem Deconnexion = new MenuItem("Deconnexion");

            modifyProfileMenuItem.setOnAction(event -> {
                // Handle the action when "Modifier le profil" is clicked
                // For example:
                Profile();
            });

            Deconnexion.setOnAction(event -> {
                // Handle the action when "Deconnexion" is clicked
                disconnect();
            });


            // Add more menu items if needed

            contextMenu.getItems().addAll(modifyProfileMenuItem);
            contextMenu.getItems().addAll(Deconnexion);


            // Show the context menu at the location of the mouse click
            contextMenu.show(profilePicture, Side.BOTTOM, 0, 0);
        }
        @FXML
        private void Profile() {
            Session session = Session.getInstance();
            session.setCurrentUser(null);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/Profile.fxml"));
                Parent root = loader.load();
                Scene pageScene = new Scene(root);

                // Get the current stage and set the new scene
                Stage stage = (Stage) profilePicture.getScene().getWindow();
                stage.setScene(pageScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private Label Nom_prenom;

        @FXML
        private Label role_administrateur;

        public void ToAcceuil(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/Acceuil.fxml"));
                Parent newPageRoot = loader.load();

                // Create a new scene with the newPageRoot
                Scene pageScene = new Scene(newPageRoot);

                // Get the current stage and set the new scene
                Stage stage = (Stage) labeldisconnect.getScene().getWindow();
                stage.setScene(pageScene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }