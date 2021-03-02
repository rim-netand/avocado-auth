package com.netand.avocado.authorization.redis.entity;

import com.netand.avocado.authorization.model.token.RefreshTokenDescription;
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
@RedisHash( "refreshTokens" )
public class RefreshTokenEntity {

	@Id
	private String token;

	private String accessToken;
	private ZonedDateTime createDate;
	private String userId;
	private String requestIp;
	private String clientId;

	@TimeToLive( unit = TimeUnit.SECONDS )
	private long expiresIn;

	@Builder
	public RefreshTokenEntity( String token, String accessToken, ZonedDateTime createDate, String userId, String requestIp, String clientId, long expiresIn ) {

		this.token = token;
		this.accessToken = accessToken;
		this.createDate = createDate;
		this.userId = userId;
		this.requestIp = requestIp;
		this.clientId = clientId;
		this.expiresIn = expiresIn;
	}

	public RefreshTokenDescription to () {

		return RefreshTokenDescription.builder()
				.token( this.token )
				.accessToken( this.accessToken )
				.expiresIn( this.expiresIn )
				.createDate( this.createDate )
				.userId( this.userId )
				.clientId( this.clientId )
				.requestIp( this.requestIp )
				.build();
	}

	public static RefreshTokenEntity of( RefreshTokenDescription refreshTokenDescription ) {

		return RefreshTokenEntity.builder()
				.token( refreshTokenDescription.getToken() )
				.accessToken( refreshTokenDescription.getAccessToken() )
				.createDate( refreshTokenDescription.getCreateDate() )
				.expiresIn( refreshTokenDescription.getExpiresIn() )
				.clientId( refreshTokenDescription.getClientId() )
				.userId( refreshTokenDescription.getUserId() )
				.requestIp( refreshTokenDescription.getRequestIp() )
				.build();
	}
}
