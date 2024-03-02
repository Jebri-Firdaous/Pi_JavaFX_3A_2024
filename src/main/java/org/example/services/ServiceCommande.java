package org.example.services;

import org.example.entites.Article;
import org.example.entites.Commande;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande implements IServiceCommande<Commande> {

    private Connection connection;

    public ServiceCommande() {
        connection = MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouterCommande(Commande commande) throws SQLException {
        if (commande.getNombre_Article() < 1) {
            throw new IllegalArgumentException("Une commande doit contenir au moins un article.");
        }

        String sql = "INSERT INTO commande (nombre_article, prix_totale, delais_commande) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, commande.getNombre_Article());
        preparedStatement.setDouble(2, commande.getPrix_Totale());
        preparedStatement.setDate(3, new java.sql.Date(commande.getDelais_Commande().getTime()));
        preparedStatement.executeUpdate();

        // Récupérer l'ID de la commande nouvellement insérée
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        int id_Commande;
        if (generatedKeys.next()) {
            id_Commande = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Échec de la création de la commande, aucun ID généré.");
        }

        // Insérer les articles associés à la commande dans la table de liaison commande_article
        for (Article article : commande.getArticles()) {
            String insertCommandeArticleQuery = "INSERT INTO commande_article (id_commande, id_article) VALUES (?, ?)";
            PreparedStatement commandeArticleStatement = connection.prepareStatement(insertCommandeArticleQuery);
            commandeArticleStatement.setInt(1, id_Commande);
            commandeArticleStatement.setInt(2, article.getId_Article());
            commandeArticleStatement.executeUpdate();
        }
    }


    @Override

    public void modifierCommande(Commande commande) throws SQLException {
        // Assurez-vous de mettre à jour votre requête SQL pour inclure la colonne Etat_Commande
        String sql = "UPDATE commande SET nombre_article = ?, prix_totale = ?, delais_commande = ? WHERE id_commande = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, commande.getNombre_Article());
            preparedStatement.setDouble(2, commande.getPrix_Totale());
            preparedStatement.setDate(3, new java.sql.Date(commande.getDelais_Commande().getTime()));
            preparedStatement.setInt(4, commande.getId_Commande());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimerCommande(int id_Commande) throws SQLException {
        // Supprimer la commande de la table de jointure commande_article
        String deleteCommandeArticleQuery = "DELETE FROM commande_article WHERE id_commande = ?";
        PreparedStatement deleteCommandeArticleStatement = connection.prepareStatement(deleteCommandeArticleQuery);
        deleteCommandeArticleStatement.setInt(1, id_Commande);
        deleteCommandeArticleStatement.executeUpdate();

        // Supprimer la commande
        String deleteCommandeQuery = "DELETE FROM commande WHERE id_commande = ?";
        PreparedStatement deleteCommandeStatement = connection.prepareStatement(deleteCommandeQuery);
        deleteCommandeStatement.setInt(1, id_Commande);
        deleteCommandeStatement.executeUpdate();
    }

    public List<Article> getArticlesDejaInseres(Commande commande) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.* " +
                "FROM article a " +
                "INNER JOIN commande_article ca ON a.id_article = ca.id_article " +
                "WHERE ca.id_commande = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, commande.getId_Commande());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setId_Article(resultSet.getInt("id_article"));
                    article.setNom_Article(resultSet.getString("nom_article"));
                    article.setPrix_Article(resultSet.getDouble("prix_article"));
                    // Ajoutez d'autres attributs si nécessaire
                    articles.add(article);
                }
            }
        }
        return articles;
    }


    @Override
    public List<Commande> afficherCommande() throws SQLException {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT c.id_commande, c.nombre_article, c.prix_totale, c.delais_commande, GROUP_CONCAT(a.id_article) as id_articles, GROUP_CONCAT(a.nom_article) as nom_articles " +
                "FROM commande c " +
                "INNER JOIN commande_article ca ON c.id_commande = ca.id_commande " +
                "INNER JOIN article a ON ca.id_article = a.id_article " +
                "GROUP BY c.id_commande";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Commande commande = new Commande();
                commande.setId_Commande(rs.getInt("id_commande"));
                commande.setNombre_Article(rs.getInt("nombre_article"));
                commande.setPrix_Totale(rs.getDouble("prix_totale"));
                commande.setDelais_Commande(rs.getDate("delais_commande"));
                String[] idArticles = rs.getString("id_articles").split(",");
                String[] nomArticles = rs.getString("nom_articles").split(",");
                List<Article> articles = new ArrayList<>();
                for (int i = 0; i < idArticles.length; i++) {
                    Article article = new Article();
                    article.setId_Article(Integer.parseInt(idArticles[i]));
                    article.setNom_Article(nomArticles[i]);
                    articles.add(article);
                }
                commande.setArticles(articles);
                commandes.add(commande);
            }
        }
        return commandes;
    }

}
