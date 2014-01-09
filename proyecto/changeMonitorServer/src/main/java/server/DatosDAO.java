package server;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monitor.MonitoringThread;

import com.google.gson.Gson;

/**
 * Clase que crea un nuevo proceso de monitorización.
 */
public class DatosDAO {

	/**
	 * Lee y transforma los datos enviados por json, los valida y comienza la monitorización.
	 */
	public String startMonitor(String json){
		Gson gson = new Gson();
		Form form = null;
		try {
			//Recibe los datos y los transforma en un objeto Form
			form = gson.fromJson(json, Form.class);
			
			//Valida el formulario
			String error = validateForm(form);
			
			//Si no hay errores continua la ejecucion
			if(error.equals("")){
				//Obtiene la url real de la base de datos
				Facade f = new Facade();
				String realUrl = f.getRealURL(form.getShortUrl());
				if(realUrl==null){
					return "La url "+form.getShortUrl()+" no se encuentra en la base de datos.";
				}else{
					//Crea un nuevo formulario con la nueva url
					CompleteForm CForm = new CompleteForm(form, realUrl);
					
					//Comienza la monitorización
					MonitoringThread m = new  MonitoringThread(CForm);
					Thread r = new Thread(m); 
					r.start();
					
					//Devuelve un mensaje satisfactorio
					return "Peticion de monitorizacion para la pagina "+realUrl+" recibida.";
				}
			}
			return error;
		}catch(Exception ex){
			ex.printStackTrace();
			return ex.getMessage();
		}		
	}
	
	/**
	 * Dado un objeto Form, valida los campos: url, freq, zone y fechafin.
	 */
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
		long month = 1000*60*60*24*31; // A Month in miliseconds
		Timestamp now = new Timestamp(new Date().getTime()-form.getZone()*hour);
		if(form.getFechaFin().before(now)){
			result = result + "Fecha final incorrecta ";
		}else if(now.getTime()+month<form.getFechaFin().getTime()){
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
