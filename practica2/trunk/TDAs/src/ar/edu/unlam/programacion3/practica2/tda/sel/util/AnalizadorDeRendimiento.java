package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizCuadrada;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.SistemaLinealDeEcuaciones;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorColumna;

public class AnalizadorDeRendimiento {
	
	private final static int CANTIDAD_MEDICIONES = 3;
	private final static boolean MATRIZ_DENSA = true;
	private final static int MAX_VALOR_ALEATORIO = 100;

	public static void main(String[] args) {
		Random random = new Random(System.currentTimeMillis());
		int[] dimensiones = new int[] { 5, 10, 50, 100, 150, 250, 350, 450, 500, 600, 700, 800, 900, 1000 };
		long tiempoInicial, tiempoFinal;
		double error = 0, tiempoPromedio = 0;
		
		for(int k = 0; k < dimensiones.length; k++) {
			tiempoInicial = 0;
			tiempoFinal = 0;
			error = 0;
			
			// Generamos una matriz
			double[][] matriz = new double[dimensiones[k]][dimensiones[k]];
			if(MATRIZ_DENSA) {
				for(int i = 0; i < dimensiones[k]; i++) {
					for(int j = 0; j < dimensiones[k]; j++) {
						matriz[i][j] = random.nextInt(MAX_VALOR_ALEATORIO);
					}
				}
			} else {
				for(int i = 0; i < dimensiones[k]; i++) {
					if(i + 1 != dimensiones[k]) {
						matriz[i][i+1] = random.nextInt(MAX_VALOR_ALEATORIO);
					}
					matriz[i][i] = random.nextInt(100);
					if(i - 1 >= 0) {
						matriz[i][i-1] = random.nextInt(MAX_VALOR_ALEATORIO);
					}
				}
			}
			
			MatrizMath matrizMath = new MatrizMath(matriz);
			
			// Generamos un vector solución para dicha matriz
			double[] vectorSolucion = new double[dimensiones[k]];
			for(int i = 0; i < dimensiones[k]; i++) {
				vectorSolucion[i] = random.nextInt(MAX_VALOR_ALEATORIO);
			}
			
			VectorColumna vectorColumnaSolucion = new VectorColumna(vectorSolucion);
			
			// Calculamos el producto: vector independiente
			double[] vectorIndependiente = new double[dimensiones[k]];
			for(int i = 0; i < dimensiones[k]; i++) {
				vectorIndependiente[i] = MatrizMath.producto(matrizMath, (VectorColumna) vectorColumnaSolucion).getValorEn(i);
			}

			// Calentamos la JVM
			for(int i = 0; i < CANTIDAD_MEDICIONES; i++) {
				resolver(dimensiones[k], matriz, vectorIndependiente);
			}

			// Y tomamos las mediciones
			tiempoInicial = System.currentTimeMillis();
			for(int i = 0; i < CANTIDAD_MEDICIONES; i++) {
				error += resolver(dimensiones[k], matriz, vectorIndependiente);
			}
			tiempoFinal = System.currentTimeMillis();
			
			// Mostramos las estadísticas
			tiempoPromedio = (tiempoFinal - tiempoInicial) / (double) CANTIDAD_MEDICIONES;
			System.out.println("Dimension: " + dimensiones[k] 
					+ " -- Tiempo promedio: " + tiempoPromedio + "ms"
					+ " -- Error: " + error / CANTIDAD_MEDICIONES);			
		
		}
	}
	
	private static double resolver(int dimension, double[][] matriz, double[] vector) {
		SistemaLinealDeEcuaciones sistemaLineal = 
				new SistemaLinealDeEcuaciones(new MatrizCuadrada(matriz), new VectorColumna(vector));
		
		// Resolver sistema
		sistemaLineal.resolver();
		
		// Calcular error de solución y devolver
		return sistemaLineal.getError();
	}
	
}
