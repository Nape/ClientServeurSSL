package bd;

/**
 * Emballe une exception levée  par la base de données.
 */
public class DBException extends RuntimeException {

    /**
	 *
	 */
	private static final long serialVersionUID = 5172937397387175872L;

	public DBException(String message) {
        super(message);
    }
}
