package com.netand.avocado.authorization.model.protocol.iam.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationClientResponse {

	private boolean result;
	private String message;

	private String clientId;

	@Builder
	public VerificationClientResponse( boolean result, String message, String clientId ) {
		this.result = result;
		this.message = message;
		this.clientId = clientId;
	}
}
