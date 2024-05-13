package tn.esprit.services.gestionMedecin;

import tn.esprit.entities.gestionMedecin.Rendez_vous;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceRendezVous implements IService<Rendez_vous>{
    private Connection connection;

    public ServiceRendezVous() {
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Rendez_vous rendezVous) throws SQLException {
        String sql = "INSERT INTO `rendez_vous`(`date_rendez_vous`, `id_medecin`, `id_personne`) VALUES (?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, rendezVous.getDate_rendez_vous());
        preparedStatement.setInt(2, rendezVous.getId_medecin());
        preparedStatement.setInt(3,rendezVous.getId_personne());
        preparedStatement.executeUpdate();
    }
    public void modifier(int refRv, Timestamp date_rendez_vous, int id_medecin) throws SQLException {
        String sql = "UPDATE `rendez_vous` SET `date_rendez_vous`= ?,`id_medecin`= ? WHERE `ref_rendez_vous`= ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1,date_rendez_vous);
        preparedStatement.setInt(2,id_medecin);
        preparedStatement.setInt(3,refRv);
        preparedStatement.executeUpdate();

    }


    // Not this modifier method
    @Override
    public void modifier(int id, String nom, String prenom, int numTel, String adresse, String specialite) throws SQLException {

    }


    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `rendez_vous` WHERE `ref_rendez_vous` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Rendez_vous> afficher() throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();
        String sql = "select * from `rendez_vous` ";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }
    public List<Rendez_vous> afficherByNomClient(String nomClient) throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();
        String sql = "SELECT * FROM `rendez_vous` WHERE id_personne IN (SELECT id FROM user WHERE UPPER(nom_personne) LIKE ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,nomClient.toUpperCase()+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }
    public List<Rendez_vous> afficherByNomDoctor(String nomMedecin) throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();
        String sql = "SELECT * FROM `rendez_vous` WHERE id_medecin IN (SELECT id_medecin FROM medecin WHERE UPPER(nom_medecin) LIKE ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,nomMedecin.toUpperCase()+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }
    public List<Rendez_vous> afficherBySpecialite(String specialite) throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();
        String sql = "SELECT * FROM `rendez_vous` WHERE id_medecin IN (SELECT id_medecin FROM medecin WHERE UPPER(specialite_medecin) LIKE ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,specialite.toUpperCase()+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }
    public List<Rendez_vous> afficherByDate(String localDateTimeString) throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();

        String sql = "SELECT * FROM `rendez_vous` WHERE CAST(DATE_FORMAT(date_rendez_vous, '%d-%b-%Y %H:%i') AS CHAR) LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,localDateTimeString.toUpperCase()+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        System.out.println(desrendezVous);
        return desrendezVous;
    }
    public List<Rendez_vous> afficherByAdresse(String adresse) throws SQLException {
        List<Rendez_vous> desrendezVous = new ArrayList<>();

        String sql = "SELECT * FROM `rendez_vous` WHERE id_medecin IN (SELECT id_medecin FROM medecin WHERE UPPER(address_medecin) LIKE ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,adresse.toUpperCase()+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Rendez_vous rendezVous = new Rendez_vous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }
    public List<LocalDateTime> getAllDateRendezVousByidMedeicn(int id) throws SQLException {
        List<LocalDateTime> dateRendezVous = new ArrayList<>();
        String sql = "SELECT `date_rendez_vous` FROM `rendez_vous` WHERE `id_medecin` = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            dateRendezVous.add(resultSet.getTimestamp("date_rendez_vous").toLocalDateTime());
        }
        return  dateRendezVous;
    }

    public Rendez_vous getRendezVousByRefRv(int refRV) throws SQLException {
        Rendez_vous rendezVous = new Rendez_vous();
        String sql = "SELECT * FROM `rendez_vous` WHERE `ref_rendez_vous` = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, refRV);
        preparedStatement.executeQuery();
        ResultSet rs = preparedStatement.getResultSet();
        if (rs.next()) {
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            rendezVous.setId_personne(rs.getInt("id_personne"));
        }
        return  rendezVous;
    }
}


