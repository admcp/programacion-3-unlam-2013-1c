package ar.edu.unlam.programacion3.practica2.tda.sel;

import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.FilaDeCerosException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.MatrizSingularException;

// FIXME: No está funcionando, posiblemente eigenvalorDominante o invertir o determinante alteran la matriz original.
public class MatrizInvertible extends MatrizCuadrada {
	
	// MIEMBROS "FACTORIZACIÓN LU"
	private MatrizCuadrada factorizacionLU;
	private int[] vectorDePermutaciones;
	private int intercambiosEnFilas;


	public MatrizInvertible(int cantidadFilas) {
		super(cantidadFilas);
	}

	public MatrizInvertible(double[][] coeficientes) {
		super(coeficientes);
	}

	public MatrizInvertible inversa() {
		MatrizInvertible matrizInversa = new MatrizInvertible(cantidadFilas);
		MatrizIdentidad matrizIdentidad = new MatrizIdentidad(cantidadFilas);
		
		for(int i = 0; i < cantidadColumnas; i++) {
			VectorMath vectorAux = resolver(matrizIdentidad.getColumna(i));
			matrizInversa.setColumna(vectorAux, i);
		}
		
		matrizIdentidad = null;
	
		return matrizInversa;
	}
	
	public double determinante() {
		factorizarSistema();
		
		// Compensar el cambio de signo del determinante por cada intercambio de filas durante la factorización LU
		double determinante = (intercambiosEnFilas & 1) == 0 ? 1 : -1;
		
		for(int i = 0; i < cantidadFilas; i++) {
			int indicePermutacion = vectorDePermutaciones[i];
			determinante *= factorizacionLU.getValorEn(indicePermutacion, i);
		}
		
		return determinante;
	}
	
	public double normaDos(VectorMath vectorInicial, double error) {
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
	private double eigenvalorDominante(VectorMath vectorInicial, double error) {
		
		MatrizMath matrizTranspuesta = transponer(this);
		MatrizMath matrizAuxiliar = new MatrizInvertible(this.coeficientes);
		matrizAuxiliar = producto(matrizAuxiliar, matrizTranspuesta);
		
		// Evitar NaN cuando el error es demasiado chico
		MatrizMath matrizDeCeros = new MatrizMath(this.cantidadFilas, this.cantidadColumnas);
		if(matrizAuxiliar.equals(matrizDeCeros)) {
			return 0;
		}
		
		VectorMath eigenvectorResultante, eigenvectorAnterior;
		double eigenvalorResultante = 0, eigenvalorAnterior = error;
		
		double factor = 0;
		VectorMath vectorAuxiliar1, vectorAuxiliar2;
		
		// Calculamos el eigenvector inicial
		vectorAuxiliar1 = producto(matrizAuxiliar, vectorInicial);
		factor = 1 / vectorAuxiliar1.normaDos();
		eigenvectorResultante = VectorMath.productoEscalar(vectorAuxiliar1, factor);
		
		// Calculamos el eigenvalor inicial
		vectorAuxiliar1 = VectorMath.producto(eigenvectorResultante, producto(matrizAuxiliar, eigenvectorResultante));
		vectorAuxiliar2 = VectorMath.producto(eigenvectorResultante, eigenvectorResultante);
		
		eigenvalorResultante = Math.abs(vectorAuxiliar1.getValorEn(0) / vectorAuxiliar2.getValorEn(0));
		
		// Calculamos con respecto al error pedido
		while(Math.abs(eigenvalorAnterior - eigenvalorResultante) > error) {
			eigenvalorAnterior = eigenvalorResultante;
			eigenvectorAnterior = eigenvectorResultante;
			
			// Calculamos el eigenvector
			eigenvectorResultante = producto(matrizAuxiliar, eigenvectorAnterior);
			factor = 1 / eigenvectorResultante.normaDos();
			eigenvectorResultante = VectorMath.productoEscalar(eigenvectorResultante, factor);
			
			// Calculamos el eigenvalor
			vectorAuxiliar1 = VectorMath.producto(eigenvectorResultante, producto(matrizAuxiliar, eigenvectorResultante));
			vectorAuxiliar2 = VectorMath.producto(eigenvectorResultante, eigenvectorResultante);
			
			eigenvalorResultante = Math.abs(vectorAuxiliar1.getValorEn(0) / vectorAuxiliar2.getValorEn(0));
		
		}
		
		return eigenvalorResultante;
	}
	
	// MIEMBROS PRIVADOS PARA INVERTIR
	
	private void reiniciar() {
		this.factorizacionLU = null;
		this.vectorDePermutaciones = null;
		this.intercambiosEnFilas = 0;
	}
	
	public VectorMath resolver(VectorMath vectorDeTerminosIndependientes) throws FilaDeCerosException, MatrizSingularException {
		if (factorizacionLU == null) {
			factorizarSistema();
		}
		
		VectorMath solucionIntermedia = sustitucionHaciaAdelante(vectorDeTerminosIndependientes);
		VectorMath vectorSolucion = sustitucionHaciaAtras(solucionIntermedia);
		
		return vectorSolucion;
	}
	
	private void factorizarSistema() throws FilaDeCerosException, MatrizSingularException {
		// Si ya está factorizada reiniciar
		if (factorizacionLU != null)
			reiniciar();

		int cantidadFilasDelSistema = cantidadFilas;

		// Crear un nuevo vector de permutaciones
		// LU es inicialmente una copia del sistema actual
		factorizacionLU = (MatrizCuadrada) this.clone();
		vectorDePermutaciones = new int[cantidadFilasDelSistema];

		double[] factoresEscalares = new double[cantidadFilasDelSistema];

		// Bucle de inicialización para el vector de permutaciones y factores
		for (int i = 0; i < cantidadFilas; i++) {
			vectorDePermutaciones[i] = i; // Inicialmente no hay intercambio de filas

			// Encontrar el elemento mayor de la fila
			double elementoMaximo = 0;
			for (int j = 0; j < cantidadFilasDelSistema; j++) {
				double aux = Math.abs(factorizacionLU.getValorEn(i, j));
				if (elementoMaximo < aux) {
					elementoMaximo = aux;
				}
			}

			// Inicializar el factor de escalar para equilibrar las filas
			if (elementoMaximo != 0) {
				factoresEscalares[i] = 1 / elementoMaximo;
			} else {
				throw new FilaDeCerosException(
						"El sistema no tiene solucion por los métodos usados en este sistema: existe una fila de ceros");
			}
		}

		// Realizar eliminacion con pivoteo parcial escalado: eliminacion por Gauss-Jordan
		// con intercambio de filas (no columnas) y escalando filas para evitar errores de redondeo
		eliminacionGaussJordan(factoresEscalares);

		// Verificar el elemento de abajo a la derecha de la matriz de permutaciones
		int posicionDelCero = cantidadFilasDelSistema - 1;
		if (factorizacionLU.coeficientes[vectorDePermutaciones[posicionDelCero]][posicionDelCero] == 0)
			throw new MatrizSingularException("La matriz no es invertible: sistema sin solución");
	}

	private void eliminacionGaussJordan(double[] factoresEscalares) {
		int cantidadFilasDelSistema = cantidadFilas;

		// CICLAMOS i-VECES, DONDE i ES LA CANTIDAD DE ECUACIONES MENOS UNA
		for (int i = 0; i < cantidadFilasDelSistema - 1; i++) {
			double elementoMaximoEscalado = 0;
			int indiceElementoMaximo = 0;

			// INCIO BÚSQUEDA DEL MÁXIMO VALOR EN LA i-ÉSIMA COLUMNA
			// POR DEBAJO DEL ELEMENTO ij.
			for (int j = i; j < cantidadFilasDelSistema; j++) {
				int indicePermutacion = vectorDePermutaciones[j];
				double elementoValorAbsoluto = Math.abs(factorizacionLU.coeficientes[indicePermutacion][i]);
				double elementoEscalado = elementoValorAbsoluto * factoresEscalares[indicePermutacion];

				// Si se encontró un nuevo máximo: actualizar máximo e índice del mismo.
				// El elemento máximo se encuentra en factorizacionLU.coeficientes[j][i].
				if (elementoMaximoEscalado < elementoEscalado) {
					elementoMaximoEscalado = elementoEscalado;
					indiceElementoMaximo = j;
				}
			}
			// FIN BÚSQUEDA DEL MÁXIMO VALOR EN LA I-ÉSIMA COLUMNA

			// ¿Es una matriz singular?
			if (elementoMaximoEscalado == 0)
				throw new MatrizSingularException("La matriz no es invertible: sistema sin solución");

			// Intercambiar filas si es necesario para elegir el mejor elemento pivote.
			// Para realizar un "intercambio de filas", usamos el vectorDePermutaciones.
			// Intercambiamos filas si el elemento máximo no está la fila actual (i-ésima fila).
			if (indiceElementoMaximo != i) {
				int aux = vectorDePermutaciones[i];
				vectorDePermutaciones[i] = vectorDePermutaciones[indiceElementoMaximo];
				vectorDePermutaciones[indiceElementoMaximo] = aux;
				intercambiosEnFilas++;
			}

			// Usar el índice de la fila con el pivote permutado y elegir el elemento pivote
			int indicePivotePermutado = vectorDePermutaciones[i];
			double elementoPivote = factorizacionLU.coeficientes[indicePivotePermutado][i];

			// Realizar eleminación por debajo del elemento pivote
			for (int j = i + 1; j < cantidadFilasDelSistema; j++) {
				// Usar el índice permutado de filas
				int indicePermutacion = vectorDePermutaciones[j];
				double multiplo = factorizacionLU.coeficientes[indicePermutacion][i] / elementoPivote;

				// Poner el multiplo en la matriz inferior
				factorizacionLU.coeficientes[indicePermutacion][i] = multiplo;

				// Eliminar un factor desconocido de la matriz superior.
				if (multiplo != 0) {
					for (int k = i + 1; k < cantidadFilasDelSistema; k++) {
						double elemento = factorizacionLU.coeficientes[indicePermutacion][k];

						// Sustraer el el multiplo de la fila pivote
						elemento -= multiplo * factorizacionLU.coeficientes[indicePivotePermutado][k];
						factorizacionLU.coeficientes[indicePermutacion][k] = elemento;
					}
				}
			}
		}
	}

	private VectorMath sustitucionHaciaAdelante(VectorMath vectorTerminosIndependientes) {
		int cantidadFilasDelSistema = cantidadFilas;

		VectorMath vectorAuxiliar = new VectorMath(cantidadFilasDelSistema);

		for (int i = 0; i < cantidadFilasDelSistema; i++) {
			int indicePermutacion = vectorDePermutaciones[i];
			double productoPunto = 0;
			for (int j = 0; j < i; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.getValorEn(j);
			}
			vectorAuxiliar.setValorEn(i,  
					vectorTerminosIndependientes.getValorEn(indicePermutacion) - productoPunto);
		}

		return vectorAuxiliar;
	}

	private VectorMath sustitucionHaciaAtras(VectorMath solucionIntermedia) {
		int cantidadFilasDelSistema = cantidadFilas;

		VectorMath vectorAuxiliar = new VectorMath(cantidadFilasDelSistema);

		for (int i = cantidadFilasDelSistema - 1; i >= 0; i--) {
			int indicePermutacion = vectorDePermutaciones[i];
			double productoPunto = 0;
			for (int j = i + 1; j < cantidadFilasDelSistema; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.getValorEn(j);
			}
			vectorAuxiliar.setValorEn(i, 
					(solucionIntermedia.getValorEn(i) - productoPunto)
					/ factorizacionLU.coeficientes[indicePermutacion][i] );
		}

		return vectorAuxiliar;
	}		
	
	// MAIN
	
	public static void main(String[] args) {
		MatrizInvertible mat = new MatrizInvertible(new double[][] {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}});
		MatrizInvertible inversa = new MatrizInvertible(3);
		
		inversa = mat.inversa();
		
		System.out.println(mat);
		System.out.println(inversa);
		System.out.println(mat.determinante());
		
		MatrizInvertible matriz = new MatrizInvertible(new double[][] {{1, 1, 3}, {0, 5, 1}, {0, 7, 0}});
		System.out.println(matriz.normaDos(new VectorMath(new double[] {1, 1, 1}), 0.00001));
		
		inversa = matriz.inversa();
		
		System.out.println(matriz);
		System.out.println(inversa);
		System.out.println(matriz.determinante());
		
	}

}
