package client;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase que lee los parámetros del formulario y realiza una petición al servicio
 * de monitorizacion: http://changemonitorserver.iwebunizar.cloudbees.net/changeMonitor
 */
public class Servlet extends HttpServlet  {

	private static final long serialVersionUID = -8677908170932276120L;

	/**
	 * Recibe los parametros url, frecuencia, fecha, email y hora, los valida y llama al
	 * servicio de monitorizaciÃ³n a partir de los datos recibidos.
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		boolean paramOK = true; //Para saber si los parï¿½metros estï¿½n bien
		String url = request.getParameter("url");
		String freq = request.getParameter("freq");
		String date = request.getParameter("date");
		String email = request.getParameter("email");
		String hour = request.getParameter("hour");
		String dateDelay = request.getParameter("dateDelay");
		//Check parameters
		//Check null or empty
		if(url==null || url.equals("") || freq==null || freq.equals("") || date==null || date.equals("") ||
				email==null || email.equals("") || dateDelay==null || dateDelay.equals("")){
			System.err.println("Error: parametros vacios");
			paramOK=false;
		}
		//Check url
		String URL_PATTERN = "^http://7iw.es/.*|7iw.es/.*|www.7iw.es/.*|http://www.7iw.es/.*$";
		Pattern pattern1 = Pattern.compile(URL_PATTERN);
		Matcher matcher1 = pattern1.matcher(url);
		if(!matcher1.matches()){
			System.err.println("Error: url incorrecta");
			paramOK=false;
		}else{
			url = url.substring(url.indexOf("7iw.es/")+7);
			System.out.println(url);
		}
		//Check frequency
		if(Integer.parseInt(freq)<5 && Integer.parseInt(freq)>300){ //min frequency 5 min
			System.err.println("Error: frecuencia incorrecta");
			paramOK=false;
		}
		//Check date delay
		int zone = Integer.parseInt(dateDelay);
		if(zone%60!=0 || zone<-720 || zone>720){
			System.err.println("Error: zona incorrecta");
			paramOK=false;
		}
		//Check email
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if(!matcher.matches()){
			System.err.println("Error: el email no está bien formado");
			paramOK=false;
		}
		//Check hour
		if(hour!=null && !hour.equals("") && (Integer.parseInt(hour)>24 || Integer.parseInt(hour)<0)){
			System.err.println("Error: hora incorrecta");
			paramOK=false;
		}		
		if(paramOK){
			try{
				Timestamp dateTime = null;
				long month = 1000*60*60*24*31; // A Month in miliseconds
				if(hour!=null && !hour.equals("")){
					String dateHour = date+"-"+hour;
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH");
					Date d = df.parse(dateHour);
					dateTime = new Timestamp(d.getTime());
				}
				else{
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Date d = df.parse(date);
					dateTime = new Timestamp(d.getTime());
				}
				Timestamp now = new Timestamp(new Date().getTime());
				if(now.getTime()>dateTime.getTime() || now.getTime()+month<dateTime.getTime()){
					System.err.println("Error: fecha incorrecta");
					paramOK=false;
				}
				
				if(paramOK){
					//if all params all OK then the data is sent.
					Form form = new Form(url, Integer.parseInt(freq), dateTime, email, Integer.parseInt(dateDelay)/60);
					//System.out.println("Se va a pedir"+form);
					ClientRS client = new ClientRS();
					String result = client.sendData(form, "http://changemonitorserver.iwebunizar.cloudbees.net/changeMonitor");
					if(result.startsWith("Peticion de monitorizacion para la pagina")){					
						RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
						request.setAttribute("respuesta","Monitorizacion iniciada correctamente");
						dispatcher.forward(request,response);
					}else{
						RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
						request.setAttribute("respuesta","Error: "+result);
						dispatcher.forward(request,response);
					}
				}else{
					RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
					request.setAttribute("respuesta","Ha habido un error introduciendo los parametros, vuelva a intentarlo.");
					dispatcher.forward(request,response);
				}
			}catch(Exception ex){
				RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
				request.setAttribute("respuesta","Ha habido un error introduciendo los parametros, vuelva a intentarlo.");
				dispatcher.forward(request,response);
			}
		}
		else { //error
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			request.setAttribute("respuesta","Ha habido un error introduciendo los parámetros, vuelva a intentarlo.");
			dispatcher.forward(request,response);
		}
	}
	
	/**
	 * Devuelve la pagina con el formulario
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request,response);
	}
	
}
