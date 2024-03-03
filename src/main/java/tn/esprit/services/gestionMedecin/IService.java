package tn.esprit.services.gestionMedecin;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T t) throws SQLException;
//    void modifier(T t) throws SQLException;
public void modifier(int id, String nom, String prenom, int numTel,
                     String adresse, String specialite) throws SQLException;
    void supprimer(int id) throws SQLException;
    List<T> afficher() throws SQLException;


}
