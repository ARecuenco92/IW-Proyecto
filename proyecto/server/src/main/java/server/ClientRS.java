package server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class ClientRS {

	public static void main(String[] args) {
		/*Client c = ClientBuilder.newClient();
		WebTarget wt = c.target("http://localhost:8080/helloWorld/sayHello/");
		Response rp = wt.request().post();
		System.out.println(ClientBuilder.newClient()
				.target("http://localhost:8080/helloWorld/sayHello/{name}")
				.resolveTemplate("name", "World!")
				.request("text/plain").get().readEntity(String.class));*/
		HttpClient httpClient = new DefaultHttpClient();

	    try {
	        HttpPost request = new HttpPost("http://localhost:9090/helloWorld/sayHello/");
	        StringEntity params =new StringEntity("{\"id\":18,\"name\":\"myname\",\"surname\":\"name2\"} ");
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	        ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        int code = response.getStatusLine().getStatusCode();
	        
	        System.out.println(body);
	        System.out.println("Code: "+code);

	        // handle response here...
	    }catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
	}

}
