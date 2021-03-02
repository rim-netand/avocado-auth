package com.netand.avocado.authorization.model.protocol.iam;

public interface IamApiPaths {

	String Prefix = "/iam/v1";

	interface clients {

		String Authorize = Prefix + "/clients";
		String FindByRedirectUri = Prefix + "/clients/{id}?redirectUri={redirectUri}";
	}

	interface users {

		String Authorize = Prefix + "/users";
	}
}
