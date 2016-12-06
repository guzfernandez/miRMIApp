package org.gui;

import java.awt.EventQueue;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.common.Jugador;
import org.common.Observer;
import org.common.Server;

public class App extends UnicastRemoteObject implements Observer{

	private Server server;
	private TableroWindow window;
	private Jugador jugador;
	
	public static void main(String[] args) throws RemoteException, NotBoundException {
		new App();
	}
	
	public App() throws RemoteException, NotBoundException {
		this.server = ServerMonopolyFactory.getServerMonopolyInstance();
		server.addObserver(this);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//window = TableroWindow.getInstance();
					window = new TableroWindow();
					window.setServer(server);
					window.setVisible(true);
					
					LoginWindow loginWindow = new LoginWindow(window.getFrame());
					loginWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Siempre devolver cosas que la UI conozca (inferfaces)
		
	}
	
    public void sendMessage(String message) throws RemoteException {
		this.server.sendMessage(message);
		window.updateUI();
	}
	
	public void notificar(String mensaje) throws RemoteException {
		window.updateUI();
	}

	public void actulizarTimer(int segundo) throws RemoteException {
		window.actualizarTimer(segundo);
	}

	/*public void cambiarTurno(Jugador jugadorRecibeTurno) throws RemoteException {
		window.cambiarTurno(this.jugador, jugadorRecibeTurno);
	}*/
	
	

	public void empezarPartida(Jugador jugadorRecibeTurno) throws RemoteException {
		window.empezarPartida(this.jugador, jugadorRecibeTurno);
		window.setJugador(this.jugador);
	}
	
	public void setJugador(Jugador jugador) throws RemoteException {
		if(this.jugador == null){
			this.jugador = jugador;
		}
	}
	
	public Jugador getJugador() throws RemoteException {
		return this.jugador;
	}

	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) throws RemoteException {
		window.actualizarPosicionJugador(posAnterior, jugadorPos, posicion);
	}

	public void cambiarTurno(int posJugador, Jugador jugador) throws RemoteException {
		window.cambiarTurno(posJugador, this.jugador, jugador);
	}
	
}
