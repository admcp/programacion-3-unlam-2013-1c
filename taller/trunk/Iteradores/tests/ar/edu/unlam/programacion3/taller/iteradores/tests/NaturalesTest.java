package ar.edu.unlam.programacion3.taller.iteradores.tests;

import java.util.Iterator;

import ar.edu.unlam.programacion3.taller.iteradores.Naturales;

public class NaturalesTest {
	
	public static void main(String[] args) {
		
		Naturales naturales = new Naturales(51);
		
		for(int normal : naturales) {
			System.out.print(normal + " ");
		}
		System.out.println();
		
		naturales.setIterator(Naturales.IT_POS_PARES);

		for(int impar : naturales) {
			System.out.print(impar + " ");
		}
		System.out.println();
		
		naturales.setIterator(Naturales.IT_POS_IMPARES);

		for(int impar : naturales) {
			System.out.print(impar + " ");
		}
		System.out.println();
		
		naturales.setIterator(Naturales.IT_NORMAL);
		Iterator<Integer> naturalesIterator = naturales.iterator();
		while(naturalesIterator.hasNext()) {
			int i = naturalesIterator.next();
			if(i >= 10 && i <= 30) {
				naturalesIterator.remove();
			}
		}
		
		for(int normal : naturales) {
			System.out.print(normal + " ");
		}
		System.out.println();
		
	}

}
