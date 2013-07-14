package ar.edu.unlam.programacion3.adt.grafo;

public class Arco {

	private int nodoOrigen;
	private int nodoDestino;
	
	public Arco(int nodoOrigen, int nodoDestino) {
		this.nodoOrigen = nodoOrigen;
		this.nodoDestino = nodoDestino;
	}

	public int getNodoOrigen() {
		return nodoOrigen;
	}

	public void setNodoOrigen(int nodoOrigen) {
		this.nodoOrigen = nodoOrigen;
	}

	public int getNodoDestino() {
		return nodoDestino;
	}

	public void setNodoDestino(int nodoDestino) {
		this.nodoDestino = nodoDestino;
	}

	@Override
	public String toString() {
		return "(" + nodoOrigen + ", " + nodoDestino + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nodoDestino;
		result = prime * result + nodoOrigen;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Arco))
			return false;
		Arco other = (Arco) obj;
		if (nodoOrigen != other.nodoOrigen) {
			if(nodoOrigen != other.nodoDestino) {
				return false;
			} else {
				if(nodoDestino != other.nodoOrigen) {
					return false;
				}
			}
		}
		if (nodoDestino != other.nodoDestino) {
			if(nodoDestino != other.nodoOrigen) {
				return false;
			} else {
				if(nodoOrigen != other.nodoDestino) {
					return false;
				}
			}
		}
	
		return true;
	}
	
}
