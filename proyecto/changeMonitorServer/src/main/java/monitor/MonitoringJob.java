package monitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import server.ChangeVO;
import server.Facade;
import server.HTMLPage;

/**
 * Proceso que monitoriza una pagina web y comprueba si se han producido cambios.
 */
public class MonitoringJob implements Job{

	/**
	 * Consulta una url pasada como parametro y anota en la BD si se ha producido
	 * un cambio o no cada vez que es invocado.
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// Get the URL 
		JobDataMap data = context.getJobDetail().getJobDataMap();
		int id = data.getInt("id");
		String url = data.getString("url");
		int zone = data.getInt("zone");
		
		try {
			
			// Get the HTML of the page
			HTMLPage html = new HTMLPage(url);
		    
		    // Hash
			String hash = html.getHash();
		    
			long hour = 3600000;
		    // Compare it with the last version
			Timestamp date = new Timestamp(System.currentTimeMillis()-zone*hour);
			Facade facade = new Facade();
			String last = facade.getHash(id);
			boolean change = false;
			if(!last.equals(hash)){
				change = true;
				//Update the hash
				facade.update(id, hash);
				
			}
			// Write Change into BD
			ChangeVO changeVO = new ChangeVO(id, -1, date, change);
			facade.insertChange(changeVO);
			System.out.println("Cambio: "+id+" - "+date+" - "+change);
			System.out.println("OldHash: "+last+"  New Hash: "+hash);
		} 
		catch (MalformedURLException e) {
				e.printStackTrace();
		} 
		catch (IOException e) {
				e.printStackTrace();
				Facade facade = new Facade();
				long hour = 3600000;
				Timestamp date = new Timestamp(System.currentTimeMillis()-zone*hour);
				ChangeVO changeVO = new ChangeVO(id, -1, date, true);
				try {
					facade.insertChange(changeVO);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
