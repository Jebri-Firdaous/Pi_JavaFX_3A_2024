package tn.esprit.services.gestionTransport;

import tn.esprit.entities.gestionTransport.Station;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface IServiceBillet<T>  {
    void ajouter(T t) throws SQLException;


    void modifierBillet(int id, String destination, Timestamp date, int station, String prix, String duree) throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> recuperer() throws SQLException;
    List <Station> getStations() throws SQLException;
}
