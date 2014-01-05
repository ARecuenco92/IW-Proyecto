package server;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("form")
public class CheckService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String json) {
		Gson gson = new Gson();
		Form form = null;

		try {
			form = gson.fromJson(json, Form.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		//Hacer algo
		System.out.println(form);
		return form.toString();
	}
}
