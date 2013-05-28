package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorColumna;

public class ProbadorDeSEL {

	private final static String inPath = "doc/loteDePruebas/entradas/";
	private final static String outPath = "doc/loteDePruebas/salidasGeneradas/";
	private final static String inExtension = ".in";
	private final static String outExtension = ".out";

	public static void main(String[] args) {
		File file;
		FileReader fileReader;
		BufferedReader bufferedReaderIn = null;
		BufferedReader bufferedReaderOut = null;

		// Listamos archivos en directorio de salida terminados en ".out".
		file = new File(outPath);
		String[] archivosDeEntrada = file.list(new FiltroPorExtension(outExtension));

		for (String nombreArchivo : archivosDeEntrada) {
			try {
				// Generamos los paths completos
				String fullOutPath = new StringBuffer()
										.append(outPath)
										.append(nombreArchivo)
										.toString();
				String fullInPath = new StringBuffer()
										.append(inPath)
										.append(nombreArchivo.replace(outExtension, inExtension))
										.toString();

				// Abrimos el archivo in para lectura
				file = new File(fullInPath);
				fileReader = new FileReader(file);
				bufferedReaderIn = new BufferedReader(fileReader);

				// Abrimos el archivo out para lectura
				file = new File(fullOutPath);
				fileReader = new FileReader(file);
				bufferedReaderOut = new BufferedReader(fileReader);

				System.out.println("Comparando: " + fullOutPath + " con " + fullInPath);
				
				String buffer;
				String[] splitBuffer;

				/*
				 * Estructura esperada del archivo "*.in": 
				 * n (dimensión del sistema) 
				 * i j <valor> (i-fila j-columna valor) x n^2 -matriz de coeficientes- 
				 * i (i-fila) x n -vector independiente-
				 */

				// Leemos la primer línea del archivo de entrada
				buffer = bufferedReaderIn.readLine();
				int dimension = Integer.parseInt(buffer);

				// Creamos una matriz para contener los coeficientes
				double[][] matriz = new double[dimension][dimension];

				// Llenamos la matriz con los valores del archivo
				int ii, jj;
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						buffer = bufferedReaderIn.readLine();
						splitBuffer = buffer.split(" ");
						ii = Integer.parseInt(splitBuffer[0]);
						jj = Integer.parseInt(splitBuffer[1]);
						if (ii != i || jj != j) {
							throw new Error("Archivo mal formado");
						}
						matriz[i][j] = Double.parseDouble(splitBuffer[2]);
					}
				}
				
				// Creamos un vector para contener los valores del vector solución
				double[] vector = new double[dimension];

				// Llenamos el vector con los valores del archivo
				for (int i = 0; i < dimension; i++) {
					buffer = bufferedReaderIn.readLine();
					vector[i] = Double.parseDouble(buffer);
				}
				
				/*
				 * Estructura esperada del archivo "*.out": 
				 * n (dimensión del sistema) 
				 * i (i-fila) x n -vector solucion-
				 * e (error)
				 * ó RAZON
				 */
				
				// Leemos la primer línea del archivo de entrada
				buffer = bufferedReaderOut.readLine();
				
				if(buffer.equals("Matriz Singular") || buffer.equals("Imposible resolver por Factorizacion LU")) {
					System.out.println("El archivo es válido.");
				} else {
					int dimension2 = Integer.parseInt(buffer);
					
					if(dimension2 == dimension) {
						// Creamos un vector para contener los valores del vector solución
						double[] vectorSalida = new double[dimension];

						// Llenamos el vector con los valores del archivo
						for (int i = 0; i < dimension; i++) {
							buffer = bufferedReaderOut.readLine();
							vectorSalida[i] = Double.parseDouble(buffer);
						}
						
						// Leemos el error
						double error = 0;
						buffer = bufferedReaderOut.readLine();
						error = Double.parseDouble(buffer);
						
						if(error > 10e-12) {
							System.out.println("Atención: el error es mayor que lo permitido.");
						}
						
						// Verificamos si el vector de salida es una solución posible al sistema
						VectorColumna vectorSolucionCalculada 
							= MatrizMath.producto(new MatrizMath(matriz), new VectorColumna(vectorSalida));
						VectorColumna vectorSolucionLeida = new VectorColumna(vector);
						
						for(int i = 0; i < dimension; i++) {
							if(!aproximadamenteIguales(vectorSolucionCalculada.getValorEn(i), 
									                   vectorSolucionLeida.getValorEn(i), 
									                   10e-12)) {
								System.out.println("Valor en " + (i + 1) + " difiere en " 
									                   + Math.abs(vectorSolucionCalculada.getValorEn(i) - vectorSolucionLeida.getValorEn(i)));
							}
						}
						System.out.println("El archivo parece válido.");
						
					} else {
						System.out.println("Error: dimensiones no compatibles.");
					}
				}

			} catch(FileNotFoundException ex) {
				ex.printStackTrace();
			} catch(IOException ex) {
				ex.printStackTrace();
			} catch(NumberFormatException ex) {
				ex.printStackTrace();
			} finally {
				if (bufferedReaderIn != null) {
					try {
						bufferedReaderIn.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
				if (bufferedReaderOut != null) {
					try {
						bufferedReaderOut.close();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		}

	}
	
	private static boolean aproximadamenteIguales(double valor1, double valor2, double epsilon) {
		return Math.abs(valor1 - valor2) <= epsilon;
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
