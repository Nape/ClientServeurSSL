package bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objet.Reservation;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour interfacer la table des réservations dans la base de données
 */
public class BDReservation {

    /**
     * Ajoute une réservation dans la table.
     *
     * @param arrive
     * @param depart
     * @param commentaire
     * @param idClient
     * @param idEmploye
     */
    public static void ajouterReservation(Date arrive, Date depart, String commentaire, int idClient, int idEmploye) {
        try {
            String requete = "insert into BDTP3.RESERVATION (ARRIVEE, DEPART,COMMENTAIRE,ID_CLIENT,ID_EMPLOYE)" + " VALUES (?,?,?,?,?)";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setDate(1, arrive);
            prepStm.setDate(2, depart);
            prepStm.setString(3, commentaire);
            prepStm.setInt(4, idClient);
            prepStm.setInt(5, idEmploye);
            prepStm.execute();
            prepStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Supprimer une réservation de la table
     *
     * @param idReservation
     */
    public static void supprimerReservation(int idReservation) {
        try {
            String requete = "delete from BDTP3.RESERVATION WHERE ID = ?";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setInt(1, idReservation);

            prepStm.executeUpdate();
            prepStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Modifie une réservation
     *
     * @param idReservation
     * @param arrive
     * @param depart
     * @param commentaire
     */
    public static void modifierReservation(int idReservation, Date arrive, Date depart, String commentaire) {
        try {
            String requete = "update BDTP3.RESERVATION set ARRIVEE = ?, DEPART = ?, COMMENTAIRE = ? WHERE ID = ?";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setDate(1, arrive);
            prepStm.setDate(2, depart);
            prepStm.setString(3, commentaire);
            prepStm.setInt(4, idReservation);

            prepStm.executeUpdate();
            prepStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Une liste de toutes les réservations. S'il n'y a pas de réservation, une liste vide.
     */
    public static ObservableList<Reservation> GetAllReservations() {
        ObservableList<Reservation> arrayListReservation = FXCollections.observableArrayList();
        try {
            String requete = "select * from BDTP3.RESERVATION";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            ResultSet resultSet = prepStm.executeQuery();

            while (resultSet.next()) {
                Reservation reservationTemp = new Reservation();

                reservationTemp.setID(resultSet.getInt(1));
                reservationTemp.setArrive(resultSet.getDate(2));
                reservationTemp.setDepart(resultSet.getDate(3));
                reservationTemp.setCommentaire(resultSet.getString(4));
                reservationTemp.setIdClient(resultSet.getInt(5));
                reservationTemp.setIdEmploye(resultSet.getInt(6));

                arrayListReservation.add(reservationTemp);

            }

            prepStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayListReservation;
    }
}
