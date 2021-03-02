package com.netand.avocado.authorization.redis.entity;

import com.netand.avocado.authorization.model.token.AuthorizationCodeDescription;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Getter
@ToString
@RedisHash( "authorize-codes" )
public class AuthorizationCodeEntity {

	@Id
	private String code;
	private String clientId;
	private String scope;
	private ZonedDateTime createDate;

	@TimeToLive( unit = TimeUnit.SECONDS )
	private long expiresIn;

	@Builder
	public AuthorizationCodeEntity( String code, String clientId, String scope, ZonedDateTime createDate, long expiresIn ) {

		this.code = code;
		this.clientId = clientId;
		this.scope = scope;
		this.createDate = createDate;
		this.expiresIn = expiresIn;
	}

	public AuthorizationCodeDescription to () {

		return AuthorizationCodeDescription.builder()
				.code( this.code )
				.clientId( this.clientId )
				.scope( this.scope )
				.createDate( this.createDate )
				.expiresIn( this.expiresIn )
				.build();
	}

	public static AuthorizationCodeEntity of( AuthorizationCodeDescription authorizationCodeDescription ) {

		return AuthorizationCodeEntity.builder()
				.code( authorizationCodeDescription.getCode() )
				.clientId( authorizationCodeDescription.getClientId() )
				.scope( authorizationCodeDescription.getScope() )
				.expiresIn( authorizationCodeDescription.getExpiresIn() )
				.build();
	}
}
