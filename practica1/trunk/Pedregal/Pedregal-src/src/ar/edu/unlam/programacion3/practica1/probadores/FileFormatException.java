package ar.edu.unlam.programacion3.practica1.probadores;

public class FileFormatException extends RuntimeException {

	private static final long serialVersionUID = 6947210996994806606L;

	public FileFormatException() {
		super();
	}

	public FileFormatException(String message) {
		super(message);
	}

	public FileFormatException(Throwable cause) {
		super(cause);
	}

	public FileFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FileFormatException(String fileName, String line) {
		super("Archivo " + fileName + " mal Formado. Linea: " + line);
	}

}
