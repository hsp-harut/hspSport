package am.chronograph.ex;

/**
 * A exception which is thrown when system was unable to send the email message.
 * 
 * @author tigranbabloyan
 *
 */
public class EmailSentFailedException extends RuntimeException {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -5411096261307105330L;

	public EmailSentFailedException() {
		super();
	}

	public EmailSentFailedException(String message) {
		super(message);
	}

	public EmailSentFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailSentFailedException(Throwable cause) {
		super(cause);
	}
}
