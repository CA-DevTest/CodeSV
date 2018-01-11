package com.ca.codesv.ws.endpoint;

import com.ca.codesv.types.greeting.Greeting;
import com.ca.codesv.types.greeting.ObjectFactory;
import com.ca.codesv.types.greeting.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Webservice endpoint.
 */
@Endpoint
public class HelloWorldEndpoint {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldEndpoint.class);

	/**
	 * Webservice endpoint.
	 *
	 * @param request request
	 *
	 * @return generated greeting
	 */
	@PayloadRoot(namespace = "http://codesv.ca.com/types/greeting", localPart = "person")
	@ResponsePayload
	public Greeting sayHello(@RequestPayload Person request) {
		LOGGER.info("Endpoint received person[firstName={},lastName={}]", request.getFirstName(),
				request.getLastName());

		String greeting = "Hello " + request.getFirstName() + " " + request.getLastName() + "!";

		ObjectFactory factory = new ObjectFactory();
		Greeting response = factory.createGreeting();
		response.setGreeting(greeting);

		LOGGER.info("Endpoint sending greeting='{}'", response.getGreeting());
		return response;
	}
}
