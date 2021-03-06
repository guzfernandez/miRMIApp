package org.common;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Observer extends Remote, Serializable{
	public void mostrarJugadores() throws RemoteException;
	public void actualizarTimer(int segundo) throws RemoteException;
	public void empezarPartida(Jugador jugador) throws RemoteException;
	public void setJugador(Jugador jugador) throws RemoteException;
	public Jugador getJugador() throws RemoteException;
	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) throws RemoteException;
	public void cambiarTurno(int posJugador, Jugador jugador) throws RemoteException;
	public void comprarPropiedad(Jugador jugador, int posicion) throws RemoteException;
	public void acciones(Jugador jugador, List<String> acciones) throws RemoteException;
	public void pagarMulta(Jugador dueño, int cantidad) throws RemoteException;
	public void venderPropiedad(Jugador jugador, int posicion) throws RemoteException;
	public void ganador(Jugador jugador) throws RemoteException;
	public void perdedor(Jugador jugador) throws RemoteException;
	public void actualizarListaJugadores() throws RemoteException;
}
