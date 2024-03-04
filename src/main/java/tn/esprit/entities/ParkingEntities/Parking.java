package tn.esprit.entities.ParkingEntities;

public class Parking {
    private int ref;
    private String nom, addresse, etat;
    private int nbPlaceMax, nbPlaceOcc;
    private float lati, longi;

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
    public Parking(int ref, String nom, String addresse, int nbPlaceMax, int nbPlaceOcc, float lati, float longi, String etat) {
        this.ref = ref;
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.nbPlaceOcc = nbPlaceOcc;
        this.etat = etat;
        this.lati = lati;
        this.longi = longi;
    }

    public Parking(int ref, String nom, String addresse, int nbPlaceMax) {
        this.ref = ref;
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
    }

    public Parking(int ref, String nom, String addresse, int nbPlaceMax, float lati, float longi) {
        this.ref = ref;
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.lati = lati;
        this.longi = longi;
    }

    public Parking(String nom, String addresse, int nbPlaceMax, int nbPlaceOcc, String etat) {
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.nbPlaceOcc = nbPlaceOcc;
        this.etat = etat;
    }

    public Parking(String nom, String addresse, int nbPlaceMax, int nbPlaceOcc, float lati, float longi, String etat) {
        this.nom = nom;
        this.addresse = addresse;
        this.nbPlaceMax = nbPlaceMax;
        this.nbPlaceOcc = nbPlaceOcc;
        this.etat = etat;
        this.lati = lati;
        this.longi = longi;
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

    public float getLati() {
        return lati;
    }

    public void setLati(float lati) {
        this.lati = lati;
    }

    public float getLongi() {
        return longi;
    }

    public void setLongi(float longi) {
        this.longi = longi;
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
