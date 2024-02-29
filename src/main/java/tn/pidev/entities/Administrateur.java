package tn.pidev.entities;

public class Administrateur extends Personne {
    private  int id_personne  ;

    private String role;

    public Administrateur() {
    }
    public Administrateur(String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String role) {
        super(nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
        this.role= role;

    }


    public Administrateur(int id_personne, String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String role) {
        super(id_personne,nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
        this.role = role;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Administrateur{"
                +", Nom:= " + getNom_personne()
                +", prenom= " +getPrenom_personne()
                +",  tel= " +getNumero_telephone()
                +", mail= " +getMail_personne()+
                ", role= " + role + '\'' +
                '}';
    }
}
