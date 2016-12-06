package org.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class Jugador implements Remote, Serializable {
	private String nombre;
	private String password;
	private int dinero;
	private int partGanadas;
	private int partPerdidas;
	
	public Jugador(String nombre, String password, int dinero, int partGanadas, int partPerdidas) throws RemoteException {
		super();
		this.nombre = nombre;
		this.password = password;
		this.dinero = dinero;
		this.partGanadas = partGanadas;
		this.partPerdidas = partPerdidas;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDinero() {
		return dinero;
	}
	public void setDinero(int dinero) {
		this.dinero = dinero;
	}
	public int getPartGanadas() {
		return partGanadas;
	}
	public void setPartGanadas(int partGanadas) {
		this.partGanadas = partGanadas;
	}
	public int getPartPerdidas() {
		return partPerdidas;
	}
	public void setPartPerdidas(int partPerdidas) {
		this.partPerdidas = partPerdidas;
	}
}
