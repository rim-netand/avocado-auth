package com.netand.avocado.authorization.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class RefreshTokenDescription {

	private String token;
	private String accessToken;
	private ZonedDateTime createDate;
	private String userId;
	private String requestIp;
	private String clientId;
	private long expiresIn;

	@Builder
	public RefreshTokenDescription( String token,
	                                String accessToken,
	                                ZonedDateTime createDate,
	                                String userId,
	                                String requestIp,
	                                String clientId,
	                                long expiresIn ) {

		this.token = token;
		this.accessToken = accessToken;
		this.createDate = createDate;
		this.userId = userId;
		this.requestIp = requestIp;
		this.clientId = clientId;
		this.expiresIn = expiresIn;
	}
}
