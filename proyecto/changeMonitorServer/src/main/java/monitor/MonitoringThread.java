package monitor;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
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
import server.HTMLPage;

/**
 * Clase que gestiona una petición de monitorización y envía los mail.
 */
public class MonitoringThread implements Runnable{

	CompleteForm form;
	int id;
	
	public MonitoringThread(){
		
	}
	
	public MonitoringThread(CompleteForm form) throws IOException, NoSuchAlgorithmException{
		this.form = form;	
		
		// Get the HTML of the page
		HTMLPage html = new HTMLPage(form.getRealUrl());
	    
	    // Hash
		String hash = html.getHash();
		
		// Write into BD
		id=new Facade().insert(form.getRealUrl(), form.getFreq(), form.getFechaFin(), hash, form.getEmail());
		
		System.out.println("Empezando la monitorizacion: "+id+" - "+form.getRealUrl());
	}
	
	/**
	 * Envia un mail informativo de que se ha procesado la peticion, luego crea un proceso 
	 * que se encargara de monitorizar la pagina web seleccionada. Una vez terminado el
	 * tiempo establecido, envia un mail con el pdf resultado de la monitorizacion
	 */
	public void run() {
        try {
			//Mail informativo
        	Mail mail = new Mail(form.getRealUrl(), form.getEmail()); 
        	mail.sendMail();
        	
        	// First we must get a reference to a scheduler
	        SchedulerFactory sf = new StdSchedulerFactory();
	        Scheduler sched = sf.getScheduler();
	
	        // define the job and tie it to our MonitorinJob class
	        JobDetail job = newJob(MonitoringJob.class)
	            .withIdentity("job"+id, "monitoring")
	            .usingJobData("id", id)
	            .usingJobData("url", form.getRealUrl())
	            .usingJobData("zone", form.getZone())
	            .build();
	        
	        long hour = 3600000;
	        
	        // Trigger the job to run on the next round minute
	        Trigger trigger = newTrigger()
	            .withIdentity("trigger"+id, "monitoring")
	            .withSchedule(simpleSchedule()
	            .withIntervalInMinutes(form.getFreq())
	            .repeatForever())
	            .endAt(new Timestamp(form.getFechaFin().getTime()+form.getZone()*hour))
	            .build();
	        
	        // Tell quartz to schedule the job using our trigger
	        sched.scheduleJob(job, trigger);
	
	        // Start up the scheduler (nothing can actually run until the 
	        // scheduler has been started)
	        sched.start();
	        
	        //Duerme hasta que pase el tiempo requerido
	        try {
				Thread.sleep(form.getFechaFin().getTime()-form.getStartDate().getTime()+10000);
			} 
	        catch (InterruptedException e) {
				e.printStackTrace();
			}
	        
	        System.out.println("Ha finalizado la monitorización");
	        // Genera el PDF con los resultados
	        ArrayList<String> changes = new Facade().getChanges(id);
	        int numberOfChanges = getNumberOfCHanges(changes);
	        String pdfName = "informe_"+id+".pdf";
	        PDF pdf = new PDF(pdfName, form.getRealUrl(), form.getStartDate().toString(), form.getFechaFin().toString(), Integer.toString(form.getFreq()), 
	        		form.getEmail(), Integer.toString(numberOfChanges), changes);
	        pdf.generatePDF();
	        
	        System.out.println("PDF generado, se va a enviar el mail");
	        
	        // Send mail
	        mail = new Mail(form.getRealUrl(), form.getEmail(), pdfName);
	        mail.sendMail();
	        
	        System.out.println("Mail enviado");
		} 
        catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Dado un string con los resultados de la monitorizacion, devuelve el numero de
	 * cambios existentes para esos resultados.
	 */
	private int getNumberOfCHanges (ArrayList<String> changes){
		int count = 0;
		for (String row : changes) {
			String[] splitStr = row.split("\\s+");
			
			if(splitStr[2].equals("Si")){
				count++;
			}
					
	    }
		return count;
	}
}
