package ar.edu.unlam.programacion3.practica2.tda.sel.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

public class GeneradorDeSEL {
	private static final String NEWLINE = System.getProperty("line.separator");
	private final static String outPath = "doc/loteDePruebas/entradas/";

	public static void main(String[] args) {
		// Variables para escritura
		File file = null;
		PrintWriter printWriter = null;

		// Variables para lectura desde consola
		InputStreamReader inputStreamReader = null;
		BufferedReader inputBufferedReader = null;

		// Valores aleatorios
		Random random = new Random(System.nanoTime());

		try {
			// Abrimos la consola
			inputStreamReader = new InputStreamReader(System.in);
			inputBufferedReader = new BufferedReader(inputStreamReader);

			// Leemos el nombre del archivo
			System.out.print("Nombre del archivo de salida (sin extensión): ");
			String fileName = inputBufferedReader.readLine();

			// Leemos la dimension del sistema
			int dimension = 0;

			System.out.print("Dimensión del sistema aleatoria (s/n) (2 a 1000): ");
			switch (inputBufferedReader.readLine()) {
			case "s":
			case "S":
				break;
			case "n":
			case "N":
				System.out.print("Dimensión del sistema (2 a 1000): ");
				dimension = Integer.parseInt(inputBufferedReader.readLine());
				break;
			default:
				throw new Error("Valor erróneo");
			}

			// Leemos los coeficientes (aleatorio, máximos y mínimos)
			double valorMin = -1;
			double valorMax = -1;

			System.out.print("Valor Mínimo: ");
			valorMin = Double.parseDouble(inputBufferedReader.readLine());
			System.out.print("Valor Máximo (" + (valorMin + 1) + " a ...): ");
			valorMax = Double.parseDouble(inputBufferedReader.readLine());

			boolean valoresSucesivos = false;
			System.out.print("Valores aleatorios o sucesivos (a/s): ");
			switch (inputBufferedReader.readLine()) {
			case "a":
			case "A":
				break;
			case "s":
			case "S":
				valoresSucesivos = true;
				break;
			default:
				throw new Error("Valor erróneo");
			}

			boolean truncar = true;
			System.out.print("Truncar valores (s/n): ");
			switch (inputBufferedReader.readLine()) {
			case "s":
			case "S":
				break;
			case "n":
			case "N":
				truncar = false;
				break;
			default:
				throw new Error("Valor erróneo");
			}

			// Abrimos el archivo de salida
			file = new File(outPath + fileName + ".in");
			printWriter = new PrintWriter(file);

			// Primera linea indica la dimension del sistema
			if (dimension <= 0) {
				dimension = random.nextInt(99) + 2;
			}
			printWriter.println(dimension);

			double valor = 0;
			String format = truncar ? "%d %d %.0f" + NEWLINE : "%d %d %.2f" + NEWLINE;

			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (!valoresSucesivos) {
						printWriter.format(format, i, j, random.nextDouble() * (valorMax - valorMin) + valorMin);
					} else {
						printWriter.format(format, i, j, valor);
						valor += (valorMax - valorMin) / dimension;
					}
				}
				valor = 0;
			}

			format = truncar ? "%.0f" + NEWLINE : "%.2f" + NEWLINE;
			for (int i = 0; i < dimension; i++) {
				if (!valoresSucesivos) {
					printWriter.format(format, random.nextDouble() * (valorMax - valorMin) + valorMin);
				} else {
					printWriter.format(format, valor);
					valor += (valorMax - valorMin) / dimension;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cerramos el stream de escritura
				if (printWriter != null) {
					printWriter.close();
				}
				// Cerramos el stream de lectura desde consola
				if (inputBufferedReader != null) {
					inputBufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
