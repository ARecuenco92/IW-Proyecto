package client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/hello" })
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = -8677908170932276120L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		boolean paramOK = true; //Para saber si los par�metros est�n bien
		String url = request.getParameter("url");
		String freq = request.getParameter("freq");
		String date = request.getParameter("date");
		String email = request.getParameter("email");
		String hour = request.getParameter("hour");
		String dateHour = date+"-"+hour;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH");
		Date d = null;
		Timestamp dateTime = null;
		try {
			d = df.parse(dateHour);
			dateTime = new Timestamp(d.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//Check parameters
		//Check null or empty
		if(url==null || url.equals("") || freq==null || freq.equals("") || date==null || date.equals("") ||
				email==null || email.equals("") || hour==null || hour.equals("")){
			System.err.println("Error: se han detectado par�metros nulos o vac�os");
			paramOK=false;
			
		}
		else{
			//Check frequency
			if(Integer.parseInt(freq)<=0){
				System.err.println("Error: frecuencia incorrecta");
				paramOK=false;
			}
			//Check email
			 String EMAIL_PATTERN = 
					"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			 Pattern pattern = Pattern.compile(EMAIL_PATTERN);
			 Matcher matcher = pattern.matcher(email);
			 if(!matcher.matches()){
				 System.err.println("Error: el email no est� bien formado");
				 paramOK=false;
			 }
		}
		
		//if all params all OK then the data is sent.
		if(paramOK){
		
			Form form = new Form(url, Integer.parseInt(freq), dateTime, email);
			ClientRS client = new ClientRS();
			String result = client.sendData(form, "http://changemonitorserver.arecuenco92.eu.cloudbees.net/changeMonitor");
			System.out.println(result);
		}
	}
	
}
