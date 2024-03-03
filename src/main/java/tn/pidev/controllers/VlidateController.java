package tn.pidev.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.pidev.entities.Administrateur;
import tn.pidev.services.ServiceAdmin;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class VlidateController {
    ServiceAdmin sa = new ServiceAdmin();
    Administrateur admin = new Administrateur();
    @FXML
    private TextField tel;
    @FXML
    private Label instructionLabel;
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
                    tel.setStyle("-fx-border-color: red;");
                    instructionLabel.setVisible(true);
                } else {
                    tel.setStyle("");
                    instructionLabel.setVisible(false);
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
            clip.setCenterX(profileImageView.getFitWidth() / 2);
            clip.setCenterY(profileImageView.getFitHeight() / 2);
            clip.setRadius(profileImageView.getFitWidth() / 2);
            profileImageView.setClip(clip);
            profileImageView.setPreserveRatio(true);
            selectedImageFile = selectedFile; // Initialisation de selectedImageFile avec le fichier sélectionné
            cheminPhotoProfile = selectedFile.getAbsolutePath();
        }
    }

    @FXML
    public void ToValidate(ActionEvent actionEvent) throws SQLException {
        if (!cheminPhotoProfile.isEmpty() && selectedImageFile != null && adminId != 0) {
            try {
                Administrateur administrateur = sa.getAdministrateur(adminId);
                administrateur.setImage_personne(cheminPhotoProfile);
                administrateur.setNumero_telephone(Integer.parseInt(tel.getText()));
                System.out.println(adminId);

                sa.modifier(administrateur);

                System.out.println(sa.getAdministrateur(adminId));
                System.out.println("Administrateur modifié avec succès !");
                navigateToPage("Acceuil.fxml");
            } catch (SQLException e) {
                // Gérer les erreurs de modification
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

