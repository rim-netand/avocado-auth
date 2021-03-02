package com.netand.avocado.authorization.model.enums;

import lombok.Getter;

@Getter
public enum ErrorResponseTypes {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//@formatter:off
	INVALID_REQUEST             ( "invalid_request",            "Invalid request. Request is missing a required parameter or invalid parameter value."),
	UNAUTHORIZED_CLIENT         ( "unauthorized_client",        "Client is not authorized."),
	ACCESS_DENIED               ( "access_denied",              "The resource owner or authorization server denied."),
	UNSUPPORTED_RESPONSE_TYPE   ( "unsupported_response_type",  "Unsupported response type."),
	INVALID_SCOPE               ( "invalid_scope",              "Scope is invalid, unknown, or malformed."),
	SERVER_ERROR                ( "server_error",               "Server error."),
	TEMPORARILY_UNAVAILABLE     ( "temporarily_unavailable",    ""),
	;
	//@formatter:on
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String name;
	private final String description;

	ErrorResponseTypes( String name, String description ){

		this.name = name;
		this.description = description;
	}
}
