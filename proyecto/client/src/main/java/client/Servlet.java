package client;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/hello" })
public class Servlet extends HttpServlet {

	private static final long serialVersionUID = -8677908170932276120L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
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
		Form form = new Form(url, Integer.parseInt(freq), dateTime, email);
		ClientRS client = new ClientRS();
		String result = client.sendData(form, "http://server2.iwebunizar.cloudbees.net/rest/sayHello");
		System.out.println(result);
	}
	
}
