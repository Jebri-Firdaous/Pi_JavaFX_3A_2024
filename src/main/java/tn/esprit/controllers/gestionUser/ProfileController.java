package tn.esprit.controllers.gestionUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionUserServices.ServiceUser;
import tn.esprit.services.gestionUserServices.Session;

import java.sql.SQLException;

public class ProfileController {
    @FXML
    private Button modifierButton;

    @FXML
    private Label labeldisconnect;

    @FXML
    private ComboBox<String> role;

    @FXML
    private Label mailInvalid1;

    @FXML
    private TextField mail;

    @FXML
    private Label Nom_prenom;

    @FXML
    private TextField nom;

    @FXML
    private Label telInvalid;

    @FXML
    private Label SelectionnerRole1;

    @FXML
    private Label SelectionnerRole;

    @FXML
    private Label role_administrateur;

    @FXML
    private TextField tel;

    @FXML
    private TextField prenom;
    Session session = Session.getInstance();
    User user = session.getCurrentUser();

    void initData(User user) {

        if (user != null) {
            // Set values from currentUser to the fields
            nom.setText(user.getNom_personne());
            prenom.setText(user.getPrenom_personne());
            mail.setText(user.getEmail());
            tel.setText(String.valueOf(user.getNumero_telephone()));
            role.setValue(user.getRole_admin());
            Nom_prenom.setText(user.getNom_personne()+" "+user.getPrenom_personne());
            role_administrateur.setText(user.getRole_admin());
            System.out.println(user.getRole_admin());
            // Set other fields as needed
        }


    }
    ServiceUser sa= new ServiceUser();
    private int id_personne;
    public void modifierAdmin(ActionEvent event) {
        // Récupérer les nouvelles valeurs des champs de la page de modification
        String nouveauNom = nom.getText();
        String nouveauPrenom = prenom.getText();
        String nouveauNumero = tel.getText();
        String nouveauRole = (String) role.getValue();
        String nouveauMail = mail.getText();

        // Mettre à jour les valeurs de l'objet admin
        user.setNom_personne(nouveauNom);
        user.setPrenom_personne(nouveauPrenom);
        user.setNumero_telephone(Integer.parseInt(nouveauNumero));
        user.setEmail(nouveauMail);
        user.setRole_admin(nouveauRole);

        try {
            // Appeler la méthode de service pour modifier l'administrateur dans la base de données
            System.out.println(user.getId());
            sa.modifierAdmin(user);

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("L'administrateur a été modifié avec succès !");
            alert.showAndWait();

            // Naviguer vers la liste des administrateurs
        } catch (SQLException e) {
            // Gérer les erreurs de modification
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Erreur lors de la modification de l'administrateur");
            alert.setContentText("Une erreur s'est produite lors de la modification de l'administrateur. Veuillez réessayer.");
            alert.showAndWait();
        }
    }


}
