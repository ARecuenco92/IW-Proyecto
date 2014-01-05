package monitor;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;

import mail.Mail;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import pdf.PDF;
import server.CompleteForm;
import server.Facade;
import server.Form;
import server.HTMLPage;

public class MonitoringThread implements Runnable{

	CompleteForm form;
	int id = 1;
	
	public MonitoringThread(){
		
	}
	
	public MonitoringThread(CompleteForm form) throws IOException, NoSuchAlgorithmException{
		this.form = form;	
		
		// Get the HTML of the page
		HTMLPage html = new HTMLPage(form.getRealUrl());
	    
	    // Hash
		String hash = html.getHash();
		
		// Write into BD
		new Facade().insert(1, form.getRealUrl(), form.getFreq(), form.getFechaFin(), hash);
		
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
	            .usingJobData("url", form.getRealUrl())
	            .build();
	        
	        // Trigger the job to run on the next round minute
	        Trigger trigger = newTrigger()
	            .withIdentity("trigger"+id, "monitoring")
	            .withSchedule(simpleSchedule()
	            .withIntervalInSeconds(form.getFreq())
	            .repeatForever())
	            .endAt(form.getFechaFin())
	            .build();
	        
	        // Tell quartz to schedule the job using our trigger
	        sched.scheduleJob(job, trigger);
	
	        // Start up the scheduler (nothing can actually run until the 
	        // scheduler has been started)
	        sched.start();
	        
	        try {
				Thread.sleep(form.getFechaFin().getTime()-form.getStartDate().getTime()+10000);
			} 
	        catch (InterruptedException e) {
				e.printStackTrace();
			} 
	        // Generate PDF
	        ArrayList<String> changes = new Facade().getChanges(id);
	        int numberOfChanges = getNumberOfCHanges(changes);
	        String pdfName = "informe_"+id+".pdf";
	        PDF pdf = new PDF(pdfName, form.getRealUrl(), form.getStartDate().toString(), form.getFechaFin().toString(), Integer.toString(form.getFreq()), 
	        		form.getEmail(), Integer.toString(numberOfChanges), changes);
	        pdf.generatePDF();
	        
	        // Send mail
	        Mail mail = new Mail(form.getRealUrl(), form.getEmail(), pdfName);
	        mail.sendMail();
	        // shut down the scheduler
	        sched.shutdown(true);
		} 
        catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private int getNumberOfCHanges (ArrayList<String> changes){
		int count = 0;
		for (String row : changes) {
			String[] splitStr = row.split("\\s+");
			
			if(splitStr[1].equals("Si")){
				count++;
			}
			
					
	    }
		return count;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException{
		Date s = new Date(System.currentTimeMillis()+305000);
		MonitoringThread m = new  MonitoringThread(new CompleteForm(
				new Form("a", 60, s, "cjperez8086@gmail.com"),
				"http://localhost:9999/sslist/login.html"));
		Thread r = new Thread(m); 
		r.start();
	}
}
