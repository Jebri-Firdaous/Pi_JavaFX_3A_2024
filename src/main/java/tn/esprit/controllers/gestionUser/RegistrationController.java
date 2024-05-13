package tn.esprit.controllers.gestionUser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceAdmin;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


public class RegistrationController {
    public ServiceAdmin sa = new ServiceAdmin();
    public ServiceUser serviceUser = new ServiceUser();


    @FXML
    private ComboBox<String> role;

    @FXML
    private TextField mail;

    @FXML
    private TextField mdp;



    @FXML
    private Button creercpt;
    @FXML
    private Label roleInvalid;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;
    @FXML
    private AnchorPane background;
    @FXML
    private Button button;
    @FXML
    private Connection connection;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView backgroundImage;
    @FXML
    private Label nomInvalid;
    @FXML
    private Label mailInvalid;

    @FXML
    private Label prenomInvalid;
    @FXML
    private Label mdpInvalid;
    @FXML
    private Label telInvalid;

    public void initialize() {
        nom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isValidName(newValue)) {
                    nom.setStyle("-fx-border-color: red; -fx-border-width: 2px"); // Mettre en surbrillance en rouge si le format est incorrect
                    nomInvalid.setVisible(true);
                } else {
                    nom.setStyle("");
                    nomInvalid.setVisible(false);

                }
            }
        });
        prenom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isValidName(newValue)) {
                    prenom.setStyle("-fx-border-color: red; -fx-border-width: 2px"); // Mettre en surbrillance en rouge si le format est incorrect
                    prenomInvalid.setVisible(true);
                } else {
                    prenom.setStyle("");
                    prenomInvalid.setVisible(false);

                }
            }
        });

        mail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isValidEmail(newValue)) {
                    mail.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    mailInvalid.setVisible(true);
                } else {
                    mail.setStyle("");
                    mailInvalid.setVisible(false);
                }
            }
        });
        mdp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!isValidPassword(newValue)) {
                    mdp.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    mdpInvalid.setVisible(true);

                } else {
                    mdp.setStyle("");
                    mdpInvalid.setVisible(false);
                }
            }
        });
        role.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    role.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    roleInvalid.setVisible(true);
                } else {
                    role.setStyle("");
                    roleInvalid.setVisible(false);
                }
            }
        });
    }

    private boolean isValidPassword(String password) {
        // Au moins un caractère spécial, une majuscule, un chiffre, et au moins 8 caractères au total
        return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidRole(String role) {
        return role != null && !role.isEmpty();
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
        return (domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo") || domain.equals("esprit")) &&
                (extension.equals("com") || extension.equals("tn"));
    }

    @FXML
    void ToConnexion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/pageConnexion.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) mail.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (
                IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }


    public void ToAfficherListeAdmine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
            Parent root = loader.load();
            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) mail.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }


    @FXML
    void AjouterAdmin(ActionEvent event) {


        String roleSelected = role.getValue();
        String nomSaisi = nom.getText();
        String prenomSaisi = prenom.getText();
        String mailSaisi = mail.getText();
        String mdpSaisi = mdp.getText();


        if (!nomSaisi.isEmpty() && !prenomSaisi.isEmpty() && !mailSaisi.isEmpty() && !mdpSaisi.isEmpty() && !roleSelected.isEmpty()) {
            if (isValidName(nomSaisi) && isValidName(prenomSaisi) && isValidEmail(mailSaisi) && isValidPassword(mdpSaisi) && isValidRole(roleSelected)) {
                try {
                    User newUser = new User(nomSaisi, prenomSaisi, 0, mailSaisi, mdpSaisi, "", null, 0, "[\"ADMIN\"]", roleSelected, false, false);

                    serviceUser.ajouterAdmin(newUser);
                    System.out.println(newUser);
                    int adminId = serviceUser.getAdminId(nomSaisi,prenomSaisi, mailSaisi,mdpSaisi,"[\"ADMIN\"]");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setContentText("Compte ajoutée avec succes!");
                    alert.showAndWait();
                    ToValidate(adminId);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez vérifier les champs saisis.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }


    }


    public void ToValidate(int adminId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/ValidateProfile.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur de la page de validation du compte
            VlidateController validateController = loader.getController();
            validateController.setAdminId(adminId);

            Scene pageScene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) nom.getScene().getWindow();
            stage.setScene(pageScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la page ");
            e.printStackTrace();
        }
    }

}
