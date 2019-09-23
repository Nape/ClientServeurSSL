package server.protocol;

/**
 * Représente les type de messages, les actions, que le serveur peut envoyer à un client
 */
public enum Action {
    LOGOUT,
    LOGIN,

    ADD_CLIENT,
    MODIFY_CLIENT,
    DELETE_CLIENT,
    GET_ALL_CLIENTS,

    ADD_RESERVATION,
    MODIFY_RESERVATION,
    DELETE_RESERVATION,
    GET_ALL_RESERVATIONS;

    public static Action StringToAction(String rhs) {
        for (Action lhs : Action.values()) {
            if (lhs.toString().equals(rhs))
                return lhs;
        }

        return null;
    }
}
