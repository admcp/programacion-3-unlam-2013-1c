package ar.edu.unlam.programacion3.practicaadhoc.tda.tests.monticulo;

import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.Monticulo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.MonticuloMaximo;

public class TestMonticuloMaximo {

	public static void main(String[] args) {
		Monticulo<Integer> monticuloMax = new MonticuloMaximo<Integer>();
		monticuloMax.add(20);
		monticuloMax.add(7);
		monticuloMax.add(15);
		monticuloMax.add(6);
		monticuloMax.add(2);
		monticuloMax.add(8);
		monticuloMax.add(9);
		monticuloMax.add(5);
		
		while (!monticuloMax.isEmpty()) {
			System.out.println(monticuloMax);
			monticuloMax.remove();
		}
	}
	
}
