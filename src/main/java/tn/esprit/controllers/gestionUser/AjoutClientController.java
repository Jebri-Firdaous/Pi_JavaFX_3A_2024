package tn.esprit.controllers.gestionUser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceClient;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;


public class AjoutClientController {
    public ServiceUser sc = new ServiceUser();

    @FXML
    private Label prenomInvalid;

    @FXML
    private TextField mail;

    @FXML
    private Label nomInvalid;

    @FXML
    private Label label;

    @FXML
    private Label roleInvalid;

    @FXML
    private Label mailInvalid;

    @FXML
    private TextField nom;

    @FXML
    private Label telInvalid;

    @FXML
    private Label mdpInvalid;

    @FXML
    private ComboBox<String> genre;

    @FXML
    private PasswordField mdp;

    @FXML
    private Label roleInvalid1;

    @FXML
    private TextField tel;

    @FXML
    private TextField prenom;

    @FXML
    private TextField age;

    @FXML
    private Label remplirChamps;

    @FXML
    private Label ageInvalid;

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
        age.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}")) {
                    age.setStyle("-fx-border-color: red; -fx-border-width: 2px");
                    ageInvalid.setVisible(true);
                } else {
                    age.setStyle("");
                    ageInvalid.setVisible(false);
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

    }

    private boolean isValidPassword(String password) {
        // Au moins un caractère spécial, une majuscule, un chiffre, et au moins 8 caractères au total
        return password.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isvalideAge(String age) {
        return age.matches("\\d{0,3}");
    }

    private boolean isValidNum(String age) {
        return age.matches("\\d{0,8}");
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


    public void ToAfficherListeClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherClient.fxml"));
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
    void AjouterClient(ActionEvent event) {


        String genreSelected = genre.getValue();
        String nomSaisi = nom.getText();
        String prenomSaisi = prenom.getText();
        String mailSaisi = mail.getText();
        String mdpSaisi = mdp.getText();
        String ageSaise = age.getText();
        String telf = tel.getText();
        int numsaisie = Integer.parseInt(tel.getText());
        int ageClient = Integer.parseInt(age.getText());



        if (!nomSaisi.isEmpty() && !prenomSaisi.isEmpty() && !mailSaisi.isEmpty() && !telf.isEmpty() && !ageSaise.isEmpty() && !mdpSaisi.isEmpty() && !genreSelected.isEmpty()) {
            if (isValidName(nomSaisi) && isValidName(prenomSaisi) && isValidEmail(mailSaisi) && isValidPassword(mdpSaisi) && isvalideAge(ageSaise) && isValidNum(telf) && isvalideAge(ageSaise)) {
                try {
                    User newUser = new User(nomSaisi, prenomSaisi,numsaisie, mailSaisi, mdpSaisi, "", genreSelected, ageClient, "[\"CLIENT\"]", null, false, false);

                    sc.ajouterClient(newUser);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setContentText("Compte ajoutée avec succes!");
                    alert.showAndWait();
                    ToAfficherListeClient();
                    nom.setText("");
                    prenom.setText("");
                    mail.setText("");
                    mdp.setText("");
                    tel.setText("");

                    age.setText("");



                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez vérifier les champs saisis.");
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs.");
            alert.show();
        }


    }

    private void ToValidate(int clientId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/ValidateProfile.fxml"));
            Parent root = loader.load();

            // Accéder au contrôleur de la page de validation du compte
            VlidateController validateController = loader.getController();
            validateController.setAdminId(clientId);

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



