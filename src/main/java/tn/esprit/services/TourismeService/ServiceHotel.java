package tn.esprit.services.TourismeService;

import tn.esprit.entities.TourismeEntities.Hotel;
import tn.esprit.entities.TourismeEntities.Reservation;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceHotel implements IService<Hotel> {

    private final Connection connection;
    ServiceReservation sr = new ServiceReservation();
    private List<Hotel> hotels;

    public ServiceHotel() {
        connection = MyDataBase.getInstance().getConnection();
    }

    /*--------------------------------------------------AJOUT------------------------------------------------------*/
    @Override
    public void ajouter(Hotel hotel) throws SQLException {
        String sql = "INSERT INTO `hotel`(`nom_hotel`, `adress_hotel`,  `prix1` , `prix2` , `prix3` , `numero1` , `numero2` , `numero3` ) VALUES ('" + hotel.getNom_hotel() + "','" + hotel.getAdress_hotel() + "' , '" + hotel.getPrix1() + "' ,  '" + hotel.getPrix2() + "' , ' " + hotel.getPrix3() + "' , ' " + hotel.getNumero1() + "'  , ' " + hotel.getNumero2() + "'  , " + hotel.getNumero3() + " )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
    }

    /*--------------------------------------------------Modifier------------------------------------------------------*/

    @Override
    public void modifier(Hotel hotel) throws SQLException {
        String sql = "UPDATE hotel SET nom_hotel = ?, adress_hotel= ?  , prix1= ? , prix2= ? , prix3= ?  , numero1= ? , numero2= ? , numero3= ? WHERE id_hotel = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, hotel.getNom_hotel());
        preparedStatement.setString(2, hotel.getAdress_hotel());
        preparedStatement.setFloat(3, hotel.getPrix1());
        preparedStatement.setFloat(4, hotel.getPrix2());
        preparedStatement.setFloat(5, hotel.getPrix3());
        preparedStatement.setInt(6, hotel.getNumero1());
        preparedStatement.setInt(7, hotel.getNumero2());
        preparedStatement.setInt(8, hotel.getNumero3());
        preparedStatement.setInt(9, hotel.getId_hotel());
        preparedStatement.executeUpdate();
    }

    /*--------------------------------------------------Supprimer------------------------------------------------------*/
    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from hotel where id_hotel = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    /*--------------------------------------------------Afficher------------------------------------------------------*/
    @Override
    public List<Hotel> afficher() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "select * from hotel";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            Hotel H = new Hotel();
            H.setId_hotel(rs.getInt("id_hotel"));
            H.setNom_hotel(rs.getString("nom_hotel"));
            H.setAdress_hotel(rs.getString("adress_hotel"));
            H.setPrix1(rs.getFloat("prix1"));
            H.setPrix2(rs.getFloat("prix2"));
            H.setPrix3(rs.getFloat("prix3"));
            H.setNumero1(rs.getInt("numero1"));
            H.setNumero2(rs.getInt("numero2"));
            H.setNumero3(rs.getInt("numero3"));
            hotels.add(H);
        }
        return hotels;
    }

    /*--------------------------------------------------Unicité-nom------------------------------------------------------*/
    public boolean nomHotelExisteDeja(String nomHotel) throws SQLException {
        String query = "SELECT COUNT(*) FROM hotel WHERE nom_hotel = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nomHotel);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getInt(1) > 0;
            }
        }
    }

    /*--------------------------------------------------recuperer-'nom_hotel'------------------------------------------------------*/
    public String getNomHotelById(int idHotel) throws SQLException {
        String nomHotel = null;
        String sql = "SELECT nom_hotel FROM hotel WHERE id_hotel = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idHotel);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    nomHotel = rs.getString("nom_hotel");
                }
            }
        }
        return nomHotel;
    }

    /*--------------------------------------------------recuperer-'id_hotel'------------------------------------------------------*/
    public int getHotelIdByNom(String nomHotel) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int hotelId = -1;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/e_city_last_version", "root", "0000");
            String query = "SELECT id_hotel FROM hotel WHERE nom_hotel = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, nomHotel);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                hotelId = resultSet.getInt("id_hotel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return hotelId;
    }

    /*--------------------------------------------------recuperer-prix1------------------------------------------------------*/
    public float getPrix1ById(int idHotel) throws SQLException {
        float prix = 0.0f;
        String query = "SELECT prix1 FROM hotel WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idHotel);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                prix = resultSet.getFloat("prix1");
            }
        }
        return prix;
    }

    /*--------------------------------------------------recuperer-prix2------------------------------------------------------*/
    public float getPrix2ById(int idHotel) throws SQLException {
        float prix = 0.0f;
        String query = "SELECT prix2 FROM hotel WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idHotel);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                prix = resultSet.getFloat("prix2");
            }
        }
        return prix;
    }

    /*--------------------------------------------------recuperer-prix3------------------------------------------------------*/
    public float getPrix3ById(int idHotel) throws SQLException {
        float prix = 0.0f;
        String query = "SELECT prix3 FROM hotel WHERE id_hotel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, idHotel);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                prix = resultSet.getFloat("prix3");
            }
        }
        return prix;
    }

    public int updateNombreChambres(int idHotel, String champNombreChambres) throws SQLException {
        int nombreChambresActuel = 0;
        String query = "SELECT " + champNombreChambres + " FROM hotel WHERE id_hotel = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHotel);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nombreChambresActuel = resultSet.getInt(champNombreChambres);
                }
            }
        }

        // Vérifier si le nombre de chambres est déjà égal à zéro
        if (nombreChambresActuel > 0) {
            String updateQuery = "UPDATE hotel SET " + champNombreChambres + " = ? WHERE id_hotel = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setInt(1, nombreChambresActuel - 1);
                updateStatement.setInt(2, idHotel);
                updateStatement.executeUpdate();
            }
        }
        return nombreChambresActuel;
    }


    //////////---------------------------------------------------------------------------------------------//////////////////

    public int updateNombreChambresApresModification(int idHotel, String ancienType, String nouveauType) throws SQLException {
        int nombreChambresAncienType = 0;
        int nombreChambresNouveauType = 0;

        // Sélectionner le nombre de chambres pour l'ancien type
        String queryAncienType = "SELECT nombre_chambres FROM hotel WHERE id_hotel = ? AND type_chambre = ?";
        try (PreparedStatement statementAncienType = connection.prepareStatement(queryAncienType)) {
            statementAncienType.setInt(1, idHotel);
            statementAncienType.setString(2, ancienType);
            try (ResultSet resultSet = statementAncienType.executeQuery()) {
                if (resultSet.next()) {
                    nombreChambresAncienType = resultSet.getInt("nombre_chambres");
                }
            }
        }

        // Sélectionner le nombre de chambres pour le nouveau type
        String queryNouveauType = "SELECT nombre_chambres FROM hotel WHERE id_hotel = ? AND type_chambre = ?";
        try (PreparedStatement statementNouveauType = connection.prepareStatement(queryNouveauType)) {
            statementNouveauType.setInt(1, idHotel);
            statementNouveauType.setString(2, nouveauType);
            try (ResultSet resultSet = statementNouveauType.executeQuery()) {
                if (resultSet.next()) {
                    nombreChambresNouveauType = resultSet.getInt("nombre_chambres");
                }
            }
        }

        // Mettre à jour le nombre de chambres pour l'ancien type
        String updateQueryAncienType = "UPDATE hotel SET nombre_chambres = ? WHERE id_hotel = ? AND type_chambre = ?";
        try (PreparedStatement updateStatementAncienType = connection.prepareStatement(updateQueryAncienType)) {
            updateStatementAncienType.setInt(1, nombreChambresAncienType + 1);
            updateStatementAncienType.setInt(2, idHotel);
            updateStatementAncienType.setString(3, ancienType);
            updateStatementAncienType.executeUpdate();
        }

        // Mettre à jour le nombre de chambres pour le nouveau type
        String updateQueryNouveauType = "UPDATE hotel SET nombre_chambres = ? WHERE id_hotel = ? AND type_chambre = ?";
        try (PreparedStatement updateStatementNouveauType = connection.prepareStatement(updateQueryNouveauType)) {
            updateStatementNouveauType.setInt(1, nombreChambresNouveauType - 1);
            updateStatementNouveauType.setInt(2, idHotel);
            updateStatementNouveauType.setString(3, nouveauType);
            updateStatementNouveauType.executeUpdate();
        }

        return nombreChambresAncienType;
    }


    public int getNombreChambresDisponiblesByType(int idHotel, Reservation.TypeChambre typeChambre) throws SQLException {
        int nombreChambresDisponibles = 0;
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Établir une connexion à la base de données
            connection = MyDataBase.getInstance().getConnection();

            // Requête SQL pour récupérer le nombre de chambres disponibles pour un type de chambre spécifique dans un hôtel donné
            String query = "SELECT ";
            switch (typeChambre) {
                case NORMAL:
                    query += "numero1";
                    break;
                case STANDARD:
                    query += "numero2";
                    break;
                case LUXE:
                    query += "numero3";
                    break;
                default:
                    // Gérer le cas par défaut si nécessaire
                    break;
            }
            query += " FROM hotel WHERE id_hotel = ?";

            // Préparer la déclaration SQL
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, idHotel);

            // Exécuter la requête
            rs = stmt.executeQuery();

            // Vérifier si un enregistrement a été retourné
            if (rs.next()) {
                // Récupérer le nombre de chambres disponibles pour le type de chambre spécifié
                nombreChambresDisponibles = rs.getInt(1);
            }
        } finally {
            // Fermer les ressources (ResultSet, Statement, Connection)
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return nombreChambresDisponibles;
    }

    public void diminuerNombreChambresDisponibles(int idHotel, Reservation.TypeChambre typeChambre) throws SQLException {
        ServiceReservation sr = new ServiceReservation(); // Créer une instance de ServiceReservation
        String champNombreChambres = sr.getTypeChambreColumnName(typeChambre);
        int nombreChambresDisponibles = getNombreChambresDisponibles(idHotel, champNombreChambres);

        // Vérifier que le nombre de chambres disponibles est supérieur à zéro avant de le diminuer
        if (nombreChambresDisponibles > 0) {
            // Décrémenter le nombre de chambres disponibles
            String query = "UPDATE hotel SET " + champNombreChambres + " = " + champNombreChambres + " - 1 WHERE id_hotel = ? AND " + champNombreChambres + " > 0";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, idHotel);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    // Décrémentation réussie
                    System.out.println("Décrémentation réussie pour les chambres de type " + typeChambre);
                } else {
                    // Aucune chambre disponible à décrémenter
                    System.out.println("Erreur : Aucune chambre disponible à décrémenter pour les chambres de type " + typeChambre);
                }
            }
        } else {
            System.out.println("Erreur : Le nombre de chambres disponibles est déjà nul pour ce type de chambre dans cet hôtel.");
        }
    }

    public int getNombreChambresDisponibles(int idHotel, String champNombreChambres) throws SQLException {
        int nombreChambresDisponibles = 0;
        String query = "SELECT " + champNombreChambres + " FROM hotel WHERE id_hotel = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idHotel);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    nombreChambresDisponibles = resultSet.getInt(champNombreChambres);
                }
            }
        }
        return nombreChambresDisponibles;
    }


    public List<Hotel> rechercherHotelsDansLaBase(String recherche) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String sql = "SELECT id_hotel, nom_hotel, adress_hotel, prix1, prix2, prix3, numero1, numero2, numero3 " +
                "FROM hotel " +
                "WHERE nom_hotel LIKE ? OR adress_hotel LIKE ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + recherche + "%");
        preparedStatement.setString(2, "%" + recherche + "%");
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            Hotel hotel = new Hotel();
            hotel.setId_hotel(rs.getInt("id_hotel"));
            hotel.setNom_hotel(rs.getString("nom_hotel"));
            hotel.setAdress_hotel(rs.getString("adress_hotel"));
            hotel.setPrix1(rs.getFloat("prix1"));
            hotel.setPrix2(rs.getFloat("prix2"));
            hotel.setPrix3(rs.getFloat("prix3"));
            hotel.setNumero1(rs.getInt("numero1"));
            hotel.setNumero2(rs.getInt("numero2"));
            hotel.setNumero3(rs.getInt("numero3"));
            hotels.add(hotel);
        }
        return hotels;


    }


//    public List<Hotel> rechercherHotelsDansLaBase(String nom) throws SQLException {
//        List<Hotel> hotelsTrouves = new ArrayList<>();
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = MyDataBase.getInstance().getConnection();
//            String query = "SELECT * FROM hotel WHERE nom_hotel LIKE ?";
//            statement = connection.prepareStatement(query);
//            statement.setString(1, "%" + nom + "%");
//            resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                int hotelId = resultSet.getInt("id_hotel");
//                String hotelNom = resultSet.getString("nom_hotel");
//                String adresse = resultSet.getString("adresse_hotel");
//                float prix1 = resultSet.getFloat("prix1");
//                float prix2 = resultSet.getFloat("prix2");
//                float prix3 = resultSet.getFloat("prix3");
//                int nombre1 = resultSet.getInt("numero1");
//                int nombre2 = resultSet.getInt("numero2");
//                int nombre3 = resultSet.getInt("numero3");
//
//                // Ajouter l'hôtel trouvé à la liste des hôtels
//                hotelsTrouves.add(new Hotel(hotelId, hotelNom, adresse, prix1, prix2, prix3, nombre1, nombre2, nombre3));
//            }
//        } finally {
//            // Fermeture des ressources
//            if (resultSet != null) {
//                resultSet.close();
//            }
//            if (statement != null) {
//                statement.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//
//        return hotelsTrouves;
//    }


}
