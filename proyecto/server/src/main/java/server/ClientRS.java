package server;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class ClientRS {

	public static void main(String[] args) {
		HttpClient httpClient = new DefaultHttpClient();

	    try {
	        HttpPost request = new HttpPost("http://localhost:9090/helloWorld/sayHello/");
	        Form form = new Form("www.algo.com", 10, new Date(), "626125@unizar.es");
	        StringEntity params = new StringEntity(new Gson().toJson(form));
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        
	        HttpResponse response = httpClient.execute(request);
	        ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        int code = response.getStatusLine().getStatusCode();    
	        System.out.println(body);
	        System.out.println("Code: "+code);
	    }catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
	}

}
