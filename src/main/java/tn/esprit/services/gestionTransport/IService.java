package tn.esprit.services.gestionTransport;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException;

    void modifier(int id,String noms,String adresses,String type) throws SQLException;


    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
    public boolean stationExists(String nom, String adresse) throws SQLException;
}
