package server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	
	public void insert(int id, String url, int frequency, Date endDate, String hash){
		Connection connection = null;
        try{
		   connection = ConnectionManager.getConnection();
           /* Create "preparedStatement". */
           String queryString = "INSERT INTO datos " +
               "(idDatos, pagina, frecuencia, fechaFin, email, hash) VALUES (?, ?, ?, ?,?,?)";                    
           PreparedStatement preparedStatement = 
               connection.prepareStatement(queryString);
           
           /* Fill "preparedStatement". */    
           preparedStatement.setInt(1, id);
           preparedStatement.setString(2,url);
           preparedStatement.setInt(3, frequency);
           preparedStatement.setDate(4, endDate);
           preparedStatement.setString(5, "email@email.com");
           preparedStatement.setString(6, hash);

           /* Execute query. */                    
           int insertedRows = preparedStatement.executeUpdate();
               
           if (insertedRows != 1) {
               throw new SQLException( "Problemas insertando cambios");
           }
	       connection.close();                    
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        finally{
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }   
	}
	
	public String getHash(int id) throws SQLException{
		Connection connection = null;
        try{
		   connection = ConnectionManager.getConnection();
	       String queryString = "SELECT hash FROM datos " +
	                "WHERE idDatos = ?";    
	       
	       PreparedStatement preparedStatement = 
	                connection.prepareStatement(queryString);
	               
	       /* Fill "preparedStatement". */    
	       preparedStatement.setInt(1, id);
	             
	       /* Execute query. */                    
	       ResultSet resultSet = preparedStatement.executeQuery();
	               
	       /* cojo los parámetros*/
	               
	   	  	if(resultSet.next()==true){
	   	  		return resultSet.getString("hash");
	        }
	        else return null;        
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
            return null;
        } 
        finally{
        	connection.close();
        }        
	}
	
	/*
	 * Devuelve todos los cambios relacionados con la tabla identificada con [idDatos].
	 * Es decir, devuelve todos los cambios de una página monitorizada en concreto.
	 * Estos cambios son devueltos en el formato específico que necesita la clase PDF 
	 * para generar el informe.
	 */
	public String[][] getChanges(int idDatos){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String[][] content = null;
        try{
		   connection = ConnectionManager.getConnection();
           /* Create "preparedStatement". */
           String queryString = "SELECT FechaHora, cambio FROM cambios WHERE idDatos = ?";                    
           preparedStatement = 
               connection.prepareStatement(queryString);
           
           /* Fill "preparedStatement". */    
           preparedStatement.setInt(1, idDatos);
           

           /* Execute query. */                    
           ResultSet rs = preparedStatement.executeQuery(queryString );
           while (rs.next()) {
        	   //No sé como cogerlo en el formato porque no es string
           	String FechaHora = rs.getString("FechaHora");
           	String cambio = rs.getString("cambio");	
           }                   
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        } 
        finally{
        	try{
	        	if (preparedStatement != null) {
					preparedStatement.close();
				}
	 
				if (connection != null) {
					connection.close();
				}
				
        	} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    			
    		}
        	
        }
		return content; //Devuelve los cambios
	}
	
	
}
