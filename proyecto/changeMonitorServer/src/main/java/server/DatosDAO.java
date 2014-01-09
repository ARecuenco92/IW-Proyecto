package server;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.MonitoringThread;

import com.google.gson.Gson;

public class DatosDAO {

	public String startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			//Recibe los datos y los transforma en un objetoForm
			form = gson.fromJson(json, Form.class);
			
			String error = validateForm(form);
			
			if(error.equals("")){
				//Obtiene la urlReal de la base de datos
				Facade f = new Facade();
				String realUrl = f.getRealURL(form.getShortUrl());
				if(realUrl==null){
					return "La url "+form.getShortUrl()+" no se encuentra en la base de datos.";
				}else{
					CompleteForm CForm = new CompleteForm(form, realUrl);
					
					//Start thread 
					MonitoringThread m = new  MonitoringThread(CForm);
					Thread r = new Thread(m); 
					r.start();
					
					//Envio del Mail inicial
					return "Peticion de monitorizacion para la pagina "+realUrl+" recibida.";
				}
			}
			return error;
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}		
	}
	
	public String validateForm(Form form){
		String result = "";
		if(form.getShortUrl().contains("'")){
			result = result + "URL contiene caracteres no válidos ";
		}
		if(form.getFreq()<5 || form.getFreq()>300){
			result = result + "Frecuencia fuera de rango ";
		}
		if(form.getZone()<-12 || form.getZone()>12){
			result = result + "Zona fuera de rango ";
		}
		long hour = 3600000;
		if(form.getFechaFin().before(new Timestamp(new Date().getTime()-form.getZone()*hour))){
			result = result + "Fecha final incorrecta ";
		}
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(form.getEmail());
		if(!matcher.matches()){
			result = result + "Email incorrecto";
		}
		return result;
	}
	
}
