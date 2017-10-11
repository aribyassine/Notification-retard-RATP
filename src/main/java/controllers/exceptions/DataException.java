package controllers.exceptions;

/**
 * @author Mohamed T. KASSAR
 *
 */

public class DataException extends Exception {
	private static final long serialVersionUID = 1L;
	public DataException(String message) {
		super(message);
	}
}
