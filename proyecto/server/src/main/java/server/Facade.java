package server;

import java.sql.Connection;
import java.sql.SQLException;

public class Facade {

	public void insertChange(ChangeVO change) throws SQLException{
		Connection connection = null;
        try{
		   connection = ConnectionManager.getConnection();
	       ChangeDAO changeDAO = new ChangeDAO();
		   
	       changeDAO.insertChange(change,connection);  
	       connection.close();                    
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        finally{
		connection.close();
        }        
	}
	
}
