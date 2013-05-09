package ar.edu.unlam.programacion3.practica2.tda.tests.complejo.adicionales.vectorcomplejo;

import java.util.Random;

import ar.edu.unlam.programacion3.practica2.tda.complejo.Complejo;
import ar.edu.unlam.programacion3.practica2.tda.complejo.adicionales.vectorcomplejo.VectorComplejo;

public class TestVectorComplejo {

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
}
