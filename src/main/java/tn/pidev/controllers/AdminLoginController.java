    /*---------------------------------------------------- Import -----------------------------------------------------------*/
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

    /*------------------------------------------------- Controller  --------------------------------------------------------*/
public class AdminLoginController {
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
        private final String url = "jdbc:mysql://localhost:3306/e-city";
        private final String user = "root";
        private final String password = "";

/*---------------------------------------------------------------------------------------------------------------------------*/
/*--------------------------------------------- Navigate to confirmer mail --------------------------------------------------*/
    public void confirmermail(ActionEvent actionEvent)
    {
        navigateTo("confirmermail.fxml");


    }
    /*------------------------------------------ Navigate to Creer un Compte page -------------------------------------------*/
    public void ToaddPage(ActionEvent actionEvent) {
        try
        {FXMLLoader loader = new FXMLLoader(getClass().getResource("/creerCompte.fxml"));
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

    /*-----------------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------Méthode de controle de saisie-----------------------------------------------*/
//------------>Mail valide :
    //1- L'e-mail doit contenir exactement un symbole '@'
    //2- Vérifier la partie avant '@' de l'e-mail ne peut pas être vide
    //3- Le domaine doit contenir exactement un point séparant le nom de domaine et l'extension
    //4- Vérifier le domaine et l'extension
    private boolean isValidEmail(String email)
    {
    // 1-
        String[] parts = email.split("@");
        if (parts.length != 2) {return false;}

    // 2-
        String localPart = parts[0];
        if (localPart.isEmpty()) {return false;}

    // 3-
        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2) {return false;}

    // 4-
        String domain = domainParts[0];
        String extension = domainParts[1];
        return (domain.equals("gmail") || domain.equals("hotmail") || domain.equals("yahoo")|| domain.equals("esprit")) &&
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
        String emailText = email.getText();
        String passwordText = mdp.getText();
        boolean connexionReussie = verifierConnexion(emailText, passwordText);
        Alert alert = new Alert(Alert.AlertType.ERROR);


        //1- Champs vide:
        if (!emailText.isEmpty() && !passwordText.isEmpty())
            {
                if (connexionReussie)
                {
                    navigateTo("Acceuil.fxml");
                }
                 else {
                        alert.setHeaderText(null);
                        alert.setContentText("E-mail ou mot de passe incorrect.");
                        alert.showAndWait();
                      }
            }
        else
            {
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();

            }
    }



    /*--------------------- Navigate to Acceuil page optionnel just pour voir l'acceuil --------------------------------------*/
    public void ToAcceuil(ActionEvent actionEvent)
    {
            navigateTo("Acceuil.fxml");
    }

/*----------------------------------------------------------------------------------------------------------------------------*/
/*------------------------- Navigate to Interface dans le meme stage methode general a appelé --------------------------------*/

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
}