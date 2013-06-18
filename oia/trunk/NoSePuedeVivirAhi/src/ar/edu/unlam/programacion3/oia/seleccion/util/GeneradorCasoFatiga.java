package ar.edu.unlam.programacion3.oia.seleccion.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class GeneradorCasoFatiga {
	public static void main(String[] args) {
		File file = null;
		PrintWriter bufferOut = null;
		
		try {
			file = new File("fatiga.in");
			bufferOut = new PrintWriter(file);
			
			bufferOut.println("200000");
			for(int i = 0; i < 200000; i++) {
				bufferOut.println("5");
				for(int j = 0; j < 4; j++) {
					bufferOut.println("0 0");
				}
				bufferOut.println("-50 60");
			}
			
		} catch(FileNotFoundException ex) {
			ex.printStackTrace();
		} finally {
			if(bufferOut != null) {
				bufferOut.close();
			}
		}
		
		
	}
}
