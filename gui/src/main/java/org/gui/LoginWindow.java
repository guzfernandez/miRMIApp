package org.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;

import org.common.Jugador;
import org.common.Observer;
import org.common.PartidaController;
import org.common.Server;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.awt.event.ActionEvent;

public class LoginWindow {

	private JDialog frame;
	private JTextField textField;
	private JTextField textField_1;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setVisible(boolean visible) {
		this.frame.setVisible(visible);
	}

	public LoginWindow(JFrame parent) {
		initialize(parent);
	}

	private void initialize(JFrame parent) {
		frame = new JDialog(parent);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText();
				String password = textField_1.getText();
				
				try {
					Server server = ServerMonopolyFactory.getServerMonopolyInstance();
					Jugador jugador = server.getLoginController().login(username, password);
					
					if(jugador == null){
						JOptionPane.showMessageDialog(null, "Error! Datos incorrectos.");
					}
					else{
						boolean esta = server.getPartidaController().agregarJugador(jugador);
						
						if(esta){
							JOptionPane.showMessageDialog(null, "Error! El jugador ya está en la partida.");
						}
						else{
							frame.dispose();
							jugador.setDinero(100);//200
							server.setJugador(jugador);
							
							List<Observer> observers = server.getObservers();
							
							for(Observer o : observers){
								server.getPartidaController().agregarObserver(o);
							}
						}
					}
					
				} catch (RemoteException e1) {
					e1.printStackTrace();
				} catch (NotBoundException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		textField = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, btnNewButton, 0, SpringLayout.EAST, textField);
		textField.setToolTipText("");
		springLayout.putConstraint(SpringLayout.NORTH, textField, 51, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, textField, -160, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, btnNewButton, 6, SpringLayout.SOUTH, textField_1);
		springLayout.putConstraint(SpringLayout.NORTH, textField_1, 6, SpringLayout.SOUTH, textField);
		springLayout.putConstraint(SpringLayout.WEST, textField_1, 0, SpringLayout.WEST, textField);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel, 0, SpringLayout.NORTH, textField);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel, -6, SpringLayout.WEST, textField);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password:");
		springLayout.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 0, SpringLayout.NORTH, textField_1);
		springLayout.putConstraint(SpringLayout.EAST, lblNewLabel_1, -6, SpringLayout.WEST, textField_1);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
