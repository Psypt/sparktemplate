package pt.ubiquity.sparktemplate.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import static org.junit.Assert.*;
import pt.ubiquity.sparktemplate.ServerStart;

public class SysUserTest {

	private ServerStart server;
	private Client client;
	
	@Before
	public void setUp(){
		//Starting server for test purposes
		server = new ServerStart();
		server.main(null);
		client = Client.create();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void tearDown(){
		//Stoping server
		server.routes.stop();
	}
	
	@Test
	public void testLogin(){
		WebResource webResource = client.resource("http://localhost:4567/login");

		String input = "{\"user\":\"user\",\"password\":\"1234\"}";

		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
		
		assertEquals(response.getStatus(),200);
		assertNotNull(response.getEntity(String.class));
		
	}
	
}
