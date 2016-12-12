package obligatorio;

import java.util.ArrayList;
import java.util.List;

import org.common.Jugador;

public class Partida {
	private static List<Jugador> jugadores;

	public Partida() {
		jugadores = new ArrayList<Jugador>();
	}
	
	public boolean agregarJugador(Jugador jugador){
		boolean esta = false;
		int i = 0;
		
		while(i < jugadores.size()){
			if(jugadores.get(i).getNombre().equals(jugador.getNombre())){
				esta = true;
				i = jugadores.size();
			}
			i++;
		}
		
		if(jugadores.size() <= 4 && !esta){
			jugadores.add(jugador);
		}
		
		return esta;
	}
	
	public void removerJugador(Jugador jugador){
		int i = 0;
		while(i < jugadores.size()){
			if(jugadores.get(i).getNombre().equals(jugador.getNombre())){
				jugadores.remove(i);
				i = jugadores.size();
			}
			i++;
		}
	}
	
	public void actualizarJugador(Jugador jugador, int pos){
		jugadores.set(pos, jugador);
	}
	
	public List<Jugador> darJugadoresEnPartida(){
		return jugadores;
	}
	
}
