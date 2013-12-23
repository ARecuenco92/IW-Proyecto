package server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonitoringJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// Get the url 
		JobDataMap data = context.getJobDetail().getJobDataMap();
		int id = data.getInt("id");
		String url = data.getString("url");
		
		try {
			
			// Get the html of the page
			HTMLPage html = new HTMLPage(url);
		    
		    // Hash
			String hash = html.getHash();
		    
		    // Compare it with the last version
			
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
	}

}
