package org.example.services;

import org.example.entities.Parking;
import org.example.entities.Place;
import org.example.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceService implements IService<Place>{
    private Connection connection;

    public PlaceService() {
        connection= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Place place) throws SQLException {
        String sql = "insert into place(num_place, type_place, etat, id_parking) values (?, ?, 'Libre', ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, place.getNum_place());
        preparedStatement.setString(2, place.getType_place());
        preparedStatement.setInt(3, place.getId_Parking());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(Place place) throws SQLException {
        String sql = "update `place` set `num_place` = ?, `type_place` = ? where `ref_place` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, place.getNum_place());
        preparedStatement.setString(2, place.getType_place());
        preparedStatement.setInt(3, place.getRef_place());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int ref) throws SQLException {
        String sql = "delete from place where ref_place = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, ref);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Place> recuperer() throws SQLException {
        String sql = "select * from place";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Place> places = new ArrayList<>();
        while (rs.next()) {
            Place p = new Place();
            p.setRef_place(rs.getInt("ref_place"));
            p.setNum_place(rs.getInt("num_place"));
            p.setType_place(rs.getString("type_place"));
            p.setEtat(rs.getString("etat"));
            p.setId_Parking(rs.getInt("id_parking"));

            places.add(p);
        }
        return places;
    }
    public List<Place> recupererFiltrer(int id) throws SQLException {
        String sql = "select * from place pl join parking pr on pl.id_parking = pr.id_parking where pl.id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        List<Place> places = new ArrayList<>();
        while (rs.next()) {
            Place p = new Place();
            p.setRef_place(rs.getInt("ref_place"));
            p.setNum_place(rs.getInt("num_place"));
            p.setType_place(rs.getString("type_place"));
            p.setEtat(rs.getString("etat"));
            p.setId_Parking(rs.getInt("id_parking"));

            places.add(p);
        }
        return places;
    }
    public boolean chercher(int num_place, int refParking) throws SQLException {
        String sql = "select count(*) from place where  num_place = ? and id_parking = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, num_place);
        preparedStatement.setInt(2, refParking);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();
        return rs.getInt(1) != 0;
    }
    public void updateEtat(int ref, int choice) throws SQLException {
        if (choice==0) {
            String sql = "update `place` set `etat` = 'Reservee' where `ref_place` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ref);
            preparedStatement.executeUpdate();
        }
        if (choice==1){
            String sql = "update `place` set `etat` = 'Libre' where `ref_place` = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ref);
            preparedStatement.executeUpdate();
        }
    }
}
