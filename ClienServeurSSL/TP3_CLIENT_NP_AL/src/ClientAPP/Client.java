package ClientAPP;

import com.sun.net.ssl.internal.ssl.Provider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objet.Reservation;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.Security;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Représente l'application cliente.
 */
public class Client {
    private String addressIP;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private String infoRecu;
    private InterfaceClientController interfaceClientController;

    private SSLSocketFactory sslSocketFactory;
    private SSLSocket sslSocket;

    /**
     * Constructeur.
     * C'est ici qu'on tente de se connecter au serveur.
     *
     * @param addressIP
     * @param port
     * @param interfaceClientController
     */
    public Client(String addressIP, int port, InterfaceClientController interfaceClientController) {
        this.interfaceClientController = interfaceClientController;
        this.addressIP = addressIP;
        this.port = port;

        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.trustStore", "myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword", "Soleil1234");
        System.setProperty("javax.net.debug", "all");

        try {
            this.sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            this.sslSocket = (SSLSocket) this.sslSocketFactory.createSocket(this.addressIP, this.port);

            this.out = new PrintWriter(sslSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Thread(() ->
        {
            Thread.currentThread().setName("Thread d'ecoute du serveur");
            while (true) {
                try {
                    infoRecu = this.in.readLine();
                    triInfoRecu(infoRecu);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Effectue une action en fonction du contenu de @infoRecu.
     *
     * @param infoRecu Contenu du message retourné par le serveur.
     */
    private void triInfoRecu(String infoRecu) {
        Message message = ClientAPP.Parser.Parse(infoRecu);

        switch (message.action) {
            case LOGIN:
                interfaceClientController.connectionAutorise();
                this.out.println(Action.GET_ALL_CLIENTS);
                this.out.println(Action.GET_ALL_RESERVATIONS);
                break;
            case LOGOUT:
                interfaceClientController.logout();
                break;
            case BLOCK:
                interfaceClientController.block();
                break;
            case GET_ALL_CLIENTS:
                interfaceClientController.getAllListeClient(fabriqueClient(message));
                break;
            case GET_ALL_RESERVATIONS:
                interfaceClientController.getAllListeReservation(fabriqueReservation(message));
                break;
            default:
            	break;

        }
    }

    /**
     * Construit une liste d'objet @Client en fonction d'un message retourné par le serveur.
     *
     * @param message
     * @return La liste d'objet @Client
     */
    private ObservableList<objet.Client> fabriqueClient(ClientAPP.Message message) {
        ObservableList<objet.Client> clientsList = FXCollections.observableArrayList();

        for (int i = 0; i < message.data.size(); i += 3) {
            objet.Client clientTemp = new objet.Client();
            clientTemp.setID(Integer.parseInt(message.data.get(i)));
            clientTemp.setNom(message.data.get((i + 1)));
            clientTemp.setPrenom(message.data.get((i + 2)));

            clientsList.add(clientTemp);
        }
        return clientsList;
    }

    /**
     * Construit une liste d'objet @Réservation en fonction d'un message retourné par le serveur.
     *
     * @param message
     * @return La liste d'objet @Réservation
     */
    private ObservableList<Reservation> fabriqueReservation(ClientAPP.Message message) {
        ObservableList<objet.Reservation> ReservationList = FXCollections.observableArrayList();

        for (int i = 0; i < message.data.size(); i += 6) {
            objet.Reservation reservationTemp = new objet.Reservation();
            reservationTemp.setID(Integer.parseInt(message.data.get(i)));
            try {
                reservationTemp.setArrive(new SimpleDateFormat("yyyy-MM-dd").parse(message.data.get((i + 1))));
                reservationTemp.setDepart(new SimpleDateFormat("yyyy-MM-dd").parse(message.data.get((i + 2))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reservationTemp.setCommentaire(message.data.get((i + 3)));
            reservationTemp.setIdClient(Integer.parseInt(message.data.get((i + 4))));
            reservationTemp.setIdEmploye(Integer.parseInt(message.data.get((i + 5))));

            ReservationList.add(reservationTemp);
        }
        return ReservationList;
    }

    public void login(String nom, String mdp) {
        this.out.println(Action.LOGIN.toString() + "," + nom + "," + mdp);
    }

    public void logout(String nom, String mdp) {
        this.out.println(Action.LOGOUT.toString() + "," + nom + "," + mdp);
    }

    public void ajouterClient(String nom, String prenom) {
        this.out.println(Action.ADD_CLIENT.toString() + "," + nom + "," + prenom);
        obtenirListeClient();
    }

    public void modifierClient(Integer id, String nom, String prenom) {
        this.out.println(Action.MODIFY_CLIENT.toString() + "," + id + "," + nom + "," + prenom);
        obtenirListeClient();
    }

    public void supprimerClient(int id) {
        this.out.println(Action.DELETE_CLIENT.toString() + "," + id);
        obtenirListeClient();
    }

    public void obtenirListeClient() {
        this.out.println(Action.GET_ALL_CLIENTS.toString());
    }

    public void obtenirListeReservation() {
        this.out.println(Action.GET_ALL_RESERVATIONS.toString());
    }

    public void reserver(String arrive, String depart, String commentaire, String idClient, String idEmploye) {
        this.out.println(Action.ADD_RESERVATION.toString() + "," + arrive + "," + depart + "," + commentaire + "," + idClient + "," + idEmploye);
        obtenirListeReservation();
    }

    public void modifierReservation(int id, String arrive, String depart, String commentaire) {
        this.out.println(Action.MODIFY_RESERVATION.toString() + "," + id + "," + arrive + "," + depart + "," + commentaire);
        obtenirListeReservation();
    }

    public void supprimerReservation(int id) {
        this.out.println(Action.DELETE_RESERVATION.toString() + "," + id);
        obtenirListeReservation();
    }
}
