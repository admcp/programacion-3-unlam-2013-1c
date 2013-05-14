package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizCuadrada;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizIdentidad;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizInvertible;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.SistemaLinealDeEcuaciones;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorColumna;

public class AnalizadorDeRendimiento {

	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		int[] dimensiones = new int[] { 5, 10, 50, 100, 150, 250, 350, 450, 500, 600, 700 };
		long tiempoInicial, tiempoFinal;
		double error = 0, tiempoPromedio = 0;
		
		for(int k = 0; k < dimensiones.length; k++) {
			tiempoInicial = 0;
			tiempoFinal = 0;
			
			// Generamos una matriz
			double[][] matriz = new double[dimensiones[k]][dimensiones[k]];
			for(int i = 0; i < dimensiones[k]; i++) {
				for(int j = 0; j < dimensiones[k]; j++) {
					matriz[i][j] = random.nextDouble();
				}
			}
			
			MatrizMath matrizMath = new MatrizMath(matriz);
			
			// Generamos un vector solución para dicha matriz
			double[] vectorSolucion = new double[dimensiones[k]];
			for(int i = 0; i < dimensiones[k]; i++) {
				vectorSolucion[i] = random.nextDouble();
			}
			
			VectorColumna vectorColumnaSolucion = new VectorColumna(vectorSolucion);
			
			// Calculamos el producto: vector independiente
			double[] vectorIndependiente = new double[dimensiones[k]];
			for(int i = 0; i < dimensiones[k]; i++) {
				vectorIndependiente[i] = MatrizMath.producto(matrizMath, (VectorColumna) vectorColumnaSolucion).getValorEn(i);
			}

			// Calentamos la JVM
			for(int i = 0; i < 2; i++) {
				resolver(dimensiones[k], matriz, vectorIndependiente);
			}

			// Y tomamos dos mediciones
			tiempoInicial = System.nanoTime();
			for(int i = 0; i < 2; i++) {
				error = resolver(dimensiones[k], matriz, vectorIndependiente);
			}
			tiempoFinal = System.nanoTime();
			
			// Mostramos las estadísticas
			tiempoPromedio = (tiempoFinal - tiempoInicial) / 2.0;
			System.out.println("Dimension: " + dimensiones[k] 
					+ " -- Tiempo promedio: " + tiempoPromedio + "ns"
					+ " -- Error: " + error);			
			
			// Desinicialización para evitar un OutOfMemoryError con matrices muy grandes
			matriz = null;
			matrizMath = null;

		}
	}
	
	@SuppressWarnings("unused")
	private static double resolver(int dimension, double[][] matriz, double[] vector) {
		SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(dimension);
		sistemaLineal.setCoeficientes(matriz);

		VectorColumna vectorIndependiente = new VectorColumna(dimension);
		vectorIndependiente.inicializarConVector(vector);
		
		// Resolver sistema
		VectorColumna vectorSolucion = sistemaLineal.resolver(vectorIndependiente);
		
		// Calcular error de solución
		MatrizCuadrada matrizInvertible = new MatrizInvertible(matriz);
		MatrizCuadrada inversa = ((MatrizInvertible) matrizInvertible).invertir();
		MatrizCuadrada identidadPrima = MatrizCuadrada.producto(matrizInvertible, inversa);
		
		MatrizCuadrada matrizIdentidad = new MatrizIdentidad(dimension);
		
		MatrizInvertible matrizParaCalculoDeError = new MatrizInvertible(MatrizCuadrada.restar(identidadPrima, matrizIdentidad).obtenerComoMatriz());
		
		VectorColumna vectorInicial = new VectorColumna(dimension);
		for(int i = 0; i < dimension; i++) {
			vectorInicial.setValorEn(i, 1);
		}
		
		double error = matrizParaCalculoDeError.normaDos(vectorInicial, 0.0001);
		
		// Desinicialización para evitar un OutOfMemoryError con matrices muy grandes
		sistemaLineal = null;
		matrizInvertible = null;
		inversa = null;
		identidadPrima = null;
		matrizParaCalculoDeError = null;
		
		return error;
	}
	
}
