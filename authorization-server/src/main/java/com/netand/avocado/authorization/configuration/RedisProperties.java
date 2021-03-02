package com.netand.avocado.authorization.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.redis")
@Component
@Slf4j
public class RedisProperties {

	private String host;
	private int port;
	private int database;
	private String password;
	private int timeout;

}
