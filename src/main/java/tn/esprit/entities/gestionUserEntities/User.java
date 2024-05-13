package tn.esprit.entities.gestionUserEntities;

public class User {
    private int id;
    private String nom_personne;
    private String prenom_personne;
    private int numero_telephone;
    private String email;
    private String password;
    private String image_personne;
    private String genre;
    private int age;
    private String roles;
    private String role_admin;
    private boolean is_verified;
    private boolean is_banned;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_personne() {
        return nom_personne;
    }

    public void setNom_personne(String nom_personne) {
        this.nom_personne = nom_personne;
    }

    public String getPrenom_personne() {
        return prenom_personne;
    }

    public void setPrenom_personne(String prenom_personne) {
        this.prenom_personne = prenom_personne;
    }

    public int getNumero_telephone() {
        return numero_telephone;
    }

    public void setNumero_telephone(int numero_telephone) {
        this.numero_telephone = numero_telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp_personne() {
        return password;
    }

    public void setMdp_personne(String mdp_personne) {
        this.password = mdp_personne;
    }

    public String getImage_personne() {
        return image_personne;
    }

    public void setImage_personne(String image_personne) {
        this.image_personne = image_personne;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRole_admin() {
        return role_admin;
    }

    public void setRole_admin(String role_admin) {
        this.role_admin = role_admin;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public User() {
    }

    public User(String nom_personne, String prenom_personne, int numero_telephone, String email, String mdp_personne, String image_personne, String genre, int age, String roles, String role_admin, boolean is_verified, boolean is_banned) {
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.numero_telephone = numero_telephone;
        this.email = email;
        this.password = mdp_personne;
        this.image_personne = image_personne;
        this.genre = genre;
        this.age = age;
        this.roles = roles;
        this.role_admin = role_admin;
        this.is_verified = is_verified;
        this.is_banned = is_banned;
    }

    public User(int id, String nom_personne, String prenom_personne, int numero_telephone, String email, String mdp_personne, String image_personne, String genre, int age, String roles, String role_admin, boolean is_verified, boolean is_banned) {
        this.id = id;
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.numero_telephone = numero_telephone;
        this.email = email;
        this.password = mdp_personne;
        this.image_personne = image_personne;
        this.genre = genre;
        this.age = age;
        this.roles = roles;
        this.role_admin = role_admin;
        this.is_verified = is_verified;
        this.is_banned = is_banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom_personne='" + nom_personne + '\'' +
                ", prenom_personne='" + prenom_personne + '\'' +
                ", numero_telephone=" + numero_telephone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image_personne='" + image_personne + '\'' +
                ", genre='" + genre + '\'' +
                ", age=" + age +
                ", roles='" + roles + '\'' +
                ", role_admin='" + role_admin + '\'' +
                ", is_verified=" + is_verified +
                ", is_banned=" + is_banned +
                '}';
    }

    public User(int id, String nom_personne, String prenom_personne, int numero_telephone, String email, String mdp_personne, String role_admin) {
        this.id = id;
        this.nom_personne = nom_personne;
        this.prenom_personne = prenom_personne;
        this.numero_telephone = numero_telephone;
        this.email = email;
        this.password = mdp_personne;
        this.role_admin = role_admin;

    }


    /*@Override
    public String toString() {
        return getNom_personne()+" "+getPrenom_personne();

    }*/
}
