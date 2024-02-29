package tn.pidev.services;

import tn.pidev.entities.Client;
import tn.pidev.entities.Client;
import tn.pidev.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceClient implements IService<Client> {
    private Connection connection;
/*--------------------------------------------------AJOUT------------------------------------------------------*/

    public ServiceClient() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Client client) throws SQLException {
        // Insertion dans la table "personne"
        String sqlInsertPersonne = "INSERT INTO `personne`(`nom_personne`, `prenom_personne`, `numero_telephone`, `mail_personne`, `mdp_personne`) VALUES (?, ?, ?, ?, ?)";

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

        // Insertion dans la table "client" en utilisant les données de la table "personne"
        String sqlInsertClient = "INSERT INTO client (id_personne,genre,age) " +
                "SELECT id_personne,?,?,  FROM personne WHERE id_personne = ?";
        PreparedStatement preparedStatementClient = connection.prepareStatement(sqlInsertClient);
        preparedStatementClient.setString(1, client.getGenre());
        preparedStatementClient.setInt(2, client.getAge());
        preparedStatementClient.setInt(3, idPersonne); // Utilisation de l'ID de la personne récupérée précédemment
        preparedStatementClient.executeUpdate();
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

    @Override
    public List<Client> afficher() throws SQLException {
        List<Client>
                clientList = new ArrayList<>();
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

            Client client = new Client(id_personne,nom, prenom, tel, mail, mdp, genre,age);
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
}


