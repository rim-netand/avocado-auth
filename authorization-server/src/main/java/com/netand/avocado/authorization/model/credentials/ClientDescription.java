package com.netand.avocado.authorization.model.credentials;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientDescription extends Credentials {

	@Builder
	public ClientDescription( String id, String password ) {

		super( id, password );
	}
}
