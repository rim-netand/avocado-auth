package com.netand.avocado.authorization.model.protocol.iam.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationRedirectUriResponse {

	private boolean result;
	private String message;

	private String clientId;
	private String redirectUri;

	@Builder
	public VerificationRedirectUriResponse( boolean result, String message, String clientId, String redirectUri ) {
		this.result = result;
		this.message = message;
		this.clientId = clientId;
		this.redirectUri = redirectUri;
	}
}
