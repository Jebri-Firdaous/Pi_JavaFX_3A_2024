package tn.pidev.services;

import tn.pidev.entities.Reservation;
import tn.pidev.utiles.MyDataBase;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceReservation implements IService<Reservation> {

    private final Connection connection;

    public ServiceReservation() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {

        String sql = "INSERT INTO `reservation` (`duree_reservation`, `prix_reservation`, `date_reservation`, `id_hotel`, `type_chambre`) VALUES (?, ?, ?, ? , ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, reservation.getDuree_reservation());
        preparedStatement.setFloat(2, reservation.getPrix_reservation());
        preparedStatement.setDate(3, Date.valueOf(reservation.getDate_reservation())); // Convertir LocalDate en Date SQL
        preparedStatement.setInt(4, reservation.getId_hotel());
        preparedStatement.setString(5, reservation.getType_chambre().toString());
        preparedStatement.executeUpdate();
    }


    @Override
    public void modifier(Reservation reservation) throws SQLException {

        String sql = "UPDATE reservation SET duree_reservation = ?, prix_reservation = ?, date_reservation = ?, type_chambre = ? WHERE ref_reservation = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setFloat(1, reservation.getDuree_reservation());
        preparedStatement.setFloat(2, reservation.getPrix_reservation());
        preparedStatement.setDate(3, Date.valueOf(reservation.getDate_reservation())); // Convertir LocalDate en Date SQL
        preparedStatement.setString(4, reservation.getType_chambre().toString());
        preparedStatement.setInt(5, reservation.getRef_reservation());
        preparedStatement.executeUpdate();
    }


    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from reservation where ref_reservation = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    public List<Reservation> afficher() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT " +
                "r.ref_reservation, " +
                "r.duree_reservation, " +
                "r.prix_reservation, " +
                "r.date_reservation, " +
                "r.id_hotel, " +
                "r.type_chambre, " +
                "h.nom_hotel " +
                "FROM " +
                "reservation r " +
                "JOIN " +
                "hotel h ON r.id_hotel = h.id_hotel";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setRef_reservation(rs.getInt("ref_reservation"));
                reservation.setDuree_reservation(rs.getFloat("duree_reservation"));
                reservation.setPrix_reservation(rs.getFloat("prix_reservation"));

                // Convertir la date de type Date SQL en LocalDate
                Date dateSql = rs.getDate("date_reservation");
                LocalDate dateReservation = dateSql.toLocalDate();
                reservation.setDate_reservation(dateReservation);

                reservation.setId_hotel(rs.getInt("id_hotel"));
                String typeChambreStr = rs.getString("type_chambre");
                Reservation.TypeChambre typeChambre = Reservation.TypeChambre.valueOf(typeChambreStr.toUpperCase());
                reservation.setType_chambre(typeChambre);
                reservations.add(reservation);
            }
        }
        return reservations;
    }


    public String getTypeChambreColumnName(Reservation.TypeChambre typeChambre) {
        switch (typeChambre) {
            case NORMAL:
                return "numero1";
            case STANDARD:
                return "numero2";
            case LUXE:
                return "numero3";
            default:
                throw new IllegalArgumentException("Type de chambre non valide : " + typeChambre);
        }
    }

    // Méthode pour calculer la période d'une réservation
    public long calculerPeriode(int refReservation) throws SQLException {
        long periode = 0;
        // Connexion à la base de données
        Connection connection = MyDataBase.getInstance().getConnection();

        // Requête SQL pour sélectionner la durée et la date de la réservation
        String query = "SELECT duree_reservation, date_reservation FROM reservation WHERE ref_reservation = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, refReservation);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer la durée et la date de la réservation depuis le résultat de la requête
                    float dureeReservation = resultSet.getFloat("duree_reservation");
                    LocalDate dateDebut = resultSet.getDate("date_reservation").toLocalDate();
                    // Calculer la date de fin en ajoutant la durée à la date de début
                    LocalDate dateFin = dateDebut.plusDays((long) dureeReservation);
                    // Calculer la période en jours
                    periode = java.time.temporal.ChronoUnit.DAYS.between(dateDebut, dateFin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
            throw e;
        } finally {
            // Fermer la connexion à la base de données
            if (connection != null) {
                connection.close();
            }
        }
        return periode;
    }


    public LocalDate calculerDateFinMinimale(int idHotel, Reservation.TypeChambre typeChambre) throws SQLException {
        LocalDate dateFinMinimale = LocalDate.MAX; // Initialisation à une valeur maximale pour obtenir la valeur minimale

        // Connexion à la base de données
        Connection connection = MyDataBase.getInstance().getConnection();

        // Requête SQL pour sélectionner la date de fin minimale en fonction du type de chambre
        String query = "SELECT MIN(date_reservation + INTERVAL duree_reservation DAY) FROM reservation WHERE id_hotel = ? AND type_chambre = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHotel);
            statement.setString(2, typeChambre.name()); // Utiliser le nom de l'énumérateur comme valeur de type_chambre
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer la date de fin minimale depuis le résultat de la requête
                    if (resultSet.getDate(1) != null) {
                        dateFinMinimale = resultSet.getDate(1).toLocalDate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
            throw e;
        }
        return dateFinMinimale;
    }


}
