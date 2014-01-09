package client;

import java.sql.Timestamp;

/**
 * Clase que representa un formulario con los campos: url acortada, frecuencia de monitorizacion
 * en minutos, fecha de finalizaci�n de la monitorizaci�n, email destino y zona horaria del cliente.
 */
public class Form {

	private String shortUrl;
	private int freq;
	private Timestamp fechaFin;
	private String email;
	private int zone;
    
    public Form(String shortUrl, int freq, Timestamp fechaFin, String email, int zone){
    	this.shortUrl = shortUrl;
    	this.freq= freq;
    	this.fechaFin = fechaFin;
    	this.email = email;
    	this.zone = zone;
    }
    
    public String getShortUrl() {
        return shortUrl;
	}
	
	public void setShortUrl(String shortUrl) {
	    this.shortUrl = shortUrl;
	}
	
	public int getFreq() {
	    return freq;
	}
	
	public void setFreq(int freq) {
		this.freq = freq;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
	    this.email = email;
	}
	
	public Timestamp getFechaFin() {
		return fechaFin;
	}
	
	public void setFechafin(Timestamp fechaFin) {
	    this.fechaFin = fechaFin;
	}
	
	public int getZone() {
	    return zone;
	}
	
	public void setZone(int zone) {
		this.zone = zone;
	}
	
	public String toString(){
		return "URL: "+shortUrl+"  Frecuencia: "+freq+"  FechaFin: "+fechaFin+"  Email: "+email;
	}
}
