package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	
	private final static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	private final static String DRIVER_URL = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/iweblocal";
	private final static String USER = "cjperez";
	private final static String PASSWORD = "club8086";
	
	static {
		
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
		
	}

	private ConnectionManager() {}
	
	public final static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DRIVER_URL, USER, PASSWORD);
	}
	
	
}
