package ar.edu.unlam.programacion3;


public class GeneradorLoteDePrueba {
	
	private static final boolean LOTE_A = false;
	private static final boolean LOTE_B = false;
	private static final boolean LOTE_C = true;

	public static void main(String[] args) {
		
		if (LOTE_A) {
			// Primero generamos un lote de 100 grafos con el mismo %ady
			// Tenemos que %ady = k / (N - 1).
			for (int i = 1; i <= 100; i++) {
				String nodos = String.valueOf(i + 1); // 2, 3, 4, 5, ..., 101
				String grado = String.valueOf(i); // 1, 2, 3, 4, ..., 100
				String nombreArchivo = "lote/A" + String.format("%03d", i - 1) +
						"-grafo-" + grado + "-regular-" +
						nodos + "-nodos.in";
				GrafoRegularAleatorio.main(new String[] { nodos, grado, nombreArchivo });
			}
		}
		
		
		if (LOTE_B) {
			// Segundo generamos un lote de 100 grafos con distinto grado, pero la misma cantidad de nodos
			for (int i = 1; i < 100; i++) {
				String nodos = String.valueOf(100); // 100
				String grado = String.valueOf(i); // 1, 2, 3, 4, ..., 99
				String nombreArchivo = "lote/B" + String.format("%03d", i - 1) +
						"-grafo-" + grado + "-regular-" + 
						nodos + "-nodos.in";
				GrafoRegularAleatorio.main(new String[] { nodos, grado, nombreArchivo });
			}
		}
		
		if (LOTE_C) {
			// Generamos un lote de 500, 1000, 5000, y 10000 nodos.
			// Con un 50%, un 70% y un 90% de adyacencia
			
			int[] cantidadNodos = new int[] { 500, 1000, 5000, 10000 };
			double[] adyacencias = new double[] { 0.5, 0.7, 0.9 };
			
			for(int i = 0; i < cantidadNodos.length; i++) {
				String nodos = String.valueOf(cantidadNodos[i]); // 500, 1000, 5000, 10000
				for(int j = 0; j < adyacencias.length; j++) {
					String grado = String.valueOf(grado(adyacencias[j], cantidadNodos[i])); // depende de N
					String nombreArchivo = "lote/C" + String.format("%02d", i - 1) +
							"-grafo-" + grado + "-regular-" + 
							nodos + "-nodos.in";
					GrafoRegularAleatorio.main(new String[] { nodos, grado, nombreArchivo });
				}
			}
	
		}
	}
	
	private static int grado(double ady, int nodos) {
		return (int) Math.ceil(ady * (nodos - 1));
	}
	
}