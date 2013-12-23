package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonitoringJob implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		// Get the url 
		JobDataMap data = context.getJobDetail().getJobDataMap();
		String url = data.getString("url");
		
		try {
			
			// Get the html of the page
			URL page = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(page.openStream()));
			String html="";
		    String inputLine;
		    while ((inputLine = in.readLine()) != null){
		    	html = html +inputLine;
		    }
		    in.close();
		    
		    // Hash
		    
		    // Compare it with the last version
		} 
		catch (MalformedURLException e) {
				e.printStackTrace();
		} 
		catch (IOException e) {
				e.printStackTrace();
		}
	}

}
