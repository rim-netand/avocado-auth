package com.netand.avocado.authorization.model.protocol.iam.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VerificationUserResponse {

	private boolean result;
	private String message;

	private String userName;

	@Builder
	public VerificationUserResponse( boolean result, String message, String userName ) {

		this.result = result;
		this.message = message;
		this.userName = userName;
	}
}
