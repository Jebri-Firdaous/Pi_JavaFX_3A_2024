package tn.esprit.services.gestionUserServices;

import tn.esprit.entities.gestionUserEntities.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserService <T>{
    void ajouterClient(T t) throws SQLException;
    void ajouterAdmin(T t) throws SQLException;

    void modifierAdmin(T t) throws SQLException;
    void modifierClient(T t) throws SQLException;

    List<T> afficherAdmin() throws SQLException;
    List<T> afficherClient() throws SQLException;


    void supprimerUser(int id) throws SQLException;
    List<T> rechercherAdmin(String recherche) throws SQLException;
    List<T> recherClient(String recherche) throws SQLException;
    List<User> listClient() throws SQLException;


}
