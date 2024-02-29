package tn.pidev.entities;

public class Client extends Personne{

    private String genre;
    private int age;

    public Client() {
    }

    public Client(String genre, int age) {
        this.genre = genre;
        this.age = age;
    }



    public Client(int id_personne,String nom_personne, String prenom_personne, int numero_telephone, String mail_personne, String mdp_personne, String genre, int age) {
        super(id_personne,nom_personne, prenom_personne, numero_telephone, mail_personne, mdp_personne);
        this.genre = genre;
        this.age = age;
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
                ", genre='" + genre + '\'' +
                ", age=" + age +
                '}';
    }
}
