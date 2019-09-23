package server.protocol;

/**
 * Une exception levée lorsqu'une application cliente tente de faire des actions sans s'être préalablement connecté.
 */
public class ClientNotLoggedInException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = -18179269440530132L;

	public ClientNotLoggedInException() {
        super("Could not execute an operation because the client is not logged in.");
    }
}
