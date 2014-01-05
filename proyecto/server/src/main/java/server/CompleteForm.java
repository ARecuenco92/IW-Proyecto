package server;

public class CompleteForm extends Form{

	private String realUrl;
	
	public CompleteForm(Form form, String realUrl) {
		super(form.getShortUrl(), form.getFreq(), form.getFechaFin(), form.getEmail());
		this.realUrl = realUrl;
	}
	
    public String getRealUrl() {
        return realUrl;
	}
	
	public void setShortUrl(String realUrl) {
	    this.realUrl = realUrl;
	}

}
