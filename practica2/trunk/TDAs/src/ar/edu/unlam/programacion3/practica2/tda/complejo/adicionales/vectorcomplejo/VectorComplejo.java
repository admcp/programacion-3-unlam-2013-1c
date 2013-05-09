package ar.edu.unlam.programacion3.practica2.tda.complejo.adicionales.vectorcomplejo;

import java.util.Arrays;
// import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.complejo.Complejo;

public class VectorComplejo {
	private Complejo [] vectorComplejo;
	
	public VectorComplejo (int dimension) {
		
		if (dimension <= 0) {
			throw new IllegalArgumentException();
		}
		vectorComplejo = new Complejo[dimension];
		
		for(int i = 0; i < dimension; i++){
			vectorComplejo[i] = new Complejo(); 
		}
	}
	
	public void ordenar() {
		Arrays.sort(vectorComplejo);
	}
	
	public void insertar(int posicion, Complejo valor) {
		if (posicion < 0 || posicion >= vectorComplejo.length) {
			throw new IndexOutOfBoundsException();
		}
		
		if (valor == null) {
			throw new IllegalArgumentException();
		}
		
		vectorComplejo[posicion] = valor.clone();
	}
	
	public Complejo getComplejoEn(int posicion) {
		if (posicion < 0 || posicion >= vectorComplejo.length) {
			throw new IndexOutOfBoundsException();
		}
		
		return vectorComplejo[posicion];
	}
	
	public void mostrar() {
		for(int i = 0; i < 10; i++){
			System.out.println(vectorComplejo[i] + " [" + vectorComplejo[i].modulo() + "]");
		}
	}

	// ATENCION: por decreto de la catedra, para realizar las pruebas inmediatas, el main debe escribirse dentro de la misma clase.
	// Nosotros deberiamos hacer las pruebas e la clase TestVectorComplejo.Java. Comento el main para documentar lo dicho en clase.
	/*
	public static void main(String[] args) {
		VectorComplejo complejos = new VectorComplejo(10);
		
		Random random = new Random(System.currentTimeMillis());
		
		for(int i = 0; i < 10; i++){
			complejos.insertar(i,  new Complejo(random.nextDouble() * 10, random.nextDouble() * 10));
		}
	
		complejos.mostrar();
		System.out.println();
		
		complejos.ordenar();

		complejos.mostrar();
	}
	*/
}
