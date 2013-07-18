package ar.edu.unlam.programacion3;

import ar.edu.unlam.programacion3.adt.grafo.Coloreo;
import ar.edu.unlam.programacion3.adt.grafo.GrafoNoPesado;
import ar.edu.unlam.programacion3.adt.grafo.Nodo;

public class ProbadorColoreo {
	public static void main(String[] args) {
		GrafoNoPesado grafo = new GrafoNoPesado(5);
		grafo.agregarArco(0, 1);
		grafo.agregarArco(0, 2);
		grafo.agregarArco(2, 3);
		grafo.agregarArco(1, 3);
		grafo.agregarArco(3, 4);
		System.out.println("Secuencial");
		Coloreo.secuencial(grafo);
		for(Nodo nodo :grafo.getNodos()) {
			System.out.println(nodo);
		}
		System.out.println("Powell");
		Coloreo.powell(grafo);
		for(Nodo nodo :grafo.getNodos()) {
			System.out.println(nodo);
		}
		System.out.println("Matula");
		Coloreo.matula(grafo);
		for(Nodo nodo :grafo.getNodos()) {
			System.out.println(nodo);
		}
	}
}
