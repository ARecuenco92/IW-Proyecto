package server;

import org.glassfish.jersey.server.ResourceConfig;

// Clase que registra los servicios disponibles
public class Server {
	
	/**
	 * Clase que se ejecuta al desplegar la aplicación
	 */
	public static void main(String[] args) {
		new ResourceConfig().register(CheckService.class);
	}
}
