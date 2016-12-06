package org.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Observer extends Remote, Serializable{
	public void notificar(String mensaje) throws RemoteException;
	public void actulizarTimer(int segundo) throws RemoteException;
	//public void cambiarTurno(Jugador jugador) throws RemoteException;
	public void empezarPartida(Jugador jugador) throws RemoteException;
	public void setJugador(Jugador jugador) throws RemoteException;
	public Jugador getJugador() throws RemoteException;
	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) throws RemoteException;
	public void cambiarTurno(int posJugador, Jugador jugador) throws RemoteException;
}
