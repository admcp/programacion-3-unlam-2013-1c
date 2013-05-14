package ar.edu.unlam.programacion3.practica2.tda.sel;

public class MatrizInvertible extends MatrizCuadrada {

	public MatrizInvertible(int dimension) {
		super(dimension);
	}
	
	public MatrizInvertible(double[][] coeficientes) {
		super(coeficientes);
	}

	public MatrizInvertible invertir() {
		return new MatrizInvertible(10); // TODO: Realizar algoritmo
	}

}
