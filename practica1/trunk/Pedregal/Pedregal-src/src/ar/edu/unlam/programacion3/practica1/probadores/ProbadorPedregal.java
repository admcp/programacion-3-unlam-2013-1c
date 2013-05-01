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
			
			// LEER ARCHIVOS
			
			// LECTURA ARCHIVO .in
			
			String buffer;
			int[][] pedregal;
			int dimPX, dimPY, dimCasaX, dimCasaY, cantPiedras, i;
			
			//Dimension terreno
			buffer = bufferedReaderEntrada.readLine().split(" ");
			dimPX = Integer.parseInt(buffer[0]);
			dimPY = Integer.parseInt(buffer[1]);
			pedregal = new int[dimPX][dimPY];
			
			//Dimension Casa
			buffer = bufferedReaderEntrada.readLine().split(" "));
			dimCasaX = Integer.parseInt(buffer[0]);
			dimCasaY = Integer.parseInt(buffer[1]);
			
			//Carga de la matriz con los peñascos
			
			buffer = bufferedReaderEntrada.readLine();
			cantPiedras = Integer.parseInt(buffer);
			for(i = 0; i < cantPiedras ; i++){
				buffer = bufferedReaderEntrada.readLine().split(" ");
				pedregal[Integer.parseInt(buffer[0])][Integer.parseInt(buffer[1])] = 1;
			}
			
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException ex){
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
