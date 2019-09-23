package objet;

/**
 * Représente un employé
 */
public class Employe {
    private int ID;
    private String nom;
    private String password;

    public Employe() {
    }

    public Employe(String nom, String password) {
        this.nom = nom;
        this.password = password;
    }

    public Employe(int ID, String nom, String password) {
        this.ID = ID;
        this.nom = nom;
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
