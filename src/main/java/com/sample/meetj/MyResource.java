
package com.sample.meetj;

import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/** Example resource class hosted at the URI path "/myresource"
 */

@Path("/myresource")
public class MyResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces(MediaType.APPLICATION_JSON)
    public String getIt() {
        return "Hi there!";
    }
    
    
    /** Method processing http GET request, producing "JSON" data
    * type
    * @return event information at meetup
    */
    
    @GET 
    @Path("/events")
    public JSONObject getEvent() {
    	
    	// meetup API key for user jagdeesh  "6b6e2a571e116213e6424331d206d76"
    	String MeetupAPIKey = "6b6e2a571e116213e6424331d206d76";
    	
    	JSONObject jsonObject = new JSONObject();
    	
    	String name = "Meetj"; 
    
    	 // Create Jersey client
    	ClientConfig clientConfig = new DefaultClientConfig();
    	clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    	Client client = Client.create(clientConfig);
    	WebResource resource = client.resource("https://api.meetup.com/great-hyderabad-adventure-club?key=6b6e2a571e116213e6424331d206d76"); 
    	ClientResponse response = resource.accept("application/json").get(ClientResponse.class);
    	
    	if (response.getStatus() != 200 ) {
			
    		throw new WebApplicationException();
		
    	}
    	
    	/******* JSON PARSER **********/
    	
    	String responseObject = response.getEntity(String.class);
    	
    	JsonObject root = new JsonParser().parse(responseObject).getAsJsonObject();
    	
    	Iterator iterator = root.entrySet().iterator();
         
        while (iterator.hasNext()) {
        	 Map.Entry pair = (Map.Entry) iterator.next();
        	  System.out.println(pair.getKey() + " = " + pair.getValue());
        	 iterator.remove();
	 	}
     
      try {
    		
			jsonObject.put("name",name);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return jsonObject;
    }
    
}







/*JsonElement jelement = new JsonParser().parse(responseObject);
JsonObject jobject = jelement.getAsJsonObject();
jobject = jobject.getAsJsonObject("organizer");


 
 System.out.println("\n"+jobject +"\n" );

 Iterator iteratorOverOrganiser = jobject.entrySet().iterator();

 while (iteratorOverOrganiser.hasNext()) {
 	Map.Entry pair = (Map.Entry) iteratorOverOrganiser.next();
 	System.out.println(pair.getKey() + " = " + pair.getValue());
 	iteratorOverOrganiser.remove();
		
	}*/        
