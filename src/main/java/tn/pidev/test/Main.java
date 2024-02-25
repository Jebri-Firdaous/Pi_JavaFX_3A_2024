package tn.pidev.test;

import tn.pidev.entities.Administrateur;
import tn.pidev.entities.Personne;
import tn.pidev.services.ServiceAdmin;
import tn.pidev.utils.MyDataBase;

import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        /*---------------------------------------------VERIFIER CONNEXION-----------------------------------------------------*/

        System.out.println(MyDataBase.getInstance());

        /*----------------------------------------------------AJOUT-----------------------------------------------------*/

        ServiceAdmin sA = new ServiceAdmin();
        try {
            sA.ajouter(new Administrateur("Firdaous","jebri",24500297,
                    "firdaous.jebri@gmail.com","abc","Gestion Transport"));
            System.out.println("Insertion completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }

        /*----------------------------------------------------MODIFIER ADMIN-----------------------------------------------------*/


       try {
            sA.modifier(new Administrateur(41, "FAFA", "JOUJOU",24500297,"saida.jebri@gmail.com", "abc",16 , "Gestion Transport"));
           System.out.println("Modification completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        /*----------------------------------------------------AFFICHER -----------------------------------------------------*/
        try {
            System.out.println(sA.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}