package com.netand.avocado.authorization.listener.restful.configuration;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.SessionCookieConfig;

@Configuration
public class WebConfiguration {

	@Bean
	public ServletContextInitializer servletContextInitializer() {

		return servletContext -> {

			SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
			sessionCookieConfig.setName( "AVOCADO_AUTH_SESSION_ID" );
			sessionCookieConfig.setHttpOnly( true );
		};
	}
}
