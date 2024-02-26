package org.example.services;

import org.example.entities.Parking;
import org.example.entities.Place;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingService implements IService<Parking> {

    private Connection connection;

    public ParkingService() {
        connection= MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Parking parking) throws SQLException {
        String sql = "insert into parking(nom_parking, address_parking, nombre_place_max, nombre_place_occ, etat_parking) values (?, ?, ?, '0', '0')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, parking.getNom());
        preparedStatement.setString(2, parking.getAddresse());
        preparedStatement.setInt(3, parking.getNbPlaceMax());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Parking parking) throws SQLException {
        String sql = "update `parking` set `nom_parking` = ?, `address_parking` = ?, `nombre_place_max` = ? where `id_parking` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, parking.getNom());
        preparedStatement.setString(2, parking.getAddresse());
        preparedStatement.setInt(3, parking.getNbPlaceMax());
        preparedStatement.setInt(4, parking.getRef());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int ref) throws SQLException {
        String sql = "delete from parking where id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ref);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Parking> recuperer() throws SQLException {
        String sql = "select * from parking";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Parking> parkings = new ArrayList<>();
        while (rs.next()) {
            Parking p = new Parking();
            p.setRef(rs.getInt("id_parking"));
            p.setNom(rs.getString("nom_parking"));
            p.setAddresse(rs.getString("address_parking"));
            p.setNbPlaceMax(rs.getInt("nombre_place_max"));
            p.setNbPlaceOcc(rs.getInt("nombre_place_occ"));
            p.setEtat(rs.getString("etat_parking"));

            parkings.add(p);
        }
        return parkings;
    }
    public Parking recupererById(int id) throws SQLException {
        String sql = "select * from parking where id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery(sql);
        Parking p = new Parking();
        if (rs.next()) {
            p.setRef(rs.getInt("id_parking"));
            p.setNom(rs.getString("nom_parking"));
            p.setAddresse(rs.getString("address_parking"));
            p.setNbPlaceMax(rs.getInt("nombre_place_max"));
            p.setNbPlaceOcc(rs.getInt("nombre_place_occ"));
            p.setEtat(rs.getString("etat_parking"));
        }
        return p;
    }
    public int calculNbPlace(int id) throws SQLException {
        String sql = "select count(*) from place pl join parking pr on pl.id_parking = pr.id_parking where pl.id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    public void updateNbOcc(Parking parking) throws SQLException {
        if(parking.getNbPlaceMax()>parking.getNbPlaceOcc()+1) {
            String sql = "update `parking` set `nombre_place_occ` = ?, `etat_parking` = ? where `id_parking` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, parking.getNbPlaceOcc()+1);
            preparedStatement.setString(2, "Disponible");
            preparedStatement.setInt(3, parking.getRef());
            preparedStatement.executeUpdate();
        }else
        if(parking.getNbPlaceMax()==parking.getNbPlaceOcc()+1) {
            String sql = "update `parking` set `nombre_place_occ` = ?, `etat_parking` = ? where `id_parking` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, parking.getNbPlaceOcc()+1);
            preparedStatement.setString(2, "Plein");
            preparedStatement.setInt(3, parking.getRef());
            preparedStatement.executeUpdate();
        }
    }
}
