package tn.esprit.test;
import tn.esprit.services.ServiceMedecin;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServiceMedecin serviceMedecin = new ServiceMedecin();
//        Ajouter medecin
/*        try {
            serviceMedecin.ajouter(new medecin("karim","BenSayed",
                    98989898,"Ariana","cardiologie"));
            System.out.println("Insertion completed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/

//        Modication
/*        try {
            serviceMedecin.modifier(new medecin(2,"keffjim","BenSaffyed",
                    844444,"Ariana","Cardiologie"));
            System.out.println("modification");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
//        Suppression
/*        try {
            serviceMedecin.supprimer(2);
            System.out.println("suppression avec succées");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
//        Afficher tous les medecins
        try {
            System.out.println(serviceMedecin.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}