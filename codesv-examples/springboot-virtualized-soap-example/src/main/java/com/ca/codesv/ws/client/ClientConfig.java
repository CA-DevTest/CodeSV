package com.ca.codesv.ws.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * Client configuration.
 */
@Configuration
public class ClientConfig {

	@Value("${client.default-uri}")
	private String defaultUri;

	/**
	 * Setups marshaller bean.
	 *
	 * @return jaxb marshaller
	 */
	@Bean
	Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setContextPath("com.ca.codesv.types.greeting");

		return jaxb2Marshaller;
	}

	/**
	 * Setups webservice template.
	 *
	 * @return webservice template
	 */
	@Bean
	public WebServiceTemplate webServiceTemplate() {
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.setMarshaller(jaxb2Marshaller());
		webServiceTemplate.setUnmarshaller(jaxb2Marshaller());
		webServiceTemplate.setDefaultUri(defaultUri);

		return webServiceTemplate;
	}
}
