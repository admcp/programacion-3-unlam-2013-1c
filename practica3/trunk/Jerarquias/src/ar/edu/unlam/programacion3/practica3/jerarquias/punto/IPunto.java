package ar.edu.unlam.programacion3.practica3.jerarquias.punto;

public interface IPunto {
	// No se especifican en la interfaz los metodos:
	// clone(), toString(), equals()
	// Dado que la clase que implemente esta
	// interfaz no esta obligada a sobrecargarlos
	// por estar definidos aca. Directamente
	// puede sobrecargar los de Object.
	public double distncia(Object obj);
	public double modulo();
	public void desplazamiento(Object obj);
}
