package ar.edu.unlam.programacion3.problemas.nombresrepetidos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NombresRepetidos {

	public static void main(String[] args) {
		File archivoEntrada = null;
		File archivoSalida = null;
		FileReader lectorArchivoEntrada = null;
		BufferedReader bufferedReaderEntrada = null;
		PrintWriter printWriterSalida = null;

		try {
			archivoEntrada = new File("nombres.in");
			lectorArchivoEntrada = new FileReader(archivoEntrada);
			bufferedReaderEntrada = new BufferedReader(lectorArchivoEntrada);
			archivoSalida = new File("nombres.out");
			printWriterSalida = new PrintWriter(archivoSalida);

			String cadena;
			String[] arrayCadena;
			int cantNombres;
			int cantRepetidos;

			cadena = bufferedReaderEntrada.readLine();
			arrayCadena = cadena.split(" ");
			cantNombres = Integer.parseInt(arrayCadena[0]);
			cantRepetidos = Integer.parseInt(arrayCadena[1]);

			List<Par> listaDePares = new ArrayList<Par>();
			for (int i = 0; i < cantNombres; i++) {
				cadena = bufferedReaderEntrada.readLine();
				Par objeto = new Par(cadena, 1);
				if (listaDePares.contains(objeto)) {
					listaDePares.get(listaDePares.indexOf(objeto)).incFrecuencia();
				} else {
					listaDePares.add(objeto);
				}
			}
			Collections.sort(listaDePares);

			for (int i = 0; i < cantRepetidos; i++) {
				if (listaDePares.get(i).getFrecuencia() > 1) {
					printWriterSalida.println(listaDePares.get(i).getNombre() + " "
							+ listaDePares.get(i).getFrecuencia());
				} else {
					printWriterSalida.println("No hay mas nombres repetidos.");
					break;
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} finally {
			if (bufferedReaderEntrada != null) {
				try {
					bufferedReaderEntrada.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			if (printWriterSalida != null) {
				printWriterSalida.close();
			}
		}
	}
}

class Par implements Comparable<Par> {
	String nombre;
	int frecuencia;

	Par(String nombre, int frecuencia) {
		this.frecuencia = frecuencia;
		this.nombre = nombre;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void incFrecuencia() {
		this.frecuencia++;
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Par other = (Par) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public int compareTo(Par otro) {
		return otro.frecuencia - this.frecuencia;
	}

}
