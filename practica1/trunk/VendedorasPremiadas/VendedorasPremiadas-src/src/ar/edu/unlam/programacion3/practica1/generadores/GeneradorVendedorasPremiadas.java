package ar.edu.unlam.programacion3.practica1.generadores;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class GeneradorVendedorasPremiadas {

	private final static String path = "resources/";
	
	public static void main(String[] args) {
		// Para el archivo de salida
		File file = null;
		PrintWriter printWriter = null;
		
		// Para la lectura por consola
		InputStreamReader inputStreamReader = null;
		BufferedReader inputBufferedReader = null;
		
		// Para cuestiones aleatorias
		Random random = new Random(System.nanoTime());
		
		try {
			// Abrimos la consola
			inputStreamReader = new InputStreamReader(System.in);
			inputBufferedReader = new BufferedReader(inputStreamReader);
			
			// Leemos el nombre del archivo
			System.out.print("Nombre del archivo de salida (sin extension): ");
			String fileName = inputBufferedReader.readLine();
			
			// Leemos la cantidad de vendedoras
			int cantidadVendedoras = 0;
			
			System.out.print("Cantidad de vendedoras aleatoria (s/n): ");
			switch(inputBufferedReader.readLine()) {
				case "s": case "S": 
					break;
				case "n": case "N":
					System.out.print("Cantidad de vendedoras (1 a 100): ");
					cantidadVendedoras = Integer.parseInt(inputBufferedReader.readLine());
					break;
				default:
					throw new Error("Los monos se confundieron");
			}
			
			// Leemos la cantidad de ventas por vendedora (o aleatorio)
			int cantidadVentasPorVendedora = 0;
			
			System.out.print("Cantidad de ventas aleatorias (s/n): ");
			switch(inputBufferedReader.readLine()) {
				case "s": case "S": 
					break;
				case "n": case "N":
					System.out.print("Cantidad de ventas (1 a 1000): ");
					cantidadVentasPorVendedora = Integer.parseInt(inputBufferedReader.readLine());
					break;
				default:
					throw new Error("Los monos se confundieron");
			}
			
			// Leemos los importes (aleatorio, en orden máximos y mínimos)
			double importeMin = -1;
			double importeMax = -1;
			
			System.out.print("Mínimo importe (0 a 5000): ");
			importeMin = Double.parseDouble(inputBufferedReader.readLine());
			System.out.print("Máximo importe (" + (importeMin + 1)+ " a 5000): ");
			importeMax = Double.parseDouble(inputBufferedReader.readLine());
			
			boolean importesSucesivos = false;
			System.out.print("Importes aleatorios o sucesivos (a/s): ");
			switch(inputBufferedReader.readLine()) {
				case "a": case "A": 
					break;
				case "s": case "S":
					importesSucesivos = true;
					break;
				default:
					throw new Error("Los monos se confundieron");
			}
			
			boolean truncar = true;
			System.out.print("Truncar importes (s/n): ");
			switch(inputBufferedReader.readLine()) {
				case "s": case "S": 
					break;
				case "n": case "N":
					truncar = false;
					break;
				default:
					throw new Error("Los monos se confundieron");
			}
			
			// Leemos la cantidad de ventas necesarias para participar
			int cantidadDeVentasNecesarias = 0;
			System.out.print("Cantidad de ventas aleatoria (s/n): ");
			switch(inputBufferedReader.readLine()) {
				case "s": case "S": 
					break;
				case "n": case "N":
					System.out.print("Cantidad de ventas necesarias (1 a 1000): ");
					cantidadDeVentasNecesarias = Integer.parseInt(inputBufferedReader.readLine());
					break;
				default:
					throw new Error("Los monos se confundieron");
			}
			
			// Abrimos el archivo de salida
			file = new File(path + fileName + ".in");
			printWriter = new PrintWriter(file);
			
			// Primera linea indica la cantidad de vendedoras
			if(cantidadVendedoras <= 0) {
				cantidadVendedoras = random.nextInt(100) + 1;
			}
			printWriter.println(cantidadVendedoras);
			
			// cantidadDeVendedoras-secciones con primer línea indicando
			// cantidad de ventas (cantidadVentasPorVendedora) y 
			// cantidadVentasPorVendedora-líneas con los importes de cada venta
			double importe = 0;
			String format = truncar ? "%.0f\n" : "%.2f\n";
			if(cantidadVentasPorVendedora <= 0) {
				for(int i = 0; i < cantidadVendedoras; i++) {
					cantidadVentasPorVendedora = random.nextInt(1000) + 1;
					printWriter.println(cantidadVentasPorVendedora);
					for(int j = 0; j < cantidadVentasPorVendedora; j++) {
						if(!importesSucesivos) {
							printWriter.format(format, random.nextDouble() * (importeMax - importeMin) + importeMin);
						} else {
							printWriter.format(format, importe);
							importe += (importeMax - importeMin) / cantidadVentasPorVendedora;
						}
					}
					importe = 0;
				}
			} else {
				for(int i = 0; i < cantidadVendedoras; i++) {
					printWriter.println(cantidadVentasPorVendedora);
					for(int j = 0; j < cantidadVentasPorVendedora; j++) {
						if(!importesSucesivos) {
							printWriter.format(format, random.nextDouble() * (importeMax - importeMin) + importeMin);
						} else {
							printWriter.format(format, importe);
							importe += (importeMax - importeMin) / cantidadVentasPorVendedora;
						}
					}
					importe = 0;
				}
			}
			
			// Última línea con la cantidad de ventas necesarias
			if(cantidadDeVentasNecesarias <= 0) {
				cantidadDeVentasNecesarias = random.nextInt(1000) + 1;
			}
			printWriter.print(cantidadDeVentasNecesarias);
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
		} finally {	
			try {
				// Cerramos el stream de escritura
				if(printWriter != null) {
					printWriter.close();
				}
				// Cerramos el stream de lectura desde consola
				if(inputBufferedReader != null) {
					inputBufferedReader.close();
				}
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}

	}

}
