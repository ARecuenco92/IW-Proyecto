package client;

import java.sql.Timestamp;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

public class ClientRS {

	public String sendData(Form form, String url){
		HttpClient httpClient = new DefaultHttpClient();
		String result = null;
	    try {
	        HttpPost request = new HttpPost(url);
	        StringEntity params = new StringEntity(new Gson().toJson(form));
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        
	        HttpResponse response = httpClient.execute(request);
	        ResponseHandler<String> handler = new BasicResponseHandler();
	        String body = handler.handleResponse(response);
	        int code = response.getStatusLine().getStatusCode();    
	        result=body+" \nCode: "+code;
	    }catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
	    return result;
	}
	
	public static void main(String[] args){
		System.out.println(new ClientRS().sendData(new Form("FvQZJj", 2, new Timestamp(System.currentTimeMillis()+300000), "626125@unizar.es"), 
				"http://changemonitorserver.arecuenco92.eu.cloudbees.net/changeMonitor"));
	}

}
