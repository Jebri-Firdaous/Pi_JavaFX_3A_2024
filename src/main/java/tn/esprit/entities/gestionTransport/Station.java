package tn.esprit.entities.gestionTransport;

import java.io.Serializable;

public class Station implements Serializable {
    private int id_station;
    private String nom_station;
    private  String adress_station;
    private  String type;

    public Station(int id_station, String nom_station, String adress_station,String type) {
        this.id_station = id_station;
        this.nom_station = nom_station;
        this.adress_station = adress_station;
        this.type=type;
    }

    public Station(String nom_station, String adress_station, String type) {
        this.nom_station = nom_station;
        this.adress_station = adress_station;
        this.type = type;
    }

    public Station(String nom_station) {
        this.nom_station = nom_station;
    }

    public Station() {

    }

    public  String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId_station() {
        return id_station;
    }

    public void setId_station(int id_station) {
        this.id_station = id_station;
    }

    public String getNom_station() {
        return nom_station;
    }

    public void setNom_station(String nom_station) {
        this.nom_station = nom_station;
    }

    public String getAdress_station() {
        return adress_station;
    }

    public void setAdress_station(String adress_station) {
        this.adress_station = adress_station;
    }

    @Override
    public String toString() {
        return

                nom_station + ' ' + adress_station + ' ' + type ;
    }
}
