package tn.esprit.services.gestionUserServices;

import com.mysql.cj.xdevapi.JsonArray;
import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.entities.gestionMedecin.Medecin;
import tn.esprit.entities.gestionUserEntities.Administrateur;
import tn.esprit.entities.gestionUserEntities.User;
import tn.esprit.utils.MyDataBase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ServiceUser implements IUserService<User> {
    private Connection connection;
    private List<User> adminList;
    private List<User> clientList;
    private List<User> nomClient;

    public ServiceUser() {

        connection = MyDataBase.getInstance().getConnection();
        adminList = new ArrayList<>();
        clientList = new ArrayList<>();
    }
    public List<String> afficherclient() throws SQLException {
        List<String> nomClients = new ArrayList<>(); // Liste pour stocker les noms des clients

        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom_personne(rs.getString("Nom_personne"));
                user.setPrenom_personne(rs.getString("prenom_personne"));

                if (user.getRoles().contains("CLIENT")) {
                    // Ajouter le nom du client à la liste
                    nomClients.add(user.getNom_personne() + " " + user.getPrenom_personne());
                }
            }
        } // La connexion sera automatiquement fermée après la fin du bloc try-with-resources

        return nomClients; // Retourner la liste des noms des clients
    }

    @Override
    public void ajouterClient(User user) throws SQLException {
        String jsonRoles = "[\"CLIENT\"]";
        String hashedPassword = PasswordEncryption.hashPassword(user.getMdp_personne()); // Hash the password

        String sql = "INSERT INTO `user`(`nom_personne`, `prenom_personne`," +
                " `numero_telephone`, `email`,`password`,`image_personne`,`genre`,`age`,`roles`) VALUES (?, ?, ?, ?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom_personne());
        preparedStatement.setString(2, user.getPrenom_personne());
        preparedStatement.setInt(3, user.getNumero_telephone());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, hashedPassword); // Use hashed password
        preparedStatement.setString(6, user.getImage_personne());
        preparedStatement.setString(7, user.getGenre());
        preparedStatement.setInt(8, user.getAge());
        preparedStatement.setObject(9, jsonRoles);

        preparedStatement.executeUpdate();
    }
    @Override
    public void ajouterAdmin(User user) throws SQLException {
        String jsonRoles = "[\"ADMIN\"]";
        String hashedPassword = PasswordEncryption.hashPassword(user.getMdp_personne()); // Hash the password

        String sql = "INSERT INTO `user`(`nom_personne`, `prenom_personne`," +
                " `numero_telephone`, `email`,`password`,`image_personne`,`role_admin`,`roles`) VALUES ( ?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom_personne());
        preparedStatement.setString(2, user.getPrenom_personne());
        preparedStatement.setInt(3, user.getNumero_telephone());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, hashedPassword); // Use hashed password
        preparedStatement.setString(6, user.getImage_personne());
        preparedStatement.setString(7, user.getRole_admin());
        preparedStatement.setObject(8, jsonRoles);


        preparedStatement.executeUpdate();
    }
    @Override
    public void modifierAdmin(User user) throws SQLException {
        String sql = "UPDATE User SET nom_personne = ?, prenom_personne = ?, numero_telephone = ?, email = ?, image_personne = ?, role_admin = ? WHERE id = ? AND JSON_CONTAINS(roles, '[\"ADMIN\"]')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom_personne());
        preparedStatement.setString(2, user.getPrenom_personne());
        preparedStatement.setInt(3, user.getNumero_telephone());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getImage_personne());
        preparedStatement.setString(6, user.getRole_admin());
        preparedStatement.setInt(7, user.getId()); // Assuming the ID is an integer



        preparedStatement.executeUpdate();


    }
    public int getAdminId(String nom, String prenom, String mail, String mdp, String roles) throws SQLException {
        String sql = "SELECT id, password FROM user WHERE nom_personne = ? AND prenom_personne = ? AND email = ? AND roles = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, mail);
            statement.setString(4, roles);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    if (PasswordEncryption.verifyPassword(mdp, hashedPassword)) {
                        return resultSet.getInt("id");
                    } else {
                        throw new SQLException("Invalid password.");
                    }
                } else {
                    throw new SQLException("Admin not found.");
                }
            }
        }



}




    @Override
    public void modifierClient(User user) throws SQLException {
        String sql = "UPDATE User SET nom_personne = ?, prenom_personne = ?, numero_telephone = ?, email = ?, password = ?, image_personne = ?, age = ?, genre=? WHERE id = ? AND JSON_CONTAINS(roles, '[\"CLIENT\"]')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getNom_personne());
        preparedStatement.setString(2, user.getPrenom_personne());
        preparedStatement.setInt(3, user.getNumero_telephone());
        preparedStatement.setString(4, user.getEmail());
        preparedStatement.setString(5, user.getMdp_personne());
        preparedStatement.setString(6, user.getImage_personne());
        preparedStatement.setInt(7, user.getAge());

        preparedStatement.setString(8, user.getGenre());
        preparedStatement.setInt(9, user.getId()); // Assuming the ID is an integer



        preparedStatement.executeUpdate();


    }

    @Override
    public List<User> afficherAdmin() throws SQLException {
        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom_personne(rs.getString("Nom_personne"));
                user.setPrenom_personne(rs.getString("prenom_personne"));
                user.setNumero_telephone(rs.getInt("numero_telephone"));
                user.setEmail(rs.getString("email"));
                user.setImage_personne(rs.getString("image_personne"));
                user.setGenre(rs.getString("genre"));
                user.setAge(rs.getInt("age"));
                user.setRoles(rs.getString("roles"));
                user.setRole_admin(rs.getString("role_admin"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setIs_banned(rs.getBoolean("is_banned"));

                if (user.getRoles().contains("ADMIN")) {
                    adminList.add(user);
                } else {
                    clientList.add(user);
                }
            }
        }
        return adminList; // Vous pouvez retourner n'importe quelle liste, selon vos besoins
    }

    @Override
    public List<User> afficherClient() throws SQLException {
        String sql = "SELECT * FROM User";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom_personne(rs.getString("Nom_personne"));
                user.setPrenom_personne(rs.getString("prenom_personne"));
                user.setNumero_telephone(rs.getInt("numero_telephone"));
                user.setEmail(rs.getString("email"));
                user.setImage_personne(rs.getString("image_personne"));
                user.setGenre(rs.getString("genre"));
                user.setAge(rs.getInt("age"));
                user.setRoles(rs.getString("roles"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setIs_banned(rs.getBoolean("is_banned"));

                if (user.getRoles().contains("ADMIN")) {
                    adminList.add(user);
                } else {
                    clientList.add(user);
                }
            }
        }
        return clientList; // Vous pouvez retourner n'importe quelle liste, selon vos besoins
        }





    @Override
    public void supprimerUser(int id) throws SQLException {
        String sql = "DELETE FROM `user` WHERE `id`= ?  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }
    public void banner(int id) throws SQLException {
        String sql = "UPDATE User SET is_verified = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, true);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Handle case where no rows were updated (user not found)
                System.out.println("not found");
            } else {
                // Handle successful update
                System.out.println("found");
            }
        }
    }
    public void annulerbann(int id) throws SQLException {
        String sql = "UPDATE User SET is_verified = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, false);
            statement.setInt(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                // Handle case where no rows were updated (user not found)
                System.out.println("not found");
            } else {
                // Handle successful update
                System.out.println("found");
            }
        }
    }

    public String getUserRoles(int userId) throws SQLException {
        String roles = null;
        String sql = "SELECT roles FROM User WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    roles = resultSet.getString("roles");
                }
            }
        }
        return roles;
    }
    public int authentication(String email, String password) {
        int id = -1;
        int isBanned = 0;
        String roleUser = "";
        String storedHashedPassword = "";

        try {
            String req = "SELECT * FROM `user` WHERE `email` = ?";
            PreparedStatement stmt = connection.prepareStatement(req);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                isBanned = rs.getInt("is_banned");
                roleUser = rs.getString("roles");
                storedHashedPassword = rs.getString("password");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        // Verify the password using the hashed password from the database

        if (PasswordEncryption.verifyPassword(password, storedHashedPassword)) {
            if (isBanned == 1) {
                id = 0;
            }
            if (roleUser.equals("[\"CLIENT\"]")) {
                id = -2;
            }
        } else {
            // Password doesn't match
            id = -1;
        }

        return id;
    }

    public User getOneById(int id) {
        User user = null;
        try {
            String req = "SELECT * FROM `user` WHERE `id` = ?";
            PreparedStatement stmt = connection.prepareStatement(req);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(id);
                user.setEmail(rs.getString("email"));
                user.setRoles(rs.getString("roles"));
                user.setMdp_personne(rs.getString("password"));
                user.setNom_personne(rs.getString("nom_personne"));
                user.setPrenom_personne(rs.getString("prenom_personne"));
                user.setImage_personne(rs.getString("image_personne"));
                user.setRole_admin(rs.getString("role_admin"));
                user.setAge(rs.getInt("age"));
                user.setGenre(rs.getString("genre"));
                user.setNumero_telephone(rs.getInt("numero_telephone"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setIs_banned(rs.getBoolean("is_banned"));
                // Set other user properties similarly
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return user;
    }
    public User getOneByEmail(String email) {
        User user = null;
        try {
            String sql = "SELECT * FROM user WHERE email =  ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String role = rs.getString("role_admin");
                String nom = rs.getString("nom_personne");
                String prenom = rs.getString("prenom_personne");
                int tel = rs.getInt("numero_telephone");
                String mail = rs.getString("email");
                String mdp = rs.getString("password");

                user = new User(id, nom, prenom, tel, mail, mdp, role);
                System.out.println(user);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'administrateur : " + e.getMessage());
        }
        return user;
    }



    public String getnombyEmail(String email) {
        String NomPrenom = "";
        try {
            String sql = "SELECT * FROM user WHERE email =  ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nom_personne");
                String prenom = rs.getString("prenom_personne");

                NomPrenom = nom + " " + prenom;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'administrateur : " + e.getMessage());
        }
        return NomPrenom;
    }


    public List<User> rechercherAdmin(String recherche) throws SQLException {
        List<User> administrateurs = new ArrayList<>();


        String sql = "SELECT id,role_admin,email,nom_personne,prenom_personne,numero_telephone " +
                "FROM user " +
                "WHERE (nom_personne LIKE ? OR role_admin LIKE ?) AND JSON_CONTAINS(roles, '[\"ADMIN\"]') ";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + recherche + "%");
        preparedStatement.setString(2, "%" + recherche + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            User admin = new User();
            admin.setId(rs.getInt("id"));
            admin.setNom_personne(rs.getString("nom_personne"));
            admin.setPrenom_personne(rs.getString("prenom_personne"));
            admin.setNumero_telephone(rs.getInt("numero_telephone"));
            admin.setEmail(rs.getString("email"));
            admin.setRole_admin(rs.getString("role_admin"));

            administrateurs.add(admin);
        }
        System.out.println(administrateurs);
        return administrateurs;
    }

    @Override
    public List<User> recherClient(String recherche) throws SQLException {
        List<User> administrateurs = new ArrayList<>();


        String sql = "SELECT id,email,nom_personne,prenom_personne,numero_telephone,age,genre " +
                "FROM user " +
                "WHERE (nom_personne LIKE ?OR prenom_personne LIKE ?) AND JSON_CONTAINS(roles, '[\"CLIENT\"]') ";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + recherche + "%");
        preparedStatement.setString(2, "%" + recherche + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            User admin = new User();
            admin.setId(rs.getInt("id"));
            admin.setNom_personne(rs.getString("nom_personne"));
            admin.setPrenom_personne(rs.getString("prenom_personne"));
            admin.setNumero_telephone(rs.getInt("numero_telephone"));
            admin.setEmail(rs.getString("email"));
            admin.setGenre(rs.getString("genre"));
            admin.setAge(rs.getInt("age"));

            administrateurs.add(admin);
        }
        System.out.println(administrateurs);
        return administrateurs;    }
    @Override
    public List<User> listClient() throws SQLException {
        List<User> clients = new ArrayList<>();

        String sql = "SELECT * FROM user WHERE JSON_CONTAINS(roles, '[\"CLIENT\"]')";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom_personne(rs.getString("Nom_personne"));
                user.setPrenom_personne(rs.getString("prenom_personne"));
                user.setNumero_telephone(rs.getInt("numero_telephone"));
                user.setEmail(rs.getString("email"));
                user.setImage_personne(rs.getString("image_personne"));
                user.setGenre(rs.getString("genre"));
                user.setAge(rs.getInt("age"));
                user.setRoles(rs.getString("roles"));
                user.setRole_admin(rs.getString("role_admin"));
                user.setIs_verified(rs.getBoolean("is_verified"));
                user.setIs_banned(rs.getBoolean("is_banned"));

                clients.add(user);
            }
        }

        return clients;
    }

}
