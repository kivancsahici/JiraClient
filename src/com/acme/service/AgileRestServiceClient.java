package com.acme.service;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.acme.service.entity.AgileIssue;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AgileRestServiceClient {
	//private static final String UM_SERVICE_URL = "https://dashing-mock-api.herokuapp.com/issues";
	private static final String UM_SERVICE_URL = "http://localhost:3000/issues";
	
	public static List<AgileIssue> callService() {
		ClientConfig clientConfig = new ClientConfig();    
	    clientConfig.property(ClientProperties.READ_TIMEOUT, 10000);
	    clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 5000);
	    
	    ClientBuilder builder = new JerseyClientBuilder();
	    Client client = builder.withConfig(clientConfig).build();
	    client.register(JacksonFeature.class);		    
		try {			
			WebTarget webTarget = client.target(UM_SERVICE_URL);
			Response response = webTarget.request(MediaType.APPLICATION_JSON).get();			
			
			//https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
			try {
			    ObjectMapper mapper = new ObjectMapper();
			    JsonFactory f = new JsonFactory();
			    List<AgileIssue> lstUser = null;
			    JsonParser jp = f.createParser(response.readEntity(String.class));
			    TypeReference<List<AgileIssue>> tRef = new TypeReference<List<AgileIssue>>() {};
			    lstUser = mapper.readValue(jp, tRef);
			    return lstUser;
			    /*
			    for (AgileIssue user : lstUser) {
			        System.out.println(user.getKey());
			        System.out.println(user.getFields().getAssignee().getAvatarUrls().getX4848());
			    }*/

			} catch (JsonGenerationException e) {
			    e.printStackTrace();
			} catch (JsonMappingException e) {
			    e.printStackTrace();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			return null;
		}
		catch(ProcessingException | WebApplicationException e) {
			e.printStackTrace();
			return null;
			//silently ignore, we don't want to block mantra if Mule is down for whatever reason
		}
	}
	/*
	public static void main(String[] args) {
		System.setProperty("https.proxyHost", "localhost");
		System.setProperty("https.proxyPort", "3128");

		callService();
	}*/
}
