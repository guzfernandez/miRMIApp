package org.gui;

import java.awt.EventQueue;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
	}
	
    public void mostrarJugadores() throws RemoteException {
		window.updateUI();
	}
	
	public void notificar(String mensaje) throws RemoteException {
		window.updateUI();
	}

	public void actulizarTimer(int segundo) throws RemoteException {
		window.actualizarTimer(segundo);
	}

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

	public void comprarPropiedad(Jugador jugador, int posicion) throws RemoteException {
		window.comprarPropiedad(jugador, posicion);
	}

	public void acciones(Jugador jugador, List<String> acciones) throws RemoteException {
		window.mostrarOpciones(jugador, acciones);
	}

	public void pagarMulta(Jugador dueño, int cantidad) throws RemoteException {
		window.pagarMulta(dueño, cantidad);
	}

	public void venderPropiedad(Jugador jugador, int posicion) throws RemoteException {
		window.venderPropiedad(jugador, posicion);
	}

	public void ganador(Jugador jugador) throws RemoteException {
		window.ganador(jugador);
	}

	public void actualizarListaJugadores() throws RemoteException {
		window.actualizarListaJugadores();
	}

	public void perdedor(Jugador jugador) throws RemoteException {
		window.perdedor(jugador);
	}
	
}
