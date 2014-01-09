package server;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import database.ConnectionGlobalManager;
import database.ConnectionManager;

/**
 * Clase que agrupa las operaciones relacionadas con los accesos a las BBDD.
 */
public class Facade {

	/**
	 * Dada una url acortada [url], devuelve la url real correspondiente almacenada
	 * en la BBDD global o devuelve null en caso de error o no existir.
	 */
	public String getRealURL(String url){
		Connection connection = null;
		String realURL = null;
		try{
		   connection = ConnectionGlobalManager.getConnection();
		   String queryString = "SELECT URL_DST FROM URLs WHERE URL_ACOR=?";
		   
		   PreparedStatement preparedStatement = 
	               connection.prepareStatement(queryString);
		   
		   preparedStatement.setString(1,url);
		   
		   ResultSet resultSet = preparedStatement.executeQuery();
		   
		   if(resultSet.next()==true){
	   	  		realURL = resultSet.getString("URL_DST");
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
        return realURL;
	}
	
	/**
	 * Añade un nuevo cambio en la base de datos local
	 */
	public synchronized void insertChange(ChangeVO change) throws SQLException{
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
    		try {
    			connection.close();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }        
	}
	
	/**
	 * Dada una url real, una frecuencia, una fecha de finalizacion, un hash y un email,
	 * almacena dichos campos en la base de datos local.
	 */
	public synchronized int insert(String url, int frequency, Timestamp endDate, String hash, String email){
		Connection connection = null;
        try{
		   connection = ConnectionManager.getConnection();
           /* Create "preparedStatement". */
           String queryString = "INSERT INTO datos " +
               "(pagina, frecuencia, fechaFin, email, hash) VALUES (?,?,?,?,?)";                    
           PreparedStatement preparedStatement = 
               connection.prepareStatement(queryString);
           
           /* Fill "preparedStatement". */    
           preparedStatement.setString(1,url);
           preparedStatement.setInt(2, frequency);
           preparedStatement.setTimestamp(3, endDate);
           preparedStatement.setString(4, email);
           preparedStatement.setString(5, hash);

           /* Execute query. */                    
           int insertedRows = preparedStatement.executeUpdate();
               
           if (insertedRows != 1) {
               throw new SQLException( "Problemas insertando datos");
           }
           
           queryString = "select last_insert_id() as last_id";
           preparedStatement = connection.prepareStatement(queryString);
           ResultSet resultSet = preparedStatement.executeQuery();
		   if(resultSet.next()){
	   	  		return resultSet.getInt("last_id");
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
        return 0;
	}
	
	/**
	 * Dada una peticion identificada por id, modifica el hash correspondiente
	 * de la misma al valor [hash].
	 */
	public void update(int id, String hash){
		Connection connection = null;
        try{
		   connection = ConnectionManager.getConnection();
           /* Create "preparedStatement". */
           String queryString = "UPDATE datos " +
               "SET hash= ? WHERE idDatos= ?";                    
           PreparedStatement preparedStatement = 
               connection.prepareStatement(queryString);
           
           /* Fill "preparedStatement". */ 
           preparedStatement.setString(1, hash);
           preparedStatement.setInt(2, id);
           
           /* Execute query. */                    
           int insertedRows = preparedStatement.executeUpdate();
               
           if (insertedRows != 1) {
               throw new SQLException( "Problemas actualizando datos");
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
	
	/**
	 * Dada una peticion identificada por id, devuelve último hash de la misma.
	 */
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
    		try {
    			connection.close();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }        
	}
	
	/**
	 * Devuelve todos los cambios relacionados con la tabla identificada con [idDatos].
	 * Es decir, devuelve todos los cambios de una página monitorizada en concreto.
	 * Estos cambios son devueltos en el formato específico que necesita la clase PDF 
	 * para generar el informe.
	 */
	public ArrayList<String> getChanges(int idDatos){
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ArrayList<String> content = new ArrayList<String>();
        try{
		   connection = ConnectionManager.getConnection();
           /* Create "preparedStatement". */
           String queryString = "SELECT FechaHora, cambio FROM cambios WHERE idDatos = ?";                    
           preparedStatement = 
               connection.prepareStatement(queryString);
           
           /* Fill "preparedStatement". */    
           preparedStatement.setInt(1, idDatos);
           

           /* Execute query. */                    
           ResultSet rs = preparedStatement.executeQuery();
           while (rs.next()) {
        	   //Se cogen los parámetros
        	Timestamp fecha = rs.getTimestamp("FechaHora");
           	int cambio = rs.getInt("cambio");	
           	String cambioStr = "Si";
           	if(cambio==0){
           		cambioStr="No";
           	}
           	
           	content.add(fecha.toString()+" "+cambioStr);
           	
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
