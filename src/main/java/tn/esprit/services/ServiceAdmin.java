package tn.esprit.services;

import tn.esprit.entities.Administrateur;
import tn.esprit.entities.Client;
import tn.esprit.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService <Administrateur> {
    private Connection connection;

    public ServiceAdmin(){
        connection= MyDataBase.getInstance().getConnection();
    }


    /*--------------------------------------------------AJOUT------------------------------------------------------*/
    @Override
    public void ajouter(Administrateur administrateur) throws SQLException {
        // Insertion dans la table "personne"
       /* String sqlInsertPersonne = "INSERT INTO `personne`(`nom_personne`, `prenom_personne`, `numero_telephone`, `mail_personne`, `mdp_personne`) VALUES (?, ?, ?, ?, ?)";

              PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlInsertPersonne);
        preparedStatementPersonne.setInt(1, 1);
        preparedStatementPersonne.setString(1, administrateur.getNom_personne());
        preparedStatementPersonne.setString(2, administrateur.getPrenom_personne());
        preparedStatementPersonne.setInt(3, administrateur.getNumero_telephone());
        preparedStatementPersonne.setString(4, administrateur.getMail_personne());
        preparedStatementPersonne.setString(5, administrateur.getMdp_personne());
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
        String sqlInsertAdmin = "INSERT INTO `administrateur` (`id_admin`, `role`, `id_personne`) SELECT ?, ?, id_personne FROM personne WHERE id_personne = ?";
        PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlInsertAdmin);
        preparedStatementAdmin.setInt(1, administrateur.getId_admin());
        preparedStatementAdmin.setString(2, administrateur.getRole());
        preparedStatementAdmin.setInt(3, idPersonne); // Utilisation de l'ID de la personne récupérée précédemment
        preparedStatementAdmin.executeUpdate();*/
    }

    @Override
    public void modifier(int id, String nom, String prenom, int numTel, String adresse, String specialite) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    /*--------------------------------------------------MODIFIER------------------------------------------------------*/

    public void modifier(Administrateur administrateur) throws SQLException {

        int id_personne = administrateur.getId_personne();

        // Mise à jour dans la table "administrateur"
        try {
            String sqlUpdateAdmin = "UPDATE administrateur SET role = ? WHERE id_personne = ?";
            PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlUpdateAdmin);
            preparedStatementAdmin.setString(1, administrateur.getRole());
            preparedStatementAdmin.setInt(2, id_personne);
            preparedStatementAdmin.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception
            System.err.println("Erreur lors de la mise à jour de l'administrateur dans la table 'administrateur' : " + e.getMessage());
            throw e;
        }

        // Mise à jour dans la table "personne"
        try {
            String sqlUpdatePersonne = "UPDATE personne SET nom_personne = ?, prenom_personne = ?, numero_telephone = ?, mail_personne = ?, mdp_personne = ? WHERE id_personne = ?";
            PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlUpdatePersonne);
            preparedStatementPersonne.setString(1, administrateur.getNom_personne());
            preparedStatementPersonne.setString(2, administrateur.getPrenom_personne());
            preparedStatementPersonne.setInt(3, administrateur.getNumero_telephone());
            preparedStatementPersonne.setString(4, administrateur.getMail_personne());
            preparedStatementPersonne.setString(5, administrateur.getMdp_personne());
            preparedStatementPersonne.setInt(6, id_personne);
            preparedStatementPersonne.executeUpdate();
        } catch (SQLException e) {
            // Gérer l'exception
            System.err.println("Erreur lors de la mise à jour de l'administrateur dans la table 'personne' : " + e.getMessage());
            throw e;
        }

    }
    /*--------------------------------------------------AFFICHE------------------------------------------------------*/

    @Override
    public List<Administrateur> afficher() throws SQLException {
        List<Administrateur> administrateurList = new ArrayList<>();
        /*String sql = "SELECT a.role, p.nom_personne, p.prenom_personne, p.numero_telephone, p.mail_personne, p.mdp_personne " +
                "FROM administrateur a JOIN personne p ON p.id_personne = a.id_personne";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String role = rs.getString("role");
            String nom = rs.getString("nom_personne");
            String prenom = rs.getString("prenom_personne");
            int tel = rs.getInt("numero_telephone");
            String mail = rs.getString("mail_personne");
            String mdp = rs.getString("mdp_personne");

            Administrateur administrateur = new Administrateur(nom, prenom, tel, mail, mdp, role);
            administrateurList.add(administrateur);
        }*/
        return administrateurList;
    }

}





