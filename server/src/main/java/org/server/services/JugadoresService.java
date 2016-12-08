package org.server.services;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.common.Jugador;

public class JugadoresService {

	private static final JugadoresService instance = new JugadoresService();

	private Map<String, Jugador> jugadores = new HashMap<String, Jugador>();

	public static JugadoresService getInstance() {
		return instance;
	}

	private JugadoresService() {
		try {
			jugadores.put("test", new Jugador("test", "test", 1000, 3, 9));
			jugadores.put("test2", new Jugador("test2", "test2", 1000, 2, 8));
			jugadores.put("test3", new Jugador("test3", "test3", 1000, 3, 3));
			jugadores.put("test4", new Jugador("test3", "test3", 1000, 0, 5));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public Jugador getJugadorByUsername(String username) {
		return jugadores.get(username);
		
	}
}
