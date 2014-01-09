package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Clase que almacena los datos para conectarse a la BD Global
public class ConnectionGlobalManager {
	private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private final static String DRIVER_URL = "jdbc:mysql://ec2-176-34-253-124.eu-west-1.compute.amazonaws.com:3306/ingenieriaweb";
	private final static String USER = "iwebunizar";
	private final static String PASSWORD = "zaragoza2013";
	
	static {
		
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
		
	}

	private ConnectionGlobalManager() {}
	
	/**
	 * Devuelve un objeto Connection apuntando a la base de datos global
	 */
	public final static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
	}
}
