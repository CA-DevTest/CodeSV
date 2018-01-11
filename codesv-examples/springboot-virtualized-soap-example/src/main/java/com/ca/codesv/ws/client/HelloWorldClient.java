package com.ca.codesv.ws.client;

import com.ca.codesv.types.greeting.Greeting;
import com.ca.codesv.types.greeting.ObjectFactory;
import com.ca.codesv.types.greeting.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Webservice client.
 */
@Component
public class HelloWorldClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldClient.class);

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	/**
	 * Sending request to the webservice.
	 *
	 * @param firstName first name
	 * @param lastName  last name
	 *
	 * @return response from webservice
	 */
	public String sayHello(String firstName, String lastName) {
		ObjectFactory factory = new ObjectFactory();
		Person person = factory.createPerson();

		person.setFirstName(firstName);
		person.setLastName(lastName);

		LOGGER.info("Client sending person[firstName={},lastName={}]", person.getFirstName(),
				person.getLastName());

		Greeting greeting = (Greeting) webServiceTemplate.marshalSendAndReceive(person);

		LOGGER.info("Client received greeting='{}'", greeting.getGreeting());
		return greeting.getGreeting();
	}
}
