package ClientAPP;

import javafx.collections.ObservableList;
import objet.Client;
import objet.Reservation;

/**
 * Interface que l'interface graphique cliente doit implémenter.
 */
public interface InterfaceClientController
{
     void connectionAutorise();

     void logout();

     void block();

     void getAllListeClient(ObservableList<Client> clientArrayList);

     void getAllListeReservation(ObservableList<Reservation> clientArrayList);
}
