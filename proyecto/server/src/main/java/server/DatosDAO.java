package server;

import com.google.gson.Gson;

public class DatosDAO {

	public void startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			form = gson.fromJson(json, Form.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		//Hacer algo
		Facade f = new Facade();
		String realUrl = null;
		try{
			realUrl = f.getRealURL(form.getShortUrl());
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(realUrl!=null){
			CompleteForm CForm = new CompleteForm(form, realUrl);
			System.out.println(CForm);
		}
		

	}
	
}
