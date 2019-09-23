package bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour interfacer la table des employés dans la base de données
 */
public class BDEmploye {
    /**
     * Verifie que les informations de l'employé sont valides.
     *
     * @param nomEmploye
     * @param motPasse
     * @return Vrai s'il existe, faux autrement
     */
    public static boolean connexionEmploye(String nomEmploye, String motPasse) {
        boolean trouve = false;
        try {
            String requete = "select * from BDTP3.EMPLOYE where NOM = ? AND PASSWORD = ?";

            PreparedStatement prepStm = MySQLJDBC.getConnection().prepareStatement(requete);

            prepStm.setString(1, nomEmploye);
            prepStm.setString(2, motPasse);
            ResultSet resultSet = prepStm.executeQuery();

            if (resultSet.next()) {
                trouve = true;
            }
            prepStm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trouve;
    }

}