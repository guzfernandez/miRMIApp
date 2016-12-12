package org.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PartidaController extends Remote{
	
	public boolean agregarJugador(Jugador jugador) throws RemoteException;
	public void removerJugador(Jugador jugador) throws RemoteException;
	public void mostrarDatosJugador() throws RemoteException;
	public void agregarObserver(Observer observer) throws RemoteException;
	public List<Jugador> darJugadoresEnPartida() throws RemoteException;
	public void accion(Jugador jugador, CasillaTipo tipo, boolean dueño) throws RemoteException;
	public void empezarPartida() throws RemoteException;
	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) throws RemoteException;
	public void cambiarTurno(int jugPos) throws RemoteException;
	public void comprarPropiedad(Jugador jugador, int posicion) throws RemoteException;
	public void venderPropiedad(Jugador jugador, int posicion) throws RemoteException;
	public void actualizarJugador(Jugador jugador) throws RemoteException;
}
