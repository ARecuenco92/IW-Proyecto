package server;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class MonitoringThread implements Runnable{

	private int id;
	private String url;
	private int frequency;
	private Date endDate;
	
	public MonitoringThread(){
		
	}
	
	public MonitoringThread(int id, String url, int frequency, Date endDate) throws IOException, NoSuchAlgorithmException{
		this.id = id;
		this.url = url;
		this.frequency = frequency;
		this.endDate = endDate;		
		
		// Get the html of the page
		HTMLPage html = new HTMLPage(url);
	    
	    // Hash
		String hash = html.getHash();
		
		// Write into BD
		PrintWriter pw = new PrintWriter("last"+id);
		pw.print(hash);
		pw.close();
		
	}
	
	public void run() {
        try {
			 // First we must get a reference to a scheduler
	        SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();
	
	        // define the job and tie it to our MonitorinJob class
	        JobDetail job = newJob(MonitoringJob.class)
	            .withIdentity("job"+id, "monitoring")
	            .usingJobData("id", id)
	            .usingJobData("url", url)
	            .build();
	        
	        // Trigger the job to run on the next round minute
	        Trigger trigger = newTrigger()
	            .withIdentity("trigger"+id, "monitoring")
	            .withSchedule(simpleSchedule()
	            .withIntervalInHours(frequency)
	            .repeatForever())
	            .endAt(endDate)
	            .build();
	        
	        // Tell quartz to schedule the job using our trigger
	        sched.scheduleJob(job, trigger);
	
	        // Start up the scheduler (nothing can actually run until the 
	        // scheduler has been started)
	        sched.start();
	
	        // shut down the scheduler
	        sched.shutdown(true);
		} 
        catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
