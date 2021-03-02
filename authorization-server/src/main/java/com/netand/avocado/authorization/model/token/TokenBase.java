package com.netand.avocado.authorization.model.token;

import lombok.Data;

@Data
public class TokenBase {

	private final String token;
	private final String tokenType;

	public TokenBase( String token, String tokenType ) {
		this.token = token;
		this.tokenType = tokenType;
	}
}
