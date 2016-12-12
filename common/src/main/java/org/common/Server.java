package org.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Server extends Remote {
	public void addObserver(Observer observer) throws RemoteException;
	public List<Observer> getObservers() throws RemoteException;
	public void mostrarJugadores() throws RemoteException;
	public LoginController getLoginController() throws RemoteException;
	public PartidaController getPartidaController() throws RemoteException;
	public int tirarDado() throws RemoteException;
	public void setJugador(Jugador jugador) throws RemoteException;
	public void pagarMulta(Jugador dueño, int cantidad) throws RemoteException;
}
