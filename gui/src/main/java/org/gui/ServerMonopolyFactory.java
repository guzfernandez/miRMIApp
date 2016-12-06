package org.gui;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.common.Server;

public class ServerMonopolyFactory {

	private static Server serverInstance = null;

	private ServerMonopolyFactory() {
	}

	public static Server getServerMonopolyInstance() throws RemoteException, NotBoundException {
		if (ServerMonopolyFactory.serverInstance == null) {
			System.setProperty("java.security.policy", "file:///Users/Guz/CEI/DDA/java.policy");
			Registry registry = LocateRegistry.getRegistry(1099);
			ServerMonopolyFactory.serverInstance = (Server) registry.lookup("server");
		}
		return ServerMonopolyFactory.serverInstance;
	}
}
