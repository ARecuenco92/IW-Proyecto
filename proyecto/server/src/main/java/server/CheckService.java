package server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("form")
public class CheckService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String json) {
		new DatosDAO().startMonitor(json);
		return "OK";
	}
}
