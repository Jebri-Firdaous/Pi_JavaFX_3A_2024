package tn.esprit.services.ParkingServices;

import tn.esprit.entities.ParkingEntities.Parking;
import tn.esprit.utils.Parking.MyDataBase;

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
        System.out.println(parking.getLati());
        String sql = "insert into parking(nom_parking, address_parking, nombre_place_max, nombre_place_occ, latitude, longitude, etat_parking) values (?, ?, ?, '0', ?, ?, 'Disponible')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, parking.getNom());
        preparedStatement.setString(2, parking.getAddresse());
        preparedStatement.setInt(3, parking.getNbPlaceMax());
        preparedStatement.setFloat(4, parking.getLati());
        preparedStatement.setFloat(5, parking.getLongi());
        preparedStatement.executeUpdate();
    }
    public void ajouterFull(Parking parking) throws SQLException {
        System.out.println(parking.getLati());
        String sql = "insert into parking(id_parking, nom_parking, address_parking, nombre_place_max, nombre_place_occ, latitude, longitude, etat_parking) values (?, ?, ?, ?, '0', ?, ?, 'Disponible')";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, parking.getRef());
        preparedStatement.setString(2, parking.getNom());
        preparedStatement.setString(3, parking.getAddresse());
        preparedStatement.setInt(4, parking.getNbPlaceMax());
        preparedStatement.setFloat(5, parking.getLati());
        preparedStatement.setFloat(6, parking.getLongi());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Parking parking) throws SQLException {
        String sql = "update `parking` set `nom_parking` = ?, `address_parking` = ?, `nombre_place_max` = ?, `latitude` = ?, `longitude` = ? where `id_parking` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, parking.getNom());
        preparedStatement.setString(2, parking.getAddresse());
        preparedStatement.setInt(3, parking.getNbPlaceMax());
        preparedStatement.setFloat(4, parking.getLati());
        preparedStatement.setFloat(5, parking.getLongi());
        preparedStatement.setInt(6, parking.getRef());
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
            p.setLati(rs.getFloat("latitude"));
            p.setLongi(rs.getFloat("longitude"));
            p.setEtat(rs.getString("etat_parking"));

            parkings.add(p);
        }
        return parkings;
    }
    public Parking recupererById(int id) throws SQLException {
        String sql = "select * from parking where `id_parking` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Parking p = new Parking();
        if (rs.next()) {
            p.setRef(rs.getInt("id_parking"));
            p.setNom(rs.getString("nom_parking"));
            p.setAddresse(rs.getString("address_parking"));
            p.setNbPlaceMax(rs.getInt("nombre_place_max"));
            p.setNbPlaceOcc(rs.getInt("nombre_place_occ"));
            p.setLati(rs.getFloat("latitude"));
            p.setLongi(rs.getFloat("longitude"));
            p.setEtat(rs.getString("etat_parking"));
        }
        return p;
    }
    public List<Parking> recupererByEtat(String et) throws SQLException {
        String sql = "select * from parking where `etat_parking` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, et);
        ResultSet rs = preparedStatement.executeQuery();
        List<Parking> parkings = new ArrayList<>();
        while (rs.next()) {
            Parking p = new Parking();
            p.setRef(rs.getInt("id_parking"));
            p.setNom(rs.getString("nom_parking"));
            p.setAddresse(rs.getString("address_parking"));
            p.setNbPlaceMax(rs.getInt("nombre_place_max"));
            p.setNbPlaceOcc(rs.getInt("nombre_place_occ"));
            p.setLati(rs.getFloat("latitude"));
            p.setLongi(rs.getFloat("longitude"));
            p.setEtat(rs.getString("etat_parking"));

            parkings.add(p);
        }
        return parkings;
    }
    public int calculNbPlace(int id) throws SQLException {
        String sql = "select count(*) from place pl join parking pr on pl.id_parking = pr.id_parking where pl.id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt(1);
    }
    public void updateNbOcc(Parking parking, int choice) throws SQLException {
        if(choice==0) {
            String sql = "update `parking` set `nombre_place_occ` = ?, `etat_parking` = ? where `id_parking` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println(parking.getNbPlaceOcc()+"1");
            preparedStatement.setInt(1, parking.getNbPlaceOcc() + 1);
            if (calculNbPlace(parking.getRef()) > parking.getNbPlaceOcc() + 1)
                preparedStatement.setString(2, "Disponible");
            else if (calculNbPlace(parking.getRef()) == parking.getNbPlaceOcc() + 1)
                preparedStatement.setString(2, "Plein");
            else
                preparedStatement.setString(2, "err");
            preparedStatement.setInt(3, parking.getRef());
            preparedStatement.executeUpdate();
        }
        else{
            String sql = "update `parking` set `nombre_place_occ` = ?, `etat_parking` = ? where `id_parking` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            System.out.println(parking.getNbPlaceOcc()+"3");
            preparedStatement.setInt(1, parking.getNbPlaceOcc() - 1);
            preparedStatement.setString(2, "Disponible");
            preparedStatement.setInt(3, parking.getRef());
            preparedStatement.executeUpdate();
        }
    }
}
