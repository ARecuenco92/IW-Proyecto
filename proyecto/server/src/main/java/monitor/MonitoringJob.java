package monitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import server.ChangeVO;
import server.Facade;
import server.HTMLPage;

public class MonitoringJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// Get the URL 
		JobDataMap data = context.getJobDetail().getJobDataMap();
		int id = data.getInt("id");
		String url = data.getString("url");
		
		try {
			
			// Get the HTML of the page
			HTMLPage html = new HTMLPage(url);
		    
		    // Hash
			String hash = html.getHash();
		    
		    // Compare it with the last version
			Date date = new Date(System.currentTimeMillis());
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
		} 
		catch (MalformedURLException e) {
				e.printStackTrace();
		} 
		catch (IOException e) {
				e.printStackTrace();
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
