package client;

import java.util.Date;

public class Form {

	private String shortUrl;
	private int freq;
	private Date fechaFin;
	private String email;
    
    public Form(String shortUrl, int freq, Date fechaFin, String email){
    	this.shortUrl = shortUrl;
    	this.freq= freq;
    	this.fechaFin = fechaFin;
    	this.email = email;
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
	
	public Date getFechaFin() {
		return fechaFin;
	}
	
	public void setFechafin(Date fechaFin) {
	    this.fechaFin = fechaFin;
	}
	
	public String toString(){
		return "URL: "+shortUrl+"  Frecuencia: "+freq+"  FechaFin: "+fechaFin+"  Email: "+email;
	}
}
