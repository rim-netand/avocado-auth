package com.netand.avocado.authorization.model.credentials;

import lombok.Getter;

@Getter
public class Credentials {

	private String id;
	private String password;

	public Credentials( String id, String password ) {
		this.id = id;
		this.password = password;
	}
}
