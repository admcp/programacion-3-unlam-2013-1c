package ar.edu.unlam.programacion3.adt.conjuntos;

public class ConjuntoDisjunto {
	
	private int[] conjunto; // conjunto[i] representa el i-ésimo conjunto
	private int[] cantidadDeElementos; // cantidadDeElementos[i] es la cantidad de elementos en el i-ésimo conjunto
	private int cantidadConjuntos; // cantidad de conjuntos disjuntos
	
	// Crear una estructura de "cantidadConjuntos"-conjuntos disjuntos
	public ConjuntoDisjunto(int cantidadConjuntos) {
		if(cantidadConjuntos < 0) {
			throw new IllegalArgumentException();
		}
		
		this.cantidadConjuntos = cantidadConjuntos;
		conjunto = new int[cantidadConjuntos];
		cantidadDeElementos = new int[cantidadConjuntos];
		
		for(int i = 0; i < cantidadConjuntos; i++) {
			conjunto[i] = i;
			cantidadDeElementos[i] = 1;
		}	
	}
	
	// Encontrar el i-ésimo conjunto del elemento
	public int buscar(int elemento) {
		if(elemento < 0 || elemento >= conjunto.length) {
			throw new IndexOutOfBoundsException();
		}
		
		while(elemento != conjunto[elemento]) {
			elemento = conjunto[elemento];
		}
		
		return elemento;
	}
	
	// Cantidad de conjuntos disjuntos
	public int getCantidadConjuntos() {
		return cantidadConjuntos;
	}
	
	// ¿Pertenecen elemento1 y elemento2 al mismo conjunto?
	public boolean pertenecenAlMismoConjunto(int elemento1, int elemento2) {
		return buscar(elemento1) == buscar(elemento2);
	}
	
	// Unir los conjuntos que contienen al elemento1 y al elemento2
	public void unir(int elemento1, int elemento2) {
		int i = buscar(elemento1);
		int j = buscar(elemento2);
		
		if(i == j) {
			return; // Ya estan en el mismo conjunto
		}
		
		if(cantidadDeElementos[i] < cantidadDeElementos[j]) {
			conjunto[i] = j;
			cantidadDeElementos[j] += cantidadDeElementos[i]; 
		} else {
			conjunto[j] = i;
			cantidadDeElementos[i] += cantidadDeElementos[j]; 
		}
		
		cantidadConjuntos--;
	}

}
