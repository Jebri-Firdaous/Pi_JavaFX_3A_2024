package tn.esprit.services.gestionTransport;

import tn.esprit.entities.gestionTransport.Station;
import tn.esprit.entities.gestionTransport.billet;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BilletService implements IServiceBillet<billet>
{
    private Connection connection ;
    public BilletService()
    {
        connection= MyDataBase.getInstance().getConnection();
    }


    @Override
    public void ajouter (billet billet ) throws SQLException
    {

        String sql = "INSERT INTO billet (destination_voyage, date_depart, station, prix, duree,id_personne) " +
                "VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,billet.getDestination_voyage());
       preparedStatement.setTimestamp(2,billet.getDate_depart());
        preparedStatement.setInt(3,billet.getId_station());
        preparedStatement.setString(4,billet.getPrix());
        preparedStatement.setString(5, billet.getDuree());
        preparedStatement.setInt(6,billet.getId_personne());
        preparedStatement.executeUpdate();

    }




    @Override
    public void modifierBillet(int ref,String destination ,Timestamp date,int id_station ,String prix,String duree) throws SQLException {
        String sql = "Update billet set destination_voyage = ?, date_depart = ?  , station = ? , prix=? , duree=?  where ref_voyage = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,destination);
        preparedStatement.setTimestamp(2,date);
        preparedStatement.setInt(3,id_station);
        preparedStatement.setString(4, prix);
        preparedStatement.setString(5, duree);
        preparedStatement.setInt(6, ref);
        preparedStatement.executeUpdate();
    }

    public void changePrixBilletByRef(int ref,String prix) throws SQLException {
        String sql = "Update billet set prix= ?  where ref_voyage = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, prix);
        preparedStatement.setInt(2, ref);
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from billet where ref_voyage = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<billet> recuperer() throws SQLException {
        String sql = "select * from billet";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<billet> billets = new ArrayList<>();
        while (rs.next()) {
            billet billet = new billet();
            billet.setRef_voyage(rs.getInt("ref_voyage"));
            billet.setDestination_voyage(rs.getString("destination_voyage"));
            billet.setDate_depart(rs.getTimestamp("date_depart"));
            billet.setId_station(rs.getInt("station"));
            billet.setDuree(rs.getString("duree"));
            billet.setPrix(rs.getString("prix"));
            billet.setId_personne(rs.getInt("id_personne"));
            billets.add(billet);
            System.out.println("goooooooood");
        }
        return billets;
    }
    @Override
    public List<Station> getStations() throws SQLException {
        List<Station> listStation = new ArrayList<>();
        String sql = "SELECT DISTINCT `id_station`,`nom_station`,`adress_station`,`type` FROM `Station` ";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            Station station=new Station();
   station.setNom_station(rs.getString("nom_station"));
   station.setId_station(rs.getInt("id_station"));
   station.setAdress_station(rs.getString("adress_station"));
   station.setType(rs.getString("type"));
   listStation.add(station);

        }
        return listStation;
    }
}
