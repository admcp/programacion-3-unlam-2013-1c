package ar.edu.unlam.programacion3.taller.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
	
	// ALGUNAS CONSTANTES GENERALES
	private static final String DEFAULT_HOST = "localhost";
	private static final int DEFAULT_PORT = 5222;
	private static final String DEFAULT_USERNAME = "anonymous";
	private static boolean DEBUG = true;
	
	// DATOS DEL CLIENTE
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private String host;
	private int port;
	private String userName;
	boolean connected;
	
	// CONSTRUCTORES
	public ChatClient() {
		this.host = DEFAULT_HOST;
		this.port = DEFAULT_PORT;
		this.userName = DEFAULT_USERNAME;
	}
	
	public ChatClient(String host, int port, String userName) {
		this.host = host;
		this.port = port;
		this.userName = userName;
	}
	
	// MÉTODOS UTILITARIOS
	private boolean connect() {
		// Tratamos de crear la conexión con el servidor
		try {
			socket = new Socket(host, port);
		} catch (UnknownHostException ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Error: host desconocido.");
			return false;
		} catch (IOException ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Error al abrir conexión con " + host + ":" + port + ".");
			return false;
		}
		
		// En este punto: conexión establecida
		System.out.println("Conexión correcta con " + socket.getInetAddress() + "(:" + socket.getPort() + ")");
		connected = true;
		
		// Tratamos de abrir los canales de E/S
		try {
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Error al abrir streams de E/S.");
			return false;
		}
		
		// En este punto podemos esperar a los mensajes del servidor
		new ServerListener().start();
		
		// Enviamos el nombre de usuario por única vez
		socketOut.println(userName);
		
		return true;
	}
	
	private void disconnect() {
		connected = false;
		try {
			socketOut.close();
			socketIn.close();
			socket.close();
		} catch(IOException ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Problema al cerrar conexión.");
		}
	}
	
	private void sendMessage(String message) {
		try {
			socketOut.println(message);
		} catch(Exception ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Error al enviar datos al servidor.");
		}
	}
	
	private boolean isConnected() {
		return connected;
	}
	
	// CLASE UTILITARIA
	class ServerListener extends Thread {
		@Override
		public void run() {
			while(connected) {
				try {
					System.out.println(socketIn.readLine());
					System.out.print("> ");
				} catch (IOException ex) {
					if(connected) {
						if(DEBUG) ex.printStackTrace();
						System.err.println("Error en la conexión");
						break;
					}
				}
			}
		}
	}

	// PUNTO DE EJECUCIÓN
	/**
	 * Se puede lanzar desde consola respetando los siguientes parámetros
	 * @param args [0] host, [1] puerto, [2] usuario; o bien,
	 * @param args [0] host, [1] puerto; o bien,
	 * @param args [0] usuario; o bien,
	 * @param args null.
	 */
	public static void main(String[] args) {
		String host = DEFAULT_HOST;
		int port = DEFAULT_PORT;
		String userName = DEFAULT_USERNAME;
			
		// Manejamos las distintas formas de ejecución
		switch(args.length) {
			case 0: // No usó ningún argumento (lanzado desde Eclipse, pe), usamos defaults
				break;
			case 1: // El único argumento fue el nombre de usuario
				userName = args[0];
				break;
			case 2: // Se uso el host y el puerto.
				try {
					host = args[0];
					port = Integer.parseInt(args[1]);
				} catch(NumberFormatException ex) {
					if(DEBUG) ex.printStackTrace();
					System.err.println("Puerto incorrecto");
					System.out.println("Uso: java ChatClient [host] [puerto] [usuario]");
					System.exit(1);
				}
				break;
			case 3: // Se usaron los tres argumentos
				try {
					host = args[0];
					port = Integer.parseInt(args[1]);
					userName = args[2];
				} catch(NumberFormatException ex) {
					if(DEBUG) ex.printStackTrace();
					System.err.println("Puerto incorrecto");
					System.out.println("Uso: java ChatClient [host] [puerto] [usuario]");
					System.exit(1);
				}
			default: // Macanas
				System.out.println("Uso: java ChatClient [host] [puerto] [usuario]");
				System.exit(1);
		}
		
		// Creamos el cliente
		ChatClient client = new ChatClient(host, port, userName);
		
		// Tratamos de conectar
		if(!client.connect()) {
			System.exit(1);
		}
		
		// Abrimos la lectura de la consola del cliente
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while(client.isConnected()) {
			String inputLine;
			try {
				System.out.print("> ");
				inputLine = consoleIn.readLine();
				client.sendMessage(inputLine);
				if(inputLine.equalsIgnoreCase("quit")) {
					client.disconnect();
				}
			} catch (IOException ex) {
				if(DEBUG) ex.printStackTrace();
				System.err.println("Error al leer desde la consola.");
				client.disconnect();
			}
		}
	}
	
}
