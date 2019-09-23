package bd;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objet.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour interfacer la table des clients dans la base de données
 */
public class BDClient {

    /**
     * Ajouter un client
     * @param nomClient Nom du client
     * @param prenomClient Prenom du client
     */
    public static void ajouterClient(String nomClient, String prenomClient) {
        try {
            String requete = "insert into BDTP3.CLIENT (NOM, PRENOM)" + " VALUES (?,?)";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setString(1, nomClient);
            prepStm.setString(2, prenomClient);
            prepStm.execute();
            prepStm.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Supprimer un client par son ID, s'il existe.
     * @param idClient L'id du client à supprimer
     */
    public static void supprimerClient(int idClient) {
        try {
            String requete = "delete from BDTP3.CLIENT WHERE ID = ?";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setInt(1, idClient);

            prepStm.executeUpdate();
            prepStm.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Modifier un client, s'il existe.
     * @param idClient Id du client
     * @param nomClient Nouveau nom
     * @param prenomClient Nouveau prénom
     */
    public static void modifierClient(int idClient, String nomClient, String prenomClient) {
        try {
            String requete = "update BDTP3.CLIENT set NOM = ?, PRENOM = ? WHERE ID = ?";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setString(1, nomClient);
            prepStm.setString(2, prenomClient);
            prepStm.setInt(3, idClient);

            prepStm.executeUpdate();
            prepStm.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * @return A list containing every client in the database. If the database is empty, returns an empty list.
     */
    public static ObservableList<Client> GetAllClients()
    {
        ObservableList<Client> arrayListClient = FXCollections.observableArrayList();

        try
        {
            String requete = "select * from BDTP3.CLIENT";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            ResultSet resultSet = prepStm.executeQuery();

            while (resultSet.next())
            {
                Client clientTemp = new Client();

                clientTemp.setID(resultSet.getInt(1));
                clientTemp.setNom(resultSet.getString(2));
                clientTemp.setPrenom(resultSet.getString(3));

                arrayListClient.add(clientTemp);
            }
            resultSet.close();
            prepStm.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return arrayListClient;
    }
}

