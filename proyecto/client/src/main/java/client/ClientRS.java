package client;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

/**
 * Cliente HTTP que realiza una peticion POST enviando un formulario como json
 */
public class ClientRS {

	/**
	 * Realiza una peticion POST a la url [url] enviando como contenido el objeto
	 * Form [form] codificado como json
	 */
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
	        result = handler.handleResponse(response);  
	    }catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	    }
	    return result;
	}
	
}
