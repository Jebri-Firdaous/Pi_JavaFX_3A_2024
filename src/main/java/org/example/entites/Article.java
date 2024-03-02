package org.example.entites;

import java.util.List;

public class Article {

    private int id_Article;
    private String nom_Article;
    private double prix_Article;
    private int quantite_Article;
    private TypeArticle type_article;
    private String description_article;
    private String photo_article; // Lien vers la photo de l'article
    private List<Commande> commandes; // Liste des commandes associées à cet article
    private boolean bestArticle;





    // Enum pour les types d'articles
    public enum TypeArticle {
        ELECTRONICS,
        CLOTHING,
        BOOKS,
        // Ajoutez d'autres types selon vos besoins
    }

    // Constructeurs, getters et setters

    public Article() {
    }

    public Article(int id_Article, String nom_Article, double prix_Article, int quantite_Article, TypeArticle type_article, String description_article, String photo_article) {
        this.id_Article = id_Article;
        this.nom_Article = nom_Article;
        this.prix_Article = prix_Article;
        this.quantite_Article = quantite_Article;
        this.type_article = type_article;
        this.description_article = description_article;
        this.photo_article = photo_article;
    }

    public Article(String nom_Article, double prix_Article, int quantite_Article, TypeArticle type_article, String description_article, String photo_article) {
        this.nom_Article = nom_Article;
        this.prix_Article = prix_Article;
        this.quantite_Article = quantite_Article;
        this.type_article = type_article;
        this.description_article = description_article;
        this.photo_article = photo_article;
    }

    public Article(int id_Article, String nom_Article, double prix_Article, int quantite_Article, TypeArticle type_article, String description_article, String photo_article, List<Commande> commandes) {
        this.id_Article = id_Article;
        this.nom_Article = nom_Article;
        this.prix_Article = prix_Article;
        this.quantite_Article = quantite_Article;
        this.type_article = type_article;
        this.description_article = description_article;
        this.photo_article = photo_article;
        this.commandes = commandes;
    }
    // Les autres constructeurs, getters et setters

    public int getId_Article() {
        return id_Article;
    }

    public void setId_Article(int id_Article) {
        this.id_Article = id_Article;
    }

    public String getNom_Article() {
        return nom_Article;
    }

    public void setNom_Article(String nom_Article) {
        this.nom_Article = nom_Article;
    }

    public double getPrix_Article() {
        return prix_Article;
    }

    public void setPrix_Article(double prix_Article) {
        this.prix_Article = prix_Article;
    }

    public int getQuantite_Article() {
        return quantite_Article;
    }

    public void setQuantite_Article(int quantite_Article) {
        this.quantite_Article = quantite_Article;
    }

    public TypeArticle getType_article() {
        return type_article;
    }

    public void setType_article(TypeArticle type_article) {
        this.type_article = type_article;
    }

    public String getDescription_article() {
        return description_article;
    }

    public void setDescription_article(String description_article) {
        this.description_article = description_article;
    }

    public String getPhoto_article() {
        return photo_article;
    }

    public void setPhoto_article(String photo_article) {
        this.photo_article = photo_article;
    }

    public List<Commande> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<Commande> commandes) {
        this.commandes = commandes;
    }
    public boolean isBestArticle() {
        return bestArticle;
    }

    public void setBestArticle(boolean bestArticle) {
        this.bestArticle = bestArticle;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id_Article=" + id_Article +
                ", nom_Article='" + nom_Article + '\'' +
                ", prix_Article=" + prix_Article +
                ", quantite_Article=" + quantite_Article +
                ", type_article=" + type_article +
                ", description_article='" + description_article + '\'' +
                ", photo_article='" + photo_article + '\'' +
                '}';
    }
}
