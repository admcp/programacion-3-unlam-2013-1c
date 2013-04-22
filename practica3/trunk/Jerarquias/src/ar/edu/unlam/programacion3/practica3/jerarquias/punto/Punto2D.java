package ar.edu.unlam.programacion3.practica3.jerarquias.punto;

import static java.lang.Math.*;

public class Punto2D implements Cloneable, IPunto {

	private double x;
	private double y;

	//	CONSTRUCTORES
	public Punto2D() {
		this(0, 0);
	}
	
	public Punto2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// GETTERS/SETTERS
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	// COMPORTAMIENTO DESDE OBJECT
	
	@Override
   public String toString() {
	   return "[x=" + x + ", y=" + y + "]";
   }

	@Override
   public int hashCode() {
	   final int prime = 31;
	   int result = 1;
	   long temp;
	   temp = Double.doubleToLongBits(x);
	   result = prime * result + (int) (temp ^ (temp >>> 32));
	   temp = Double.doubleToLongBits(y);
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
	   Punto2D other = (Punto2D) obj;
	   if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
		   return false;
	   if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
		   return false;
	   return true;
   }

	@Override
   public Object clone() throws CloneNotSupportedException {
		Punto2D clon = null;
		try {
			clon = (Punto2D) super.clone(); 
		} catch (CloneNotSupportedException ex){
			throw new AssertionError();
		}
	   return clon;
   }

	// OPERACIONES
	
	// METODOS DE INTERFAZ
	
	@Override
   public double distncia(Object obj) {
		chequearNulo(obj);
	   Punto2D refPunto2D = (Punto2D) obj;
		return sqrt(pow(refPunto2D.getX() - x, 2) + pow(refPunto2D.getY() - y, 2));
   }

	@Override
   public double modulo() {
	   return sqrt(pow(x, 2) + pow(y, 2));
   }

	@Override
   public void desplazamiento(Object obj) {
		chequearNulo(obj);
		Punto2D refPunto2D = (Punto2D) obj;
	   this.x += refPunto2D.getX();
	   this.y += refPunto2D.getY();
   }
	
	// UTILITARIOS
	
	public static void chequearNulo(Object obj) {
		if(obj == null) {
			throw new IllegalArgumentException("El parámetro no puede ser nulo");
		}
	}

}
