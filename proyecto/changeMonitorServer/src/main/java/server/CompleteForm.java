package server;

import java.sql.Timestamp;

public class CompleteForm extends Form{

	private String realUrl;
	private Timestamp startDate;
	
	public CompleteForm(Form form, String realUrl) {
		super(form.getShortUrl(), form.getFreq(), form.getFechaFin(), form.getEmail(), form.getZone());
		this.realUrl = realUrl;
        long hour=3600000;
		this.startDate = new Timestamp(System.currentTimeMillis()-form.getZone()*hour);
	}
	
    public String getRealUrl() {
        return realUrl;
	}
	
	public void setRealUrl(String realUrl) {
	    this.realUrl = realUrl;
	}
	
	public Timestamp getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Timestamp startDate) {
	    this.startDate = startDate;
	}
	
	public String toString(){
		return super.toString()+" URL Real: "+realUrl+" StartDate: "+startDate;
	}

}
