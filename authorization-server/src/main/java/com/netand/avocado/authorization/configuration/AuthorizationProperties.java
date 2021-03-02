package com.netand.avocado.authorization.configuration;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
public class AuthorizationProperties {

	@Value( "${application.version}" )
	private String applicationVersion = "1.0.0";
	@Value( "${application.title}" )
	private String applicationTitle = "Authorization";

	@Value( "${server.port}" )
	private int port = 8080;

	@Value( "${spring.profiles}" )
	private String profiles ="local";

	private RedisProperties redisProperties;

	private RabbitMqProperties rabbitMqProperties;

	private int tokenLength = 40;

	@Getter
	@Setter
	@ToString
	public static class expiredTime {

		private int accessToken = 3600;
		private int refreshToken = 432000;
		private int authorizationCode = 300;
	}

}
