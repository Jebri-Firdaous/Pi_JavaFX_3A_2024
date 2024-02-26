package org.example.entities;

public class Parking {
    private int ref;
    private String nom, addresse, etat;
    private int nbPlaceMax, nbPlaceOcc;

    public Parking() {
    }

    public Parking(int ref, String nom, String addresse, int nbPlaceMax, int nbPlaceOcc, String etat) {
        this.ref = ref;
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.nbPlaceOcc = nbPlaceOcc;
        this.etat = etat;
    }

    public Parking(int ref, String nom, String addresse, int nbPlaceMax) {
        this.ref = ref;
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
    }

    public Parking(String nom, String addresse, int nbPlaceMax, int nbPlaceOcc, String etat) {
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.nbPlaceOcc = nbPlaceOcc;
        this.etat = etat;
    }

    public int getRef() {
        return ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public int getNbPlaceMax() {
        return nbPlaceMax;
    }

    public void setNbPlaceMax(int nbPlaceMax) {
        this.nbPlaceMax = nbPlaceMax;
    }

    public int getNbPlaceOcc() {
        return nbPlaceOcc;
    }

    public void setNbPlaceOcc(int nbPlaceOcc) {
        this.nbPlaceOcc = nbPlaceOcc;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Parking{" +
                "ref=" + ref +
                ", nom='" + nom + '\'' +
                ", addresse='" + addresse + '\'' +
                ", nbPlaceMax=" + nbPlaceMax +
                ", nbPlaceOcc=" + nbPlaceOcc +
                ", etat=" + etat +
                '}';
    }
}
