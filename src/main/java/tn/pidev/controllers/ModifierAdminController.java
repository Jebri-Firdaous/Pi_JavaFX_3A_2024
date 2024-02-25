package tn.pidev.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.pidev.entities.Administrateur;
import tn.pidev.services.ServiceAdmin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierAdminController {

    @FXML
    private Label mdpCourt;
    private ServiceAdmin sa =new ServiceAdmin();
    @FXML
    private ComboBox<String> role;

    @FXML
    private Label mailInvalid1;

    @FXML
    private TextField mail;

    @FXML
    private Label label;

    @FXML
    private TextField nom;

    @FXML
    private Button inscrire;

    @FXML
    private Label telInvalid;

    @FXML
    private Label SelectionnerRole1;

    @FXML
    private Label SelectionnerRole;

    @FXML
    private TextField mdp;

    @FXML
    private TextField tel;

    @FXML
    private TextField prenom;

    @FXML
    private Label remplirChamps;
    private  Administrateur admin =new Administrateur();




    public void initialize(Administrateur administrateur)
    {
        this.admin=administrateur;
        if (administrateur!=null)
        {
            nom.setText(administrateur.getNom_personne());
            prenom.setText(administrateur.getPrenom_personne());
            tel.setText(String.valueOf(administrateur.getNumero_telephone()));
            mail.setText(administrateur.getMail_personne());
            mdp.setText(administrateur.getMdp_personne());
            role.setValue(administrateur.getRole());
        }

    }
    public void ToAfficherListeAdmine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherAdmin.fxml"));
            Parent root = loader.load();
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

    public void modifierAdmin(ActionEvent actionEvent) {
        if (admin != null) {
            String nouveauNom = nom.getText();
            String nouveauPrenom = prenom.getText();
            Integer nouveauNumero = Integer.parseInt(tel.getText());
            String nouveauRole = role.getValue();
            String nouveauMail = mail.getText();
            String nouveauMdp = mdp.getText();
            try {
                    admin.setNom_personne(nouveauNom);
                    admin.setPrenom_personne(nouveauPrenom);
                    admin.setNumero_telephone(nouveauNumero);
                    admin.setRole(nouveauRole);
                    admin.setMail_personne(nouveauMail);
                    admin.setMdp_personne(nouveauMdp);

                    sa.ajouter(admin);


                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Administrateur modifié avec succès !");
                    alert.showAndWait();
                    ToAfficherListeAdmine();
                } catch (SQLException e) {
                    // Handle SQL exception
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de la modification de l'administrateur : " + e.getMessage());
                    alert.showAndWait();

            } catch (NumberFormatException e) {
            // Handle number format exception (if tel is not a valid integer)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro de téléphone doit être un entier.");
            alert.showAndWait();
        }

        } else {
            System.err.println("L'objet administrateur n'est pas initialisé. Appeler initData pour l'initialiser.");
        }
    }





//    public void modifierAdmin(ActionEvent actionEvent) {
//        if (admin != null) {
//            try {
//                // Récupérer les nouvelles valeurs des champs de texte
//                String nouveauNom = nom.getText();
//                String nouvellePrenom = prenom.getText();
//                int nvtel=Integer.parseInt(tel.getText()) ;
//                String nouvelleRole = role.getValue();
//                String nouvelleqMail = mail.getText();
//                String nouvelleMdp = mdp.getText();
//
//
//                // Mettre à jour les données de l'hôtel
//                admin.setNom_personne(nouveauNom);
//                admin.setPrenom_personne(nouvellePrenom);
//                admin.setNumero_telephone(nvtel);
//                admin.setRole(nouvelleRole);
//                admin.setMail_personne(nouvelleqMail);
//                admin.setMdp_personne(nouvelleMdp);
//
//                // Appeler la méthode de service pour modifier l'hôtel dans la base de données
//                sa.modifier(admin);
//
//                // Afficher un message de confirmation
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Information");
//                alert.setHeaderText(null);
//                alert.setContentText("Hotel modifié avec succès !");
//                alert.showAndWait();
//
//                // Rafraîchir l'affichage si nécessaire
//
//            } catch (NumberFormatException e) {
//                // Gérer les erreurs de format des nombres dans les champs de texte
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Erreur");
//                alert.setHeaderText(null);
//                alert.setContentText("Veuillez saisir des valeurs numériques valides pour les prix.");
//                alert.showAndWait();
//            } catch (SQLException e) {
//                // Gérer les erreurs de modification
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Erreur");
//                alert.setHeaderText(null);
//                alert.setContentText("Erreur lors de la modification de l'hotel : " + e.getMessage());
//                alert.showAndWait();
//            }
//        } else {
//            System.err.println("L'objet hotel n'est pas initialisé. Appeler initData pour l'initialiser.");
//        }
//
//
//    }



}
