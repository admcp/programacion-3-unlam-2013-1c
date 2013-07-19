package ar.edu.unlam.programacion3.adt.grafo;


public class Nodo implements Cloneable, Comparable<Nodo> {

	private int nombre;
	private int color;
	private int grado;
	
	public Nodo(int nombre) {
		this.nombre = nombre;
		this.color = 0;
		this.grado = 0;
		
	}

	public int getNombre() {
		return nombre;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}
	
	public void incrementarGrado() {
		grado++;
	}
	
	public void decrementarGrado() {
		if(grado >= 0) {
			grado--;
		} else {
			throw new IllegalStateException();
		}
	}
	
	@Override
	public int compareTo(Nodo otro) {
		return this.grado - otro.grado;
	}
	
	@Override
	public String toString() {
		return nombre + " " + color + "(" + grado + ")";
	}

	@Override
	public Nodo clone() {
		Nodo clon = null;
		try {
			clon = (Nodo) super.clone();
		} catch(CloneNotSupportedException ex) {
			throw new AssertionError();
		}
		
		clon.nombre = nombre;
		clon.color = color;
		clon.grado = grado;
		
		return clon;
	}
	
}
