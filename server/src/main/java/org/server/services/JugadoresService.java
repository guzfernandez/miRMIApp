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
			jugadores.put("test", new Jugador("test", "test", 2348, 3, 9));
			jugadores.put("test2", new Jugador("test2", "test2", 4536, 2, 8));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public Jugador getJugadorByUsername(String username) {
		return jugadores.get(username);
		
	}
}
