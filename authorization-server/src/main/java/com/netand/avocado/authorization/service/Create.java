package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.data.AccessTokenDataHandler;
import com.netand.avocado.authorization.component.data.AuthorizationCodeDataHandler;
import com.netand.avocado.authorization.component.RandomUtil;
import com.netand.avocado.authorization.component.data.RefreshTokenDataHandler;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.avocado.authorization.model.token.AuthorizationCodeDescription;
import com.netand.avocado.authorization.model.token.RefreshTokenDescription;
import com.netand.avocado.authorization.model.enums.MDCKeys;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@Slf4j
public class Create {

	private static final int AUTHORIZATION_CODE_SIZE = 22;
	private static final int TOKEN_EXPIRE_TIME_SEC = 3600;          // 60분
	private static final int REFRESH_EXPIRE_TIME_SEC = 432000;      // 5일
	private static final int AUTHORIZE_CODE_EXPIRE_TIME_SEC = 300;  // 5분

	private final AccessTokenDataHandler accessTokenDataHandler;
	private final AuthorizationCodeDataHandler authorizationCodeDataHandler;
	private final RefreshTokenDataHandler refreshTokenDataHandler;
	private final RandomUtil randomUtil;

	public Create( @Autowired AccessTokenDataHandler accessTokenDataHandler,
	               @Autowired AuthorizationCodeDataHandler authorizationCodeDataHandler,
	               @Autowired RefreshTokenDataHandler refreshTokenDataHandler ) {

		this.accessTokenDataHandler = accessTokenDataHandler;
		this.authorizationCodeDataHandler = authorizationCodeDataHandler;
		this.refreshTokenDataHandler = refreshTokenDataHandler;
		this.randomUtil = RandomUtil.getInstance();
	}

	public AccessTokenDescription getToken( String clientId, String userId ) throws AuthorizationServerException {

		String token = randomUtil.getRandomCode( AUTHORIZATION_CODE_SIZE );

		return accessTokenDataHandler.save( AccessTokenDescription.builder()
				.token( token )
				.expiresIn( TOKEN_EXPIRE_TIME_SEC )
				.clientId( clientId )
				.userId( userId )
				.requestIp( MDC.get( MDCKeys.Request_RemoteAddress ) )
				.createDate( ZonedDateTime.now() )
				.build() );
	}

	public AccessTokenDescription getAccessTokenAndRefreshToken( String clientId, String userId ) throws AuthorizationServerException {

		String token = randomUtil.getRandomCode( AUTHORIZATION_CODE_SIZE );
		String refreshToken = randomUtil.getRandomCode( AUTHORIZATION_CODE_SIZE );

		ZonedDateTime now = ZonedDateTime.now();

		refreshTokenDataHandler.save( RefreshTokenDescription.builder()
				.token( refreshToken )
				.accessToken( token )
				.createDate( now )
				.clientId( clientId )
				.userId( userId )
				.requestIp( MDC.get( MDCKeys.Request_RemoteAddress ) )
				.expiresIn( REFRESH_EXPIRE_TIME_SEC )
				.build() );

		return accessTokenDataHandler.save( AccessTokenDescription.builder()
				.token( token )
				.expiresIn( TOKEN_EXPIRE_TIME_SEC )
				.clientId( clientId )
				.userId( userId )
				.requestIp( MDC.get( MDCKeys.Request_RemoteAddress ) )
				.createDate( now )
				.refreshToken( refreshToken )
				.build() );
	}

	public AuthorizationCodeDescription getAuthorizationCode( String clientId, String scope ) throws AuthorizationServerException {

		String code = randomUtil.getRandomCode( AUTHORIZATION_CODE_SIZE );

		return authorizationCodeDataHandler.save( AuthorizationCodeDescription.builder()
				.clientId( clientId )
				.code( code )
				.scope( scope )
				.createDate( ZonedDateTime.now() )
				.expiresIn( AUTHORIZE_CODE_EXPIRE_TIME_SEC )
				.build() );
	}


}
