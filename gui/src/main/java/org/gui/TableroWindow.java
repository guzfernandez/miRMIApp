package org.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import org.common.Jugador;
import org.common.Server;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TableroWindow {

	private Jugador jugador;
	private JFrame frame;
	private Server server;
	private List<Jugador> jugadores;
	private int posicion;
	private boolean carcel = false;
	private int intento = 0;
	private List<String> suertes = new ArrayList<String>();
	private List<String> destinos = new ArrayList<String>();
	private int posAnterior = -1;
	private CasillaTipo[][] posiciones;
	private JLabel lblJp_1, lblJp_2, lblJp_3, lblJp_4, lblEsperando, lblTurno, lblDinero, lblDescripcion;
	private JButton btnTirarDado, btnComprar, btnPasar;
	private CasillaPanel panel, panel_1, panel_2, panel_3, panel_4, panel_5, panel_6, panel_7, panel_8, panel_9, panel_10, panel_11, panel_12, panel_13, panel_14, panel_15;
	private JButton btnSalir;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableroWindow window = new TableroWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TableroWindow() {
		jugadores = new ArrayList<Jugador>();
		posicion = 0;
		posiciones = new CasillaTipo[16][1];
		initialize();
		llenarPosiciones();
		llenarListas();
	}

	public void setServer(Server server) {
		this.server = server;
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}
	
	public JFrame getFrame() {
		return this.frame;
	}
	
	public void updatePositions(int dado){
		if(!carcel){
			int aux = posicion+dado;
			if(aux > 15){
				posicion = aux-16;
				this.jugador.setDinero(this.jugador.getDinero()+200);
				mostrarDatos();
			}
			else{
				posicion += dado;
			}
		}
		
		try {
			server.getPartidaController().actualizarPosicionJugador(posAnterior, getJugadorPos(jugador)+1, posicion);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		getOpciones(posAnterior, getJugadorPos(jugador), posicion);
	}
	
	private void getOpciones(int posAnterior, int jugPos, int posicion){
		getPanel(posAnterior).getLabel(jugPos+1).setVisible(false);
		getPanel(posicion).getLabel(jugPos+1).setVisible(true);
		this.posAnterior = posicion;
		
		CasillaTipo tipo = null;
		
		if(posiciones[posicion][0] == CasillaTipo.INICIO){
			tipo = CasillaTipo.INICIO;
		}
		else if(posiciones[posicion][0] == CasillaTipo.PROPIEDAD){
			tipo = CasillaTipo.PROPIEDAD;
		}
		else if(posiciones[posicion][0] == CasillaTipo.SUERTE){
			tipo = CasillaTipo.SUERTE;
		}
		else if(posiciones[posicion][0] == CasillaTipo.DESTINO){
			tipo = CasillaTipo.DESTINO;
		}
		else if(posiciones[posicion][0] == CasillaTipo.CARCEL){
			tipo = CasillaTipo.CARCEL;
		}
		else if(posiciones[posicion][0] == CasillaTipo.POLICIA){
			tipo = CasillaTipo.POLICIA;
		}
		else if(posiciones[posicion][0] == CasillaTipo.LIBRE){
			tipo = CasillaTipo.LIBRE;
		}
		else if(posiciones[posicion][0] == CasillaTipo.SERVICIO){
			tipo = CasillaTipo.SERVICIO;
		}
		
		System.out.println(tipo.toString());
		boolean dueño = false;
		
		if(getPanel(posicion).getDueño() != null){
			// Tiene dueño
			dueño = true;
		}
		
		try {
			server.getPartidaController().accion(this.jugador, tipo.toString(), dueño);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarOpciones(Jugador jugador, List<String> acciones){
		/*System.out.println("Para "+jugador.getNombre());
		
		for(String s : acciones){
			System.out.println(s);
		}*/
		
		if(jugador.getNombre().equals(this.jugador.getNombre())){
			// Simplificar esto
			if(acciones.contains("PASAR")){
				btnPasar.setEnabled(true);
			}
			if(acciones.contains("COMPRAR")){
				btnComprar.setEnabled(true);
			}
			if(acciones.contains("MULTA")){
				this.jugador.setDinero(this.jugador.getDinero()-50);
				
				try {
					server.pagarMulta(darDueño(posicion), 50);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				
				mostrarDatos();
				
				btnPasar.setEnabled(true);
			}
			if(acciones.contains("CARCEL")){
				carcel = true;
				posicion = 4;
				updatePositions(0);
			}
			if(acciones.contains("SUERTE")){
				btnPasar.setEnabled(true);
				Random r = new Random();
				int i = r.nextInt(4);
				String suerte = suertes.get(i);
				lblDescripcion.setText("<html>"+suerte+"</html>");
			}
			if(acciones.contains("DESTINO")){
				btnPasar.setEnabled(true);
				Random r = new Random();
				int i = r.nextInt(4);
				String destino = destinos.get(i);
				lblDescripcion.setText("<html>"+destino+"</html>");
			}
		}
		
		//System.out.println("----------");
	}
	
	public void comprarPropiedad(Jugador jugador, int posicion){
		getPanel(posicion).setDueño(jugador);
		getPanel(posicion).lblDueño.setText(jugador.getNombre());
		getPanel(posicion).lblDueño.setVisible(true);
		
		if(jugador.getNombre().equals(this.jugador.getNombre())){
			this.jugador.setDinero(this.jugador.getDinero()-100);
		}
		
		mostrarDatos();
		pasarTurno(jugador);
	}
	
	private void pasarTurno(Jugador jugador){
		intento = 0;
		try {
			server.getPartidaController().cambiarTurno(getJugadorPos(jugador));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		btnComprar.setEnabled(false);
		btnPasar.setEnabled(false);
	}
	
	private void salirCarcel() {
		if(carcel){
			Random r = new Random();
			int i = r.nextInt(6)+1;
			int i2 = r.nextInt(6)+1;
			
			System.out.println("R1: "+i+", R2: "+i2);
			
			intento++;
			
			if(i == i2){
				carcel = false;
				lblDescripcion.setText("Has salido de la cárcel!");
				intento = 3;
			}
			else{
				lblDescripcion.setText("Has fallado! "+intento+"/3");
			}
			
			if(intento >= 3){
				btnSalir.setVisible(false);
				btnPasar.setEnabled(true);
			}
		}
	}
	
	public void actualizarTimer(int segundo) {
		lblEsperando.setText("La partida empieza en "+segundo+" segundos.");
	}
	
	public void cambiarTurno(int pos, Jugador jugador, Jugador recibeTurno) {
		lblTurno.setText("Es el turno de "+recibeTurno.getNombre());
		if(jugador.getNombre().equals(recibeTurno.getNombre())){
			if(carcel){
				btnSalir.setVisible(true);
			}
			else{
				btnSalir.setVisible(false);
				btnTirarDado.setEnabled(true);
			}
		}
		else{
			btnTirarDado.setEnabled(false);
		}
	}
	
	public void updateUI(){
		try {
			jugadores = server.getPartidaController().darJugadoresEnPartida();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		for(int i=0; i<jugadores.size(); i++){
			switch(i){
			case 0:
				lblJp_1.setText("JP1: "+jugadores.get(i).getNombre());	
				break;
			case 1:
				lblJp_2.setText("JP2: "+jugadores.get(i).getNombre());
				break;
			case 2:
				lblJp_3.setText("JP3: "+jugadores.get(i).getNombre());
				break;
			case 3:
				lblJp_4.setText("JP4: "+jugadores.get(i).getNombre());
				break;
				default: break;
			} 
		}
	}
	
	public void pagarMulta(Jugador dueño, int cantidad){
		if(jugador.getNombre().equals(dueño.getNombre())){
			this.jugador.setDinero(this.jugador.getDinero()+cantidad);
			mostrarDatos();
		}
	}
	
	public void actualizarPosicionJugador(int posAnterior, int jugadorPos, int posicion) {
		getPanel(posAnterior).getLabel(jugadorPos).setVisible(false);
		getPanel(posicion).getLabel(jugadorPos).setVisible(true);
	}
	
	public void empezarPartida(Jugador jugador, Jugador recibeTrurno) {
		if(jugador.getNombre().equals(recibeTrurno.getNombre())) {
			btnTirarDado.setEnabled(true);
		} 
		
		lblEsperando.setText("");
		for(int i=1; i<=jugadores.size(); i++){
			panel.getLabel(i).setVisible(true);
		}
		lblTurno.setText("Es el turno de "+jugadores.get(0).getNombre()+" (JP1)");
		posAnterior = 0;
	}
	
	private void mostrarDatos(){
		lblDinero.setText("$"+this.jugador.getDinero());
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 500, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		// Izquierda
		panel = new CasillaPanel(CasillaTipo.INICIO);
		springLayout.putConstraint(SpringLayout.NORTH, panel, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel, 100, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel, 100, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(panel);
		
		panel_1 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_1, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, panel_1, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, panel_1, -400, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel_1);
		
		panel_2 = new CasillaPanel(CasillaTipo.SUERTE);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_1, 0, SpringLayout.NORTH, panel_2);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_2, -198, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, panel_2, 200, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_2, 0, SpringLayout.WEST, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, panel_2, 0, SpringLayout.EAST, panel_1);
		frame.getContentPane().add(panel_2);
		
		panel_3 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_3, 0, SpringLayout.SOUTH, panel_2);
		springLayout.putConstraint(SpringLayout.WEST, panel_3, 0, SpringLayout.WEST, panel_2);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_3, 100, SpringLayout.SOUTH, panel_2);
		springLayout.putConstraint(SpringLayout.EAST, panel_3, 0, SpringLayout.EAST, panel_2);
		frame.getContentPane().add(panel_3);
		
		panel_4 = new CasillaPanel(CasillaTipo.POLICIA);
		springLayout.putConstraint(SpringLayout.NORTH, panel_4, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.WEST, panel_4, 0, SpringLayout.WEST, panel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_4, 100, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, panel_4, 0, SpringLayout.EAST, panel_3);
		frame.getContentPane().add(panel_4);
		
		// Abajo
		panel_5 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_5, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.WEST, panel_5, 100, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_5, 0, SpringLayout.SOUTH, panel_4);
		springLayout.putConstraint(SpringLayout.EAST, panel_5, 100, SpringLayout.EAST, panel_4);
		frame.getContentPane().add(panel_5);
		
		panel_6 = new CasillaPanel(CasillaTipo.SERVICIO);
		springLayout.putConstraint(SpringLayout.NORTH, panel_6, 0, SpringLayout.NORTH, panel_4);
		springLayout.putConstraint(SpringLayout.WEST, panel_6, 100, SpringLayout.WEST, panel_5);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_6, 0, SpringLayout.SOUTH, panel_5);
		springLayout.putConstraint(SpringLayout.EAST, panel_6, 100, SpringLayout.EAST, panel_5);
		frame.getContentPane().add(panel_6);
		
		panel_7 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_7, 0, SpringLayout.NORTH, panel_5);
		springLayout.putConstraint(SpringLayout.WEST, panel_7, 100, SpringLayout.WEST, panel_6);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_7, 0, SpringLayout.SOUTH, panel_6);
		springLayout.putConstraint(SpringLayout.EAST, panel_7, 100, SpringLayout.EAST, panel_6);
		frame.getContentPane().add(panel_7);
		
		panel_8 = new CasillaPanel(CasillaTipo.LIBRE);
		springLayout.putConstraint(SpringLayout.NORTH, panel_8, 0, SpringLayout.NORTH, panel_6);
		springLayout.putConstraint(SpringLayout.WEST, panel_8, 0, SpringLayout.EAST, panel_7);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_8, 100, SpringLayout.NORTH, panel_7);
		springLayout.putConstraint(SpringLayout.EAST, panel_8, 0, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(panel_8);
		
		// Derecha
		panel_9 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_9, -100, SpringLayout.NORTH, panel_6);
		springLayout.putConstraint(SpringLayout.WEST, panel_9, 300, SpringLayout.EAST, panel_2);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_9, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, panel_9, 0, SpringLayout.EAST, panel_8);
		frame.getContentPane().add(panel_9);
		
		panel_10 = new CasillaPanel(CasillaTipo.DESTINO);
		springLayout.putConstraint(SpringLayout.NORTH, panel_10, 0, SpringLayout.NORTH, panel_2);
		springLayout.putConstraint(SpringLayout.WEST, panel_10, 0, SpringLayout.WEST, panel_8);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_10, 0, SpringLayout.SOUTH, panel_2);
		springLayout.putConstraint(SpringLayout.EAST, panel_10, 0, SpringLayout.EAST, panel_8);
		frame.getContentPane().add(panel_10);
		
		panel_11 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_11, 0, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.WEST, panel_11, 0, SpringLayout.WEST, panel_8);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_11, 0, SpringLayout.SOUTH, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, panel_11, 0, SpringLayout.EAST, panel_8);
		frame.getContentPane().add(panel_11);
		
		panel_12 = new CasillaPanel(CasillaTipo.CARCEL);
		springLayout.putConstraint(SpringLayout.NORTH, panel_12, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_12, 0, SpringLayout.WEST, panel_8);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_12, 0, SpringLayout.NORTH, panel_11);
		springLayout.putConstraint(SpringLayout.EAST, panel_12, 0, SpringLayout.EAST, panel_8);
		frame.getContentPane().add(panel_12);
		
		// Arriba
		panel_13 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_13, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_13, 0, SpringLayout.WEST, panel_7);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_13, -300, SpringLayout.NORTH, panel_7);
		springLayout.putConstraint(SpringLayout.EAST, panel_13, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(panel_13);
		
		panel_14 = new CasillaPanel(CasillaTipo.SERVICIO);
		springLayout.putConstraint(SpringLayout.NORTH, panel_14, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_14, 0, SpringLayout.WEST, panel_6);
		springLayout.putConstraint(SpringLayout.SOUTH, panel_14, -300, SpringLayout.NORTH, panel_6);
		springLayout.putConstraint(SpringLayout.EAST, panel_14, 0, SpringLayout.EAST, panel_6);
		frame.getContentPane().add(panel_14);
		
		panel_15 = new CasillaPanel(CasillaTipo.PROPIEDAD);
		springLayout.putConstraint(SpringLayout.NORTH, panel_15, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, panel_15, -398, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, panel_15, 0, SpringLayout.WEST, panel_5);
		springLayout.putConstraint(SpringLayout.EAST, panel_15, -100, SpringLayout.EAST, panel_6);
		frame.getContentPane().add(panel_15);
		
		final JLabel label = new JLabel("0");
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
	
		btnTirarDado = new JButton("Tirar dado");
		btnTirarDado.setEnabled(false);
		btnTirarDado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Tirar dado
					int dado = server.tirarDado();
					label.setText(String.valueOf(dado));
					updatePositions(dado);
					btnTirarDado.setEnabled(false);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnTirarDado, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnTirarDado, 0, SpringLayout.EAST, panel_6);
		frame.getContentPane().add(btnTirarDado);
		
		springLayout.putConstraint(SpringLayout.WEST, label, 138, SpringLayout.EAST, panel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, label, -6, SpringLayout.NORTH, btnTirarDado);
		frame.getContentPane().add(label);
		
		lblTurno = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblTurno, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, lblTurno, 6, SpringLayout.EAST, panel_1);
		frame.getContentPane().add(lblTurno);
		
		lblDinero = new JLabel("$0");
		lblDinero.setForeground(new Color(0, 128, 0));
		springLayout.putConstraint(SpringLayout.WEST, lblDinero, 6, SpringLayout.EAST, panel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, lblDinero, -6, SpringLayout.NORTH, panel_5);
		frame.getContentPane().add(lblDinero);
		
		lblDescripcion = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblDescripcion, 17, SpringLayout.SOUTH, lblTurno);
		springLayout.putConstraint(SpringLayout.WEST, lblDescripcion, 6, SpringLayout.EAST, panel_1);
		frame.getContentPane().add(lblDescripcion);
		
		btnComprar = new JButton("Comprar");
		btnComprar.setEnabled(false);
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprar
				try {
					server.getPartidaController().comprarPropiedad(jugador, posicion);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnComprar, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnComprar, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(btnComprar);
		
		lblEsperando = new JLabel("Esperando a mas jugadores...");
		springLayout.putConstraint(SpringLayout.WEST, lblEsperando, 6, SpringLayout.EAST, panel_2);
		springLayout.putConstraint(SpringLayout.SOUTH, lblEsperando, -44, SpringLayout.NORTH, label);
		springLayout.putConstraint(SpringLayout.EAST, lblEsperando, -6, SpringLayout.WEST, panel_10);
		frame.getContentPane().add(lblEsperando);
		
		lblJp_1 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_1, 0, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_1, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_1);
		
		lblJp_2 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_2, 0, SpringLayout.SOUTH, lblJp_1);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_2, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_2);
		
		lblJp_3 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_3, 7, SpringLayout.SOUTH, lblJp_2);
		springLayout.putConstraint(SpringLayout.EAST, lblDescripcion, -6, SpringLayout.WEST, lblJp_3);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_3, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_3);
		
		lblJp_4 = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblEsperando, 59, SpringLayout.SOUTH, lblJp_4);
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_4, 0, SpringLayout.SOUTH, lblJp_3);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_4, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_4);
		
		btnPasar = new JButton("Pasar");
		btnPasar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pasar
				pasarTurno(getSiguienteJugador(jugador));
			}
		});
		btnPasar.setEnabled(false);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPasar, -25, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnPasar, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(btnPasar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setVisible(false);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Salir de la carcel
				salirCarcel();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnSalir, -50, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnSalir, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(btnSalir);
		
	}
	
	private void llenarPosiciones(){
		posiciones[0][0] = CasillaTipo.INICIO;
		posiciones[1][0] = CasillaTipo.PROPIEDAD;
		posiciones[2][0] = CasillaTipo.SERVICIO;
		posiciones[3][0] = CasillaTipo.PROPIEDAD;
		posiciones[4][0] = CasillaTipo.CARCEL;
		posiciones[5][0] = CasillaTipo.PROPIEDAD;
		posiciones[6][0] = CasillaTipo.DESTINO;
		posiciones[7][0] = CasillaTipo.PROPIEDAD;
		posiciones[8][0] = CasillaTipo.LIBRE;
		posiciones[9][0] = CasillaTipo.PROPIEDAD;
		posiciones[10][0] = CasillaTipo.SERVICIO;
		posiciones[11][0] = CasillaTipo.PROPIEDAD;
		posiciones[12][0] = CasillaTipo.POLICIA;
		posiciones[13][0] = CasillaTipo.PROPIEDAD;
		posiciones[14][0] = CasillaTipo.SUERTE;
		posiciones[15][0] = CasillaTipo.PROPIEDAD;
	}
	
	private CasillaPanel getPanel(int i){
		CasillaPanel aux = null;
		switch (i) {
		case 0:
			aux = panel;
			break;
		case 1:
			aux = panel_15;
			break;
		case 2:
			aux = panel_14;
			break;
		case 3:
			aux = panel_13;
			break;
		case 4:
			aux = panel_12;
			break;
		case 5:
			aux = panel_11;
			break;
		case 6:
			aux = panel_10;
			break;
		case 7:
			aux = panel_9;
			break;
		case 8:
			aux = panel_8;
			break;
		case 9:
			aux = panel_7;
			break;
		case 10:
			aux = panel_6;
			break;
		case 11:
			aux = panel_5;
			break;
		case 12:
			aux = panel_4;
			break;
		case 13:
			aux = panel_3;
			break;
		case 14:
			aux = panel_2;
			break;
		case 15:
			aux = panel_1;
			break;
		default:
			break;
		}
		return aux;
	}
	
	public Jugador getJugador() {
		return this.jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
		
		mostrarDatos();
	}

	
	private int getJugadorPos(Jugador jugador){
		int i = 0;
		int pos = -1;
		while(i < jugadores.size()){
			if(jugadores.get(i).getNombre().equals(jugador.getNombre())){
				pos = i;
			}
			i++;
		}
		return pos;
	}
	
	private Jugador getSiguienteJugador(Jugador jugador){
		int i = getJugadorPos(jugador);
		i = i++;
		
		if(i >= jugadores.size()){
			i = 0;
		}
		
		return jugadores.get(i);
	}
	
	private Jugador darDueño(int posicion){
		return getPanel(posicion).getDueño();
	}
	
	private void llenarListas(){
		suertes.add("Planta árboles en sus barrios y recibe por cada propiedad $50.");
		suertes.add("Usted ha sido elegido Presidente del Consejo. Pague $50 a cada uno de los jugadores.");
		suertes.add("Le paga a cada jugador $25 por deudas antiguas.");
		suertes.add("El siguiente jugador está algo triste y usted con su gran corazón le obsequia $50.");
	
		destinos.add("Usted ha ganado el segundo premio en un certamen de belleza. Cobre $75 a cada participante.");
		destinos.add("Vaya a la cárcel.");
		destinos.add("Una tia abuela desconocida choca con su Ferrari y lamentablemente fallece. Usted hereda $200.");
		destinos.add("Problemas con la DGI. Pague al banco $250.");
	}
}
