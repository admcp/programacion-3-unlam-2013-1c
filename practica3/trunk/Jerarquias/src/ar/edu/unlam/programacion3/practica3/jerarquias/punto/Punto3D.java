package ar.edu.unlam.programacion3.practica3.jerarquias.punto;

public class Punto3D extends Punto2D implements Cloneable{
	
	private double z;
	
	// CONSTRUCTORES
	public Punto3D() {
		this(0, 0, 0);
	}
	
	public Punto3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}

	// GETTERS/SETTERS
	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	//	COMPORTAMIENTO DESDE OBJEC
	@Override
   public String toString() {
	   return "[x=" + getX() + ",y=" + getY() + ",z=" + z + "]";
   }
	
	@Override
   public int hashCode() {
	   final int prime = 31;
	   int result = super.hashCode();
	   long temp;
	   temp = Double.doubleToLongBits(z);
	   result = prime * result + (int) (temp ^ (temp >>> 32));
	   return result;
   }

	@Override
   public boolean equals(Object obj) {
	  
		if (!super.equals(obj)) {
	   	return false;
	   }
		
		if (this == obj)
		   return true;
	   if (!super.equals(obj))
		   return false;
	   if (getClass() != obj.getClass())
		   return false;
	   Punto3D other = (Punto3D) obj;
	   if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
		   return false;
	   return true;
   }

	@Override
   public Object clone() throws CloneNotSupportedException {
	   Punto3D clon = null;
	   try {
	   	clon = (Punto3D)super.clone();
	   } catch (CloneNotSupportedException ex){
			throw new AssertionError();
		}	   
	   return clon;
   }	
}
