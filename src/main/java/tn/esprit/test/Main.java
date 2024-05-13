package tn.esprit.test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.controllers.gestionMedecin.AfficherListRendezVousController;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.services.gestionMedecin.ServiceRendezVous;
import tn.esprit.services.gestionUserServices.ServiceUser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException, JsonProcessingException {
        // Initialize connection parameters
        String url = "jdbc:mysql://localhost:3306/e_city_final";
        String username = "root";
        String password = "";



        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            ServiceUser userService = new ServiceUser();
userService.authentication("jdsnjs@gmail.com","A123456789+");
   // ------------------------Test d'affichage-----------------------
            /*
            System.out.println("Les Administrateurs: \n");
            // Affichage des utilisateurs
            List<User> administrateurs = userService.afficherAdmin();
            for (User admin : administrateurs) {
                System.out.println(admin.toString()); // Supposant que vous avez remplacé toString() dans la classe User
            }
            System.out.println("\n Les Clients: \n");

            List<User> clients = userService.afficherClient();
            for (User client : clients) {
                System.out.println(client.toString()); // Supposant que vous avez remplacé toString() dans la classe User
            }*/
 //---------------------AjoutClient------------------------//

//            User nouveauClient = new User();
//            nouveauClient.setNom_personne("Client");
//            nouveauClient.setPrenom_personne("PrenomClient");
//            nouveauClient.setNumero_telephone(21159746);
//            nouveauClient.setEmail("client2@example.com");
//            nouveauClient.setMdp_personne("MotDePasse");
//            nouveauClient.setImage_personne("chemin/vers/image");
//            nouveauClient.setGenre("Femme");
//            nouveauClient.setAge(25);
//
//
//            try {
//                // Appel de la méthode pour ajouter le client
//                userService.ajouterClient(nouveauClient);
//                System.out.println("Client ajouté avec succès !");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }



 //-------------------AjoutAdmin-------------------------------

//            User nouveauAdmin = new User();
//            nouveauAdmin.setNom_personne("Admin");
//            nouveauAdmin.setPrenom_personne("prenomAdmin");
//            nouveauAdmin.setNumero_telephone(24500297);
//            nouveauAdmin.setEmail("admin22@example.com");
//            nouveauAdmin.setMdp_personne("MotDePasse");
//            nouveauAdmin.setImage_personne("chemin/vers/image");
//            nouveauAdmin.setGenre("Femme");
//            nouveauAdmin.setRole_admin("Gestion Santé");
//
//
//            try {
//                // Appel de la méthode pour ajouter le client
//                userService.ajouterAdmin(nouveauAdmin);
//                System.out.println("Admin ajouté avec succès !");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

            //---------------------------modifier un admin-----------------------------------------//
//            User userToUpdate = new User();
//            userToUpdate.setId(85); // Set the ID of the user you want to update
//            String rolesFromDatabase = userService.getUserRoles(userToUpdate.getId()); // Assuming there's a method to fetch user roles by ID
//
//            userToUpdate.setNom_personne("Htaytem");
//            userToUpdate.setPrenom_personne("Updated Lastname");
//            userToUpdate.setNumero_telephone(24500297);
//            userToUpdate.setEmail("test24updated_10_05_2024@example.com");
//            userToUpdate.setMdp_personne("newpassword");
//            userToUpdate.setImage_personne("newimage.jpg");
//            userToUpdate.setRole_admin("Gestion Transport");
//            if (rolesFromDatabase != null && rolesFromDatabase.contains("ADMIN")) {
//                try {
//                    userService.modifierAdmin(userToUpdate);
//                    System.out.println("User with ID " + userToUpdate.getId() + " updated successfully.");
//                } catch (SQLException e) {
//                    System.err.println("Error updating user: " + e.getMessage());
//                }
//            } else {
//                // User does not have the 'ADMIN' role, show a message
//                System.out.println("User with ID " + userToUpdate.getId() + " is not an admin. Modification not allowed.");
//            }

            //---------------------------modifier un client-----------------------------------------//
//            User userToUpdate = new User();
//            userToUpdate.setId(89); // Set the ID of the user you want to update
//            String rolesFromDatabase = userService.getUserRoles(userToUpdate.getId()); // Assuming there's a method to fetch user roles by ID
//
//            userToUpdate.setNom_personne("updated");
//            userToUpdate.setPrenom_personne("Updated Lastname");
//            userToUpdate.setNumero_telephone(24500297);
//            userToUpdate.setEmail("testbbb@example.com");
//            userToUpdate.setMdp_personne("newpassword");
//            userToUpdate.setImage_personne("newimage.jpg");
//            userToUpdate.setAge(20);
//            userToUpdate.setGenre("Femme");
//
//            if (rolesFromDatabase != null && rolesFromDatabase.contains("CLIENT")) {
//                try {
//                    userService.modifierClient(userToUpdate);
//                    System.out.println("User with ID " + userToUpdate.getId() + " updated successfully.");
//                } catch (SQLException e) {
//                    System.err.println("Error updating user: " + e.getMessage());
//                }
//            } else {
//                // User does not have the 'ADMIN' role, show a message
//                System.out.println("User with ID " + userToUpdate.getId() + " is not a client. Modification not allowed.");
//            }


            //--------------------supprimer user------------------------//
//            int idToDelete = 122; // L'ID de l'utilisateur que vous souhaitez supprimer
//
//            try {
//                // Appel de la méthode supprimerUser pour supprimer l'utilisateur avec l'ID spécifié
//                userService.supprimerUser(idToDelete);
//                System.out.println("User with ID " + idToDelete + " deleted successfully.");
//            } catch (SQLException e) {
//                System.err.println("Error deleting user: " + e.getMessage());
//            }

            } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}