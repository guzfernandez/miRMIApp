package obligatorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.common.Jugador;

public class Partida {
	private static List<Jugador> jugadores;

	public Partida() {
		jugadores = new ArrayList<Jugador>();
	}
	
	public void agregarJugador(Jugador jugador){
		if(jugadores.size() <= 4){
			jugadores.add(jugador);
		}
	}
	
	public List<Jugador> darJugadoresEnPartida(){
		return jugadores;
	}
	
}
