package obligatorio;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.common.Jugada;
import org.common.Jugador;
import org.common.Observer;
import org.common.PartidaController;

public class PartidaControllerImpl extends UnicastRemoteObject implements PartidaController, Serializable{
	
	private List<Observer> observers;
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
		partida = new Partida();
		observers = new ArrayList<Observer>();
	}
	
	public void agregarJugador(Jugador jugador) {
		partida.agregarJugador(jugador);
		
		System.out.println("Jugadores: "+darJugadoresEnPartida().size());
		
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
	
	private int i = 0;
	
	public void agregarObserver(Observer observer) {
		if(!observers.contains(observer)){
			observers.add(observer);
			
			actulizarUI();
			i++;
			System.out.println("Observer: #"+i+" - " +observer.toString());
		}
	}

	public List<Jugador> darJugadoresEnPartida() {
		return partida.darJugadoresEnPartida();	// Cambiar esto despues
	}
	
	public void empezarPartida() throws RemoteException {
		posicionesJugadores = new int[4][1];
		posicionesJugadores[0][0] = 0;
		posicionesJugadores[1][0] = 0;
		posicionesJugadores[2][0] = 0;
		posicionesJugadores[3][0] = 0;
		
		System.out.println("Observers: "+observers.size());
		
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
		
		for(Observer o : observers){
			try {
				o.cambiarTurno(pos, jugadores.get(pos));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void comprarPropiedad(Jugador jugador, int posicion) throws RemoteException {
		for(Observer o : observers){
			try {
				o.comprarPropiedad(jugador, posicion);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void accion(Jugador jugador, String accion, boolean dueño) throws RemoteException {
		List<String> acciones = new ArrayList<String>();
		
		if(accion.equals("INICIO")){
			acciones.add("PASAR");
			acciones.add("RECOMPENSA");	// +$100
		}
		else if(accion.equals("SERVICIO") || accion.equals("PROPIEDAD")){
			if(dueño){
				acciones.add("MULTA");
			}
			else{
				acciones.add("COMPRAR");
				acciones.add("PASAR");
			}
		}
		else if(accion.equals("CARCEL") || accion.equals("LIBRE")){
			acciones.add("PASAR");
		}
		else if(accion.equals("DESTINO")){
			acciones.add("DESTINO");
		}
		else if(accion.equals("POLICIA")){
			acciones.add("CARCEL");
		}
		else if(accion.equals("SUERTE")){
			acciones.add("SUERTE");
		}
		
		for(Observer o : observers){
			try {
				o.acciones(jugador, acciones);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
