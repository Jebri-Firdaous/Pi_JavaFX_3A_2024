package tn.pidev.entities;

public class Client extends Personne{
    private int id_client;
    private String genre;
    private int age;

    public Client() {
    }

    public Client(String genre, int age) {
        this.genre = genre;
        this.age = age;
    }

    public Client(int id_personne, String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, int id_client, String genre, int age) {
        super(id_personne, nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
        this.id_client = id_client;
        this.genre = genre;
        this.age = age;
    }

    public Client(String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String genre, int age) {
        super(nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
        this.genre = genre;
        this.age = age;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id_client=" + id_client +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                '}';
    }
}
