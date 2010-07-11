package ar.com.natlehmann.cdcatalogue.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ar.com.natlehmann.cdcatalogue.dao.DaoException;

public class DaoConnection {
	
	private static DaoConnection instance;
	private Connection connection;
	
	private static final String URL = "jdbc:mysql://localhost/cdcatalogue";
	private static final String USER = "cdcatalogue";
	private static final String PASSWORD = "cdcatalogue";
	
	private DaoConnection() throws DaoException {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
		} catch (Exception e) {
			throw new DaoException("Error al inicializar DaoConnection", e);
		}
	}
	
	public static DaoConnection getInstance() throws DaoException {
		
		if (instance == null) {
			instance = new DaoConnection();
		}
		return instance;
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	
	public void terminate() throws SQLException {
		this.connection.close();
	}

}
