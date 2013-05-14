package ar.edu.unlam.programacion3.practica2.tda.sel;

public class SistemaLinealDeEcuaciones {

	private MatrizCuadrada factorizacionLU;

	public SistemaLinealDeEcuaciones(int dimension) {
		factorizacionLU = new MatrizCuadrada(dimension);
	}

	public void setCoeficientes(double[][] matrizDeCoeficientes) {
		factorizacionLU.inicializarConMatriz(matrizDeCoeficientes);
	}

	public VectorColumna resolver(VectorColumna vectorIndependiente) {
		return new VectorColumna(10); // TODO: Completar algoritmo
	}

}
