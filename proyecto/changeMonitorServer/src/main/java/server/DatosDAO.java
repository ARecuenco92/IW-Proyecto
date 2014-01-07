package server;

import mail.Mail2;
import monitor.MonitoringThread;

import com.google.gson.Gson;

public class DatosDAO {

	public String startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			form = gson.fromJson(json, Form.class);
			Facade f = new Facade();
			String realUrl = f.getRealURL(form.getShortUrl());
			CompleteForm CForm = new CompleteForm(form, realUrl);
			
			//Start thread 
			MonitoringThread m = new  MonitoringThread(CForm);
			Thread r = new Thread(m); 
			r.start();
			Mail2 mail = new Mail2(CForm.getRealUrl(), CForm.getEmail());
			return mail.sendMail();
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}		
	}
	
}
