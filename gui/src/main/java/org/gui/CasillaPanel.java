package org.gui;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Color;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.common.CasillaTipo;
import org.common.Jugador;

import javax.swing.JLabel;

public class CasillaPanel extends JPanel {
	private Jugador dueño = null;
	private JLabel lblJ1, lblJ2, lblJ3, lblJ4;
	public JLabel lblDueño;
	private int precio;
	private int multa;
	
	public CasillaPanel(CasillaTipo casillaTipo) {
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		lblJ1 = new JLabel("JP1");
		lblJ1.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, lblJ1, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblJ1, 10, SpringLayout.WEST, this);
		add(lblJ1);
		
		lblJ2 = new JLabel("JP2");
		lblJ2.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, lblJ2, 0, SpringLayout.NORTH, lblJ1);
		springLayout.putConstraint(SpringLayout.EAST, lblJ2, -10, SpringLayout.EAST, this);
		add(lblJ2);
		
		lblJ3 = new JLabel("JP3");
		lblJ3.setVisible(false);
		springLayout.putConstraint(SpringLayout.WEST, lblJ3, 0, SpringLayout.WEST, lblJ1);
		springLayout.putConstraint(SpringLayout.SOUTH, lblJ3, -10, SpringLayout.SOUTH, this);
		add(lblJ3);
		
		lblJ4 = new JLabel("JP4");
		lblJ4.setVisible(false);
		springLayout.putConstraint(SpringLayout.NORTH, lblJ4, 0, SpringLayout.NORTH, lblJ3);
		springLayout.putConstraint(SpringLayout.EAST, lblJ4, 0, SpringLayout.EAST, lblJ2);
		add(lblJ4);
		
		lblDueño = new JLabel("");
		lblDueño.setVisible(false);
		springLayout.putConstraint(SpringLayout.WEST, lblDueño, -15, SpringLayout.EAST, lblJ3);
		springLayout.putConstraint(SpringLayout.SOUTH, lblDueño, 0, SpringLayout.SOUTH, this);
		add(lblDueño);
		
		setBorder(new LineBorder(new Color(0, 0, 0)));
		switch (casillaTipo) {
		case PROPIEDAD:
			setBorder(new TitledBorder(null, "Propiedad", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			setBackground(Color.ORANGE);
			break;
		case INICIO:
			changeColor();
			setBorder(new TitledBorder(null, "Inicio", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
			setBackground(Color.RED);
			break;
		case SERVICIO:
			setBorder(new TitledBorder(null, "Servicio", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			setBackground(Color.PINK);
			break;
		case CARCEL:
			changeColor();
			setBorder(new TitledBorder(null, "Carcel", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
			setBackground(Color.DARK_GRAY);
			break;
		case DESTINO:
			setBorder(new TitledBorder(null, "Destino", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			setBackground(Color.GREEN);
			break;
		case LIBRE: 
			setBorder(new TitledBorder(null, "Libre", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			setBackground(Color.WHITE);
			break;
		case POLICIA:
			changeColor();
			setBorder(new TitledBorder(null, "Policia", TitledBorder.CENTER, TitledBorder.TOP, null, Color.WHITE));
			setBackground(Color.BLUE);
			break;
		case SUERTE: 
			setBorder(new TitledBorder(null, "Suerte", TitledBorder.CENTER, TitledBorder.TOP, null, null));
			setBackground(Color.YELLOW);
			break;
		default:
			break;
		}
	}
	
	private void changeColor(){
		lblJ1.setForeground(Color.WHITE);
		lblJ2.setForeground(Color.WHITE);
		lblJ3.setForeground(Color.WHITE);
		lblJ4.setForeground(Color.WHITE);
		lblDueño.setForeground(Color.WHITE);
	}
	
	public JLabel getLabel(int i){
		JLabel lbl = null;
		switch (i) {
		case 1:
			lbl = lblJ1;
			break;
		case 2:
			lbl = lblJ2;
			break;
		case 3:
			lbl = lblJ3;
			break;
		case 4:
			lbl = lblJ4;
			break;
		default:
			break;
		}
		return lbl;
	}
	
	public void setDueño(Jugador jugador){
		this.dueño = jugador;
	}
	
	public Jugador getDueño(){
		return this.dueño;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public int getMulta() {
		return multa;
	}

	public void setMulta(int multa) {
		this.multa = multa;
	}

}
