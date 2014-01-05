package server;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.ConnectionGlobalManager;
import database.ConnectionManager;

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
	
	public String getRealURL(String url) throws SQLException{
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
        	connection.close();
        }
        return realURL;
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
               throw new SQLException( "Problemas insertando datos");
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
           	Date fecha = rs.getDate("FechaHora");
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
	
	public static void main(String[] args) throws SQLException{
		Facade f = new Facade();
		System.out.println(f.getRealURL("FvQZJj"));
	}
	
}
