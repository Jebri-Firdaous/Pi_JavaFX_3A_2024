package tn.esprit.entities.ParkingEntities;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Place{
    private int ref_place;
    private int num_place;
    private String type_place;
    private String etat;
    private int id_Parking;
    private int idCli;

    public Place() {
    }

    public Place(int ref_place, int num_place, String type_place, String etat, int id_Parking) {
        this.ref_place = ref_place;
        this.num_place = num_place;
        this.type_place = type_place;
        this.etat = etat;
        this.id_Parking = id_Parking;
    }

    public Place(int ref_place, int num_place, String type_place) {
        this.ref_place = ref_place;
        this.num_place = num_place;
        this.type_place = type_place;
    }

    public Place(int num_place, String type_place, int id_Parking) {
        this.num_place = num_place;
        this.type_place = type_place;
        this.id_Parking = id_Parking;
    }

    public int getRef_place() {
        return ref_place;
    }

    public void setRef_place(int ref_place) {
        this.ref_place = ref_place;
    }

    public int getNum_place() {
        return num_place;
    }

    public void setNum_place(int num_place) {
        this.num_place = num_place;
    }

    public String getType_place() {
        return type_place;
    }

    public void setType_place(String type_place) {
        this.type_place = type_place;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getId_Parking() {
        return id_Parking;
    }

    public void setId_Parking(int id_Parking) {
        this.id_Parking = id_Parking;
    }

    public int getIdCli() {
        return idCli;
    }

    public void setIdCli(int idCli) {
        this.idCli = idCli;
    }

    @Override
    public String toString() {
        return "Place{" +
                "ref_place=" + ref_place +
                ", num_place=" + num_place +
                ", type_place='" + type_place + '\'' +
                ", etat='" + etat + '\'' +
                ", id_Parking=" + id_Parking +
                '}';
    }
}
