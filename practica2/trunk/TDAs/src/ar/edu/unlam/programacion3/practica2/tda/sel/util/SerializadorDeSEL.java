package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizIdentidad;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizInvertible;
import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.SistemaLinealDeEcuaciones;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorColumna;

public class SerializadorDeSEL {

	private final static String inPath = "loteDePruebas/entradas/";
	private final static String outPath = "loteDePruebas/SalidasGeneradas/";
	private final static String inExtension = ".in";
	private final static String outExtension = ".out";

	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;

		// Listamos archivos en directorio de entrada terminados en ".in".
		file = new File(inPath);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(inExtension));

		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos los paths completos
				String fullInPath = new StringBuffer().append(inPath).append(nombreArchivo).toString();
				String fullOutPath = new StringBuffer().append(outPath)
						.append(nombreArchivo.replace(inExtension, outExtension)).toString();

				// Abrimos el archivo in para lectura
				file = new File(fullInPath);
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);

				// Creamos el archivo out para salida
				file = new File(fullOutPath);
				printWriter = new PrintWriter(file);

				String buffer;
				String[] splitBuffer;

				/*
				 * Estructura esperada del archivo "*.in": 
				 * n (dimensión del sistema) 
				 * i j <valor> (i-fila j-columna valor) x n^2 -matriz de coeficientes- 
				 * i (i-fila) x n -vector independiente-
				 */

				// Leemos la primer línea del archivo de entrada
				buffer = bufferedReader.readLine();
				int dimension = Integer.parseInt(buffer);

				// Creamos una matriz para contener los coeficientes
				double[][] matriz = new double[dimension][dimension];

				// Llenamos la matriz con los valores del archivo
				int ii, jj;
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						buffer = bufferedReader.readLine();
						splitBuffer = buffer.split(" ");
						ii = Integer.parseInt(splitBuffer[0]);
						jj = Integer.parseInt(splitBuffer[0]);
						if (ii != i || jj != j) {
							throw new Error("Archivo mal formado");
						}
						matriz[i][j] = Double.parseDouble(splitBuffer[3]);
					}
				}

				// Creamos un vector para contener los valores del vector
				// solución
				double[] vector = new double[dimension];

				// Llenamos el vector con los valores del archivo
				for (int i = 0; i < dimension; i++) {
					buffer = bufferedReader.readLine();
					vector[i] = Double.parseDouble(buffer);
				}

				// Deserializamos ambas estructuras
				SistemaLinealDeEcuaciones sistemaLineal = new SistemaLinealDeEcuaciones(dimension);
				sistemaLineal.setCoeficientes(matriz);

				VectorColumna vectorIndependiente = new VectorColumna(dimension);
				vectorIndependiente.inicializarConVector(vector);
				
				// Resolver sistema
				VectorColumna vectorSolucion = sistemaLineal.resolver(vectorIndependiente);
				
				// Calcular error de solución
				MatrizInvertible matrizInvertible = new MatrizInvertible(matriz);
				MatrizInvertible inversa = matrizInvertible.invertir();
				
				MatrizIdentidad matrizIdentidad = new MatrizIdentidad(dimension);
				
				double error = MatrizMath.restar(matrizIdentidad, inversa).normaDos();
				
				/*
				 * Estructura esperada del archivo "*.out": 
				 * n (dimensión del sistema) 
				 * i (i-fila) x n -vector solución-
				 * e (error)
				 */
				
				printWriter.println(dimension);
				for(int i = 0; i < dimension; i++) {
					printWriter.println(vectorSolucion.getValorEn(i));
				}
				printWriter.print(error);
				

			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (printWriter != null) {
					printWriter.close();
				}
			}
		}

	}

	private static class FiltroPorExtension implements FilenameFilter {
		private String extension;

		public FiltroPorExtension(String extension) {
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(extension);
		}
	}
}