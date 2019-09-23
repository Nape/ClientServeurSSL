package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  Singleton représente connection à la base de données.
 */
public class MySQLJDBC {
    private static String url;
    private static String login;
    private static String passwd;
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                url = "jdbc:mysql://localhost:8889/BDTP3?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true";
                login = "root";
                passwd = "root";
                connection = DriverManager.getConnection(url, login, passwd);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    /**
     * Vide et remet à zéro les IDs autoincrémentés
     */
    public static void resetBD() {
        truncateTableClient();
        truncateTableEmploye();
        truncateTableReservation();
    }

    private static void truncateTableClient() {
        Statement requete = null;
        try {
            requete = MySQLJDBC.getConnection().createStatement();
            String sql = "TRUNCATE `BDTP3`.`CLIENT`";
            requete.execute(sql);
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void truncateTableEmploye() {
        Statement requete = null;
        try {
            requete = MySQLJDBC.getConnection().createStatement();
            String sql = "TRUNCATE `BDTP3`.`EMPLOYE`";
            requete.execute(sql);
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void truncateTableReservation() {
        Statement requete = null;
        try {
            requete = MySQLJDBC.getConnection().createStatement();
            String sql = "TRUNCATE `BDTP3`.`RESERVATION`";
            requete.execute(sql);
            requete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
