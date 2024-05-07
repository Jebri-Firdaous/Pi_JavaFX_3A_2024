package tn.esprit.services.gestionUserServices;

import tn.esprit.entities.gestionUserEntities.Client;
import tn.esprit.utils.MyDataBase;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient implements IService<Client> {
    private final Connection connection;
    /*--------------------------------------------------AJOUT------------------------------------------------------*/

    public ServiceClient() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Client client) throws SQLException {
        // Insertion dans la table "personne"
        String sqlInsertPersonne = "INSERT INTO `personne`(`nom_personne`, `prenom_personne`, `numero_telephone`, `mail_personne`, `mdp_personne`,`image_personne`) VALUES (?, ?, ?, ?, ?,?)";

        PreparedStatement preparedStatementPersonne = connection.prepareStatement(sqlInsertPersonne);
        preparedStatementPersonne.setInt(1, 1);
        preparedStatementPersonne.setString(1, client.getNom_personne());
        preparedStatementPersonne.setString(2, client.getPrenom_personne());
        preparedStatementPersonne.setInt(3, client.getNumero_telephone());
        preparedStatementPersonne.setString(4, client.getMail_personne());
        preparedStatementPersonne.setString(5, client.getMdp_personne());
        preparedStatementPersonne.setString(6, client.getImage_personne());
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
        String sqlInsertAdmin = "INSERT INTO client (genre,age, id_personne) " +
                "SELECT ?,?, id_personne FROM personne WHERE id_personne = ?";
        PreparedStatement preparedStatementAdmin = connection.prepareStatement(sqlInsertAdmin);
        preparedStatementAdmin.setString(1, client.getGenre());
        preparedStatementAdmin.setInt(2, client.getAge());
        preparedStatementAdmin.setInt(3, idPersonne); // Utilisation de l'ID de la personne récupérée précédemment
        preparedStatementAdmin.executeUpdate();
    }

    @Override
    public void modifier(Client client) throws SQLException {
        String sqlUpdate = "UPDATE client c INNER JOIN personne p ON p.id_personne = c.id_personne " +
                "SET p.nom_personne = ?, p.prenom_personne = ?, p.numero_telephone = ?, " +
                "p.mail_personne = ?, p.mdp_personne = ?, c.genre=? , c.age= ? WHERE c.id_personne = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
        preparedStatement.setString(1, client.getNom_personne());
        preparedStatement.setString(2, client.getPrenom_personne());
        preparedStatement.setInt(3, client.getNumero_telephone());
        preparedStatement.setString(4, client.getMail_personne());
        preparedStatement.setString(5, client.getMdp_personne());
        preparedStatement.setString(6, client.getGenre());
        preparedStatement.setInt(7, client.getAge());
        preparedStatement.setInt(8, client.getId_personne());

        preparedStatement.executeUpdate();
    }

    public int getClientId(String nom, String prenom, String mail, String mdp) throws SQLException {
        String query = "SELECT id_personne FROM client WHERE  nom_personne = ? AND prenom_personne = ? AND mail_personne = ? AND mdp_personne=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, mail);
            preparedStatement.setString(4, mdp);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id_personne");
                } else {
                    throw new SQLException("Client introuvable");
                }
            }
        }
    }

    @Override
    public List<Client> afficher() throws SQLException {
        List<Client> clientList = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM client c JOIN personne p ON p.id_personne = c.id_personne";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            int id_personne = rs.getInt("id_personne");
            String genre = rs.getString("genre");
            int age = rs.getInt("age");
            String nom = rs.getString("nom_personne");
            String prenom = rs.getString("prenom_personne");
            int tel = rs.getInt("numero_telephone");
            String mail = rs.getString("mail_personne");
            String mdp = rs.getString("mdp_personne");
            String image_personne = rs.getString("image_personne");

            Client client = new Client(id_personne, nom, prenom, tel, mail, mdp, image_personne, genre, age);
            clientList.add(client);
        }
        return clientList;
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sqlDeleteClient = "DELETE FROM client WHERE id_personne = ?";

// Préparer la déclaration SQL
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteClient)) {
            // Définir le paramètre de l'identifiant
            preparedStatement.setInt(1, id);

            // Exécuter la requête de suppression
            preparedStatement.executeUpdate();
        }

    }

    @Override
    public List<Client> rechercher(String recherche) throws SQLException {
        List<Client> Clients = new ArrayList<>();


        String sql = "SELECT p.id_personne, p.nom_personne, p.prenom_personne, p.numero_telephone, p.mail_personne, p.mdp_personne, p.image_personne " +
                "FROM Client a " +
                "JOIN personne p ON a.id_personne = p.id_personne " +
                "WHERE p.nom_personne LIKE ? ";


        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + recherche + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Client client = new Client();
            client.setId_personne(rs.getInt("id_personne"));
            client.setNom_personne(rs.getString("nom_personne"));
            client.setPrenom_personne(rs.getString("prenom_personne"));
            client.setNumero_telephone(rs.getInt("numero_telephone"));
            client.setMail_personne(rs.getString("mail_personne"));
            client.setMdp_personne(rs.getString("mdp_personne"));
            Clients.add(client);
        }
        return Clients;
    }

    @Override

    public List<Client> getAllClients() throws SQLException {

        List<Client> clients = new ArrayList<>();

        String query = "SELECT c.id_personne, p.nom_personne, p.prenom_personne " +

                "FROM client c " +

                "INNER JOIN personne p ON c.id_personne = p.id_personne"; // Assurez-vous que les noms de vos tables et colonnes sont corrects

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                Client client = new Client();

                client.setId_personne(resultSet.getInt("id_personne"));

                client.setNom_personne(resultSet.getString("nom_personne"));

                client.setPrenom_personne(resultSet.getString("prenom_personne"));

// Ajoutez d'autres propriétés de client si nécessaire

                clients.add(client);

            }

        }

        return clients;

    }


}


