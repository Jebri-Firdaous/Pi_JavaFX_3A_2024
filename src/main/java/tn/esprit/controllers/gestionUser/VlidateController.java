package tn.esprit.controllers.gestionUser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import tn.esprit.entities.gestionUserEntities.Administrateur;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceAdmin;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class VlidateController {
    ServiceAdmin sa = new ServiceAdmin();
    ServiceUser serviceUser = new ServiceUser();
    Administrateur admin = new Administrateur();
    @FXML
    private TextField tel;

    @FXML
    private Label telInvalid;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView profileImageView;
    private File selectedImageFile;
    private String cheminPhotoProfile = "";
    private int adminId;

    public void initialize() {
        tel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,8}")) {
                    tel.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    telInvalid.setVisible(true);
                } else {
                    tel.setStyle("");
                    telInvalid.setVisible(false);
                }
            }
        });

    }

    @FXML
    void uploadProfilePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo de profil");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(image);
            Circle clip = new Circle();
            double radius = 150;
            clip.setCenterX(radius); // Centre X du cercle, adapté à votre mise en page
            clip.setCenterY(radius); // Centre Y du cercle, adapté à votre mise en page
            clip.setRadius(radius);
            profileImageView.setClip(clip);
            profileImageView.setPreserveRatio(true);

            // Position de l'image dans votre mise en page
            double layoutX = 100;
            double layoutY = 25;
            profileImageView.setLayoutX(layoutX);
            profileImageView.setLayoutY(layoutY);

            selectedImageFile = selectedFile;
            cheminPhotoProfile = selectedFile.getAbsolutePath();
        }
    }

    @FXML
    public void ToValidate(ActionEvent actionEvent) throws SQLException {
        if (!cheminPhotoProfile.isEmpty() && selectedImageFile != null && adminId != 0) {
            User administrateur = serviceUser.getOneById(adminId);
            System.out.println(administrateur);

// Update the properties of the administrateur object
            administrateur.setImage_personne(cheminPhotoProfile);
            administrateur.setNumero_telephone(Integer.parseInt(tel.getText()));

            try {
                // Call the modifierAdmin method to update the user in the database
                serviceUser.modifierAdmin(administrateur);
                System.out.println(administrateur);

                // Display success notification
                Image icon = new Image(getClass().getResourceAsStream("/gestionUserRessources/ticke_icon.png"));
                ImageView imageView = new ImageView(icon);
                imageView.setFitWidth(50); // Set the desired width of the icon
                imageView.setFitHeight(50); // Set the desired height of the icon

                Notifications.create()
                        .graphic(imageView) // Set the notification icon
                        .title("Notification")
                        .text("Validation de compte avec succès !")
                        .hideAfter(Duration.seconds(5)) // Hide the notification after 5 seconds
                        .position(Pos.BOTTOM_RIGHT) // Position the notification at the bottom right of the screen
                        .owner(rootPane) // Specify the parent container of the notification
                        .show();

                System.out.println("Administrateur modifié avec succès !");
                navigateToPage("gestionUserRessources/pageConnexion.fxml");
            } catch (SQLException e) {
                // Handle errors that occur during the modification process
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Erreur lors de la modification de l'administrateur");
                alert.setContentText("Une erreur s'est produite lors de la modification de l'administrateur. Veuillez réessayer.");
                alert.showAndWait();
            }
        } else {
            // Afficher une alerte si les conditions ne sont pas remplies
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez sélectionner une image de profil et entrer un numéro de téléphone.");
            alert.showAndWait();
        }


    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    private void navigateToPage(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFileName));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) tel.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page : " + fxmlFileName);
            e.printStackTrace();
        }
    }


}

