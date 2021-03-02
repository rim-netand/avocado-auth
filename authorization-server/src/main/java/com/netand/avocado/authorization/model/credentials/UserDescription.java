package com.netand.avocado.authorization.model.credentials;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDescription extends Credentials {


	@Builder
	public UserDescription( String id, String password ) {

		super( id, password );
	}
}
