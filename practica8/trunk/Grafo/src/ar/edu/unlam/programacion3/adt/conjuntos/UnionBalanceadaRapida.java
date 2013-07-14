package ar.edu.unlam.programacion3.adt.conjuntos;

public class UnionBalanceadaRapida {
	
	private int[] id;
	private int[] tam;
	private int cantidad;
	
	public UnionBalanceadaRapida(int cantidad) {
		if(cantidad < 0) {
			throw new IllegalArgumentException();
		}
		
		this.cantidad = cantidad;
		id = new int[cantidad];
		tam = new int[cantidad];
		
		for(int i = 0; i < cantidad; i++) {
			id[i] = i;
			tam[i] = 1;
		}	
	}
	
	public int buscar(int elemento) {
		if(elemento < 0 || elemento >= id.length) {
			throw new IndexOutOfBoundsException();
		}
		
		while(elemento != id[elemento]) {
			elemento = id[elemento];
		}
		
		return elemento;
	}
	
	public int getCantidadConjuntos() {
		return cantidad;
	}
	
	public boolean estanConectados(int elemento1, int elemento2) {
		return buscar(elemento1) == buscar(elemento2);
	}
	
	public void unir(int elemento1, int elemento2) {
		int i = buscar(elemento1);
		int j = buscar(elemento2);
		
		if(i == j) {
			return;
		}
		
		if(tam[i] < tam[j]) {
			id[i] = j;
			tam[j] += tam[i]; 
		} else {
			id[j] = i;
			tam[i] += tam[j]; 
		}
		
		cantidad--;
	}

}
