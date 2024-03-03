package tn.esprit.services.gestionShopping;




import tn.esprit.entities.gestionShopping.Article;

import java.sql.SQLException;
import java.util.List;

public interface IServiceArticle <T> {

    void ajouterArticle(T t) throws SQLException;
    void modifierArticle(T t) throws SQLException;
    void supprimerArticle(int id_Article) throws SQLException;
    List<T> afficherArticle() throws SQLException;

    List<Article> rechercherArticles(String recherche) throws SQLException;
}
