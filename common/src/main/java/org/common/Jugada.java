package org.common;

import java.io.Serializable;
import java.util.List;

public class Jugada implements Serializable{

	private List<String> acciones;

	public List<String> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<String> acciones) {
		this.acciones = acciones;
	}

	public Jugada(List<String> acciones) {
		super();
		this.acciones = acciones;
	}
	
}
