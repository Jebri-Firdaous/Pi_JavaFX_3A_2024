package org.example.test;

import org.example.entites.Article;
import org.example.services.ServiceArticle;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServiceArticle serviceArticle = new ServiceArticle();

        try {
            // Création d'un article existant à modifier (remplacez les valeurs par celles de l'article que vous souhaitez modifier)
            Article articleAModifier = new Article();
            articleAModifier.setId_Article(34); // Remplacez 1 par l'ID de l'article à modifier
            articleAModifier.setNom_Article("Nouveau nom");
            articleAModifier.setPrix_Article(25.99);
            articleAModifier.setQuantite_Article(50);

            // Affichage de l'article avant modification
            System.out.println("Article avant modification : " + articleAModifier);

            // Modification de l'article
            serviceArticle.modifierArticle(articleAModifier);
            System.out.println("Article modifié avec succès.");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
