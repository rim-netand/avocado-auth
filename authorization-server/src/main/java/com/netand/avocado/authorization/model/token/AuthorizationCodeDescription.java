package com.netand.avocado.authorization.model.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class AuthorizationCodeDescription {

	private String code;
	private String clientId;
	private String scope;
	private ZonedDateTime createDate;
	private long expiresIn;

	@Builder
	public AuthorizationCodeDescription( String code, String clientId, String scope, ZonedDateTime createDate, long expiresIn ) {

		this.code = code;
		this.clientId = clientId;
		this.scope = scope;
		this.createDate = createDate;
		this.expiresIn = expiresIn;
	}
}
