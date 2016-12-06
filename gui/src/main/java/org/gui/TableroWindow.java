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
import java.awt.event.ActionEvent;
import java.awt.Font;

public class TableroWindow {

	private Jugador jugador;
	private JFrame frame;
	private Server server;
	private List<Jugador> jugadores;
	private int posicion;
	private int posAnterior = -1;
	private CasillaTipo[][] posiciones;
	private JLabel lblJp_1, lblJp_2, lblJp_3, lblJp_4, lblEsperando, lblTurno;
	private JButton btnTirarDado, btnComprar, btnPasar;
	private CasillaPanel panel, panel_1, panel_2, panel_3, panel_4, panel_5, panel_6, panel_7, panel_8, panel_9, panel_10, panel_11, panel_12, panel_13, panel_14, panel_15;
	
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
		int aux = posicion+dado;
		if(aux > 15){
			posicion = aux-16;
		}
		else{
			posicion += dado;
		}
		try {
			server.getPartidaController().actualizarPosicionJugador(posAnterior, getJugadorPos(jugador)+1, posicion);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		mostrarOpciones(posAnterior, getJugadorPos(jugador), posicion);
	}
	
	private void mostrarOpciones(int posAnterior, int jugPos, int posicion){
		getPanel(posAnterior).getLabel(jugPos+1).setVisible(false);
		getPanel(posicion).getLabel(jugPos+1).setVisible(true);
		this.posAnterior = posicion;
		
		if(posiciones[posicion][0] == CasillaTipo.INICIO){
			System.out.println("INICIO");
		}
		else if(posiciones[posicion][0] == CasillaTipo.PROPIEDAD){
			System.out.println("PROPIEDAD");
		}
		else if(posiciones[posicion][0] == CasillaTipo.SUERTE){
			System.out.println("SUERTE");
		}
		else if(posiciones[posicion][0] == CasillaTipo.DESTINO){
			System.out.println("DESTINO");
		}
		else if(posiciones[posicion][0] == CasillaTipo.CARCEL){
			System.out.println("CARCEL");
		}
		else if(posiciones[posicion][0] == CasillaTipo.POLICIA){
			System.out.println("POLICIA");
		}
		else if(posiciones[posicion][0] == CasillaTipo.LIBRE){
			System.out.println("LIBRE");
		}
		else if(posiciones[posicion][0] == CasillaTipo.SERVICIO){
			System.out.println("SERVICIO");
		}
		
		try {
			server.getPartidaController().cambiarTurno(jugPos);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void actualizarTimer(int segundo) {
		lblEsperando.setText("La partida empieza en "+segundo+" segundos.");
	}
	
	public void cambiarTurno(int pos, Jugador jugador, Jugador recibeTurno) {
		if(jugador.getNombre().equals(recibeTurno.getNombre())){
			btnTirarDado.setEnabled(true);
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
				lblJp_1.setText(jugadores.get(i).getNombre());	
				break;
			case 1:
				lblJp_2.setText(jugadores.get(i).getNombre());
				break;
			case 2:
				lblJp_3.setText(jugadores.get(i).getNombre());
				break;
			case 3:
				lblJp_4.setText(jugadores.get(i).getNombre());
				break;
				default: break;
			} 
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
					int dado = server.tirarDado();
					label.setText(String.valueOf(dado));
					updatePositions(dado);
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
		
		lblTurno = new JLabel("Es el turno de: ");
		springLayout.putConstraint(SpringLayout.NORTH, lblTurno, 6, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, lblTurno, 6, SpringLayout.EAST, panel_1);
		frame.getContentPane().add(lblTurno);
		
		JLabel label_1 = new JLabel("$500");
		label_1.setForeground(new Color(0, 128, 0));
		springLayout.putConstraint(SpringLayout.WEST, label_1, 6, SpringLayout.EAST, panel_3);
		springLayout.putConstraint(SpringLayout.SOUTH, label_1, -6, SpringLayout.NORTH, panel_5);
		frame.getContentPane().add(label_1);
		
		JLabel lblDescripcin = new JLabel("Descripción:");
		springLayout.putConstraint(SpringLayout.NORTH, lblDescripcin, 17, SpringLayout.SOUTH, lblTurno);
		springLayout.putConstraint(SpringLayout.WEST, lblDescripcin, 6, SpringLayout.EAST, panel_1);
		frame.getContentPane().add(lblDescripcin);
		
		btnComprar = new JButton("Comprar");
		btnComprar.setEnabled(false);
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Comprar
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnComprar, 0, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnComprar, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(btnComprar);
		
		lblEsperando = new JLabel("Esperando a mas jugadores...");
		springLayout.putConstraint(SpringLayout.NORTH, lblEsperando, 75, SpringLayout.SOUTH, lblDescripcin);
		springLayout.putConstraint(SpringLayout.WEST, lblEsperando, 55, SpringLayout.EAST, panel_2);
		frame.getContentPane().add(lblEsperando);
		
		lblJp_1 = new JLabel("JP1");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_1, 0, SpringLayout.NORTH, panel_1);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_1, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_1);
		
		lblJp_2 = new JLabel("JP2");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_2, 0, SpringLayout.SOUTH, lblJp_1);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_2, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_2);
		
		lblJp_3 = new JLabel("JP3");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_3, 0, SpringLayout.NORTH, lblDescripcin);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_3, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_3);
		
		lblJp_4 = new JLabel("JP4");
		springLayout.putConstraint(SpringLayout.NORTH, lblJp_4, 0, SpringLayout.SOUTH, lblJp_3);
		springLayout.putConstraint(SpringLayout.EAST, lblJp_4, -6, SpringLayout.WEST, panel_11);
		frame.getContentPane().add(lblJp_4);
		
		btnPasar = new JButton("Pasar");
		btnPasar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pasar
			}
		});
		btnPasar.setEnabled(false);
		springLayout.putConstraint(SpringLayout.SOUTH, btnPasar, -25, SpringLayout.SOUTH, panel_3);
		springLayout.putConstraint(SpringLayout.EAST, btnPasar, 0, SpringLayout.EAST, panel_7);
		frame.getContentPane().add(btnPasar);
		
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
		return jugador;
	}

	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
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
		
}
