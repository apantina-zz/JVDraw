package hr.fer.zemris.java.hw16.jvdraw.geoeditors;

/**
 * A runtime exception used in the {@link GeometricalObjectEditor} class and its
 * subclasses.
 * 
 * @author 0036502252
 *
 */
public class EditException extends RuntimeException {

	/**
	 * Auto-generated serial version ID.
	 */
	private static final long serialVersionUID = 3055707646385552386L;

	/**
	 * Constructs a new {@link EditException} with {@code null} as its detail
	 * message.
	 */
	public EditException() {
		super();
	}

	/**
	 * Constructs a new {@link EditException} with the specified detail message.
	 * 
	 * @param message
	 *            the detail message.
	 */
	public EditException(String message) {
		super(message);
	}

	/**
	 * Constructs a new {@link EditException} with the specified detail message
	 * and cause.
	 * 
	 * @param message
	 *            the detail message
	 * @param cause
	 *            the cause
	 */
	public EditException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new {@link EditException} with the specified cause.
	 * 
	 * @param cause
	 *            the cause
	 */
	public EditException(Throwable cause) {
		super(cause);
	}
}
