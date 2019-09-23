package ClientAPP;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objet.Reservation;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Le contrôleur FXML de l'application cliente
 */
public class PrimaryController implements InterfaceClientController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TabPane mainTabPane;

    @FXML
    private Tab tabLogin;

    @FXML
    private TextField tfNomUtilisateur;

    @FXML
    private TextField tfMotDePasse;

    @FXML
    private Label labelLogin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnLogout;

    @FXML
    private Tab tabReservation;

    @FXML
    private Tab tabLogout;

    @FXML
    private ListView<Reservation> listViewReservation;

    @FXML
    private DatePicker datePickerArrive;

    @FXML
    private DatePicker datePickerDepart;

    @FXML
    private TextArea textAreaCommentaire;

    @FXML
    private Label lblIdEmploye;

    @FXML
    private Button btnReserve;

    @FXML
    private Tab tabClient;

    @FXML
    private ListView<objet.Client> listViewClient;

    @FXML
    private TextField tfPrenomClient;

    @FXML
    private TextField tfNomClient;

    @FXML
    private Button btnAjouterClient;

    @FXML
    private TextField tfModifPrenomClient;

    @FXML
    private TextField tfModifNomClient;

    @FXML
    private TextField tfClientReservation;

    @FXML
    private TextField tfIdEmployeReservation;

    @FXML
    private Label lblClientReservation;

    @FXML
    private Label lblIDClient;

    @FXML
    private Button btnSupprimerClient;

    @FXML
    private Button btnModifierClient;

    @FXML
    private Button btnChargeListeClient;

    @FXML
    private Button btnChargeListeReservation;

    @FXML
    private Button btnModifierReservation;

    @FXML
    private Button btnSupprimerReservation;

    @FXML
    private DatePicker datePickerArriveM;

    @FXML
    private DatePicker datePickerDepartM;

    @FXML
    private TextField tfIdEmployeReservationM;

    @FXML
    private TextField tfClientReservationM;

    @FXML
    private TextArea textAreaCommentaireM;

    @FXML
    private Label lblIdReservation;

    private Client client;


    @FXML
    void initialize() {
        tabClient.setDisable(true);
        tabReservation.setDisable(true);
        tabLogout.setDisable(true);

        client = new Client("127.0.0.1", 44444, this);

        initListView();
        initButton();
    }


    @FXML
    void login(ActionEvent event) {
        if (!tfNomUtilisateur.getText().isEmpty() && !tfMotDePasse.getText().isEmpty()) {
            client.login(tfNomUtilisateur.getText(), tfMotDePasse.getText());
        }
    }

    @FXML
    void logout(ActionEvent event) {
        client.logout(tfNomUtilisateur.getText(), tfMotDePasse.getText());
    }


    @FXML
    void ajouterClient(ActionEvent event) {
        client.ajouterClient(tfNomClient.getText(), tfPrenomClient.getText());
        tfNomClient.clear();
        tfPrenomClient.clear();
    }

    @FXML
    void modifierClient(ActionEvent event) {
        client.modifierClient(Integer.valueOf(lblIDClient.getText()), tfModifNomClient.getText(), tfModifPrenomClient.getText());

        tfModifPrenomClient.clear();
        tfModifNomClient.clear();
        lblIDClient.setText("");
        listViewClient.getSelectionModel().clearSelection();
    }

    @FXML
    void supprimerClient(ActionEvent event) {
        client.supprimerClient(Integer.valueOf(lblIDClient.getText()));
        tfModifNomClient.clear();
        tfModifPrenomClient.clear();
        lblIDClient.setText("");
        listViewClient.getSelectionModel().clearSelection();
    }


    @FXML
    void reserver(ActionEvent event) {

        client.reserver(
                datePickerArrive.getValue().toString(),
                datePickerDepart.getValue().toString(),
                textAreaCommentaire.getText(),
                tfClientReservation.getText(),
                tfIdEmployeReservation.getText()
        );

        datePickerArrive.setValue(null);
        datePickerDepart.setValue(null);
        textAreaCommentaire.clear();
        tfClientReservation.clear();
        tfIdEmployeReservation.clear();

    }

    @FXML
    void modifierReservation(ActionEvent event) {
        client.modifierReservation(
                Integer.valueOf(lblIdReservation.getText()),
                datePickerArriveM.getValue().toString(),
                datePickerDepartM.getValue().toString(),
                textAreaCommentaireM.getText()
        );

        lblIdReservation.setText("");
        datePickerArriveM.setValue(null);
        datePickerDepartM.setValue(null);
        textAreaCommentaireM.clear();
        tfClientReservationM.clear();
        tfIdEmployeReservationM.clear();

    }

    @FXML
    public void supprimerReservation(ActionEvent event) {
        client.supprimerReservation(Integer.valueOf(lblIdReservation.getText()));
        lblIdReservation.setText("");
        datePickerArriveM.setValue(null);
        datePickerDepartM.setValue(null);
        textAreaCommentaireM.clear();
        tfClientReservationM.clear();
        tfIdEmployeReservationM.clear();
    }

    @FXML
    void chargerListReservation(ActionEvent event) {
        client.obtenirListeReservation();
    }

    @FXML
    void chargerListeClient(ActionEvent event) {
        client.obtenirListeClient();
    }

    @Override
    public void connectionAutorise() {
        mainTabPane.getSelectionModel().select(1);
        tabLogin.setDisable(true);
        tabLogout.setDisable(false);
        tabClient.setDisable(false);
        tabReservation.setDisable(false);
    }

    @Override
    public void logout() {
        resetData();
    }

    private void resetData() {
        mainTabPane.getSelectionModel().select(0);
        tabLogin.setDisable(false);
        tabLogout.setDisable(true);
        tabClient.setDisable(true);
        tabReservation.setDisable(true);

    }

    @Override
    public void block() {
        Platform.runLater(() ->
        {
            Attempt.attempt(btnLogin, tfNomUtilisateur, tfMotDePasse);
        });
    }

    @Override
    public void getAllListeClient(ObservableList<objet.Client> clientsList) {
        Platform.runLater(() ->
        {
            listViewClient.setItems(clientsList);
            listViewClient.refresh();
        });
    }

    @Override
    public void getAllListeReservation(ObservableList<Reservation> reservationsList) {
        Platform.runLater(() ->
        {
            listViewReservation.setItems(reservationsList);
            listViewReservation.refresh();
        });
    }

    private void initListView() {
        listViewClient.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<objet.Client>() {
            @Override
            public void changed(ObservableValue<? extends objet.Client> observable, objet.Client oldValue, objet.Client newValue) {
                if (newValue != null) {
                    objet.Client clientTemp = listViewClient.getSelectionModel().getSelectedItem();
                    tfModifNomClient.setText(clientTemp.getNom());
                    tfModifPrenomClient.setText(clientTemp.getPrenom());
                    lblIDClient.setText(Integer.toString(clientTemp.getID()));
                }
            }
        });

        listViewReservation.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Reservation>() {
            @Override
            public void changed(ObservableValue<? extends Reservation> observable, objet.Reservation oldValue, objet.Reservation newValue) {
                if (newValue != null) {
                    objet.Reservation reservationTemp = listViewReservation.getSelectionModel().getSelectedItem();

                    lblIdReservation.setText(String.valueOf(reservationTemp.getID()));
                    datePickerArriveM.setValue(convertirDate(reservationTemp.getArrive()));
                    datePickerDepartM.setValue(convertirDate(reservationTemp.getDepart()));
                    textAreaCommentaireM.setText(reservationTemp.getCommentaire());
                    tfClientReservationM.setText(String.valueOf(reservationTemp.getIdClient()));
                    tfIdEmployeReservationM.setText(String.valueOf(reservationTemp.getIdEmploye()));
                }
            }
        });


    }

    private LocalDate convertirDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void initButton() {

        btnAjouterClient.disableProperty().bind(javafx.beans.binding.Bindings.isEmpty(tfNomClient.textProperty())
                .or(javafx.beans.binding.Bindings.isEmpty(tfPrenomClient.textProperty())));

        btnModifierClient.disableProperty().bind(javafx.beans.binding.Bindings.isEmpty(tfModifNomClient.textProperty())
                .or(javafx.beans.binding.Bindings.isEmpty(tfModifPrenomClient.textProperty())));

        btnSupprimerClient.disableProperty().bind(javafx.beans.binding.Bindings.isEmpty(tfModifNomClient.textProperty())
                .or(javafx.beans.binding.Bindings.isEmpty(tfModifPrenomClient.textProperty())));


        btnReserve.disableProperty().bind(datePickerDepart.valueProperty().isNull().or(datePickerArrive.valueProperty().isNull())
                .or(tfClientReservation.textProperty().isEmpty())
                .or(tfIdEmployeReservation.textProperty().isEmpty()));

        btnModifierReservation.disableProperty().bind(datePickerDepartM.valueProperty().isNull().or(datePickerArriveM.valueProperty().isNull())
                .or(tfClientReservationM.textProperty().isEmpty())
                .or(tfIdEmployeReservationM.textProperty().isEmpty()));

        btnSupprimerReservation.disableProperty().bind(datePickerDepartM.valueProperty().isNull().or(datePickerArriveM.valueProperty().isNull())
                .or(tfClientReservationM.textProperty().isEmpty())
                .or(tfIdEmployeReservationM.textProperty().isEmpty()));


    }

}
