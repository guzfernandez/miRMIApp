package org.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Accion implements Serializable{
	private List<String> acciones;
	
	public Accion() {
		super();
	}
	
	public List<String> getAcciones() {
		return acciones;
	}

	public void setAcciones(List<String> acciones) {
		this.acciones = acciones;
	}

}
