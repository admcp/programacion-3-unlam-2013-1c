package ar.edu.unlam.programacion3.taller.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatServer {
	
	// ALGUNAS CONSTANTES GENERALES
	private static final int DEFAULT_PORT = 5222;
	private static boolean DEBUG = true;

	// ID ÚNICAS
	private static Long uniqueId = 0L;
	
	// DATOS DEL SERVIDOR
	private Map<Long, ClientThread> clientMap;
	private int port;
	private boolean running;
	
	// CONSTRUCTORES
	public ChatServer() {
		this(DEFAULT_PORT);
	}
	
	public ChatServer(int port) {
		this.port = port;
		this.clientMap = new HashMap<Long, ClientThread>();
	}
	
	// MÉTODOS UTILITARIOS
	private void launch() {
		running = true;
		try {
			// Creamos el servidor
			ServerSocket serverSocket = new ServerSocket(port);
			
			// Y lo dejamos esperando conexiones mientras esté corriendo
			while(running) {
				System.out.println("Servidor escuchando en el puerto " + port);
				
				// Aceptamos conexiones
				Socket newClient = serverSocket.accept();
				
				// Creamos un thread por cada conexión
				ClientThread newClientThread = new ClientThread(newClient);
				
				// Lo ponemos en un mapa para fácil acceso
				clientMap.put(newClientThread.id, newClientThread);
				
				// Lanzamos el thread
				newClientThread.start();
			}
			
			// Cuando deja de correr cerramos todas la conexiones
			serverSocket.close();
			for(ClientThread client : clientMap.values()) {
				client.close();
			}
			
		} catch (IOException ex) {
			if(DEBUG) ex.printStackTrace();
			System.err.println("Error fatal en servidor.");
			System.exit(-1);
		}
	}
	
	private synchronized void broadcast(String message, Long id) {
		// Mostramos el mensaje en el servidor primero
		System.out.println(message);
		
		// Recorremos la lista de todos los clientes
		Iterator<Map.Entry<Long, ClientThread>> iterator = clientMap.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<Long, ClientThread> entry = iterator.next();
			// Si este fue el cliente que envió el mensaje, lo salteamos
			if(id != null && id.equals(entry.getKey())) {
				continue;
			}
			// Tratamos de enviar el mensaje al cliente, si se desconectó, lo eliminamos
			if(!entry.getValue().writeMessage(message)) {
				System.out.println(entry.getValue().userName + " se ha desconectado. Quitado de la lista.");
				iterator.remove();
			}
		}
	}
	
	private synchronized void remove(Long id) {
		clientMap.remove(id);
	}
	
	// CLASE UTILITARIA
	class ClientThread extends Thread {

		// DATOS DEL CLIENTE
		Socket socket;
		PrintWriter socketOut;
		BufferedReader socketIn;
		Long id;
		String userName;
		boolean connected;

		public ClientThread(Socket newClient) {
			// Asignar un id única
			id = ++uniqueId;
			
			this.socket = newClient;
			try {
				socketOut = new PrintWriter(socket.getOutputStream(), true);
				socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// Leemos el nombre de usuario
				userName = socketIn.readLine();
				broadcast(userName + " se ha conectado.", null);
				connected = true;
			} catch(IOException ex) {
				if(DEBUG) ex.printStackTrace();
				System.err.println("Error al abrir streams de E/S.");
				return;
			}
		}
		
		@Override
		public void run() {
			// Mientras este conectado
			while(connected) {
				try {
					String message = socketIn.readLine();
					if(message.equalsIgnoreCase("quit")) {
						broadcast(userName + " se ha desconectado.", null);
						connected = false;
					} else {
						broadcast(userName + ": " + message, id);
					}
				} catch (IOException ex) {
					if(DEBUG) ex.printStackTrace();
					System.err.println("Error al leer desde el cliente " + userName + ".");
					connected = false;
				}
			}
			
			// Cuando se desconecta
			remove(id);
			close();
		}
		
		private void close() {
			try {
				socketOut.close();
				socketIn.close();
				socket.close();
			} catch(IOException ex) {
				if(DEBUG) ex.printStackTrace();
				System.err.println("Problema al cerrar conexión.");
			}
		}
		
		private boolean isConnected() {
			return socket.isConnected();
		}

		private boolean writeMessage(String message) {
			// Si el cliente no está conectado: cerramos y devolvemos por falso
			if(!isConnected()) {
				close();
				return false;
			} else {
				socketOut.println(message);
				return true;
			}
		}
		
	}
	
	// PUNTO DE EJECUCIÓN
	
	/**
	 * Se puede lanzar desde consola respetando los siguientes parámetros
	 * @param args [0] puerto; o bien,
	 * @param args null.
	 */
	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		switch(args.length) {
			case 0:
				break;
			case 1:
				try {
					port = Integer.parseInt(args[0]);
				} catch(NumberFormatException ex) {
					if(DEBUG) ex.printStackTrace();
					System.err.println("Puerto incorrecto");
					System.out.println("Uso: java ChatServer [puerto]");
				}
				break;
			default:
				System.out.println("Uso: java ChatServer [puerto]");
		}
		ChatServer chatServer = new ChatServer(port);
		chatServer.launch();
	}
	
}
