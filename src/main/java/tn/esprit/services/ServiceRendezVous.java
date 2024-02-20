package tn.esprit.services;

import tn.esprit.entities.RendezVous;
import tn.esprit.utils.MyDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    }

    @Override
    public List afficher() throws SQLException {
        return null;
    }
}
