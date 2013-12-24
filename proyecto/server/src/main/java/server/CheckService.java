package server;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("sayHello")
public class CheckService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String getMessage(String json) {
		Gson gson = new Gson();
		Form form = null;

		// Read the existing address book.
		try {
			form = gson.fromJson(json, Form.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println(form);
		return form.toString();
	}
	
	/*public void getMessage(@PathParam("name") String name, @PathParam("surname") String surname) {
		System.out.println(name);
		return "Hello "+name+" "+surname;
	}*/
}
