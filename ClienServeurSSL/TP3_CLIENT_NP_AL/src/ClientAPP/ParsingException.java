package ClientAPP;

/**
 * Represents the occurence of an unexpected event during the parsing of a Message
 */
public class ParsingException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7739627738680121382L;

	public ParsingException(String message) {
        super(message);
    }
}
