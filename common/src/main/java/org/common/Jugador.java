package org.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "jugadores")
public class Jugador implements Remote, Serializable {
	
	@Id
	private String nombre;
	private String password;
	
	@Transient
	private int dinero;
	private int partGanadas;
	private int partPerdidas;
	
	public Jugador(){
		super();
	}
	
	public Jugador(String nombre, String password, int partGanadas, int partPerdidas) throws RemoteException {
		super();
		this.nombre = nombre;
		this.password = password;
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
