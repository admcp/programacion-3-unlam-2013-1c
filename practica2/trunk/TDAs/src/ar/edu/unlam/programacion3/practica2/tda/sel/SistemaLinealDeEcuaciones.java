package ar.edu.unlam.programacion3.practica2.tda.sel;

import java.util.Arrays;
import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.FilaDeCerosException;
import ar.edu.unlam.programacion3.practica2.tda.sel.exceptions.MatrizSingularException;

public class SistemaLinealDeEcuaciones {

	private MatrizCuadrada sistemaDeEcuaciones;
	private MatrizCuadrada factorizacionLU;
	private int[] permutacion;
	private int intercambiosEnFilas;

	public SistemaLinealDeEcuaciones(int dimension) {
		sistemaDeEcuaciones = new MatrizCuadrada(dimension);
		reiniciarSistemaLineal();
	}

	public void setCoeficientes(double[][] matrizDeCoeficientes) {
		sistemaDeEcuaciones = new MatrizCuadrada(matrizDeCoeficientes);
	}
	
	public int[] getPermutacion() {
		return Arrays.copyOf(permutacion, permutacion.length);
	}

	public MatrizCuadrada getFactorizacionLU() {
		return factorizacionLU;
	}

	public int getIntercambiosEnFilas() {
		return intercambiosEnFilas;
	}

	// MÉTODOS UTILITARIOS
	
	private void reiniciarSistemaLineal() {
		this.factorizacionLU = null;
		this.permutacion = null;
		this.intercambiosEnFilas = 0;
	}
	
	// MÉTODOS DE RESOLUCIÓN DEL SISTEMA MEDIANTE FACTORIZACIÓN LU
	
	public VectorColumna resolver(VectorColumna vectorTerminosIndependientes) {
		MatrizMath.validarReferencia(vectorTerminosIndependientes);
		MatrizMath.validarDimension(vectorTerminosIndependientes.cantidadFilas, sistemaDeEcuaciones.cantidadFilas);
		
		factorizarSistema();
		VectorColumna solucionIntermedia = sustitucionHaciaAdelante(vectorTerminosIndependientes);
		VectorColumna vectorSolucion = sustitucionHaciaAtras(solucionIntermedia);
		reiniciarSistemaLineal();
	
		return vectorSolucion;
	}
	
	protected void factorizarSistema() {
		// Si ya está factorizada reiniciar
		if(factorizacionLU != null)
			reiniciarSistemaLineal();
		
		int cantidadFilasDelSistema = sistemaDeEcuaciones.cantidadFilas;
		
		// Crear un nuevo vector de permutaciones
		// LU es inicialmente una copia del sistema actual
		factorizacionLU = new MatrizCuadrada(sistemaDeEcuaciones.obtenerComoMatriz());
		permutacion = new int[cantidadFilasDelSistema];
		
		double[] factoresEscalares = new double[cantidadFilasDelSistema];
		
		// Bucle de inicialización para el vector de permutaciones y factores
		for(int i = 0; i < sistemaDeEcuaciones.cantidadFilas; i++) {
			permutacion[i] = i; // Inicialmente no hay intercambio de filas
			
			// Encontrar el elemento mayor de la fila
			double elementoMaximo = 0;
			for(int j = 0; j < cantidadFilasDelSistema; j++) {
				double aux = Math.abs(factorizacionLU.getValorEn(i, j));
				if(elementoMaximo < aux) {
					elementoMaximo = aux;
				}
			}
			
			// Inicializar el factor de escalar para equilibrar las filas
			if(elementoMaximo != 0)	{
				factoresEscalares[i] = 1 / elementoMaximo;
			} else { 
				throw new FilaDeCerosException("El sistema no tiene solucion por lo métodos usados en este sistema: existe una fila de ceros");
			}
		}
		
		// Realizar eliminacion con pivoteo parcial escalado: eliminacion por Gauss-Jordan
		// con intercambio de filas (no columnas) y escalando filas para evitar errores de redondeo
		eliminacionGaussJordan(factoresEscalares);
		
		// Verificar el elemento de abajo a la derecha de la matriz de permutaciones
		int posicionDelCero = cantidadFilasDelSistema - 1;
		if(factorizacionLU.coeficientes[permutacion[posicionDelCero]][posicionDelCero] == 0)
			throw new MatrizSingularException("La matriz no es invertible: sistema sin solución");
	}

	private void eliminacionGaussJordan(double[] factoresEscalares) {
		int cantidadFilasDelSistema = sistemaDeEcuaciones.cantidadFilas;
		
		// Bucle, una vez por cada pivote: 0...numeroFilas-1
		for(int i = 0; i < cantidadFilasDelSistema - 1; i++) {
			double elementoMaximoEscalado = 0;
			int indiceElementoMaximo = 0;
			
			// Empezando desde el i-ésima fila del pivote, mirar hacia abajo
			// en la columna de i para encontrar el mayor elemento escalado.
			for(int j = i; j < cantidadFilasDelSistema; j++) {
				// Usar el índice permutado de filas
				int indicePermutacion = permutacion[j];
				double elementoValorAbsoluto = Math.abs(factorizacionLU.coeficientes[indicePermutacion][i]);
				double elementoEscalado = elementoValorAbsoluto * factoresEscalares[indicePermutacion];
				
				if(elementoMaximoEscalado < elementoEscalado) {
					// Actualizar máximo e índice
					elementoMaximoEscalado = elementoEscalado;
					indiceElementoMaximo = j;
				}
			}
			
			// ¿Es una matriz singular?
			if(elementoMaximoEscalado == 0)
				throw new MatrizSingularException("La matriz no es invertible: sistema sin solución");
			
			// Intercambiar filas si es necesario para elegir el mejor
			// elemento pivote al hacer dicha fila la fila pivote
			if(indiceElementoMaximo != i) {
				int aux = permutacion[i];
				permutacion[i] = permutacion[indiceElementoMaximo];
				permutacion[indiceElementoMaximo] = aux;
				intercambiosEnFilas++;
			}
			
			// Usar el índice de la fila con el pivote permutado
			int indicePivotePermutado = permutacion[i];
			double elementoPivote = factorizacionLU.coeficientes[indicePivotePermutado][i];
			
			// Realizar eleminación por debajo del elemento pivote
			for(int j = i + 1; j < cantidadFilasDelSistema; j++) {
				// Usar el índice permutado de filas
				int indicePermutacion = permutacion[j];
				double multiplo = factorizacionLU.coeficientes[indicePermutacion][i] / elementoPivote;
				
				// Poner el multiplo en la matriz inferior
				factorizacionLU.coeficientes[indicePermutacion][i] = multiplo;
				
				// Eliminar un factor desconocido de la matriz superior.
				if(multiplo != 0) {
					for(int k = i + 1; k < cantidadFilasDelSistema; k++) {
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
		int cantidadFilasDelSistema = sistemaDeEcuaciones.cantidadFilas;
		
		VectorColumna vectorAuxiliar = new VectorColumna(cantidadFilasDelSistema);
		
		for(int i = 0; i < cantidadFilasDelSistema; i++) {
			int indicePermutacion = permutacion[i];
			double productoPunto = 0;
			for(int j = 0; j < i; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.coeficientes[j][0];
			}
			vectorAuxiliar.coeficientes[i][0] = 
					vectorTerminosIndependientes.coeficientes[indicePermutacion][0] - productoPunto;
		}
		
		return vectorAuxiliar;
	}
	
	private VectorColumna sustitucionHaciaAtras(VectorColumna solucionIntermedia) {
		int cantidadFilasDelSistema = sistemaDeEcuaciones.cantidadFilas;
		
		VectorColumna vectorAuxiliar = new VectorColumna(cantidadFilasDelSistema);
		
		for(int i = cantidadFilasDelSistema - 1 ; i >= 0; i--) {
			int indicePermutacion = permutacion[i];
			double productoPunto = 0;
			for(int j = i + 1; j < cantidadFilasDelSistema; j++) {
				productoPunto += 
						factorizacionLU.coeficientes[indicePermutacion][j] * vectorAuxiliar.coeficientes[j][0];
			}
			vectorAuxiliar.coeficientes[i][0] = 
					(solucionIntermedia.coeficientes[i][0] - productoPunto) / factorizacionLU.coeficientes[indicePermutacion][i];
		}
		
		return vectorAuxiliar;
	}
	
	public String mostrarFactorizacionLU() {
		return factorizacionLU.toString();
	}
	
	public static void main(String[] args) {
		VectorColumna solucionCorrecta = new VectorColumna(new double[] {1, 2, 3, 4, 5});
		int n = solucionCorrecta.getCantidadComponentes();
		Random random = new Random(System.currentTimeMillis());
		SistemaLinealDeEcuaciones matrizA = new SistemaLinealDeEcuaciones(n);
		VectorColumna vectorB = new VectorColumna(n);
		
		// Generar una matriz de coeficientes aleatorios
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				matrizA.sistemaDeEcuaciones.setValorEn(i, j, 20 * random.nextDouble() - 10);
			}
		}
		
		// Calcular los valores del vectorB usando la solución correcta
		for(int i = 0; i < n; i++) {
			double productoPunto = 0;
			for(int j = 0; j < n; j++) {
				productoPunto += matrizA.sistemaDeEcuaciones.getValorEn(i, j) * solucionCorrecta.getValorEn(j);
			}
			vectorB.setValorEn(i, productoPunto);
		}
		
		System.out.println("Matriz de coef. A: \n" + matrizA.sistemaDeEcuaciones);
		System.out.println("Vector b: \n" + vectorB);
		
		VectorColumna solucionObtenida = matrizA.resolver(vectorB);
		
		System.out.println("Factorización LU de A: \n" + matrizA.mostrarFactorizacionLU());
		System.out.println("Vector solucion: \n" + solucionObtenida);
		
		System.out.println("Norma del Vector Error: \n" + VectorColumna.restar(solucionObtenida, solucionCorrecta).normaDos());
	}

}
