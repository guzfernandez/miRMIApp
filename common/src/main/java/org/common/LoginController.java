package org.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginController extends Remote {

	public Jugador login(String username, String password) throws RemoteException;
	
}
