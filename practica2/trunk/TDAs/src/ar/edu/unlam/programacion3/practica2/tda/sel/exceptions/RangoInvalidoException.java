package ar.edu.unlam.programacion3.practica2.tda.sel.exceptions;

public class RangoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 8183393112985309099L;

	public RangoInvalidoException() {
		
	}

	public RangoInvalidoException(String message) {
		super(message);
	}

	public RangoInvalidoException(Throwable cause) {
		super(cause);
	}

	public RangoInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RangoInvalidoException(int valorObtenido, int valorMinimo, int valorMaximo) {
		this("La dimension (" + valorObtenido + ") no está dentro del rango aceptable: " + valorMinimo + " < x < " + valorMaximo);
	}

}
