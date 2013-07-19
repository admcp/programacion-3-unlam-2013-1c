package ar.edu.unlam.programacion3.practica2.tda.sel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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

	// MIEMBROS "PÚBLICOS"
	private MatrizCuadrada matrizDeCoeficientes;
	private VectorMath vectorDeTerminosIndependientes; 
	private VectorMath vectorSolucion;
	private double error;

	// MIEMBROS "FACTORIZACIÓN LU"
	private MatrizCuadrada factorizacionLU;
	private int[] vectorDePermutaciones;
	@SuppressWarnings("unused") private int intercambiosEnFilas;

	// CONSTRUCTORES
	public SistemaLinealDeEcuaciones(String uriArchivoEntrada) throws FileNotFoundException,
																	  IOException,
																	  NumberFormatException,
																	  FilaDeCerosException,
																	  MatrizSingularException {
		BufferedReader archivoEntrada = 
				new BufferedReader(new FileReader(new File(uriArchivoEntrada)));
		
		String buffer;
		String[] splitBuffer;

		/*
		 * Estructura esperada del archivo "*.in": 
		 * n (dimensión del sistema) 
		 * i j <valor> (i-fila j-columna valor) x n^2 -matriz de coeficientes- 
		 * i (i-fila) x n -vector independiente-
		 */

		// Leemos la primer línea del archivo de entrada
		buffer = archivoEntrada.readLine();
		int dimension = Integer.parseInt(buffer);

		// Creamos una matriz para contener los coeficientes
		double[][] matriz = new double[dimension][dimension];

		// Llenamos la matriz con los valores del archivo
		int ii, jj;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				buffer = archivoEntrada.readLine();
				splitBuffer = buffer.split(" ");
				ii = Integer.parseInt(splitBuffer[0]);
				jj = Integer.parseInt(splitBuffer[1]);
				if (ii != i || jj != j) {
					archivoEntrada.close();
					throw new Error("Archivo mal formado");
				}
				matriz[i][j] = Double.parseDouble(splitBuffer[2]);
			}
		}
		
		// Creamos un vector para contener los valores del vector solución
		double[] vector = new double[dimension];

		// Llenamos el vector con los valores del archivo
		for (int i = 0; i < dimension; i++) {
			buffer = archivoEntrada.readLine();
			vector[i] = Double.parseDouble(buffer);
		}

		// Deserializamos ambas estructuras
		if(matriz == null || vector == null) {
			archivoEntrada.close();
			throw new NullPointerException();
		}
		
		MatrizMath.validarDimension(matriz.length, vector.length);
		
		this.matrizDeCoeficientes = new MatrizCuadrada(matriz);
		this.vectorDeTerminosIndependientes = new VectorMath(vector);
		reiniciarSistemaLineal();
				
		// Cerramos el archivo
		archivoEntrada.close();
		
		// Resolvemos el SEL
		try {
			resolver();
		} catch(MatrizSingularException ex) {
			System.err.println("Matriz Singular");
			throw new MatrizSingularException(ex);
		} catch(FilaDeCerosException ex) {
			System.err.println("Imposible resolver por Factorizacion LU");
			throw new FilaDeCerosException(ex);
		}
	}
	
	public SistemaLinealDeEcuaciones(MatrizCuadrada matrizDeCoeficientes, VectorMath vectorDeTerminosIndependientes) {
		if(matrizDeCoeficientes == null || vectorDeTerminosIndependientes == null) {
			throw new NullPointerException();
		}
		
		MatrizMath.validarDimension(vectorDeTerminosIndependientes.getCantidadComponentes(), matrizDeCoeficientes.cantidadFilas);
		
		this.matrizDeCoeficientes = matrizDeCoeficientes;
		this.vectorDeTerminosIndependientes = vectorDeTerminosIndependientes;
		reiniciarSistemaLineal();
	}
	
	// MÉTODOS PÚBLICOS

	public void resolver() throws FilaDeCerosException, MatrizSingularException {
		if(vectorDeTerminosIndependientes == null) {
			throw new IllegalStateException("Inicialice el vector de términos independientes.");
		}
		
		if (factorizacionLU == null) {
			factorizarSistema();
		}
		
		VectorMath solucionIntermedia = sustitucionHaciaAdelante(vectorDeTerminosIndependientes);
		VectorMath vectorSolucion = sustitucionHaciaAtras(solucionIntermedia);
		
		// Calcular error en la solución
		calcularError();
		
		// Guardar solucion temporal
		this.vectorSolucion = vectorSolucion;
	}
	
	public static void resolverSELDesdeArchivo(String uriArchivoEntrada, String uriArchivoSalida) throws FileNotFoundException,
																										 IOException,
																										 NumberFormatException {
		PrintWriter bufferSalida = null;
		try {
			
			bufferSalida = new PrintWriter(new File(uriArchivoSalida));
			
			// Levantamos el archivo y resolvemos el SEL.
			SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(uriArchivoEntrada);
			
			/*
			 * Estructura esperada del archivo "*.out": 
			 * n (dimensiónn del sistema) 
			 * i (i-fila) x n -vector solución-
			 * e (error)
			 */
			
			int dimension = sistemaLineal.matrizDeCoeficientes.cantidadFilas;
			VectorMath vectorSolucion = sistemaLineal.getVectorSolucion();
			double error = sistemaLineal.getError();
			
			bufferSalida.println(dimension);
			for(int i = 0; i < dimension; i++) {
				bufferSalida.println(vectorSolucion.getValorEn(i));
			}
			
			bufferSalida.print(error != Double.NaN ? error : 0);
		} catch(MatrizSingularException ex) {
			bufferSalida.println("Matriz Singular");
		} catch(FilaDeCerosException ex) {
			bufferSalida.println("Imposible resolver por Factorizacion LU");
		} finally {
			if(bufferSalida != null) {
				bufferSalida.close();
			}
		}
	}

	// GETTERS
	
	public MatrizCuadrada getMatrizDeCoeficientes() {
		return matrizDeCoeficientes;
	}
	
	public VectorMath getVectorDeTerminosIndependientes() {
		return vectorDeTerminosIndependientes;
	}

	public VectorMath getVectorSolucion() {
		if(vectorSolucion == null) {
			this.resolver();
		}
		return vectorSolucion;
	}
	
	public double getError() {
		return factorizacionLU == null ? 0 : error;
	}

	// MÉTODOS UTILITARIOS
	private void reiniciarSistemaLineal() {
		this.factorizacionLU = null;
		this.vectorDePermutaciones = null;
		this.intercambiosEnFilas = 0;
	}
	
	protected void factorizarSistema() throws FilaDeCerosException, MatrizSingularException {
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

	private VectorMath sustitucionHaciaAdelante(VectorMath vectorTerminosIndependientes) {
		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

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
		int cantidadFilasDelSistema = matrizDeCoeficientes.cantidadFilas;

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
		VectorMath vectorInicial = new VectorMath(matrizDeCoeficientes.cantidadFilas);
		for(int i = 0; i < matrizDeCoeficientes.cantidadFilas; i++) {
			vectorInicial.setValorEn(i, 1);
		}
		
		// Calculamos el error
		error = matrizParaCalculoDeError.normaDos(vectorInicial, 0.0001);
	}
	
	public static void main(String[] args) {
		SistemaLinealDeEcuaciones sistemaLineal = null;
		
		try {
			// Cargamos desde el archivo
			sistemaLineal =	new SistemaLinealDeEcuaciones("doc/loteDePruebas/entradas/08 - 10x10_normal.in");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		if(sistemaLineal != null) {
			// Mostramos por pantalla los datos del archivo
			System.out.println("Matriz de coef. A: \n" + sistemaLineal.getMatrizDeCoeficientes());
			System.out.println("Vector b: \n" + sistemaLineal.getVectorDeTerminosIndependientes());
			
			// Mostramos el resultado por pantalla
			System.out.println("Vector solucion: \n" + sistemaLineal.getVectorSolucion());
			System.out.println("Error: \n" + sistemaLineal.getError());
		}
	}

}
