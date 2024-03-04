package tn.esprit.entities.TourismeEntities;


import java.util.Arrays;

public class Hotel {

    private int id_hotel;
    private String nom_hotel;
    private String adress_hotel;

    private float prix1;
    private float prix2;
    private float prix3;

    private int numero1 ;

    private int numero2 ;
    private int numero3 ;

    public Hotel() {

    }

    public Hotel(int id_hotel, String nom_hotel, String adress_hotel, float prix1, float prix2, float prix3  , int numero1 , int numero2 , int numero3) {
        this.id_hotel = id_hotel;
        this.nom_hotel = nom_hotel;
        this.adress_hotel = adress_hotel;
        this.prix1 = prix1;
        this.prix2 = prix2;
        this.prix3 = prix3;
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.numero3 = numero3;


    }

    public Hotel(String nom_hotel, String adress_hotel, float prix1, float prix2, float prix3 , int numero1 , int numero2 , int numero3) {

        this.nom_hotel = nom_hotel;
        this.adress_hotel = adress_hotel;
        this.prix1 = prix1;
        this.prix2 = prix2;
        this.prix3 = prix3;
        this.numero1 = numero1;
        this.numero2 = numero2;
        this.numero3 = numero3;

    }

    public int getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(int id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getNom_hotel() {
        return nom_hotel;
    }

    public void setNom_hotel(String nom_hotel) {
        this.nom_hotel = nom_hotel;
    }

    public String getAdress_hotel() {
        return adress_hotel;
    }

    public void setAdress_hotel(String adress_hotel) {
        this.adress_hotel = adress_hotel;
    }


    public float getPrix1() {
        return prix1;
    }

    public void setPrix1(float prix1) {
        this.prix1 = prix1;
    }

    public float getPrix2() {
        return prix2;
    }

    public void setPrix2(float prix2) {
        this.prix2 = prix2;
    }

    public float getPrix3() {
        return prix3;
    }

    public void setPrix3(float prix3) {
        this.prix3 = prix3;
    }

    public int getNumero1() {
        return numero1;
    }


    public void setNumero1(int numero1) {
        this.numero1 = numero1;
    }

    public int getNumero2() {
        return numero2;
    }

    public void setNumero2(int numero2) {
        this.numero2 = numero2;
    }

    public int getNumero3() {
        return numero3;
    }

    public void setNumero3(int numero3) {
        this.numero3 = numero3;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "id_hotel=" + id_hotel +
                ", nom_hotel='" + nom_hotel + '\'' +
                ", adress_hotel='" + adress_hotel + '\'' +
                ", prix1=" + prix1 +
                ", prix2=" + prix2 +
                ", prix3=" + prix3 +
                ", numero1=" + numero1 +
                ", numero2=" + numero2 +
                ", numero3=" + numero3 +
                '}';
    }
}


