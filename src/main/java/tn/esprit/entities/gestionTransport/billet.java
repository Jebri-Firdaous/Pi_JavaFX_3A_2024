package tn.esprit.entities.gestionTransport;

import tn.esprit.services.gestionTransport.StationService;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class billet {
    private int ref_voyage;

    private String destination_voyage;
    private Timestamp date_depart;
    private int id_station;
    private String prix;
    private String duree;
    private int id_personne;

    public billet() {
    }


    public billet(int ref_voyage, String destination_voyage, Timestamp date_depart, int id_station,String prix,String duree) {
        this.ref_voyage = ref_voyage;
        this.destination_voyage = destination_voyage;
        this.date_depart = date_depart;
        this.id_station= id_station;
        this.prix=prix;
        this.duree=duree;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }

    public billet(int ref_voyage, String destination_voyage, Timestamp date_depart, int id_station, String prix, String duree, int id_personne) {
        this.ref_voyage = ref_voyage;
        this.destination_voyage = destination_voyage;
        this.date_depart = date_depart;
        this.id_station = id_station;
        this.prix = prix;
        this.duree = duree;
        this.id_personne = id_personne;
    }

    public billet(String destination_voyage, LocalDateTime date_depart, int id_station, String prix, String duree) {

        this.destination_voyage = destination_voyage;
        this.date_depart = new Timestamp(date_depart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());;
        this.id_station= id_station;
        this.prix=prix;
        this.duree=duree;
    }

    public String getPrix() {
        return prix;
    }

    public void setId_station(int id_station) {
        this.id_station = id_station;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }




    public int getRef_voyage() {
        return ref_voyage;
    }

    public void setRef_voyage(int ref_voyage) {
        this.ref_voyage = ref_voyage;
    }



    public String getDestination_voyage() {
        return destination_voyage;
    }

    public void setDestination_voyage(String destination_voyage) {
        this.destination_voyage = destination_voyage;
    }

    public Timestamp getDate_depart() {
        return date_depart;
    }

    public void setDate_depart(Timestamp date_depart) {
        this.date_depart = date_depart;
    }

    public int getId_station() {
        return id_station;
    }


    //    @Override
//    public String toString() {
//        return "billet{" +
//                "ref_voyage=" + ref_voyage +
//
//                ", destination_voyage='" + destination_voyage + '\'' +
//                ", date_depart='" + date_depart + '\'' +
//
//
//                ", station=" + station +
//                '}';
//    }

    @Override
    public String toString() {
        StationService stationService = new StationService();
        Station s;
        try {
            s = stationService.getStationById(id_station);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return
                    "destination'=" + destination_voyage + '\'' +
                    "---- date=" + date_depart +
                    "---- prix=" + prix + "DT"+ '\'' +
                    "---- duree='" + duree + '\'' +
                    "---- station="+  s.getNom_station()+"****"+s.getAdress_station()+"****"+s.getType();


    }
}
