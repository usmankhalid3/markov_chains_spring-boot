package alogic.markov.core.exceptions;

public class InvalidOrderException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidOrderException() {
		super("Invalid order: Expected to be >= 1");
	}
}
