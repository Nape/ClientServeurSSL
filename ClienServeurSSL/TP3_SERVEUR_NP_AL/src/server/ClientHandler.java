package server;

import bd.BDClient;
import bd.BDEmploye;
import bd.BDReservation;
import bd.DBException;
import javafx.collections.ObservableList;
import logging.Logger;
import objet.Client;
import objet.Reservation;
import server.protocol.*;

import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientHandler implements Runnable
{
    private SSLSocket socket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean listening;
    private boolean connected;
    private int connAttempt;

    public ClientHandler(SSLSocket socket)
    {
        this.socket = socket;
        this.listening = false;
        this.connected = false;
        this.connAttempt = 0;
    }

    private void sendMessageToClient(Message message)
    {
        StringBuilder transmission = new StringBuilder();
        transmission.append(message.action.toString());

        for (int index = 0; index < message.data.size(); index++)
        {
            transmission.append(",");
            transmission.append(message.data.get(index));
        }

        //!!!!!!!!!!!!!!!PRINTLN !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        this.out.println(transmission.toString());
        this.out.flush();
    }

    private boolean isLoggedIn()
    {
        return this.connected;
    }

    @Override
    public void run()
    {
        try
        {
            Logger.getInstance().informational(this.getClass(), "Succesfully listening to client: " + this.socket);

            this.listening = true;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String transmission;
            while ((transmission = this.in.readLine()) != null && this.listening)
            {
                Logger.getInstance().informational(this.getClass(), "Received communication from client: " + this.socket);

                if (transmission.toLowerCase().equals("bye"))
                {
                    Logger.getInstance().informational(this.getClass(), "Client " + this.socket + " breaks communication");
                    this.listening = false;
                }
                else
                    {
                    try
                    {
                        Message message = Parser.Parse(transmission);
                        Logger.getInstance().informational(this.getClass(), "Received a valid message: " + message);

                        switch (message.action)
                        {
                            case LOGIN:
                                if (this.connAttempt < 2)
                                {
                                    String username = message.data.get(0);
                                    String password = message.data.get(1);

                                    if (BDEmploye.connexionEmploye(username, password))
                                    {
                                        this.connected = true;
                                        this.out.println("LOGIN");
                                    }
                                    else
                                    {
                                        this.connAttempt += 1;
                                    }
                                }
                                else
                                {
                                    this.connAttempt = 0;
                                    this.out.println("BLOCK");
                                }
                                break;

                            case LOGOUT:
                                String username = message.data.get(0);
                                String password = message.data.get(1);
                                if (BDEmploye.connexionEmploye(username, password))
                                {
                                    this.out.println("LOGOUT");
                                    this.connected = false;
                                }
                                break;

                            case ADD_CLIENT:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                String lastname = message.data.get(0);
                                String firstname = message.data.get(1);

                                BDClient.ajouterClient(lastname, firstname);
                                break;

                            case DELETE_CLIENT:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                String id = message.data.get(0);
                                BDClient.supprimerClient(Integer.parseInt(id));
                                break;

                            case MODIFY_CLIENT:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                String idModC = message.data.get(0);
                                String lastnameMod = message.data.get(1);
                                String firstnameMod = message.data.get(2);

                                BDClient.modifierClient(Integer.parseInt(idModC), lastnameMod, firstnameMod);
                                break;

                            case GET_ALL_CLIENTS:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                ArrayList<String> data = new ArrayList<>();
                                ObservableList<Client>  clients = BDClient.GetAllClients();
                                for (Client client : clients)
                                {
                                    data.add(Integer.toString(client.getID()));
                                    data.add(client.getNom());
                                    data.add(client.getPrenom());
                                }

                                sendMessageToClient(new Message(Action.GET_ALL_CLIENTS, data));
                                break;

                            case ADD_RESERVATION:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                java.sql.Date arrives = java.sql.Date.valueOf(message.data.get(0));
                                java.sql.Date leaves  = java.sql.Date.valueOf(message.data.get(1));
                                String comments = message.data.get(2);
                                int clientId = Integer.parseInt(message.data.get(3));
                                int employeeId = Integer.parseInt(message.data.get(4));

                                BDReservation.ajouterReservation(arrives, leaves, comments, clientId, employeeId);
                                break;

                            case DELETE_RESERVATION:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                int idDel = Integer.parseInt(message.data.get(0));
                                BDReservation.supprimerReservation(idDel);
                                break;

                            case MODIFY_RESERVATION:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                int idModR = Integer.parseInt(message.data.get(0));
                                java.sql.Date arrivesModR = java.sql.Date.valueOf(message.data.get(1));
                                java.sql.Date leavesModR  = java.sql.Date.valueOf(message.data.get(2));
                                String commentsModR = message.data.get(3);

                                BDReservation.modifierReservation(idModR, arrivesModR, leavesModR, commentsModR);
                                break;

                            case GET_ALL_RESERVATIONS:
                                if (!isLoggedIn())
                                    throw new ClientNotLoggedInException();

                                ArrayList<String> dataAllR = new ArrayList<>();
                                ObservableList<Reservation> reservations = BDReservation.GetAllReservations();
                                for (Reservation reservation : reservations)
                                {
                                    dataAllR.add(Integer.toString(reservation.getID()));
                                    dataAllR.add(reservation.getArrive().toString());
                                    dataAllR.add(reservation.getDepart().toString());
                                    dataAllR.add(reservation.getCommentaire().toString());
                                    dataAllR.add(String.valueOf(reservation.getIdClient()));
                                    dataAllR.add(String.valueOf(reservation.getIdEmploye()));
                                }

                                sendMessageToClient(new Message(Action.GET_ALL_RESERVATIONS, dataAllR));
                                break;
                        }
                    }
                    catch (ClientNotLoggedInException e)
                    {
                        Logger.getInstance().error(this.getClass(), "Error trying to execute an action.\n\t" + e.getMessage());
                    }
                    // in this context, these are all parsing exceptions
                    catch (ParsingException |ArrayIndexOutOfBoundsException|NumberFormatException e)
                    {
                        Logger.getInstance().error(this.getClass(), "Error during message parsing.\n\tMake sure it is a valid message\n\t" + e.getMessage());
                    }
                    catch (DBException e)
                    {
                        Logger.getInstance().error(this.getClass(), "Error during dabatase operation.\n\t" + e.getMessage());
                    }
                }
            }

            this.in.close();
            this.out.close();
            this.socket.close();
        }
        catch (IOException e)
        {
            Logger.getInstance().error(this.getClass(), "An error occured while trying to run this ClientHandler\n" + e.getMessage());
        }
    }
}
