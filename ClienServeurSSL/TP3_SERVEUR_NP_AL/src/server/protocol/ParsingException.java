package server.protocol;

/**
 * Represents the occurence of an unexpected event during the parsing of a Message
 */
public class ParsingException extends RuntimeException {
    /**
	 *
	 */
	private static final long serialVersionUID = -6185098601172476083L;

	public ParsingException(String message) {
        super(message);
    }
}
