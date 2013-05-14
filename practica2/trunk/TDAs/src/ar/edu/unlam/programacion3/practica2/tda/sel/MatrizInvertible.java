package ar.edu.unlam.programacion3.practica2.tda.sel;

public class MatrizInvertible extends MatrizCuadrada {

	public MatrizInvertible(int dimension) {
		super(dimension);
	}
	
	public MatrizInvertible(double[][] coeficientes) {
		super(coeficientes);
	}

	public MatrizInvertible invertir() {
		MatrizInvertible matrizInversa = new MatrizInvertible(cantidadFilas);
		MatrizIdentidad matrizIdentidad = new MatrizIdentidad(cantidadFilas);
		SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(cantidadFilas);
		sistemaLineal.setCoeficientes(this.coeficientes);
		
		for(int i = 0; i < cantidadColumnas; i++) {
			VectorColumna vectorAux = sistemaLineal.resolver(matrizIdentidad.getColumna(i));
			matrizInversa.setColumna(vectorAux, i);
		}
	
		return matrizInversa;
	}
	
	public double determinante() {
		SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(cantidadFilas);
		sistemaLineal.setCoeficientes(this.coeficientes);
		
		sistemaLineal.factorizarSistema();
		
		// Compensar el cambio de signo del determinante por cada intercambio de filas durante la factorización LU
		double determinante = (sistemaLineal.getIntercambiosEnFilas() & 1) == 0 ? 1 : -1;
		
		for(int i = 0; i < cantidadFilas; i++) {
			int indicePermutacion = sistemaLineal.getPermutacion()[i];
			determinante *= sistemaLineal.getFactorizacionLU().getValorEn(indicePermutacion, i);
		}
		
		return determinante;
	}
	
	public double normaDos(VectorColumna vectorInicial, double error) {
		return Math.sqrt(eigenvalorDominante(vectorInicial, error));
	}
	
	/*
	 * Método numérico para calcular eigenvalores: Método Iterativo de Potencias
	 * b_(k+1) = (A * b_k) / | (A * b_k) |
	 * donde A es una matriz con eigenvalor dominante, el vector inicial tiene una componente no nula en la dirección
	 * de algún eigenvector asociado al eigenvalor dominante, un subsecuencia de (b_k) converge a algún eigenvector
	 * asociado al eigenvalor dominante.
	 * Si x es eigenvector de la matriz A, su correspondiente eigenvalor está dado por
	 * lambda = (x^t * (A * x)) / (x^t * x)
	 * llamado Cociente de Rayleigh.
	 */
	private double eigenvalorDominante(VectorColumna vectorInicial, double error) {
		
		MatrizMath matrizTranspuesta = transponer(this);
		MatrizMath matrizAuxiliar = new MatrizInvertible(this.coeficientes);
		matrizAuxiliar = producto(matrizAuxiliar, matrizTranspuesta);
		
		// Evitar NaN cuando el error es demasiado chico
		MatrizMath matrizDeCeros = new MatrizMath(this.cantidadFilas, this.cantidadColumnas);
		if(matrizAuxiliar.equals(matrizDeCeros)) {
			return 0;
		}
		
		VectorColumna eigenvectorResultante, eigenvectorAnterior;
		double eigenvalorResultante = 0, eigenvalorAnterior = error;
		
		double factor = 0;
		VectorColumna vectorAuxiliar1, vectorAuxiliar2;
		
		// Calculamos el eigenvector inicial
		vectorAuxiliar1 = producto(matrizAuxiliar, vectorInicial);
		factor = 1 / vectorAuxiliar1.normaDos();
		eigenvectorResultante = VectorColumna.productoEscalar(vectorAuxiliar1, factor);
		
		// Calculamos el eigenvalor inicial
		vectorAuxiliar1 = producto(transponer(eigenvectorResultante), producto(matrizAuxiliar, eigenvectorResultante));
		vectorAuxiliar2 = producto(transponer(eigenvectorResultante), eigenvectorResultante);
		
		eigenvalorResultante = Math.abs(vectorAuxiliar1.getValorEn(0, 0) / vectorAuxiliar2.getValorEn(0, 0));
		
		// Calculamos con respecto al error pedido
		while(Math.abs(eigenvalorAnterior - eigenvalorResultante) > error) {
			eigenvalorAnterior = eigenvalorResultante;
			eigenvectorAnterior = eigenvectorResultante;
			
			// Calculamos el eigenvector
			eigenvectorResultante = producto(matrizAuxiliar, eigenvectorAnterior);
			factor = 1 / eigenvectorResultante.normaDos();
			eigenvectorResultante = VectorColumna.productoEscalar(eigenvectorResultante, factor);
			
			// Calculamos el eigenvalor
			vectorAuxiliar1 = producto(transponer(eigenvectorResultante), producto(matrizAuxiliar, eigenvectorResultante));
			vectorAuxiliar2 = producto(transponer(eigenvectorResultante), eigenvectorResultante);
			
			eigenvalorResultante = Math.abs(vectorAuxiliar1.getValorEn(0, 0) / vectorAuxiliar2.getValorEn(0, 0));
		
		}
		
		return eigenvalorResultante;
	}
	
	public static void main(String[] args) {
		MatrizInvertible mat = new MatrizInvertible(new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
		MatrizInvertible inversa = new MatrizInvertible(3);
		
		inversa = mat.invertir();
		
		System.out.println(mat);
		System.out.println(inversa);
		System.out.println(mat.determinante());
		
		MatrizInvertible matriz = new MatrizInvertible(new double[][] {{1, 1, 3}, {0, 5, 1}, {0, 7, 0}});
		System.out.println(matriz.normaDos(new VectorColumna(new double[] {1, 1, 1}), 0.00001));
	}

}
