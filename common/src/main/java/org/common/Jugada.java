package org.common;

import java.io.Serializable;
import java.util.List;

public class Jugada implements Serializable{

	private Jugador jugador;
	private List<Accion> acciones;
	
	public Jugador getJugador() {
		return jugador;
	}
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}
	public List<Accion> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}
	
}
