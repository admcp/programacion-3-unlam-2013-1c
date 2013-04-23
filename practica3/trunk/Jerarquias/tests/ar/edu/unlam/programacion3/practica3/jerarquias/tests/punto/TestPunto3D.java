package ar.edu.unlam.programacion3.practica3.jerarquias.tests.punto;

import ar.edu.unlam.programacion3.practica3.jerarquias.punto.*;

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
		
		// PRUEBA PROPUESTA EN LA GUIA
		
		Punto2D p2D = new Punto2D(0,0);
		Punto3D p3D = new Punto3D(0,0,1);
		Punto3D a3D = null;
		System.out.println(p2D.equals(p3D));//false
		System.out.println(p3D.equals(p2D));// false
		System.out.println(p2D.equals(a3D));//false
		System.out.println(p3D.equals(a3D));//false
		a3D = new Punto3D(0,0,0);
		System.out.println(p2D.equals(a3D));//false
		System.out.println(a3D.equals(p2D));//false
	}
}
