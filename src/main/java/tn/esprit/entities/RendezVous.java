package tn.esprit.entities;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RendezVous {
    private int ref_rendez_vous;
    private Timestamp date_rendez_vous;
//    id_medecin is foreign key
    private int id_medecin;

    public RendezVous() {
    }

    public RendezVous(LocalDateTime dateTime, int idMedecin) {
        // Convert the LocalDateTime to java.sql.Timestamp
        this.date_rendez_vous = new Timestamp(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        this.id_medecin = idMedecin;
    }





    public int getRef_rendez_vous() {
        return ref_rendez_vous;
    }

    public void setRef_rendez_vous(int ref_rendez_vous) {
        this.ref_rendez_vous = ref_rendez_vous;
    }

    public java.sql.Timestamp getDate_rendez_vous() {
        return date_rendez_vous;
    }

    public void setDate_rendez_vous(Timestamp date_rendez_vous) {
        this.date_rendez_vous =  date_rendez_vous;
    }

    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    @Override
    public String toString() {
        return "RendezVous{" +
                "date_rendez_vous=" + date_rendez_vous +
                ", id_medecin=" + id_medecin +
                '}';
    }

}
