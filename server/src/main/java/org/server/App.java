package org.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.common.Server;

public class App 
{
    public static void main( String[] args )
    {
    	try {
			System.setProperty("java.security.policy", "file:///Users/Guz/CEI/DDA/java.policy");
			LocateRegistry.createRegistry(1099);
			ServerImpl obj = new ServerImpl();
			Server stub = (Server) UnicastRemoteObject.exportObject(obj, 0);
			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(1099);
			registry.bind("server", stub);

			System.out.println("Server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
    }
}
