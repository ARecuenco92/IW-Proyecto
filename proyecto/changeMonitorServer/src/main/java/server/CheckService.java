package server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("changeMonitor")
public class CheckService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String json) {
		return new DatosDAO().startMonitor(json);
	}
}
