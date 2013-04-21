package ar.edu.unlam.programacion3.practica2.tda;

// ATENCIÓN: ESTA CLASE, POR COMO ESTÁ IMPLEMENTADA, ES INMUTABLE

public class Complejo implements Cloneable {
	
	private double parteReal;
	private double parteImaginaria;
	
	// CONSTRUCTORES
	
	public Complejo() {
		this(0,0);
	}
	
	public Complejo(double real, double img) {
		this.parteReal = real;
		this.parteImaginaria = img;
	}
	
	// GETTERS/SETTERS
	
	public double getParteReal() {
		return parteReal;
	}

	public void setParteReal(double parteReal) {
		this.parteReal = parteReal;
	}

	public double getParteImaginaria() {
		return parteImaginaria;
	}

	public void setParteImaginaria(double parteImaginaria) {
		this.parteImaginaria = parteImaginaria;
	}
	
	// COMPORTAMIENTO DESDE OBJECT
	
	@Override
	public String toString() {
		return "(" + parteReal + ", " + parteImaginaria + ")";  
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(parteImaginaria);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(parteReal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complejo other = (Complejo) obj;
		if (Double.doubleToLongBits(parteImaginaria) != Double.doubleToLongBits(other.parteImaginaria))
			return false;
		if (Double.doubleToLongBits(parteReal) != Double.doubleToLongBits(other.parteReal))
			return false;
		return true;
	}
	
	@Override
	public Complejo clone() {
		Complejo clon = null;
		try {
			clon = (Complejo) super.clone() ;	
		} catch(CloneNotSupportedException ex) {
			throw new AssertionError();
		}
		return clon;
	}

	// MÉTODOS UTILITARIOS
	
	public static void chequearNulo(Object obj) {
		if(obj == null) {
			throw new IllegalArgumentException("El parámetro no puede ser nulo");
		}
	}

	// OPERACIONES SOBRE COMPLEJOS
	
	// MÉTODOS DE INSTANCIA
	
	// SUMAS:
	
	public Complejo sumar(Complejo operando) {
		chequearNulo(operando);
		return new Complejo(parteReal + operando.getParteReal(), parteImaginaria + operando.getParteImaginaria());
	}
	
	public Complejo sumar(double operando) {
		return new Complejo(parteReal + operando, parteImaginaria);
	}
	
	// RESTAS:
	
	public Complejo restar(Complejo operando) {
		chequearNulo(operando);
		return new Complejo(parteReal - operando.getParteReal(), parteImaginaria - operando.getParteImaginaria());
	}
	
	public Complejo restar(double operando) {
		return new Complejo(parteReal - operando, parteImaginaria);
	}
	
	// PRODUCTOS
	
	public Complejo multiplicar(Complejo operando) {
		chequearNulo(operando);
		return new Complejo((parteReal * operando.getParteReal()) - (parteImaginaria * operando.getParteImaginaria()),
				            (parteReal * operando.getParteImaginaria()) + (parteImaginaria * operando.getParteReal()));	
	}
	
	public Complejo multiplicar(double operando) {
		return new Complejo(parteReal * operando, parteImaginaria * operando);	
	}
	
	// DIVISIONES
	
	public Complejo dividir(Complejo operando){
		chequearNulo(operando);
		
		if(operando.getParteReal() == 0 && operando.getParteImaginaria() == 0) {
			throw new IllegalArgumentException("División por cero");
		}
		
		double auxReal, auxImg;
		
		auxReal = parteReal * operando.getParteReal() + parteImaginaria * operando.getParteImaginaria(); 
		auxReal /= Math.pow(operando.getParteReal(), 2) + Math.pow(operando.getParteImaginaria(), 2);
			
		auxImg = parteImaginaria * operando.getParteReal() - parteReal * operando.getParteImaginaria();
		auxImg /= Math.pow(operando.getParteReal(), 2) + Math.pow(operando.getParteImaginaria(), 2);			
		
		return new Complejo(auxReal, auxImg);
	}
	
	// MÉTODOS DE CLASE
	
	// SUMAS:
	
	public static Complejo sumar(Complejo operando1, Complejo operando2) {
		chequearNulo(operando1);
		chequearNulo(operando2);
		return new Complejo(operando1.getParteReal() + operando2.getParteReal(),
							operando1.getParteImaginaria() + operando2.getParteImaginaria());
	}
	
	public static Complejo sumar(Complejo operando1, double operando2) {
		chequearNulo(operando1);
		return new Complejo(operando1.getParteReal() + operando2, operando1.getParteImaginaria());
	}

	public static Complejo sumar(double operando1, Complejo operando2) {
		chequearNulo(operando2);
		return new Complejo(operando1 + operando2.getParteReal(), operando2.getParteImaginaria());
	}

	public static Complejo sumar(double operando1, double operando2) {
		return new Complejo(operando1 + operando2, 0);
	}
	
	// RESTAS:
	
	public static Complejo restar(Complejo operando1, Complejo operando2) {
		chequearNulo(operando1);
		chequearNulo(operando2);
		return new Complejo(operando1.getParteReal() - operando2.getParteReal(), 
							operando1.getParteImaginaria() - operando2.getParteImaginaria());
	}
	
	public static Complejo restar(Complejo operando1, double operando2) {
		chequearNulo(operando1);
		return new Complejo(operando1.getParteReal() - operando2, operando1.getParteImaginaria());
	}

	public static Complejo restar(double operando1, Complejo operando2) {
		chequearNulo(operando2);
		return new Complejo(operando1 - operando2.getParteReal(), operando2.getParteImaginaria());
	}

	public static Complejo restar(double operando1, double operando2) {
		return new Complejo(operando1 - operando2, 0);
	}
	
	// PRODUCTOS
	
	public static Complejo multiplicar(Complejo operando1, Complejo operando2) {
		chequearNulo(operando1);
		chequearNulo(operando2);
		return new Complejo((operando1.getParteReal() * operando2.getParteReal()) - (operando1.getParteImaginaria() * operando2.getParteImaginaria()),
				            (operando1.getParteReal() * operando2.getParteImaginaria()) + (operando1.getParteImaginaria() * operando2.getParteReal()));
		
	}

	public static Complejo multiplicar(Complejo operando1, double operando2) {
		chequearNulo(operando1);
		return new Complejo(operando1.getParteReal() * operando2, operando1.getParteImaginaria() * operando2);
	}


	public static Complejo multiplicar(double operando1, Complejo operando2) {
		chequearNulo(operando2);
		return new Complejo(operando1 * operando2.getParteReal(), operando1 * operando2.getParteImaginaria());
	}

	public static Complejo multiplicar(double a, double b) {
		return new Complejo(a * b, 0);
	}
	
	// DIVISIONES
	
	public static Complejo dividir(Complejo operando1, Complejo operando2){
		chequearNulo(operando1);
		chequearNulo(operando2);
		
		if(operando2.getParteReal() == 0 && operando2.getParteImaginaria() == 0) {
			throw new IllegalArgumentException("División por cero");
		}
		
		double auxReal, auxImg;

		auxReal = operando1.getParteReal() * operando2.getParteReal() + operando1.getParteImaginaria() * operando2.getParteImaginaria(); 
		auxReal /= Math.pow(operando2.getParteReal(), 2) + Math.pow(operando2.getParteImaginaria(), 2);
		
		auxImg = operando1.getParteImaginaria() * operando2.getParteReal() - operando1.getParteReal() * operando2.getParteImaginaria();
		auxImg /= Math.pow(operando2.getParteReal(), 2) + Math.pow(operando2.getParteImaginaria(), 2);			
	
		return new Complejo(auxReal, auxImg);

	}
	
	// OTRAS OPERACIONES
	
	public double modulo(){		
		return Math.sqrt(Math.pow(parteReal, 2) + Math.pow(parteImaginaria, 2));		
	}
	
	public static double modulo(Complejo operando){		
		return Math.sqrt(Math.pow(operando.getParteReal(), 2) + Math.pow(operando.getParteImaginaria(), 2));		
	}
	
}
