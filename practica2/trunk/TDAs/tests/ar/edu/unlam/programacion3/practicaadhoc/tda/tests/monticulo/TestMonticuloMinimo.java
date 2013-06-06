package ar.edu.unlam.programacion3.practicaadhoc.tda.tests.monticulo;

import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.Monticulo;
import ar.edu.unlam.programacion3.practicaadhoc.tda.monticulo.MonticuloMinimo;

public class TestMonticuloMinimo {

	public static void main(String[] args) {
		Monticulo<Integer> monticuloMin = new MonticuloMinimo<Integer>();
		monticuloMin.add(20);
		monticuloMin.add(7);
		monticuloMin.add(15);
		monticuloMin.add(6);
		monticuloMin.add(2);
		monticuloMin.add(8);
		monticuloMin.add(9);
		monticuloMin.add(5);

		while (!monticuloMin.isEmpty()) {
			System.out.println(monticuloMin);
			monticuloMin.remove();
		}
	}
	
}
