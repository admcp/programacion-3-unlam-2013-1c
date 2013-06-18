package ar.edu.unlam.programacion3.oia.seleccion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class NoSePuedeVivirAhi {

	public static void main(String[] args) {
		File archIn = null, archOut = null;
		FileReader archReader = null;
		BufferedReader bufferIn = null;
		PrintWriter bufferOut = null;

		try {
			archIn = new File(args.length == 0 ? "clima.in" : args[0]);
			archReader = new FileReader(archIn);
			bufferIn = new BufferedReader(archReader);
			archOut = new File(args.length == 0 ? "clima.out" : args[1]);
			bufferOut = new PrintWriter(archOut);

			String buffer;
			String[] splitBuffer;
			
			int cantidadLugares;
			int cantidadRegistros;
			List<Lugar> listaLugares = new ArrayList<Lugar>();
			
			buffer = bufferIn.readLine();
			cantidadLugares = Integer.parseInt(buffer);
			
			for(int i = 0; i < cantidadLugares; i++) {
				buffer = bufferIn.readLine();
				cantidadRegistros = Integer.parseInt(buffer);
				
				int tempMin = 100, tempMax = -100;
				int auxTempMin = 0, auxTempMax = 0;
				
				for(int j = 0; j < cantidadRegistros; j++) {
					buffer = bufferIn.readLine();
					splitBuffer = buffer.split(" ");
					
					auxTempMin = Integer.parseInt(splitBuffer[0]);
					auxTempMax = Integer.parseInt(splitBuffer[1]);
					
					if(auxTempMin <= tempMin) {
						tempMin = auxTempMin;
					}
					
					if(auxTempMax >= tempMax) {
						tempMax = auxTempMax;
					}
					
					
				}
				
				listaLugares.add(new Lugar(tempMin, tempMax));
				
			}
			
			for(int i = 0; i < listaLugares.size(); i++) {
				for(int j = 0; j < listaLugares.size(); j++) {
					if(i != j) {
						switch (listaLugares.get(i).compareTo(listaLugares.get(j))) {
							case 1: // i más hostil que j
								listaLugares.get(i).cantHostiles++;
								break;
							case 0: // i igualmente hostil que j 
								listaLugares.get(i).cantHostiles++;
								listaLugares.get(j).cantHostiles++;
								break;
							case -1: // i no es comparable con j
								listaLugares.get(i).cantNoComparables++;
								break;
						}
					}
				}
			}
			
			int index = 1;
			for(Lugar lugar : listaLugares) {
				if(lugar.cantHostiles > 0) {
					bufferOut.println(index + " " + lugar.cantNoComparables);
				}
				index++;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferIn != null) {
					bufferIn.close();
				}
				if(bufferOut != null) {
					bufferOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

class Lugar implements Comparable<Lugar>{

	int tempMin;
	int tempMax;
	int cantHostiles;
	int cantNoComparables;

	public Lugar(int tempMin, int tempMax) {
		this.tempMax = tempMax;
		this.tempMin = tempMin;
	}

	@Override
	public int compareTo(Lugar otro) {
		if((this.tempMin < otro.tempMin && this.tempMax > otro.tempMax) || // m_a < m_b && M_a > M_b   
			(this.tempMin == otro.tempMin && this.tempMax > otro.tempMax) || // m_a = m_b && M_a > M_b 
			(this.tempMin < otro.tempMin && this.tempMax == otro.tempMax)) { // m_a < m_b && M_a == M_b
			return 1;
		} else if((this.tempMin == otro.tempMin && this.tempMax == otro.tempMax)) { // m_a == m_b && M_a == M_b
			return 0;
		} else {  // no comparable
			return -1;
		}
	}
 
}
