package ar.edu.unlam.programacion3.practica2.tda.sel.exceptions;

public class DimensionInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 7601627677253740460L;

	public DimensionInvalidaException() {
		
	}

	public DimensionInvalidaException(String message) {
		super(message);
	}

	public DimensionInvalidaException(Throwable cause) {
		super(cause);
	}

	public DimensionInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DimensionInvalidaException(int valorObtenido, int valorEsperado) {
		this("La dimension no es válida. Se obtuvo un " + valorObtenido + " cuando se esperaba un " + valorEsperado);
	}

}
