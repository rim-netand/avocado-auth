package com.netand.avocado.authorization.redis.entity;

import com.netand.avocado.authorization.model.token.AccessTokenDescription;
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
@RedisHash( "accessTokens" )
public class AccessTokenEntity {

	@Id
	private final String token;

	private final String userId;
	private final String requestIp;
	private final String clientId;
	private final ZonedDateTime createDate;

	@TimeToLive( unit = TimeUnit.SECONDS )
	private final long expiresIn;

	@Builder
	public AccessTokenEntity( String token,
	                          String userId,
	                          String requestIp,
	                          String clientId,
	                          ZonedDateTime createDate,
	                          long expiresIn ) {

		this.token = token;
		this.userId = userId;
		this.requestIp = requestIp;
		this.clientId = clientId;
		this.createDate = createDate;
		this.expiresIn = expiresIn;
	}

	public AccessTokenDescription to() {

		return AccessTokenDescription.builder()
				.token( this.token )
				.userId( this.userId )
				.clientId( this.clientId )
				.requestIp( this.requestIp )
				.createDate( this.createDate )
				.expiresIn( this.expiresIn )
				.build();
	}

	public static AccessTokenEntity of( AccessTokenDescription accessTokenDescription ) {

		return AccessTokenEntity.builder()
				.token( accessTokenDescription.getToken() )
				.userId( accessTokenDescription.getUserId() )
				.requestIp( accessTokenDescription.getRequestIp() )
				.clientId( accessTokenDescription.getClientId() )
				.createDate( accessTokenDescription.getCreateDate() )
				.expiresIn( accessTokenDescription.getExpiresIn() )
				.build();
	}
}

