package ar.edu.unlam.programacion3.practica1.probadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class ProbadorPedregal {
	
	private static final String fullPath = "resources/";
	private static final String inExtension = ".in";
	private static final String outExtension = ".out";
	
	public static void main(String[] args) {
		File file = null;
		FileReader fileReaderEntrada = null, fileReaderSalida = null;
		BufferedReader bufferedReaderEntrada = null, bufferedReaderSalida = null;
		
		FiltroPorExtension filtroIn = new FiltroPorExtension(inExtension);
		FiltroPorExtension filtroOut = new FiltroPorExtension(outExtension);

		try {
			file = new File(fullPath);
			
			String[] archivoEntrada = file.list(filtroIn);
			String[] archivoSalida = file.list(filtroOut);
			
			if(archivoEntrada.length != 1 || archivoSalida.length != 1) {
				throw new Error("Pruebe de un archivo a la vez");
			}
			
			fileReaderEntrada = new FileReader(new StringBuffer(fullPath).append(archivoEntrada[0]).toString());
			fileReaderSalida = new FileReader(new StringBuffer(fullPath).append(archivoSalida[0]).toString());
			
			bufferedReaderEntrada = new BufferedReader(fileReaderEntrada);
			bufferedReaderSalida = new BufferedReader(fileReaderSalida);
			
			// Para la lectura de archivos
			String lineBuffer = null;
			String splitBuffer[] = null;
			
			// Para el archivo .in
			int[][] pedregal;
			int dimPX = 0, dimPY = 0;
			int dimCasaX = 0, dimCasaY = 0;
			int cantPiedras = 0;
			
			// Para el archivo .out
			int posInicialX = 0, posInicialY = 0;
			String orientSalida = null;
			
			// Para la solución
			boolean solucionValida = false;

			// LECTURA ARCHIVO .in
			
			// Dimension terreno
			lineBuffer = bufferedReaderEntrada.readLine();
			splitBuffer = lineBuffer.split(" ");
			dimPX = Integer.parseInt(splitBuffer[0]);
			dimPY = Integer.parseInt(splitBuffer[1]);
			if(splitBuffer.length != 2 || (dimPX < 1 || dimPX > 1000) || (dimPY < 1 || dimPY > 1000)) {
				throw new FileFormatException(archivoEntrada[0], lineBuffer);
			}
			pedregal = new int[dimPY][dimPX];
			
			// Dimension Casa
			lineBuffer = bufferedReaderEntrada.readLine();
			splitBuffer = lineBuffer.split(" ");
			dimCasaX = Integer.parseInt(splitBuffer[0]);
			dimCasaY = Integer.parseInt(splitBuffer[1]);
			if(splitBuffer.length != 2 || (dimCasaX < 1 || dimCasaX > 100) || (dimCasaY < 1 || dimCasaY > 100)) {
				throw new FileFormatException(archivoEntrada[0], lineBuffer);
			}
			
			// Carga de la matriz con los peñascos
			lineBuffer = bufferedReaderEntrada.readLine();
			cantPiedras = Integer.parseInt(lineBuffer);
			if(cantPiedras < 1 || cantPiedras > 1000) {
				throw new FileFormatException(archivoEntrada[0], lineBuffer);
			}
			for(int i = 0; i < cantPiedras ; i++){
				splitBuffer = bufferedReaderEntrada.readLine().split(" ");
				pedregal[Integer.parseInt(splitBuffer[1]) - 1][Integer.parseInt(splitBuffer[0]) - 1] = 1;
			}
			
			// LECTURA ARCHIVO .out
			
			// LEO LA PRIMERA LINEA
			lineBuffer = bufferedReaderSalida.readLine();
			if (lineBuffer.toUpperCase().equals("SI")) {
				
				// LEO LA POSICION INICIAL
				lineBuffer = bufferedReaderSalida.readLine();
				splitBuffer = lineBuffer.split(" ");
				if (splitBuffer.length != 2) {
					throw new FileFormatException(archivoSalida[0], lineBuffer);
				}
				posInicialX = Integer.parseInt(splitBuffer[0]);
				posInicialY = Integer.parseInt(splitBuffer[1]);
				
				// LEO LA ORIENTACION
				lineBuffer = bufferedReaderSalida.readLine();
				if ((lineBuffer.length() != 1) && ("N,S,E,O".indexOf(lineBuffer) < 0)) {
					throw new FileFormatException(archivoSalida[0], lineBuffer);					
				}
				orientSalida = lineBuffer;
				
			} else if(lineBuffer.toUpperCase().equals("NO")) {
				
				// NO HAY NADA QUE HACER
				solucionValida = true;
				
			} else {
				throw new FileFormatException(archivoSalida[0], lineBuffer);
			}
			
			// Llegado este punto, ambos archivos son válidos: procedemos a probar la solución.
			// Pero puede que ya sea válida (salida por "NO").
			
			if(!solucionValida) {
			
				if(posInicialX > dimPX) {
					System.err.println("Casa fuera del pedregal: " + posInicialX + " > " + dimPX);
				} else if(posInicialY > dimPY) {
					System.err.println("Casa fuera del pedregal: " + posInicialY + " > " + dimPY);
				}
				
				int dimX;
				int dimY;
				if ("S,N".indexOf(orientSalida.toUpperCase()) >= 0) {
					// Prueba horizontal
					dimX = dimCasaX;
					dimY = dimCasaY;				
				} else {
					// Prueba Vertical
					dimX = dimCasaY;
					dimY = dimCasaX;
				}
				
				boolean continuar = true;
				for(int i = 0;(i < dimX) && (continuar == true); i++) {
					for(int j = 0; (j < dimY) && (continuar == true); j++) {
						if(pedregal[posInicialY - 1 + j][ posInicialX - 1 + i] == 0) {
							solucionValida = true;							
						} else {
							solucionValida = false;
							continuar = false;
						}
					}
				}
				
			}
			
			if(solucionValida) {
				System.out.println("Solución válida.");
			} else {
				System.out.println("Solución no válida.");
			}
			
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException ex){
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				if(bufferedReaderEntrada != null) {
					bufferedReaderEntrada.close();
				}
				if(bufferedReaderSalida != null) {
					bufferedReaderSalida.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
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
