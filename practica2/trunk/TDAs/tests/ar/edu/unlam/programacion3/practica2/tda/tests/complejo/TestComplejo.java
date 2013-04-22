package ar.edu.unlam.programacion3.practica2.tda.tests.complejo;

import java.util.HashSet;
import java.util.Set;

import ar.edu.unlam.programacion3.practica2.tda.complejo.Complejo;

public class TestComplejo {
	private static Complejo complejo1, complejo2;
	private static Complejo complejoNulo;
	
	public static void main(String[] args) {
		testDeConstructores();
		testClone();
		testEquals();
		testHashCode();
		testToString();
		testExcepcionNulos();
		testSumas();
		testRestas();
		testMultiplicaciones();
		testExcepcionDivisionPorCero();
		testDivisiones();
		testModulo();
	}
	
	private static void startUp() {
		complejo1 = new Complejo(1, 1);
		complejo2 = new Complejo(2, 2);
		complejoNulo = new Complejo();
	}
	
	private static void testDeConstructores() {
		startUp();
		
		if(complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) {
			throw new AssertionError("Falla en el constructor con parámetros");
		} 
		
		if(complejoNulo.getParteReal() != 0 || complejoNulo.getParteImaginaria() != 0) {
			throw new AssertionError("Falla en el constructor por defecto");
		}
		
		tearDown();
	}
	
	private static void testClone() {
		startUp();
		
		Complejo clonComplejo1 = complejo1.clone();
		
		if(complejo1.getParteReal() != clonComplejo1.getParteReal() 
				|| complejo1.getParteImaginaria() != clonComplejo1.getParteImaginaria()) {
			throw new AssertionError("Falla en el método clone()");
		}

		complejo1.setParteReal(2);
		complejo1.setParteImaginaria(2);
		
		if(complejo1.getParteReal() == clonComplejo1.getParteReal() 
				|| complejo1.getParteImaginaria() == clonComplejo1.getParteImaginaria()) {
			throw new AssertionError("Falla en el método clone()");
		}
		
		tearDown();
	}
	
	private static void testEquals() {
		startUp();
		
		Complejo clonComplejo1 = complejo1.clone();
		Complejo clonClonComplejo1 = clonComplejo1.clone();
		
		if(!complejo1.equals(complejo1)) {
			throw new AssertionError("Falla en el método equals(): reflexividad");
		} 
		
		if(complejo1.equals(clonComplejo1) && !clonComplejo1.equals(complejo1)) {
			throw new AssertionError("Falla en el método equals(): simetria");
		} 
		
		if(complejo1.equals(clonComplejo1) && clonComplejo1.equals(clonClonComplejo1)) {
			if(!complejo1.equals(clonClonComplejo1)) {
				throw new AssertionError("Falla en el método equals(): transitividad");
			}
		} 
		
		if(complejo1 != null && complejo1.equals(null) == true) {
			throw new AssertionError("Falla en el método equals(): nulos");
		}
		
		tearDown();
	}
	
	private static void testHashCode() {
		startUp();
		
		Complejo clonComplejo1 = complejo1.clone();
		Set<Complejo> setComplejo = new HashSet<Complejo>();
		
		setComplejo.add(complejo1);
		
		if(!setComplejo.contains(clonComplejo1)) {
			throw new AssertionError("Falla en el método hashCode()");
		}
		
		tearDown();
	}
	
	private static void testToString() {
		startUp();
		
		String string1 = complejo1.toString();
		String string2 = "(" + complejo1.getParteReal() + ", " + complejo1.getParteImaginaria() + ")";
		
		if(!string1.equals(string2)) {
			throw new AssertionError("Falla en el método toString()");
		}
		
		tearDown();
	}
	
	private static void testExcepcionNulos() {
		try {
			Complejo.chequearNulo(null);
			throw new AssertionError("Falla en el método chequearNulo()");
		} catch(IllegalArgumentException ex) {
			// todo OK.
		}
	}
	
	private static void testSumas() {
		startUp();
		
		Complejo sumaInstancia, sumaClase;
		double valor1 = 1, valor2 = -1;
		
		sumaInstancia = complejo1.sumar(complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(sumaInstancia.getParteReal() != complejo1.getParteReal() + complejo2.getParteReal() || // si no se realizó la suma
				sumaInstancia.getParteImaginaria() != complejo1.getParteImaginaria() + complejo2.getParteImaginaria())) {
			throw new AssertionError("Falla en la suma con operando complejo");
		}
		sumaInstancia = null;
		
		sumaInstancia = complejo1.sumar(valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(sumaInstancia.getParteReal() != complejo1.getParteReal() + valor1 || // si no se realizó la suma
				sumaInstancia.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la suma con operando real");
		}
		sumaInstancia = null;
		
		sumaClase = Complejo.sumar(complejo1, complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(sumaClase.getParteReal() != complejo1.getParteReal() + complejo2.getParteReal() || // si no se realizó la suma
					sumaClase.getParteImaginaria() != complejo1.getParteImaginaria() + complejo2.getParteImaginaria())) {
			throw new AssertionError("Falla en la suma estática con operandos complejos");
		}
		sumaClase = null;
		
		sumaClase = Complejo.sumar(complejo1, valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(sumaClase.getParteReal() != complejo1.getParteReal() + valor1 || // si no se realizó la suma
					sumaClase.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la suma estática con operandos complejo y real (derecha)");
		}
		sumaClase = null;
		
		sumaClase = Complejo.sumar(valor1, complejo1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(sumaClase.getParteReal() != complejo1.getParteReal() + valor1 || // si no se realizó la suma
					sumaClase.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la suma estática con operandos complejo y real (izquierda)");
		}
		sumaClase = null;
		
		sumaClase = Complejo.sumar(valor1, valor2);
		if((sumaClase.getParteReal() != valor1 + valor2 || 	sumaClase.getParteImaginaria() != 0)) { // si no se realizó la suma
			throw new AssertionError("Falla en la suma estática con operandos reales");
		}
		sumaClase = null;
		
		tearDown();
	}
	
	private static void testRestas() {
		startUp();
		
		Complejo restaInstancia, restaClase;
		double valor1 = 1, valor2 = -1;
		
		restaInstancia = complejo1.restar(complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(restaInstancia.getParteReal() != complejo1.getParteReal() - complejo2.getParteReal() || // si no se realizó la resta
				restaInstancia.getParteImaginaria() != complejo1.getParteImaginaria() - complejo2.getParteImaginaria())) {
			throw new AssertionError("Falla en la resta con operando complejo");
		}
		restaInstancia = null;
		
		restaInstancia = complejo1.restar(valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(restaInstancia.getParteReal() != complejo1.getParteReal() - valor1 || // si no se realizó la resta
				restaInstancia.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la resta con operando real");
		}
		restaInstancia = null;
		
		restaClase = Complejo.restar(complejo1, complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(restaClase.getParteReal() != complejo1.getParteReal() - complejo2.getParteReal() || // si no se realizó la resta
					restaClase.getParteImaginaria() != complejo1.getParteImaginaria() - complejo2.getParteImaginaria())) {
			throw new AssertionError("Falla en la resta estática con operandos complejos");
		}
		restaClase = null;
		
		restaClase = Complejo.restar(complejo1, valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(restaClase.getParteReal() != complejo1.getParteReal() - valor1 || // si no se realizó la resta
					restaClase.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la resta estática con operandos complejo y real (derecha)");
		}
		restaClase = null;
		
		restaClase = Complejo.restar(valor1, complejo1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(restaClase.getParteReal() != complejo1.getParteReal() - valor1 || // si no se realizó la resta
					restaClase.getParteImaginaria() != complejo1.getParteImaginaria())) {
			throw new AssertionError("Falla en la resta estática con operandos complejo y real (izquierda)");
		}
		restaClase = null;
		
		restaClase = Complejo.restar(valor1, valor2);
		if((restaClase.getParteReal() != valor1 - valor2 || restaClase.getParteImaginaria() != 0)) { // si no se realizó la resta
			throw new AssertionError("Falla en la resta estática con operandos reales");
		}
		restaClase = null;
		
		tearDown();
	}
	
	private static void testMultiplicaciones() {
		startUp();
		
		Complejo productoInstancia, productoClase;
		Complejo resultado = new Complejo(0, 4);
		double valor1 = 1, valor2 = -1;
		
		productoInstancia = complejo1.multiplicar(complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(productoInstancia.getParteReal() != resultado.getParteReal() || // si no se realizó la multiplicación
				productoInstancia.getParteImaginaria() != resultado.getParteImaginaria())) {
			throw new AssertionError("Falla en la multiplicación con operando complejo");
		}
		productoInstancia = null;
		
		productoInstancia = complejo1.multiplicar(valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(productoInstancia.getParteReal() != complejo1.getParteReal() * valor1 || // si no se realizó la multiplicación
				productoInstancia.getParteImaginaria() != complejo1.getParteImaginaria() * valor1)) {
			throw new AssertionError("Falla en la multiplicación con operando real");
		}
		productoInstancia = null;
		
		productoClase = Complejo.multiplicar(complejo1, complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(productoClase.getParteReal() != resultado.getParteReal() || // si no se realizó la multiplicación
					productoClase.getParteImaginaria() != resultado.getParteImaginaria())) {
			throw new AssertionError("Falla en la multiplicación estática con operandos complejos");
		}
		productoClase = null;
		
		productoClase = Complejo.multiplicar(complejo1, valor1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(productoClase.getParteReal() != complejo1.getParteReal() * valor1 || // si no se realizó la multiplicación
					productoClase.getParteImaginaria() != complejo1.getParteImaginaria() * valor1)) {
			throw new AssertionError("Falla en la multiplicación estática con operandos complejo y real (derecha)");
		}
		productoClase = null;
		
		productoClase = Complejo.multiplicar(valor1, complejo1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(productoClase.getParteReal() != complejo1.getParteReal() + valor1 || // si no se realizó la multiplicación
					productoClase.getParteImaginaria() != complejo1.getParteImaginaria() * valor1)) {
			throw new AssertionError("Falla en la multiplicación estática con operandos complejo y real (izquierda)");
		}
		productoClase = null;
		
		productoClase = Complejo.multiplicar(valor1, valor2);
		if((productoClase.getParteReal() != valor1 * valor2 || productoClase.getParteImaginaria() != 0)) { // si no se realizó la multiplicación
			throw new AssertionError("Falla en la multiplicación estática con operandos reales");
		}
		productoClase = null;
		
		tearDown();
	}
	
	private static void testExcepcionDivisionPorCero() {
		startUp();
		
		try {
			complejo1.dividir(complejoNulo);
			throw new AssertionError("Falla en la excepción por división por cero (instancia)");
		} catch(IllegalArgumentException ex) {
			// todo OK
		}
		
		try {
			Complejo.dividir(complejo1, complejoNulo);
			throw new AssertionError("Falla en la excepción por división por cero (clase)");
		} catch(IllegalArgumentException ex) {
			// todo OK
		}
		
		tearDown();
	}
	
	private static void testDivisiones() {
		startUp();
		
		Complejo divisionInstancia, divisionClase;
		Complejo resultado = new Complejo(0.5, 0);
		
		divisionInstancia = complejo1.dividir(complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(divisionInstancia.getParteReal() != resultado.getParteReal() || // si no se realizó la división
					divisionInstancia.getParteImaginaria() != resultado.getParteImaginaria())) {
			throw new AssertionError("Falla en la división con operando complejo");
		}
		divisionInstancia = null;
		
		divisionClase = Complejo.multiplicar(complejo1, complejo2);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			(complejo2.getParteReal() != 2 || complejo2.getParteImaginaria() != 2) && // si cambió complejo2
			(divisionClase.getParteReal() != resultado.getParteReal() || // si no se realizó la división
					divisionClase.getParteImaginaria() != resultado.getParteImaginaria())) {
			throw new AssertionError("Falla en la división estática con operandos complejos");
		}
		divisionClase = null;
		
		tearDown();
	}

	private static void testModulo() {
		startUp();
		
		double moduloInstancia, moduloClase;
		double resultado = Math.sqrt(2);
		
		moduloInstancia = complejo1.modulo();
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
			moduloInstancia != resultado) { // si la operación fue incorrecta
			throw new AssertionError("Falla en el módulo (instancia)");
		}
		
		moduloClase = Complejo.modulo(complejo1);
		if((complejo1.getParteReal() != 1 || complejo1.getParteImaginaria() != 1) && // si cambió complejo1
				moduloClase != resultado) { // si la operación fue incorrecta
			throw new AssertionError("Falla en el módulo (clase)");
		}
		
		tearDown();
	}
	
	private static void tearDown() {
		complejo1 = complejo2 = null;
		complejoNulo = null;
	}
}
