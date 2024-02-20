package tn.esprit.entities;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class Medecin {
    public int id_medecin;
    public String nom_medecin;
    public String prenom_medecin_medecin;
    public int 	numero_telephone_medecin;
    public String address_medecin;
    public String specialite_medecin;

    public Medecin() {
    }

    public Medecin(int id_medecin, String nom_medecin, String prenom_medecin_medecin,
                   int numero_telephone_medecin, String address_medecin, String specialite_medecin) {
        this.id_medecin = id_medecin;
        this.nom_medecin = nom_medecin;
        this.prenom_medecin_medecin = prenom_medecin_medecin;
        this.numero_telephone_medecin = numero_telephone_medecin;
        this.address_medecin = address_medecin;
        this.specialite_medecin = specialite_medecin;
    }

    public Medecin(String nom_medecin, String prenom_medecin_medecin, int numero_telephone_medecin, String address_medecin, String specialite_medecin) {
        this.nom_medecin = nom_medecin;
        this.prenom_medecin_medecin = prenom_medecin_medecin;
        this.numero_telephone_medecin = numero_telephone_medecin;
        this.address_medecin = address_medecin;
        this.specialite_medecin = specialite_medecin;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public String getNom_medecin() {
        return nom_medecin;
    }

    public void setNom_medecin(String nom_medecin) {
        this.nom_medecin = nom_medecin;
    }

    public String getPrenom_medecin_medecin() {
        return prenom_medecin_medecin;
    }

    public void setPrenom_medecin_medecin(String prenom_medecin_medecin) {
        this.prenom_medecin_medecin = prenom_medecin_medecin;
    }

    public int getNumero_telephone_medecin() {
        return numero_telephone_medecin;
    }

    public void setNumero_telephone_medecin(int numero_telephone_medecin) {
        this.numero_telephone_medecin = numero_telephone_medecin;
    }

    public String getAddress_medecin() {
        return address_medecin;
    }

    public void setAddress_medecin(String address_medecin) {
        this.address_medecin = address_medecin;
    }

    public String getSpecialite_medecin() {
        return specialite_medecin;
    }

    public void setSpecialite_medecin(String specialite_medecin) {
        this.specialite_medecin = specialite_medecin;
    }

//    toString()
  /*  @Override
    public String toString() {
        return "medecin{" +
                "id_medecin=" + id_medecin +
                ", nom_medecin='" + nom_medecin + '\'' +
                ", prenom_medecin_medecin='" + prenom_medecin_medecin + '\'' +
                ", numero_telephone_medecin=" + numero_telephone_medecin +
                ", address_medecin='" + address_medecin + '\'' +
                ", specialite_medecin='" + specialite_medecin + '\'' +
                '}';
    }*/

//    @Override
//    public String toString() {
//        return String.format("%s %s, Numéro de téléphone: %d, Adresse: %s, Spécialité: %s",
//                prenom_medecin_medecin, nom_medecin, numero_telephone_medecin,
//                address_medecin, specialite_medecin);
//    }
    public String toStringNomPrenom() {
        StringBuilder sb = new StringBuilder();
        sb.append(prenom_medecin_medecin).append(" ").append(nom_medecin).append("\n");
        return sb.toString();
    }




    @Override
    public String toString() {
        return String.format("%s %s",
                prenom_medecin_medecin, nom_medecin, numero_telephone_medecin,
                address_medecin, specialite_medecin);
    }
}
