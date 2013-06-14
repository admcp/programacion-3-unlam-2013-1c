package ar.edu.unlam.programacion3.taller.clientebasico;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteBasico {

	/**
	 * Este programa imprime la hora que da el servidor de la NIST 
	 */
	public static void main(String[] args) {

		try {
			// Cbro la conexion al servidor con un puerto dado.
			//Socket cliente = new Socket("time-A.timefreq.bldrdoc.gov", 13);
			Socket cliente = new Socket("localhost", 8188);
			
			// capturo la secuencia de datos que me da getInputStream
			InputStream tokenDeEntrada = cliente.getInputStream();
			OutputStream tokenDeSalida = cliente.getOutputStream();
			
			// ahora el cliente puede escribir.
			InputStreamReader inputStreamReader = new InputStreamReader(System.in);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			// preparo para leer los datos del InputStream.
			Scanner in = new Scanner(tokenDeEntrada);
			PrintWriter pwOut = new PrintWriter(tokenDeSalida, true);
			try {
				while (in.hasNextLine()) {
					String linea = in.nextLine();
					System.out.println("Eco: " + linea);
					
					// envio lo que leo por teclado.
					linea = bufferedReader.readLine();
					pwOut.println(linea);
				}
				
				
				// Cuando mostre todo lo que el servidor me mando me desconecto.				
				cliente.close();				
			} finally {
				in.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

}
