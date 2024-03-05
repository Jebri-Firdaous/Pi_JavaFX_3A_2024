package tn.esprit.services.gestionUserServices;

import tn.esprit.entities.gestionUserEntities.Client;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(T t) throws SQLException;

    List<T> afficher() throws SQLException;

    void supprimer(int id) throws SQLException;

    List<T> rechercher(String recherche) throws SQLException;


    List<Client> getAllClients() throws SQLException;
}
