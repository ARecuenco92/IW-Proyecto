package server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;


// Servicio que se encarga de monitorizar una p�gina web a partir de
// los par�metros enviados a trav�s de una petici�n POST con json
@Path("changeMonitor")
public class CheckService {
	
	//Lee los datos json y comienza la monitorizacion de una pagina web
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String json) {
		return new DatosDAO().startMonitor(json);
	}
}
