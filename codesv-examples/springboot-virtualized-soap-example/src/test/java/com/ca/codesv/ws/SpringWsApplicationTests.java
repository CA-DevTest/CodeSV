package com.ca.codesv.ws;

import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.forPost;
import static com.ca.codesv.protocols.http.fluent.HttpFluentInterface.okMessage;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import com.ca.codesv.engine.junit4.VirtualServerRule;
import com.ca.codesv.ws.client.HelloWorldClient;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test showing real webservice call and virtualized one.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SpringWsApplicationTests {

	@Autowired
	private HelloWorldClient helloWorldClient;

	@Rule
	public VirtualServerRule serverRule = new VirtualServerRule();

	@Before
	public void setUp() throws Exception {
		Thread.sleep(5000);
	}

	@Test
	public void testRealService() throws InterruptedException {
		assertThat(helloWorldClient.sayHello("John", "Doe")).isEqualTo("Hello John Doe!");
	}

	@Test
	public void testVirtualizedService() {
		String virtualizedResponse = "Greeting from virtualized service, John Doe!";
		String body =
				"<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
						+ "   <SOAP-ENV:Header/>\n"
						+ "   <SOAP-ENV:Body>\n"
						+ "      <ns2:greeting xmlns:ns2=\"http://codesv.ca.com/types/greeting\">\n"
						+ "         <ns2:greeting>" + virtualizedResponse + "</ns2:greeting>\n"
						+ "      </ns2:greeting>\n"
						+ "   </SOAP-ENV:Body>\n"
						+ "</SOAP-ENV:Envelope>";

		forPost("http://localhost:9090/codesv/ws/greeting").doReturn(
				okMessage()
						.withContentType("text/xml; charset=utf-8")
						.withBody(body.getBytes())
		);

		String responseGreeting = helloWorldClient.sayHello("John", "Doe");
		assertEquals(responseGreeting, virtualizedResponse);
	}
}
