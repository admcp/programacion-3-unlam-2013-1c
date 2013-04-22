package ar.edu.unlam.programacion3.practica3.jerarquias.tests.punto;

import ar.edu.unlam.programacion3.practica3.jerarquias.punto.Punto2D;

public class TestPunto2D {

	public static void main(String[] args) {
		Punto2D punto1 = new Punto2D(45, 32);
		Punto2D punto2; 
		
		try {
			punto2 = (Punto2D) punto1.clone();
			System.out.println(punto2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
