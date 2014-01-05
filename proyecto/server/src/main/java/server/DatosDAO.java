package server;

import monitor.MonitoringThread;

import com.google.gson.Gson;

public class DatosDAO {

	public void startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			form = gson.fromJson(json, Form.class);
			Facade f = new Facade();
			String realUrl = f.getRealURL(form.getShortUrl());
			CompleteForm CForm = new CompleteForm(form, realUrl);
			System.out.println(CForm);
			
			//Start thread 
			MonitoringThread m = new  MonitoringThread(CForm);
			Thread r = new Thread(m); 
			r.start();
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
	
}
