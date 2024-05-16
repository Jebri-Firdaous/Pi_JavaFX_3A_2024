package tn.esprit.services.gestionMedecin;

import tn.esprit.entities.gestionMedecin.Medecin;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMedecin implements IService<Medecin> {
    private Connection connection;

    public ServiceMedecin() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Medecin medecin) throws SQLException {
//        Using a prepared statement not only prevents SQL injection but also helps to avoid syntax errors
        String sql = "INSERT INTO `medecin`(`nom_medecin`, `prenom_medecin_medecin`, `numero_telephone_medecin`," +
                " `address_medecin`, `specialite_medecin`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, medecin.getNom_medecin());
        preparedStatement.setString(2, medecin.getPrenom_medecin_medecin());
        preparedStatement.setInt(3, medecin.getNumero_telephone_medecin());
        preparedStatement.setString(4, medecin.getAddress_medecin());
        preparedStatement.setString(5, medecin.getSpecialite_medecin());
        preparedStatement.executeUpdate();


    }


    @Override
    public void modifier(int id, String nom, String prenom, int numTel,
                         String adresse, String specialite) throws SQLException {
        String sql = "UPDATE `medecin` SET `nom_medecin`= ? ,`prenom_medecin_medecin`= ? ,`numero_telephone_medecin`= ? ," +
                "`address_medecin`= ? ,`specialite_medecin`= ? WHERE  `id_medecin`= ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, nom);
        preparedStatement.setString(2, prenom);
        preparedStatement.setInt(3, numTel);
        preparedStatement.setString(4, adresse);
        preparedStatement.setString(5, specialite);
        preparedStatement.setInt(6, id);
        preparedStatement.executeUpdate();



    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM `medecin` WHERE `id_medecin`= ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Medecin> afficher() throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "select * from Medecin";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }
    public List<Medecin> searchByNom(String nom) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "select * from Medecin where nom_medecin LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,nom+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }
    public List<Medecin> searchByPrenom(String prenom) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "select * from Medecin where prenom_medecin_medecin LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,prenom+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }
    public List<Medecin> searchBySpacialite(String speacialite) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "select * from Medecin where specialite_medecin LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,speacialite+"%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }
    public List<Medecin> searchByNum (String numTel) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM Medecin WHERE CAST(numero_telephone_medecin AS CHAR) LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, numTel + '%');
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }
    public List<Medecin> searchByAdresse (String adresse) throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "select * from Medecin where address_medecin LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, adresse + '%');
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Medecin medecin = new Medecin();
            medecin.setId_medecin(rs.getInt("id_medecin"));
            medecin.setNom_medecin(rs.getString("nom_medecin"));
            medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
            medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
            medecin.setAddress_medecin(rs.getString("address_medecin"));
            medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            medecins.add(medecin);
        }
        return medecins;
    }

    public List<String> getAllSpecialié() throws SQLException {
        List<String> listSpecialté = new ArrayList<>();
        String sql = "SELECT DISTINCT `specialite_medecin` FROM `medecin` ";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            listSpecialté.add(rs.getString("specialite_medecin"));
        }
        return listSpecialté;
    }

    public List<Medecin> getMedecinBySpecialite(String specialite) {
        List<Medecin> listMedecin = new ArrayList<>();
        String sql = "SELECT * FROM `medecin` WHERE `specialite_medecin` LIKE ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, specialite);
            ResultSet rs = preparedStatement.executeQuery(); // Corrected line
            while (rs.next()) {
                Medecin medecin = new Medecin();
                medecin.setId_medecin(rs.getInt("id_medecin"));
                medecin.setNom_medecin(rs.getString("nom_medecin"));
                medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
                medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
                medecin.setAddress_medecin(rs.getString("address_medecin"));
                medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
                listMedecin.add(medecin);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listMedecin;
    }

    public Medecin getMedecinById(int id) {
        Medecin medecin = new Medecin();
        String sql = "SELECT * FROM `medecin` WHERE `id_medecin` = ? ";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery(); // Corrected line
            if(rs.next()){
                medecin.setId_medecin(rs.getInt("id_medecin"));
                medecin.setNom_medecin(rs.getString("nom_medecin"));
                medecin.setPrenom_medecin_medecin(rs.getString("prenom_medecin_medecin"));
                medecin.setNumero_telephone_medecin(rs.getInt("numero_telephone_medecin"));
                medecin.setAddress_medecin(rs.getString("address_medecin"));
                medecin.setSpecialite_medecin(rs.getString("specialite_medecin"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medecin;
    }




}