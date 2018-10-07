package am.chronograph.ex;

/**
 * A data exception which is thrown when data integrity errors occur in Intranet
 * application
 * 
 * @author tigranbabloyan
 *
 */
public class ChronoDataException extends RuntimeException {

	/**
	 * The serial id.
	 */
	private static final long serialVersionUID = -5411096261307105330L;

	public ChronoDataException() {
		super();
	}

	public ChronoDataException(String message) {
		super(message);
	}

	public ChronoDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public ChronoDataException(Throwable cause) {
		super(cause);
	}
}
