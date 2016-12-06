package org.server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.common.Jugador;
import org.common.LoginController;
import org.common.Observer;
import org.common.PartidaController;
import org.common.Server;

import obligatorio.PartidaControllerImpl;
import obligatorio.Controllers.LoginControllerImpl;

@SuppressWarnings("deprecation")
public class ServerImpl implements Server{
	
	private List<Observer> observers;
	
	private LoginController loginController;
	private PartidaController partidaController;
	
	public ServerImpl() {
		System.setProperty("java.security.policy","file:///Users/Guz/CEI/DDA/java.policy");

		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
		
		this.observers = new ArrayList<Observer>();
		try {
			this.loginController = new LoginControllerImpl();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		try {
			this.partidaController = new PartidaControllerImpl();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void addObserver(Observer observer) throws RemoteException {
		this.observers.add(observer);
	}

	public void sendMessage(String message) throws RemoteException {
		for(Observer o : this.observers) {
			o.notificar(message);
		}
	}

	public LoginController getLoginController() throws RemoteException {
		return loginController;
	}

	public PartidaController getPartidaController() throws RemoteException {
		return partidaController;
	}

	public List<Observer> getObservers() throws RemoteException {
		return this.observers;
	}

	public int tirarDado() throws RemoteException {
		return ThreadLocalRandom.current().nextInt(1, 7);
	}

	public void setJugador(Jugador jugador) throws RemoteException {
		for(Observer o : this.observers) {
			o.setJugador(jugador);
		}
	}


}
