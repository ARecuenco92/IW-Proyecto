package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ChangeDAO {

	public void insertChange(ChangeVO change, Connection connection){
        try{
            /* Create "preparedStatement". */
            String queryString = "INSERT INTO cambios " +
                "(idDatos, idCambios, FechaHora, cambio) VALUES (?, ?, ?, ?)";                    
            PreparedStatement preparedStatement = 
                connection.prepareStatement(queryString);
            
            /* Fill "preparedStatement". */    
            preparedStatement.setInt(1, change.getIdData());
            preparedStatement.setInt(2, change.getIdChange());
            preparedStatement.setDate(3, change.getDate());
            preparedStatement.setBoolean(4, change.getChange());

            /* Execute query. */                    
            int insertedRows = preparedStatement.executeUpdate();
                
            if (insertedRows != 1) {
                throw new SQLException( "Problemas insertando cambios");
            }
                
        } 
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
                
    } 
	
}
