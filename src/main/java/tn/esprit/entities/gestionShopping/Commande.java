package tn.esprit.entities.gestionShopping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commande {

    private int id_Commande;
    private int nombre_Article;
    private double prix_Totale;
    private Date delais_Commande;
    private List<Article> articles;
    private int id_Personne;
    private String nom_Personne;
    private String prenom_Personne;



    // Autres attributs et méthodes

    public Commande() {
        articles = new ArrayList<>();
    }

    public int getId_Commande() {
        return id_Commande;
    }

    public void setId_Commande(int id_Commande) {
        this.id_Commande = id_Commande;
    }

    public int getNombre_Article() {
        return nombre_Article;
    }

    public void setNombre_Article(int nombre_Article) {
        this.nombre_Article = nombre_Article;
    }

    public double getPrix_Totale() {
        return prix_Totale;
    }

    public void setPrix_Totale(double prix_Totale) {
        this.prix_Totale = prix_Totale;
    }

    public Date getDelais_Commande() {
        return delais_Commande;
    }

    public void setDelais_Commande(Date delais_Commande) {
        this.delais_Commande = delais_Commande;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getId_Personne() {
        return id_Personne;
    }

    public String getNom_Personne() {
        return nom_Personne;
    }

    public String getPrenom_Personne() {
        return prenom_Personne;
    }

    public void setId_Personne(int id_Personne) {
        this.id_Personne = id_Personne;
    }

    public void setNom_Personne(String nom_Personne) {
        this.nom_Personne = nom_Personne;
    }

    public void setPrenom_Personne(String prenom_Personne) {
        this.prenom_Personne = prenom_Personne;
    }
}
