package ClientAPP;

/**
 * Représente les actions que peut prendre l'application cliente
 */
public enum Action {
    LOGOUT,
    LOGIN,
    BLOCK,

    ADD_CLIENT,
    MODIFY_CLIENT,
    DELETE_CLIENT,
    GET_ALL_CLIENTS,

    ADD_RESERVATION,
    MODIFY_RESERVATION,
    DELETE_RESERVATION,
    GET_ALL_RESERVATIONS;

    /**
     * @param rhs
     * @return Un object @Action du nom contenu dans le string
     */
    public static Action StringToAction(String rhs)
    {
        for (Action lhs : Action.values()) {
            if (lhs.toString().equals(rhs))
                return lhs;
        }

        return null;
    }
}
