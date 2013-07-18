package ar.edu.unlam.programacion3;


public class GeneradorLoteDePrueba {

	public static void main(String[] args) {
		
		// Primero generamos un lote de 100 grafos con el mismo %ady
		// Tenemos que %ady = k / (N - 1).
		for(int i = 1; i <= 100; i++) {
			String nodos = String.valueOf(i + 1); // 2, 3, 4, 5, ..., 101
			String grado = String.valueOf(i); // 1, 2, 3, 4, ..., 100
			String nombreArchivo = "lote/A" + String.format("%03d", i - 1) + "-grafo-" + grado + "-regular-" + nodos + "-nodos.in";
			GrafoRegularAleatorio.main(new String[] {nodos, grado, nombreArchivo});
		}
		
		// Segundo generamos un lote de 100 grafos con distinto grado, pero la misma cantidad de nodos
		for(int i = 1; i < 100; i++) {
			String nodos = String.valueOf(100); // 100
			String grado = String.valueOf(i); // 1, 2, 3, 4, ..., 99
			String nombreArchivo = "lote/B" + String.format("%03d", i - 1) + "-grafo-" + grado + "-regular-" + nodos + "-nodos.in";
			GrafoRegularAleatorio.main(new String[] {nodos, grado, nombreArchivo});
		}
	}
	
}