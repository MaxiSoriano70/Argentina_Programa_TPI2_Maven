package Modelos;

import lombok.Data;

@Data
public class Partido {
	private Equipo local;
	private Equipo visitante;
	private EGanador ganador;
	
	public Partido(Equipo local, Equipo visitante) {
		this.local = local;
		this.visitante = visitante;
	}
	
	public EGanador getResultado() {
		return ganador;
	}

	public void setResultado(EGanador resultado) {
		this.ganador = resultado;
	}
}
