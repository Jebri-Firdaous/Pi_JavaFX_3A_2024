package tn.esprit.controllers.gestionUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tn.esprit.entities.gestionUserEntities.Administrateur;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceAdmin;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierAdminController {

    private final ServiceUser sa = new ServiceUser();
    @FXML
    private Label mdpCourt;
    @FXML
    private ComboBox<String> role;

    @FXML
    private Label mailInvalide;

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
    private User admin;
    private int id_personne;


    void initData(User administrateur) {
        this.admin = administrateur;
        id_personne = administrateur.getId();

        nom.setText(administrateur.getNom_personne());
        prenom.setText(administrateur.getPrenom_personne());
        tel.setText(String.valueOf(administrateur.getNumero_telephone()));
        mail.setText(administrateur.getEmail());
        role.setValue(administrateur.getRole_admin());


    }

    public void ToAfficherListeAdmine() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gestionUserRessources/afficherAdmin.fxml"));
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
        User administrateur = new User();
        // Récupérer les nouvelles valeurs des champs de la page de modification
        String nouveauNom = nom.getText();
        String nouveauPrenom = prenom.getText();
        String nouveauNumero = tel.getText();
        String nouveauRole = role.getValue();
        String nouveauMail = mail.getText();

        // Mettre à jour les valeurs de l'objet admin
        admin.setId(id_personne);
        admin.setNom_personne(nouveauNom);
        admin.setPrenom_personne(nouveauPrenom);
        admin.setNumero_telephone(Integer.parseInt(nouveauNumero));
        admin.setEmail(nouveauMail);
        admin.setRole_admin(nouveauRole);

        try {
            // Appeler la méthode de service pour modifier l'administrateur dans la base de données
            System.out.println(id_personne);
            System.out.println(admin.getId());
            sa.modifierAdmin(admin);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'administrateur a été modifié avec succès !");
            alert.showAndWait();

            // Naviguer vers la liste des administrateurs
            ToAfficherListeAdmine();
        } catch (SQLException e) {
            // Gérer les erreurs de modification
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la modification de l'administrateur");
            alert.setContentText("Une erreur s'est produite lors de la modification de l'administrateur. Veuillez réessayer.");
            alert.showAndWait();
        }
    }


    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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


