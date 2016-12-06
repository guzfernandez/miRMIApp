package org.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Memento {
	Connection connection = null;

	public Memento() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your PostgreSQL JDBC Driver? " + "Include in your library path!");
			e.printStackTrace();
			return;
		}
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/test-dda", "postgres", "1234");
			String queryString = "SELECT name FROM users";
			PreparedStatement statemet = connection.prepareStatement(queryString);
			ResultSet rs = statemet.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
			connection.close();
			System.out.println("wiiii");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
}
