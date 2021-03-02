package com.netand.avocado.authorization.model.protocol.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;

import java.time.ZonedDateTime;

@JsonInclude( JsonInclude.Include.NON_EMPTY )
@JsonPropertyOrder( {
		"access_token",
		"token_type",
		"expires_in",
		"refresh_token",
		"user_id",
		"create_date",
		"scope"
} )
public class AccessTokenResponseBody implements Response {

	private final AccessTokenDescription description;

	public AccessTokenResponseBody( AccessTokenDescription description ) {

		this.description = description;
	}

	@JsonProperty( "access_token" )
	public String getToken(){

		return description.getToken();
	}

	@JsonProperty( "token_type" )
	public String getTokenType(){

		return description.getTokenType();
	}

	@JsonProperty( "expires_in" )
	public long getExpiresIn() {

		return description.getExpiresIn();
	}

	@JsonProperty( "refresh_token" )
	public String getRefreshToken() {

		return description.getRefreshToken();
	}

	@JsonProperty( "user_id" )
	public String getUserId() {

		return description.getUserId();
	}

	@JsonProperty( "create_date" )
	public ZonedDateTime getCreateDate() {

		return description.getCreateDate();
	}
}
