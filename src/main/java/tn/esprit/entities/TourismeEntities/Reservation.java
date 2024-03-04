package tn.esprit.entities.TourismeEntities;

import java.time.LocalDate;

public class Reservation {

    private int ref_reservation ;
    private float duree_reservation ;
    private float prix_reservation ;
    private LocalDate date_reservation ;

    private   int  id_hotel ;
    private  int id_personne  ;
    private  TypeChambre  type_chambre ;


    public  Reservation(){

    }
    public Reservation(int ref_reservation , float duree_reservation , float  prix_reservation , LocalDate  date_reservation  ,int id_hotel  , TypeChambre type_chambre ){
        this.ref_reservation = ref_reservation ;
        this.duree_reservation = duree_reservation ;
        this.prix_reservation = prix_reservation ;
        this.date_reservation  = date_reservation ;
        this.id_hotel=id_hotel ;
        this.type_chambre  = type_chambre ;

    }

    public Reservation(float duree_reservation, float prix_reservation, LocalDate date_reservation , int id_hotel , TypeChambre type_chambre ) {
       // this.ref_reservation = 0; // Valeur par défaut
        this.duree_reservation = duree_reservation;
        this.prix_reservation = prix_reservation;
        this.date_reservation = date_reservation;
        this.id_hotel = id_hotel;
        this.type_chambre  = type_chambre ;

    }
    public Reservation(float duree_reservation, float prix_reservation, LocalDate date_reservation , int id_hotel , TypeChambre type_chambre , int id_personne) {
        // this.ref_reservation = 0; // Valeur par défaut
        this.duree_reservation = duree_reservation;
        this.prix_reservation = prix_reservation;
        this.date_reservation = date_reservation;
        this.id_hotel = id_hotel;
        this.type_chambre  = type_chambre ;
        this.id_personne  = id_personne ;
    }

    public Reservation(int ref_reservation , float duree_reservation , float  prix_reservation , LocalDate  date_reservation  ,int id_hotel  , TypeChambre type_chambre , int id_personne ){
        this.ref_reservation = ref_reservation ;
        this.duree_reservation = duree_reservation ;
        this.prix_reservation = prix_reservation ;
        this.date_reservation  = date_reservation ;
        this.id_hotel=id_hotel ;
        this.type_chambre  = type_chambre ;
        this.id_personne  = id_personne ;

    }

    public enum TypeChambre {
        NORMAL,
        STANDARD,
        LUXE

    }

    public int getRef_reservation() {
        return ref_reservation;
    }

    public void setRef_reservation(int ref_reservation) {
        this.ref_reservation = ref_reservation;
    }

    public float getDuree_reservation() {
        return duree_reservation;
    }

    public void setDuree_reservation(float duree_reservation) {
        this.duree_reservation = duree_reservation;
    }

    public float getPrix_reservation() {
        return prix_reservation;
    }

    public void setPrix_reservation(float prix_reservation) {
        this.prix_reservation = prix_reservation;
    }

    public LocalDate getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(LocalDate date_reservation) {
        this.date_reservation = date_reservation;
    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public TypeChambre getType_chambre() {
        return type_chambre;
    }

    public void setType_chambre(TypeChambre type_chambre) {
        this.type_chambre = type_chambre;
    }

    public int getId_personne() {
        return id_personne;
    }

    public void setId_personne(int id_personne) {
        this.id_personne = id_personne;
    }


    @Override
    public String toString() {
        return "Reservation{" +
                "ref_reservation=" + ref_reservation +
                ", duree_reservation=" + duree_reservation +
                ", prix_reservation=" + prix_reservation +
                ", date_reservation=" + date_reservation +
                ", id_hotel=" + id_hotel +
                ", id_personne=" + id_personne +
                ", type_chambre=" + type_chambre +
                '}';
    }
}
