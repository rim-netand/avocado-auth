package com.netand.avocado.authorization.listener.restful.controller.v1;

public interface ApiPaths {

	String Prefix = "/oauth2/v1";

	interface ServiceManagement {

		String GetVersion = Prefix + "/version";
	}

	interface Authentication {

		String Authorize = Prefix + "/authorize";
		String Token = Prefix + "/token";
		String Login = Prefix + "/login";
		String Logout = Prefix + "/logout";
		String Verify = Prefix + "/verify";

		String FindAll = Prefix + "/token";

	}

}
