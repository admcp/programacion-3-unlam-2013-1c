package ar.edu.unlam.programacion3.adt.grafos;

import java.util.Set;

public interface Grafo<N, A> {

	// Métodos generales
	public int dimension();
	public boolean estaVacio();
	public void vaciar();
	
	// Métodos sobre nodos
	public N agregarNodo(N nodo);
	public void quitarNodo(N nodo);
	public boolean existeNodo(N nodo);
	public boolean sonAdyacentes(N nodoOrigen, N nodoDestino);
	public Set<N> getConjuntoAdyacentes(N nodoOrigen);
	
	// Métodos sobre arcos
	public A agregarArco(A arco);
	public A agregarArco(N nodoOrigen, N nodoDestino);
	public boolean quitarArco(A arco);
	public boolean quitarArco(N nodoOrigen, N nodoDestino);
	public A getArco(N nodoOrigen, N nodoDestino);
	public boolean existeArco(A arco);
	public boolean existeArco(N nodoOrigen, N nodoDestino);
	
	// Métodos sobre el grafo
	public Set<N> getConjuntoNodos();
	public Set<A> getConjuntoArcos();
	
}
