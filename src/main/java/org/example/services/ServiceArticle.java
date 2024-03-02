package org.example.services;

import org.example.entites.Article;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceArticle implements IServiceArticle<Article> {

    private Connection connection;

    public ServiceArticle() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouterArticle(Article article) throws SQLException {
        if (article.getType_article() == null) {
            throw new IllegalArgumentException("Le type d'article ne peut pas être null.");
        }

        // Ajouter l'article à la base de données, la relation avec les commandes sera gérée par le service ServiceCommande
        String sql = "INSERT INTO article (nom_article, prix_article, quantite_article, type_article, description_article, photo_article) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getNom_Article());
        preparedStatement.setDouble(2, article.getPrix_Article());
        preparedStatement.setInt(3, article.getQuantite_Article());
        preparedStatement.setString(4, article.getType_article().name()); // Utilisation de l'enum
        preparedStatement.setString(5, article.getDescription_article());
        preparedStatement.setString(6, article.getPhoto_article());
        preparedStatement.executeUpdate();
    }
    @Override
    public List<Article> rechercherArticles(String recherche) throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id_article, a.nom_article, a.prix_article, a.quantite_article, a.type_article, a.description_article, a.photo_article " +
                "FROM article a " +
                "WHERE a.nom_article LIKE ? OR a.description_article LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + recherche + "%");
        preparedStatement.setString(2, "%" + recherche + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Article article = new Article();
            article.setId_Article(rs.getInt("id_article"));
            article.setNom_Article(rs.getString("nom_article"));
            article.setPrix_Article(rs.getDouble("prix_article"));
            article.setQuantite_Article(rs.getInt("quantite_article"));
            article.setType_article(Article.TypeArticle.valueOf(rs.getString("type_article"))); // Convertir la chaîne en enum
            article.setDescription_article(rs.getString("description_article"));
            article.setPhoto_article(rs.getString("photo_article"));
            articles.add(article);
        }
        return articles;
    }


    @Override
    public void modifierArticle(Article article) throws SQLException {
        // Cette méthode ne nécessite pas de modification car elle ne concerne que la mise à jour d'un article spécifique
        String sql = "UPDATE article SET nom_article = ?, prix_article = ?, quantite_article = ?, type_article = ?, description_article = ?, photo_article = ? WHERE id_article = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, article.getNom_Article());
        preparedStatement.setDouble(2, article.getPrix_Article());
        preparedStatement.setInt(3, article.getQuantite_Article());
        preparedStatement.setString(4, article.getType_article().name()); // Utilisation de l'enum
        preparedStatement.setString(5, article.getDescription_article());
        preparedStatement.setString(6, article.getPhoto_article());
        preparedStatement.setInt(7, article.getId_Article());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimerArticle(int id_Article) throws SQLException {
        // Supprimer l'article de la table article_commande avant de le supprimer de la table article
        String deleteArticleCommandeQuery = "DELETE FROM commande_article WHERE id_article = ?";
        PreparedStatement deleteArticleCommandeStatement = connection.prepareStatement(deleteArticleCommandeQuery);
        deleteArticleCommandeStatement.setInt(1, id_Article);
        deleteArticleCommandeStatement.executeUpdate();

        // Supprimer l'article de la table article
        String deleteArticleQuery = "DELETE FROM article WHERE id_article = ?";
        PreparedStatement deleteArticleStatement = connection.prepareStatement(deleteArticleQuery);
        deleteArticleStatement.setInt(1, id_Article);
        deleteArticleStatement.executeUpdate();
    }

    @Override
    public List<Article> afficherArticle() throws SQLException {
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT a.id_article, a.nom_article, a.prix_article, a.quantite_article, a.type_article, a.description_article, a.photo_article " +
                "FROM article a " ;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Article article = new Article();
            article.setId_Article(rs.getInt("id_article"));
            article.setNom_Article(rs.getString("nom_article"));
            article.setPrix_Article(rs.getDouble("prix_article"));
            article.setQuantite_Article(rs.getInt("quantite_article"));
            article.setType_article(Article.TypeArticle.valueOf(rs.getString("type_article"))); // Convertir la chaîne en enum
            article.setDescription_article(rs.getString("description_article"));
            article.setPhoto_article(rs.getString("photo_article"));
            articles.add(article);
        }
        return articles;
    }



    @Override

    public void toggleBestArticle(int articleId, boolean isBestArticle) throws SQLException {
        // Vérifier si l'article existe avant de mettre à jour
        if (!articleExists(articleId)) {
            throw new IllegalArgumentException("L'article avec l'identifiant " + articleId + " n'existe pas.");
        }

        try {
            // Désactiver l'autocommit pour gérer manuellement la transaction
            connection.setAutoCommit(false);

            // Préparer et exécuter la requête de mise à jour
            String sql = "UPDATE article SET bestArticle = ? WHERE id_article = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setBoolean(1, isBestArticle);
                preparedStatement.setInt(2, articleId);
                int rowsAffected = preparedStatement.executeUpdate();

                // Vérifier si la mise à jour a réussi
                if (rowsAffected == 0) {
                    throw new SQLException("La mise à jour de l'article avec l'identifiant " + articleId + " a échoué.");
                }

                // Valider la transaction
                connection.commit();
            } catch (SQLException e) {
                // En cas d'erreur, annuler la transaction
                connection.rollback();
                throw e;
            } finally {
                // Réactiver l'autocommit une fois la transaction terminée
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw e;
        }
    }







    // Méthode utilitaire pour vérifier si un article existe dans la base de données
    private boolean articleExists(int articleId) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM article WHERE id_article = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }






}
