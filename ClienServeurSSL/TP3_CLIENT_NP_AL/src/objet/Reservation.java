package objet;

import java.util.Date;

/**
 * Représente une réservation
 */
public class Reservation {
    private int ID;
    private Date arrive;
    private Date depart;
    private String commentaire;
    private int idClient;
    private int idEmploye;

    public Reservation() {
        this.setCommentaire(null);
    }

    public Reservation(Date arrive, Date depart, String commentaire, int idClient, int idEmployé) {
        this.arrive = arrive;
        this.depart = depart;
        this.setCommentaire(commentaire);
        this.idClient = idClient;
        this.idEmploye = idEmployé;
    }

    public Reservation(int ID, Date arrive, Date depart, String commentaire, int idClient, int idEmployé) {
        this.ID = ID;
        this.arrive = arrive;
        this.depart = depart;
        this.setCommentaire(commentaire);
        this.idClient = idClient;
        this.idEmploye = idEmployé;
    }


    public int getID() {
        return ID;
    }

    public Date getArrive() {
        return arrive;
    }

    public Date getDepart() {
        return depart;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public int getIdClient() {
        return idClient;
    }

    public int getIdEmploye() {
        return idEmploye;
    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public void setArrive(Date arrive) {
        this.arrive = arrive;
    }

    public void setDepart(Date depart) {
        this.depart = depart;
    }

    public void setCommentaire(String commentaire) {
        if (commentaire != null) {
            this.commentaire = commentaire;
        } else {
            this.commentaire = "  ";
        }
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    @Override
    public String toString() {
        return "ID: " + this.ID + " Arrivée: " + this.arrive + " Départ: " + this.depart + " Commentaire: " + this.commentaire + " ID Client: " + this.idClient + "ID Employe: " + this.idEmploye;
    }
}
