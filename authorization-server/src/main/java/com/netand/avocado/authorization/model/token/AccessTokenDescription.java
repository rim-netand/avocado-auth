package com.netand.avocado.authorization.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class AccessTokenDescription extends TokenBase {

	private final String requestIp;
	private final String clientId;
	private final String userId;
	private final ZonedDateTime createDate;
	private final String refreshToken;
	private final long expiresIn;

	@Builder
	public AccessTokenDescription( String token,
	                               String userId,
	                               String requestIp,
	                               String clientId,
	                               ZonedDateTime createDate,
	                               String refreshToken,
	                               long expiresIn ) {

		super( token, "Bearer" );

		this.userId = userId;
		this.requestIp = requestIp;
		this.clientId = clientId;
		this.createDate = createDate;
		this.expiresIn = expiresIn;
		this.refreshToken = refreshToken;
	}
}
