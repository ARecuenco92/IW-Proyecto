package server;

import org.glassfish.jersey.server.ResourceConfig;

public class Server {	
	public static void main(String[] args) {
		new ResourceConfig().register(CheckService.class);
	}
}
