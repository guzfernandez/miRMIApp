package org.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.common.Server;

public class MainWindow {
	private JFrame frame;
	private JList list;
	private JTextField txtDsd;
	private Server server;
	private DefaultListModel<String> listModel;

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, list, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, list, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, list, 190, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, list, 450, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().setLayout(springLayout);
		frame.getContentPane().add(list);

		txtDsd = new JTextField();
		txtDsd.setToolTipText("Mensaje");
		springLayout.putConstraint(SpringLayout.WEST, txtDsd, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtDsd, -28, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtDsd, 215, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(txtDsd);
		txtDsd.setColumns(10);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.sendMessage(txtDsd.getText());
					txtDsd.setText("");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, btnNewButton, 18, SpringLayout.EAST, txtDsd);
		springLayout.putConstraint(SpringLayout.SOUTH, btnNewButton, -28, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnNewButton);
		frame.setVisible(true);
	}

	public void addMessage(String messaje) {
		listModel.addElement(messaje);
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
