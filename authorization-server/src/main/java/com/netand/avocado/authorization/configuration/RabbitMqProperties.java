package com.netand.avocado.authorization.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Component
@Slf4j
public class RabbitMqProperties {

	private String host;
	private int port;
	private String username;
	private String password;
}
