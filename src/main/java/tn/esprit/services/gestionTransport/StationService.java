package tn.esprit.services.gestionTransport;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StationService implements IService<Station> {
    private Connection connection;
    @FXML
    public ComboBox<String> typeComboBox;
    public StationService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    public void ajouter(Station station) throws SQLException {

      /*  String type = typeComboBox.getValue();
        String sql = "insert into station (nom_station,adress_station,type) " +
                "values('" + station.getNom_station() + "','" + station.getAdress_station() + "'" +
                ",'" + type + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);*/
        try {

            // Préparation de la requête SQL avec des paramètres pour éviter les injections SQL
            String sql = "INSERT INTO station (nom_station, adress_station, type) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, station.getNom_station());
            statement.setString(2, station.getAdress_station());
            statement.setString(3, station.getType());

            // Exécution de la requête SQL
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void modifier(int idstation,String noms,String adresss,String type) throws SQLException {
        String sql = "Update station set nom_station = ?, adress_station = ?  , type= ?  where id_station = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,noms);
        preparedStatement.setString(2, adresss);
        preparedStatement.setString(3, type);
        preparedStatement.setInt(4, idstation);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from station where id_station = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Station> recuperer() throws SQLException {
        String sql = "select * from station";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Station> stations = new ArrayList<>();
        while (rs.next()) {
            Station s = new Station();
            s.setId_station(rs.getInt("id_station"));
            s.setNom_station(rs.getString("nom_station"));
            s.setAdress_station(rs.getString("Adress_station"));
             s.setType(rs.getString("type"));

            stations.add(s);
            System.out.println("goooooooood");
        }
        return stations;
    }
    public Station getStationByNom(String nom) throws SQLException {
        String sql = "SELECT `id_station`, `nom_station`, `adress_station`, `type` FROM `station` WHERE `nom_station` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nom);
        ResultSet rs = preparedStatement.executeQuery();
        Station s = new Station();
        if (rs.next()) {
            s.setId_station(rs.getInt("id_station"));
            s.setNom_station(rs.getString("nom_station"));
            s.setAdress_station(rs.getString("adress_station")); // Corrected case of 'adress_station'
            s.setType(rs.getString("type"));
        }
        return s;
    }
    public Station getStationById(int id) throws SQLException {
        System.out.println(id + "test ");
        String sql = "SELECT `id_station`, `nom_station`, `adress_station`, `type` FROM `station` WHERE `id_station` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Station s = new Station();
        if (rs.next()) {
            s.setId_station(rs.getInt("id_station"));
            s.setNom_station(rs.getString("nom_station"));
            s.setAdress_station(rs.getString("adress_station")); // Corrected case of 'adress_station'
            s.setType(rs.getString("type"));
        }
        return s;
    }


    public boolean stationExists(String nom, String adresse) throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            // Requête SQL pour vérifier l'existence d'une station avec le même nom et la même adresse
            String query = "SELECT COUNT(*) FROM station WHERE nom_station = ? AND adress_station = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, adresse);

            resultSet = preparedStatement.executeQuery();

            // Si le résultat de la requête est supérieur à zéro, la station existe déjà
            return resultSet.next() && resultSet.getInt(1) > 0;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

    }
}



