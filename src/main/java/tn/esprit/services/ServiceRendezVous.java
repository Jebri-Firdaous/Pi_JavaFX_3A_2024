package tn.esprit.services;

import tn.esprit.entities.Medecin;
import tn.esprit.entities.RendezVous;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRendezVous implements IService<RendezVous>{
    private Connection connection;

    public ServiceRendezVous() {
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(RendezVous rendezVous) throws SQLException {
        String sql = "INSERT INTO `rendez-vous`(`date_rendez_vous`, `id_medecin`) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setTimestamp(1, rendezVous.getDate_rendez_vous());
        preparedStatement.setInt(2, rendezVous.getId_medecin());
        preparedStatement.executeUpdate();
    }

    @Override
    public void modifier(int id, String nom, String prenom, int numTel, String adresse, String specialite) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `rendez-vous` WHERE `ref_rendez_vous` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List afficher() throws SQLException {
        List<RendezVous> desrendezVous = new ArrayList<>();
        String sql = "select * from `rendez-vous` ";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            RendezVous rendezVous = new RendezVous();
            rendezVous.setRef_rendez_vous(rs.getInt("ref_rendez_vous"));
            rendezVous.setDate_rendez_vous(rs.getTimestamp("date_rendez_vous"));
            rendezVous.setId_medecin(rs.getInt("id_medecin"));
            desrendezVous.add(rendezVous);
        }
        return desrendezVous;
    }

}
