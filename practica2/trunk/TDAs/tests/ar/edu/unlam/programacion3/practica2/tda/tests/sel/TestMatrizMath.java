package ar.edu.unlam.programacion3.practica2.tda.tests.sel;

import ar.edu.unlam.programacion3.practica2.tda.sel.MatrizMath;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorColumna;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorFila;
import ar.edu.unlam.programacion3.practica2.tda.sel.VectorMath;

public class TestMatrizMath {

	public static void main(String[] args) {
		
		MatrizMath matriz1 = new MatrizMath(2, 2);
		MatrizMath matriz2 = new MatrizMath(2, 2);
		MatrizMath identidad = new MatrizMath(2, 2);
		VectorMath vec1 = new VectorColumna(2);
		VectorMath vec2 = new VectorFila(2);
		
		vec1.setValorEn(0, 1);
		vec1.setValorEn(1, 1);
		vec2.setValorEn(0, 1);
		vec2.setValorEn(1, 1);

		for (int i = 0; i < matriz1.getCantidadFilas(); i++) {
			for (int j = 0; j < matriz1.getCantidadColumnas(); j++) {
				matriz1.setValorEn(i, j, 2);
			}
		}

		for (int i = 0; i < matriz2.getCantidadFilas(); i++) {
			for (int j = 0; j < matriz2.getCantidadColumnas(); j++) {
				matriz2.setValorEn(i, j, 3);
			}
		}

		identidad.setValorEn(0, 0, 1);
		identidad.setValorEn(1, 1, 1);

		MatrizMath aux = MatrizMath.sumar(matriz1, matriz2);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}

		aux = MatrizMath.restar(matriz1, matriz2);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}

		aux = MatrizMath.producto(matriz1, identidad);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}

		aux = MatrizMath.productoEscalar(matriz1, 1);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}

		aux = MatrizMath.producto(matriz1, vec1);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		aux = MatrizMath.producto(vec2, matriz1);

		for (int i = 0; i < aux.getCantidadFilas(); i++) {
			for (int j = 0; j < aux.getCantidadColumnas(); j++) {
				System.out.print(aux.getValorEn(i, j) + " ");
			}
			System.out.println();
		}
		
		// PRUEBO LAS NORMAS.
		
		// MATRIZ
		System.out.println();
		matriz2.setValorEn(0, 0, 4);
		matriz2.setValorEn(0, 1, 4);

		for (int i = 0; i < matriz2.getCantidadFilas(); i++) {
			for (int j = 0; j < matriz2.getCantidadColumnas(); j++) {
				System.out.print(matriz2.getValorEn(i, j) + " ");
			}
			System.out.println();
		}

		
		System.out.println();
		System.out.println("Norma 1: " + matriz2.normaUno());
		System.out.println("Norma 2: " + matriz2.normaDos());
		System.out.println("Norma i: " + matriz2.normaInfinito());
		
		// VECTOR		
		vec1.setValorEn(0, 5);
		
		System.out.println();
		for (int i = 0; i < vec1.getCantidadComponentes(); i++) {
			System.out.print(vec1.getValorEn(i) + " ");
		}
		
		System.out.println();
		System.out.println("Norma 1: " + vec1.normaUno());
		System.out.println("Norma 2: " + vec1.normaDos());
		System.out.println("Norma i: " + vec1.normaInfinito());		

		
		vec2.setValorEn(1, 3);
		
		System.out.println();
		for (int i = 0; i < vec2.getCantidadComponentes(); i++) {
			System.out.print(vec2.getValorEn(i) + " ");
		}
		
		System.out.println();
		System.out.println("Norma 1: " + vec2.normaUno());
		System.out.println("Norma 2: " + vec2.normaDos());
		System.out.println("Norma i: " + vec2.normaInfinito());				
	}
}
