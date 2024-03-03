package tn.esprit.entities.gestionMedecin;

public class Administrateur extends Personne {
    private String role;

    public Administrateur() {
    }

    public Administrateur(int id_personne, String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, int id_personne1, String role) {
        super(id_personne, nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
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
        return "Administrateur{" +
                ", role='" + role + '\'' +
                "} " + super.toString();
    }
}
