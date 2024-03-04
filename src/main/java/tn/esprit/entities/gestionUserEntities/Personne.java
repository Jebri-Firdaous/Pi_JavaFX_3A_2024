package tn.esprit.entities.gestionUserEntities;

public class Personne {
    private int id_personne;
    private String nom_personne;
    private String prenom_personne;
    private int numero_telephone;
    private String mail_personne;
    private String mdp_personne;
    private String image_personne;

    public Personne() {
    }

    public Personne(int id_personne, String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String image_personne) {
        this.id_personne = id_personne;
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.numero_telephone = numero_telephone;
        this.mail_personne = mail_personne;
        this.mdp_personne = mdp_personne;
        this.image_personne = image_personne;
    }

    public Personne(String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String image_personne) {
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.numero_telephone = numero_telephone;
        this.mail_personne = mail_personne;
        this.mdp_personne = mdp_personne;
        this.image_personne = image_personne;
    }

    public String getImage_personne() {
        return image_personne;
    }

    public void setImage_personne(String image_personne) {
        this.image_personne = image_personne;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public String getNom_personne() {
        return nom_personne;
    }

    public void setNom_personne(String nom_personne) {
        this.nom_personne = nom_personne;
    }

    public String getPrenom_personne() {
        return prenom_personne;
    }

    public void setPrenom_personne(String prenom_personne) {
        this.prenom_personne = prenom_personne;
    }

    public int getNumero_telephone() {
        return numero_telephone;
    }

    public void setNumero_telephone(int numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public String getMail_personne() {
        return mail_personne;
    }

    public void setMail_personne(String mail_personne) {
        this.mail_personne = mail_personne;
    }

    public String getMdp_personne() {
        return mdp_personne;
    }

    public void setMdp_personne(String mdp_personne) {
        this.mdp_personne = mdp_personne;
    }

    @Override
    public String toString() {
        return "tn.esprit.entities.gestionUserEntities.Personne{" +
                "nom_personne='" + nom_personne + '\'' +
                ", prenom_personne='" + prenom_personne + '\'' +
                ", numero_telephone=" + numero_telephone +
                ", mail_personne='" + mail_personne + '\'' +
                ", image_personne='" + image_personne + '\'' +
                '}';
    }
}
