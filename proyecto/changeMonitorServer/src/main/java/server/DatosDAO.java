package server;

import mail.Mail;
import monitor.MonitoringThread;

import com.google.gson.Gson;

public class DatosDAO {

	public String startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			//Recibe los datos y los transforma en un objetoForm
			form = gson.fromJson(json, Form.class);
			
			//Obtiene la urlReal de la base de datos
			Facade f = new Facade();
			String realUrl = f.getRealURL(form.getShortUrl());
			CompleteForm CForm = new CompleteForm(form, realUrl);
			
			//Start thread 
			MonitoringThread m = new  MonitoringThread(CForm);
			Thread r = new Thread(m); 
			r.start();
			
			//Envio del Mail inicial
			Mail mail = new Mail(CForm.getRealUrl(), CForm.getEmail());
			return mail.sendMail();
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}		
	}
	
}
