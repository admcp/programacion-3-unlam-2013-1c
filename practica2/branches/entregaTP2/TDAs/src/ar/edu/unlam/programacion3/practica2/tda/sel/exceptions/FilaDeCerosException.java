package ar.edu.unlam.programacion3.practica2.tda.sel.exceptions;

public class FilaDeCerosException extends RuntimeException {

	private static final long serialVersionUID = 8292326472444037488L;

	public FilaDeCerosException() {
		super();
	}

	public FilaDeCerosException(String message, Throwable cause) {
		super(message, cause);
	}

	public FilaDeCerosException(String message) {
		super(message);
	}

	public FilaDeCerosException(Throwable cause) {
		super(cause);
	}

}
