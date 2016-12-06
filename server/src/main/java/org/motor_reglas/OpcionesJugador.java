package org.motor_reglas;

import java.util.ArrayList;
import java.util.List;

public class OpcionesJugador {
	private List<String> opciones;
	
	public OpcionesJugador() {
		this.opciones = new ArrayList<String>();
	}

	public List<String> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<String> opciones) {
		this.opciones = opciones;
	}
}
