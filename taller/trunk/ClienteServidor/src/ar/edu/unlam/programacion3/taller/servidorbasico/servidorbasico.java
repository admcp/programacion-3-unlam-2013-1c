package ar.edu.unlam.programacion3.taller.servidorbasico;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class servidorbasico {

	/**
	 * Este es un servidor basico que permite una conexion cliente
	 * Hace un eco del mensaje recibido.
	 */
	public static void main(String[] args) {
		try {
			// creo un socket de servidor para escuchar el puerto
			ServerSocket s = new ServerSocket(8188);
			
			// espera que un cliente se conecte al puerto
			// accept en un proceso bloqueante para el thread actual.
			Socket cliente = s.accept();			
			try {
				InputStream tokenDeEntrada = cliente.getInputStream();
				OutputStream tokenDeSalida = cliente.getOutputStream();
				
				Scanner in = new Scanner(tokenDeEntrada);
				try {
					PrintWriter pwOut = new PrintWriter(tokenDeSalida, true);

					
					pwOut.println(" Hola !  escriba CHAU para salir. ");

					boolean fin = false;
					while (!fin && in.hasNextLine()) {
						String linea = in.nextLine();
						pwOut.println("Eco: " + linea);
						// si recibo la clave cierro el servidor.
						if (linea.trim().equals("CHAU")) {
							fin = true;
						}
					}
				} finally {
					in.close();
				}
				
			} finally {
				cliente.close();
				s.close();
			}
			
		} catch (IOException e)  {
			e.printStackTrace();
		}
		
	}

}
