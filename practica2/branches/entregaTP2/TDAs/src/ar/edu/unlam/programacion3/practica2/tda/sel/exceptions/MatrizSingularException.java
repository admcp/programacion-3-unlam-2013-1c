package ar.edu.unlam.programacion3.practica2.tda.sel.exceptions;

public class MatrizSingularException extends RuntimeException {

	private static final long serialVersionUID = 8292326472444037488L;

	public MatrizSingularException() {
		super();
	}

	public MatrizSingularException(String message, Throwable cause) {
		super(message, cause);
	}

	public MatrizSingularException(String message) {
		super(message);
	}

	public MatrizSingularException(Throwable cause) {
		super(cause);
	}

}
