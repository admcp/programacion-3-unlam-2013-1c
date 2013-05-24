package ar.edu.unlam.programacion3.practica2.tda.tests.lista;

import ar.edu.unlam.programacion3.practica2.tda.lista.ListaEnlazada;

public class TestListaEnlazada {
	
	public static void main(String[] args) {
		
		ListaEnlazada<String> lista = new ListaEnlazada<String>();
		// cargo la lista con 5 nombres
		lista.addFirst("Jose");
		lista.add("Marcos");
		lista.add("Raul");
		lista.add("Zaul");
		lista.add(1, "Gimena");

		// imprimo en pantalla la lista cargada
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// invierto la lista
		lista.reverse();
		System.out.println("Lista invertida: ");

		// imprimo en pantalla la lista cargada
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// Obtengo el indice de gimena y lo remuevo
		lista.remove(lista.indexOf("Gimena"));
		lista.removeFirst();
		lista.removeLast();
		lista.remove("Marcos");

		// imprimo en pantalla la lista tras eliminar
		System.out.println("Lista tras eliminar Gimena, El primero, el Ultimo y a Marcos: ");
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

		// Seteo a Raul con el nombre Javier
		System.out.println("Seteo Javier al elemento Raul");
		lista.set(0, "Javier");

		// Uso getter para traer el elemento de la lista e imprimirlo
		System.out.println("El elemento de la posicion 0 es:" + lista.get(0));

		// Vacio la lista
		lista.clear();

		// Muestro la lista
		System.out.println("Listra tras hacer un clear:");
		for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i));
		}

	}
	
}
