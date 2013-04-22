package ar.edu.unlam.programacion3.practica3.jerarquias.tests.punto;

import ar.edu.unlam.programacion3.practica3.jerarquias.punto.Punto3D;

public class TestPunto3D {

	public static void main(String[] args) {

		Punto3D punto1 = new Punto3D(45, 5, 2); 
		Punto3D punto2 = new Punto3D(45, 3, 2);
		Punto3D punto3 = null;
		
		try {
			System.out.println(punto1.toString());
			
			System.out.println(punto1.equals(punto2));
			
			punto3 = (Punto3D) punto1.clone();
			
			System.out.println(punto3);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
