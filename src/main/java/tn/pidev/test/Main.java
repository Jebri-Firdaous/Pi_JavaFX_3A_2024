package tn.pidev.test;

import tn.pidev.entities.TourismeEntities.Hotel;
import tn.pidev.entities.TourismeEntities.Reservation;
import tn.pidev.services.TourismeService.ServiceHotel;
import tn.pidev.services.TourismeService.ServiceReservation;

import java.sql.SQLException;
import java.time.LocalDate;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {


//        System.out.println(MyDataBase.getInstance());
//        System.out.println(MyDataBase.getInstance());

        ServiceHotel sh = new ServiceHotel();

       /* try {
            sh.ajouter(new Hotel("mouradi", "bhoumtsouk" , 2 , 3 , 5));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
*/

        try {
            sh.modifier(new Hotel(2,"kasino", "bhoumtsouk" , 1, 1 , 1 , 2 , 3 , 4));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            sh.supprimer(4);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sh.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }






        // tableau Reservation


        String sql = "SELECT type_chambre FROM reservation WHERE type_chambre in {'normal', 'standard', 'luxe'}";

        ServiceReservation sr = new ServiceReservation();

      try {
          LocalDate dateReservation = LocalDate.parse("2028-11-07");

// Ensuite, vous pouvez utiliser cette date pour créer une instance de Reservation
          sr.ajouter(new Reservation(12.5f, 45.2f, dateReservation, 2, Reservation.TypeChambre.LUXE));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            // Convertir la chaîne de date en objet LocalDate
            LocalDate dateReservation = LocalDate.parse("7-11-2028");

            // Utilisez l'objet LocalDate pour créer la réservation
            sr.ajouter(new Reservation(12.5f, 45.2f, dateReservation, 2 , Reservation.TypeChambre.NORMAL));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }






        try {
            sr.supprimer(8);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(sr.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }









    }
}