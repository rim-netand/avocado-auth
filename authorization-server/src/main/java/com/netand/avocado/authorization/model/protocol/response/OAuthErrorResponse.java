package com.netand.avocado.authorization.model.protocol.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude( JsonInclude.Include.NON_EMPTY )
@JsonPropertyOrder( {
		"error",
		"errorDescription"
} )
public class OAuthErrorResponse implements Response {

	private String error;
	private String errorDescription;

	public OAuthErrorResponse( String error, String errorDescription ) {

		this.error = error;
		this.errorDescription = errorDescription;
	}
}
