package ar.edu.unlam.programacion3.practica2.tda.sel;

import java.util.Arrays;
import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.FilaDeCerosException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.MatrizSingularException;

/*
 * Sistema de la forma A * x = b.
 * Resolución por factorización LU, donde:
 *     A = L * U. Obtenición mediante Eliminación Gauss-Jordan.
 *     L * y = b. Obtención de y mediante Sustitucion Hacia Adelante.
 *     U * x = y. Obtención de x mediante Sustitución Hacia Atrás.
 */
public class SistemaLinealDeEcuaciones {

	// MIEMBROS
	private MatrizCuadrada matrizDeCoeficientes;
	private VectorColumna vectorDeTerminosIndependientes; 
	private MatrizCuadrada factorizacionLU;
	private double error;
	private int[] vectorDePermutaciones;
	private int intercambiosEnFilas;

	// CONSTRUCTORES
	public SistemaLinealDeEcuaciones(MatrizCuadrada matrizDeCoeficientes, VectorColumna vectorDeTerminosIndependientes) {
		if(matrizDeCoeficientes == null || vectorDeTerminosIndependientes == null) {
			throw new NullPointerException();
		}
		
		MatrizMath.validarDimension(vectorDeTerminosIndependientes.cantidadFilas, matrizDeCoeficientes.cantidadFilas);
		
		this.matrizDeCoeficientes = matrizDeCoeficientes;
		this.vectorDeTerminosIndependientes = vectorDeTerminosIndependientes;
		reiniciarSistemaLineal();
	}

	public SistemaLinealDeEcuaciones(MatrizCuadrada matrizDeCoeficientes) {
		if(matrizDeCoeficientes == null) {
			throw new NullPointerException();
		}
		
		this.matrizDeCoeficientes = matrizDeCoeficientes;
		reiniciarSistemaLineal();
	}

	// SETTERS
	public VectorColumna getVectorDeTerminosIndependientes() {
		return vectorDeTerminosIndependientes;
	}

	public void setVectorDeTerminosIndependientes(VectorColumna vectorDeTerminosIndependientes) {
		this.vectorDeTerminosIndependientes = vectorDeTerminosIndependientes;
	}
	
	// GETTERS
	public int[] getPermutacion() {
		return Arrays.copyOf(vectorDePermutaciones, vectorDePermutaciones.length);
	}

	public MatrizCuadrada getFactorizacionLU() {
		return factorizacionLU;
	}

	public int getIntercambiosEnFilas() {
		return intercambiosEnFilas;
	}
	
	public double getError() {
		return factorizacionLU == null ? 0 : error;
	}

	public VectorColumna resolver() {
		if(vectorDeTerminosIndependientes == null) {
			throw new IllegalStateException("Inicialice el vector de términos independientes.");
		}
		
		if (factorizacionLU == null) {
			factorizarSistema();
		}
		
		VectorColumna solucionIntermedia = sustitucionHaciaAdelante(vectorDeTerminosIndependientes);
		VectorColumna vectorSolucion = sustitucionHaciaAtras(solucionIntermedia);
		
		// Calcular error en la solución
		calcularError();
		
		return vectorSolucion;
	}
	
	public String mostrarFactorizacionLU() {
		return factorizacionLU.toString();
	}

	// MÉTODOS UTILITARIOS
	private void reiniciarSistemaLineal() {
		this.factorizacionLU = null;
		this.vectorDePermutaciones = null;
		this.intercambiosEnFilas = 0;
	}
	
	protected void factorizarSistema() {
		// Si ya está factorizada reiniciar
		if (factorizacionLU != null)
			reiniciarSistemaLineal();

		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

		// Crear un nuevo vector de permutaciones
		// LU es inicialmente una copia del sistema actual
		factorizacionLU = new MatrizCuadrada(matrizDeCoeficientes.obtenerComoMatriz());
		vectorDePermutaciones = new int[cantidadFilasDelSistema];

		double[] factoresEscalares = new double[cantidadFilasDelSistema];

		// Bucle de inicialización para el vector de permutaciones y factores
		for (int i = 0; i < matrizDeCoeficientes.cantidadFilas; i++) {
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
		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

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

	private VectorColumna sustitucionHaciaAdelante(VectorColumna vectorTerminosIndependientes) {
		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

		VectorColumna vectorAuxiliar = new VectorColumna(cantidadFilasDelSistema);

		for (int i = 0; i < cantidadFilasDelSistema; i++) {
			int indicePermutacion = vectorDePermutaciones[i];
			double productoPunto = 0;
			for (int j = 0; j < i; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.coeficientes[j][0];
			}
			vectorAuxiliar.coeficientes[i][0] = 
					vectorTerminosIndependientes.coeficientes[indicePermutacion][0]	- productoPunto;
		}

		return vectorAuxiliar;
	}

	private VectorColumna sustitucionHaciaAtras(VectorColumna solucionIntermedia) {
		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

		VectorColumna vectorAuxiliar = new VectorColumna(cantidadFilasDelSistema);

		for (int i = cantidadFilasDelSistema - 1; i >= 0; i--) {
			int indicePermutacion = vectorDePermutaciones[i];
			double productoPunto = 0;
			for (int j = i + 1; j < cantidadFilasDelSistema; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.coeficientes[j][0];
			}
			vectorAuxiliar.coeficientes[i][0] = 
					(solucionIntermedia.coeficientes[i][0] - productoPunto)
					/ factorizacionLU.coeficientes[indicePermutacion][i];
		}

		return vectorAuxiliar;
	}
	
	private void calcularError() {
		MatrizInvertible matrizInversa = new MatrizInvertible(matrizDeCoeficientes.cantidadFilas);
		MatrizIdentidad matrizIdentidad = new MatrizIdentidad(matrizDeCoeficientes.cantidadFilas);
		
		// Calculamos la inversa de la matriz de coeficientes
		for(int i = 0; i < matrizDeCoeficientes.cantidadFilas; i++) {
			matrizInversa.setColumna(sustitucionHaciaAtras(sustitucionHaciaAdelante(matrizIdentidad.getColumna(i))), i);
		}
		
		// Calculamos la identidad prima de multiplicar la matriz de coeficiente por su inversa.
		MatrizCuadrada identidadPrima = MatrizCuadrada.producto(matrizDeCoeficientes, matrizInversa);
		
		// Restamos la identidad prima y la identidad real.
		MatrizInvertible matrizParaCalculoDeError = 
				new MatrizInvertible(MatrizCuadrada.restar(identidadPrima, matrizIdentidad).obtenerComoMatriz());
		
		// Creamos un vector para usar como vector inicial de la norma 2
		VectorColumna vectorInicial = new VectorColumna(matrizDeCoeficientes.cantidadFilas);
		for(int i = 0; i < matrizDeCoeficientes.cantidadFilas; i++) {
			vectorInicial.setValorEn(i, 1);
		}
		
		// Calculamos el error
		error = matrizParaCalculoDeError.normaDos(vectorInicial, 0.0001);
	}
	
	public static void main(String[] args) {
		
		// Generamos un vector con la solución correcta
		VectorColumna solucionCorrecta = new VectorColumna(new double[] { 1, 2, 3 });
		int n = solucionCorrecta.getCantidadComponentes();
		
		// Generamos una matriz de coeficientes aleatorios
		Random random = new Random(System.currentTimeMillis());
		MatrizCuadrada matrizA = new MatrizCuadrada(n);
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				matrizA.setValorEn(i, j, random.nextInt(100));
			}
		}
		
		SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(matrizA);
		
		// Usando la solucion correcta y la matriz, creamos un vector de términos independientes
		VectorColumna vectorB = MatrizMath.producto(matrizA, solucionCorrecta);
		
		System.out.println("Matriz de coef. A: \n" + sistemaLineal.matrizDeCoeficientes);
		System.out.println("Vector b: \n" + vectorB);

		sistemaLineal.setVectorDeTerminosIndependientes(vectorB);
		VectorColumna solucionObtenida = sistemaLineal.resolver();

		System.out.println("Factorización LU de A: \n" + sistemaLineal.mostrarFactorizacionLU());
		System.out.println("Vector solucion: \n" + solucionObtenida);

		System.out.println("Error: \n" + sistemaLineal.getError());
	}

}
