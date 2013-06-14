package ar.edu.unlam.programacion3.oia.nivel1.nosepuedevivirahi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class NoSePuedeVivirAhi {


	public static void main(String[] args) {
		File archIn = null;
		FileReader archReader = null;
		BufferedReader bufferIn = null;
		
		try{
			archIn = new File("clima.in");
			archReader = new FileReader(archIn);
			bufferIn = new BufferedReader(archReader);
			
			int cantidadLugares;
			int cantidadRegistros;
			
		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} catch(NumberFormatException e){
			e.printStackTrace();
		} finally {
			try {
				if(bufferIn != null){
					bufferIn.close();
				}
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	
	private class Lugar{
		int tempMin;
		int tempMax;
		int cantHostiles;
		int cantNoComparables;
		
		public Lugar(int tempMin, int tempMax){
			this.tempMax = tempMax;
			this.tempMin = tempMin;
		}

		public int getTempMin() {
			return tempMin;
		}

		public int getTempMax() {
			return tempMax;
		}

		public int getCantHostiles() {
			return cantHostiles;
		}

		public int getCantNoComparables() {
			return cantNoComparables;
		}
		
		public void incCantHostiles() {
			cantHostiles++;
		}

		public void incCantNoComparables() {
			cantNoComparables++;
		}
		
		
	}

}
