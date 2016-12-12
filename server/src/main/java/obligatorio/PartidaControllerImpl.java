package obligatorio;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.common.CasillaTipo;
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
	
	public boolean agregarJugador(Jugador jugador) {
		boolean esta = partida.agregarJugador(jugador);
		
		System.out.println("Jugadores: "+darJugadoresEnPartida().size());
		
		actulizarUI();
		partidaCountdown();
		
		return esta;
	}
	
	public void removerJugador(Jugador jugador) throws RemoteException {
		partida.removerJugador(jugador);
		
		if(partida.darJugadoresEnPartida().size() == 1){
			ganador(partida.darJugadoresEnPartida().get(0));
		}
		else{
			actulizarUI();	
		}
		
		for(Observer o : observers){
			try {
				o.perdedor(jugador);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void ganador(Jugador jugador){
		for(Observer o : observers){
			try {
				o.ganador(jugador);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void partidaCountdown(){
		if(darJugadoresEnPartida().size() >= 2){
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
				o.mostrarJugadores();
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
	
	public void agregarObserver(Observer observer) {
		if(!observers.contains(observer)){
			observers.add(observer);
			actulizarUI();
		}
	}

	public List<Jugador> darJugadoresEnPartida() {
		return partida.darJugadoresEnPartida();
	}
	
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

	public void cambiarTurno(int jugPos) throws RemoteException {
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

	public void accion(Jugador jugador, CasillaTipo accion, boolean dueño) throws RemoteException {
		List<String> acciones = new ArrayList<String>();
		
		if(accion == CasillaTipo.INICIO){
			acciones.add("PASAR");
			acciones.add("RECOMPENSA");	// +$100
		}
		else if(accion == CasillaTipo.SERVICIO || accion == CasillaTipo.PROPIEDAD){
			if(dueño){
				acciones.add("MULTA");
			}
			else{
				acciones.add("COMPRAR");
				acciones.add("PASAR");
			}
		}
		else if(accion == CasillaTipo.CARCEL || accion == CasillaTipo.LIBRE){
			acciones.add("PASAR");
		}
		else if(accion == CasillaTipo.DESTINO){
			acciones.add("DESTINO");
		}
		else if(accion == CasillaTipo.POLICIA){
			acciones.add("CARCEL");
		}
		else if(accion == CasillaTipo.SUERTE){
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

	public void mostrarDatosJugador() throws RemoteException {
		actulizarUI();
	}

	public void venderPropiedad(Jugador jugador, int posicion) throws RemoteException {
		for(Observer o : observers){
			try {
				o.venderPropiedad(jugador, posicion);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public void actualizarJugador(Jugador jugador) throws RemoteException {
		int pos = -1;
		int i = 0;
		
		List<Jugador> jugadores = partida.darJugadoresEnPartida();
		
		while(i < jugadores.size()){
			if(jugadores.get(i).getNombre().equals(jugador.getNombre())){
				pos = i;
				i = jugadores.size();
			}
			i++;
		}
		
		if(pos != -1){
			partida.actualizarJugador(jugador, pos);	
		}
		
		for(Observer o : observers){
			try {
				o.actualizarListaJugadores();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
}
