package tn.pidev.services;

import tn.pidev.entities.Administrateur;
import tn.pidev.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService<Administrateur> {
    private final Connection connection;

    public ServiceAdmin() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public Administrateur getAdministrateur(int id) {
        Administrateur administrateur = null;
        try {
            String sql = "SELECT * FROM administrateur JOIN personne ON administrateur.id_personne = personne.id_personne WHERE administrateur.id_personne = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id_personne = rs.getInt("id_personne");
                String role = rs.getString("role");
                String nom = rs.getString("nom_personne");
                String prenom = rs.getString("prenom_personne");
                int tel = rs.getInt("numero_telephone");
                String mail = rs.getString("mail_personne");
                String mdp = rs.getString("mdp_personne");
                String image_personne = rs.getString("image_personne");

                administrateur = new Administrateur(id_personne, nom, prenom, tel, mail, mdp, image_personne, role);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'administrateur : " + e.getMessage());
        }
        return administrateur;
    }


    /*--------------------------------------------------AJOUT------------------------------------------------------*/
    @Override
    public void ajouter(Administrateur administrateur) throws SQLException {
        // Insertion dans la table "personne"
        String sqlInsertPersonne = "INSERT INTO `personne`(`nom_personne`, `prenom_personne`, `numero_telephone`, `mail_personne`, `mdp_personne`,`image_personne`) VALUES (?, ?, ?, ?, ?,?)";

        PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlInsertPersonne);
        preparedStatementPersonne.setInt(1, 1);
        preparedStatementPersonne.setString(1, administrateur.getNom_personne());
        preparedStatementPersonne.setString(2, administrateur.getPrenom_personne());
        preparedStatementPersonne.setInt(3, administrateur.getNumero_telephone());
        preparedStatementPersonne.setString(4, administrateur.getMail_personne());
        preparedStatementPersonne.setString(5, administrateur.getMdp_personne());
        preparedStatementPersonne.setString(6, administrateur.getImage_personne());
        preparedStatementPersonne.executeUpdate();

        // Récupération de l'ID de la personne insérée
        int idPersonne = 0;
        String sqlGetInsertedId = "SELECT LAST_INSERT_ID()";
        PreparedStatement preparedStatementId = connection.prepareStatement(sqlGetInsertedId);
        ResultSet resultSetId = preparedStatementId.executeQuery();
        if (resultSetId.next()) {
            idPersonne = resultSetId.getInt(1);
        }

        // Insertion dans la table "administrateur" en utilisant les données de la table "personne"
        String sqlInsertAdmin = "INSERT INTO administrateur (role, id_personne) " +
                "SELECT ?, id_personne FROM personne WHERE id_personne = ?";
        PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlInsertAdmin);
        preparedStatementAdmin.setString(1, administrateur.getRole());
        preparedStatementAdmin.setInt(2, idPersonne); // Utilisation de l'ID de la personne récupérée précédemment
        preparedStatementAdmin.executeUpdate();
    }

    /*--------------------------------------------------MODIFIER------------------------------------------------------*/

    public void modifier(Administrateur administrateur) throws SQLException {
        String sqlUpdate = "UPDATE personne AS p INNER JOIN administrateur AS a ON p.id_personne = a.id_personne " +
                "SET p.nom_personne = ?, p.prenom_personne = ?, p.numero_telephone = ?, " +
                "p.mail_personne = ?, p.mdp_personne = ?, p.image_personne = ?, a.role = ? " +
                "WHERE p.id_personne = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate)) {
            preparedStatement.setString(1, administrateur.getNom_personne());
            preparedStatement.setString(2, administrateur.getPrenom_personne());
            preparedStatement.setInt(3, administrateur.getNumero_telephone());
            preparedStatement.setString(4, administrateur.getMail_personne());
            preparedStatement.setString(5, administrateur.getMdp_personne());
            preparedStatement.setString(6, administrateur.getImage_personne());
            preparedStatement.setString(7, administrateur.getRole());
            preparedStatement.setInt(8, administrateur.getId_personne());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Modification réussie pour l'administrateur avec l'ID : " + administrateur.getId_personne());
            } else {
                System.out.println("Aucune modification effectuée pour l'administrateur avec l'ID : " + administrateur.getId_personne());
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de l'administrateur : " + e.getMessage());
        }
    }


    /*--------------------------------------------------AFFICHE------------------------------------------------------*/
    public int getAdminId(String nom, String prenom, String mail, String mdp) throws SQLException {
        String query = "SELECT id_personne FROM personne WHERE  nom_personne = ? AND prenom_personne = ? AND mail_personne = ? AND mdp_personne=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, mdp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_personne");
                } else {
                    throw new SQLException("Admin introuvable");
                }
            }
        }
    }

    @Override
    public List<Administrateur> afficher() throws SQLException {
        List<Administrateur>
                administrateurList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM administrateur a JOIN personne p ON p.id_personne = a.id_personne";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            int id_personne = rs.getInt("id_personne");
            String role = rs.getString("role");
            String nom = rs.getString("nom_personne");
            String prenom = rs.getString("prenom_personne");
            int tel = rs.getInt("numero_telephone");
            String mail = rs.getString("mail_personne");
            String mdp = rs.getString("mdp_personne");
            String image_personne = rs.getString("image_personne");

            Administrateur administrateur = new Administrateur(id_personne, nom, prenom, tel, mail, mdp, image_personne, role);
            administrateurList.add(administrateur);
        }
        return administrateurList;
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sqlDeleteAdmin = "DELETE FROM administrateur WHERE id_personne = ?";

        // Préparer la déclaration SQL
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteAdmin)) {
            // Définir le paramètre de l'identifiant
            preparedStatement.setInt(1, id);

            // Exécuter la requête de suppression
            preparedStatement.executeUpdate();
        }
    }

    public Administrateur getAdminByEmail(String email) {
        Administrateur administrateur = null;
        try {
            String sql = "SELECT * FROM administrateur JOIN personne ON administrateur.id_personne = personne.id_personne WHERE personne.mail_personne =  ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int id_personne = rs.getInt("id_personne");
                String role = rs.getString("role");
                String nom = rs.getString("nom_personne");
                String prenom = rs.getString("prenom_personne");
                int tel = rs.getInt("numero_telephone");
                String mail = rs.getString("mail_personne");
                String mdp = rs.getString("mdp_personne");
                String image_personne = rs.getString("image_personne");

                administrateur = new Administrateur(id_personne, nom, prenom, tel, mail, mdp, image_personne, role);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de l'administrateur : " + e.getMessage());
        }
        return administrateur;
    }
}




