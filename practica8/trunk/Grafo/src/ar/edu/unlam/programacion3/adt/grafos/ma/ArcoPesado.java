package ar.edu.unlam.programacion3.adt.grafos.ma;

public class ArcoPesado extends Arco implements Comparable<ArcoPesado> {

	private double valor;
	
	public ArcoPesado(int nodoOrigen, int nodoDestino, double valor) {
		super(nodoOrigen, nodoDestino);
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(valor);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ArcoPesado))
			return false;
		ArcoPesado other = (ArcoPesado) obj;
		if (Double.doubleToLongBits(valor) != Double.doubleToLongBits(other.valor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.getNodoOrigen() + " -> " + super.getNodoDestino() + "[" + valor + "]";
	}

	@Override
	public int compareTo(ArcoPesado otro) {
		double diferencia = this.valor - otro.valor; 
		if(diferencia == 0) {
			return 0;
		} else if(diferencia > 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
}
