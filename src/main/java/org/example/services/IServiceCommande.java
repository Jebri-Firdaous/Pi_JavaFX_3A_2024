package org.example.services;

import org.example.entites.Commande;
import java.sql.SQLException;
import java.util.List;

public interface IServiceCommande<T> {

    void ajouterCommande(T t) throws SQLException;
    void modifierCommande(T t) throws SQLException;
    void supprimerCommande(int id_Commande) throws SQLException;
    List<T> afficherCommande() throws SQLException;
}
