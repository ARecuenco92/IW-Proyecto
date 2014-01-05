package server;

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
			//Llamar al proceso de alvaro
			System.out.println(CForm);
		}catch(Exception ex){
			ex.printStackTrace();
		}		
	}
	
}
