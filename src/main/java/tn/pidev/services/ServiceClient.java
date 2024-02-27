package tn.pidev.services;

import tn.pidev.entities.Administrateur;
import tn.pidev.entities.Client;
import tn.pidev.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient implements IService<Client> {
    /*--------------------------------------------------AJOUT------------------------------------------------------*/
    private Connection connection;

    public ServiceClient(){connection= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Client client) throws SQLException {
        try {
            // Insertion dans la table "personne"
            String sqlInsertPersonne = "INSERT INTO personne (nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlInsertPersonne, Statement.RETURN_GENERATED_KEYS);
            preparedStatementPersonne.setString(1, client.getNom_personne());
            preparedStatementPersonne.setString(2, client.getPrenom_personne());
            preparedStatementPersonne.setInt(3, client.getNumero_telephone());
            preparedStatementPersonne.setString(4, client.getMail_personne());
            preparedStatementPersonne.setString(5, client.getMdp_personne());
            preparedStatementPersonne.executeUpdate();

            // Récupération de l'ID de la personne insérée
            ResultSet generatedKeys = preparedStatementPersonne.getGeneratedKeys();
            int idPersonne = 0;
            if (generatedKeys.next()) {
                idPersonne = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get the generated ID for personne.");
            }

            // Insertion dans la table "client" en utilisant les données de la table "personne"
            String sqlInsertClient = "INSERT INTO client (id_personne, genre, age) VALUES (?, ?, ?)";
            PreparedStatement preparedStatementClient = connection.prepareStatement(sqlInsertClient);
            preparedStatementClient.setInt(1, idPersonne);
            preparedStatementClient.setString(2, client.getGenre());
            preparedStatementClient.setInt(3, client.getAge());
            preparedStatementClient.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding client: " + e.getMessage());
            throw e; // Rethrow the exception to be handled elsewhere if necessary
        }
        }

    /*--------------------------------------------------MODIFIER------------------------------------------------------*/

    public void modifier(Client client) throws SQLException {

        int id_personne = client.getId_personne();

        // Mise à jour dans la table "administrateur"
        try {
            String sqlUpdateCLIENT = "UPDATE client SET genre = ? , age = ? WHERE id_personne = ?";
            PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlUpdateCLIENT);
            preparedStatementAdmin.setString(1, client.getGenre());
            preparedStatementAdmin.setInt(2, client.getAge());
            preparedStatementAdmin.setInt(3, id_personne);
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
            preparedStatementPersonne.setString(1, client.getNom_personne());
            preparedStatementPersonne.setString(2, client.getPrenom_personne());
            preparedStatementPersonne.setInt(3, client.getNumero_telephone());
            preparedStatementPersonne.setString(4, client.getMail_personne());
            preparedStatementPersonne.setString(5, client.getMdp_personne());
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
    public List<Client> afficher() throws SQLException {
            List<Client> clientList = new ArrayList<>();
            String sql = "SELECT a.role, p.nom_personne, p.prenom_personne, p.numero_telephone, p.mail_personne, p.mdp_personne " +
                    "FROM client a JOIN personne p ON p.id_personne = a.id_personne";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                int age = rs.getInt("age");
                String genre = rs.getNString("genre");
                String nom = rs.getString("nom_personne");
                String prenom = rs.getString("prenom_personne");
                int tel = rs.getInt("numero_telephone");
                String mail = rs.getString("mail_personne");
                String mdp = rs.getString("mdp_personne");

                Client client = new Client(nom, prenom, tel, mail, mdp,genre,age);
                clientList.add(client);
            }
            return clientList;
        }

    @Override
    public void supprimer(int id) throws SQLException {
            String sql = "DELETE FROM `client` WHERE `id_client`= ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
    }


