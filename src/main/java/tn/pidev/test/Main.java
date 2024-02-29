package tn.pidev.test;

import tn.pidev.entities.Client;
import tn.pidev.entities.Administrateur;
import tn.pidev.entities.Personne;
import tn.pidev.services.ServiceAdmin;
import tn.pidev.services.ServiceClient;
import tn.pidev.utils.MyDataBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        /*---------------------------------------------VERIFIER CONNEXION-----------------------------------------------------*/

        System.out.println(MyDataBase.getInstance());

        /*----------------------------------------------------AJOUT Admin-----------------------------------------------------*/

    ServiceAdmin sA = new ServiceAdmin();
   /*       try {
            sA.ajouter(new Administrateur("Firdaous","jebri",24500297,
                    "firdaous.jebri@gmail.com","abc","Gestion Transport"));
            System.out.println("Insertion completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/

        /*----------------------------------------------------MODIFIER ADMIN-----------------------------------------------------*/

try
        {
            sA.modifier(new Administrateur(22, "Firdaousffff", "jj",21027918,"saida.jebri@gmail.com", "abc" , "Gestion Transport"));
           System.out.println("Modification completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        /*----------------------------------------------------AFFICHER Admin -----------------------------------------------------*/
      /*  try {
            System.out.println(sA.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
        /*----------------------------------------------------Supprimer Admin -----------------------------------------------------*/
/*
       try {
            sA.supprimer(22);
            System.out.println("suppression avec succées");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        /*----------------------------------------------------ajouter Client-----------------------------------------------------*/
        ServiceClient sC = new ServiceClient();
      /*  try {
            sC.ajouter(new Client("Maha","sahnoun",24500297, "maha.sahnoun@gmail.com","mdp","Femme",15));
            System.out.println("Insertion completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }*/
        /*----------------------------------------------------modifier Client-----------------------------------------------------*/
      /*  try {
            sC.modifier(new Client(60, "FAFA", "JOUJOU",24500297,"saida.jebri@gmail.com", "abc",3 , "Homme",30));
            System.out.println("Modification completé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }*/
    }

}