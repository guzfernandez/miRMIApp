package obligatorio;

import java.io.PushbackReader;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import org.common.Jugada;
import org.common.Jugador;
import org.common.Observer;
import org.common.PartidaController;

public class PartidaControllerImpl extends UnicastRemoteObject implements PartidaController, Serializable{
	
	private List<Observer> observers; 
	//private Partida partida = new Partida();
	//private Jugador jugador;
	private int[][] posicionesJugadores;
	private Partida partida;
	private static PartidaControllerImpl partidaInstance;
	private boolean timerCorriendo = false;
	private Timer timer = null;
	
	public PartidaControllerImpl darPartidaInstance(){
		if(partidaInstance == null){
			try {
				partidaInstance = new PartidaControllerImpl();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return partidaInstance;
	}
	
	public PartidaControllerImpl() throws RemoteException {
		super();
		System.out.println("Partida impl creada");
		//partidas = new ArrayList<Partida>();
		partida = new Partida();
		observers = new ArrayList<Observer>();
	}
	
	public void agregarJugador(Jugador jugador) {
		//partida.agregarJugador(jugador);
		
		/*if(partidas.isEmpty() || isFull(partidas.get(partidas.size()-1))){
			partidas.add(new Partida());
		}
		partidas.get(partidas.size()-1).agregarJugador(jugador);*/
		
		partida.agregarJugador(jugador);
		
		/*int i = 0;
		while(i < partidas.size()){
			if(!isFull(partidas.get(i))){
				partidas.get(i).agregarJugador(jugador);
				i = partidas.size();
			}
			i++;
		}*/
		
		System.out.println("Jugadores: "+darJugadoresEnPartida().size());
		
		// Actualizar UI
		
		actulizarUI();
		partidaCountdown();
	}
	
	private void partidaCountdown(){
		if(darJugadoresEnPartida().size() >= 1){
			if(timerCorriendo && timer != null){
				timer.cancel();
			}
			
			timer = new Timer();
			
		    TimerTask task = new TimerTask(){
		        private int i = 10;
		        public void run(){
		            if (i >= 0) {
		            	actualizarTimer(i);
		            	i--;
		            }
		            else{
		            	timerCorriendo = false;
		            	timer.cancel();
		            	timer.purge();
		            	try {
							empezarPartida();
						} catch (RemoteException e) {
							e.printStackTrace();
						}
		            }
		        }
		    };
		    timer.scheduleAtFixedRate(task, 0, 1000);
		    timerCorriendo = true;
		}
	}
	
	private void actulizarUI(){
		for(Observer o : observers){
			try {
				o.notificar("Notificar");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void actualizarTimer(int segundo){
		for(Observer o : observers){
			try {
				o.actulizarTimer(segundo);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*private boolean isFull(Partida partida){
		return partida.darJugadoresEnPartida().size() == 4;
	}*/
	
	public void agregarObserver(Observer observer) {
		observers.add(observer);
		actulizarUI();
	}

	public List<Jugador> darJugadoresEnPartida() {
		return partida.darJugadoresEnPartida();	// Cambiar esto despues
	}
	
	public Jugada accion(String accion) throws RemoteException {
		return null;
	}

	/*public int[][] getPosicionesJugadores() {
		return posicionesJugadores;
	}

	public void setPosicionesJugadores(int[][] posicionesJugadores) {
		this.posicionesJugadores = posicionesJugadores;
	}*/
	
	public void empezarPartida() throws RemoteException {
		posicionesJugadores = new int[4][1];
		posicionesJugadores[0][0] = 0;
		posicionesJugadores[1][0] = 0;
		posicionesJugadores[2][0] = 0;
		posicionesJugadores[3][0] = 0;
		for(Observer o : observers){
			try {
				o.empezarPartida(partida.darJugadoresEnPartida().get(0));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) throws RemoteException {
		for(Observer o : observers){
			try {
				o.actualizarPosicionJugador(posAnterior, jugadorPos, posicion);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void cambiarTurno(int jugPos) throws RemoteException {//JugPos: 1, Size: 2, pos: 0
		List<Jugador> jugadores = partida.darJugadoresEnPartida();
		int pos = jugPos+1;
		if(jugadores.size() <= pos){
			pos = 0;
		}
		System.out.println("JugPos: "+jugPos + ", Size: "+jugadores.size() + ", pos: "+pos);
		System.out.println("Turno: "+jugadores.get(pos).getNombre());
		
		for(Observer o : observers){
			try {
				o.cambiarTurno(pos, jugadores.get(pos));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
