package server;

import java.sql.Date;

public class CompleteForm extends Form{

	private String realUrl;
	private Date startDate;
	
	public CompleteForm(Form form, String realUrl) {
		super(form.getShortUrl(), form.getFreq(), form.getFechaFin(), form.getEmail());
		this.realUrl = realUrl;
		this.startDate = new Date(System.currentTimeMillis());
	}
	
    public String getRealUrl() {
        return realUrl;
	}
	
	public void setRealUrl(String realUrl) {
	    this.realUrl = realUrl;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public void setStartDate(Date startDate) {
	    this.startDate = startDate;
	}
	
	public String toString(){
		return super.toString()+" URL Real: "+realUrl+" StartDate: "+startDate;
	}

}
