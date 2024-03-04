package tn.esprit.services.gestionMedecin;


import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceClient implements IService<Client> {
    /*--------------------------------------------------AJOUT------------------------------------------------------*/
    private Connection connection;

    public ServiceClient(){connection= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Client client) throws SQLException {
            // Insertion dans la table "personne"
           /* String sqlInsertPersonne = "INSERT INTO `personne`(`nom_personne`, `prenom_personne`, `numero_telephone`, `mail_personne`, `mdp_personne`) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlInsertPersonne);
            preparedStatementPersonne.setInt(1, 1);
            preparedStatementPersonne.setString(1, client.getNom_personne());
            preparedStatementPersonne.setString(2, client.getPrenom_personne());
            preparedStatementPersonne.setInt(3, client.getNumero_telephone());
            preparedStatementPersonne.setString(4, client.getMail_personne());
            preparedStatementPersonne.setString(5, client.getMdp_personne());
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
            String sqlInsertAdmin = "INSERT INTO `client` (`id_client`, `genre`, `age`) SELECT ?, ?,?, id_personne FROM personne WHERE id_personne = ?";
            PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlInsertAdmin);
            preparedStatementAdmin.setInt(1, client.getId_client());
            preparedStatementAdmin.setString(2, client.getGenre());
            preparedStatementAdmin.setInt(3, client.getAge());
            preparedStatementAdmin.setInt(4, idPersonne); // Utilisation de l'ID de la personne récupérée précédemment
            preparedStatementAdmin.executeUpdate();*/
        }

    @Override
    public void modifier(int id, String nom, String prenom, int numTel, String adresse, String specialite) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    /*--------------------------------------------------MODIFIER------------------------------------------------------*/


    public void modifier(Client client) throws SQLException {

    }

    /*--------------------------------------------------AFFICHE------------------------------------------------------*/

    @Override
    public List<Client> afficher() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM personne p JOIN client c ON p.id_personne = c.id_personne";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Client client = new Client();
            client.setId_personne(rs.getInt("id_personne"));
            client.setNom_personne(rs.getString("nom_personne"));
            client.setPrenom_personne(rs.getString("prenom_personne"));
            client.setNumero_telephone(rs.getInt("numero_telephone"));
            client.setMail_personne(rs.getString("mail_personne"));
            client.setMdp_personne(rs.getString("mdp_personne"));
            client.setGenre(rs.getString("genre"));
            client.setAge(rs.getInt("age"));
            clients.add(client);
        }
        return clients;
    }

    public Client getClientById(int idPersonne) {
        Client client = new Client();
        String sql = "SELECT p.nom_personne, p.prenom_personne FROM personne p WHERE p.id_personne = ?"; // Corrected column name
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idPersonne);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                client.setNom_personne(rs.getString("nom_personne")); // Corrected column name and setter method
                client.setPrenom_personne(rs.getString("prenom_personne")); // Corrected column name and setter method
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

}
