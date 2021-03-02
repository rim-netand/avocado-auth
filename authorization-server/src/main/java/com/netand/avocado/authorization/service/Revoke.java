package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.data.AccessTokenDataHandler;
import com.netand.avocado.authorization.component.data.RefreshTokenDataHandler;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Revoke {

	private final RefreshTokenDataHandler refreshTokenDataHandler;
	private final AccessTokenDataHandler accessTokenDataHandler;

	public Revoke( @Autowired RefreshTokenDataHandler refreshTokenDataHandler,
	               @Autowired AccessTokenDataHandler accessTokenDataHandler ) {

		this.refreshTokenDataHandler = refreshTokenDataHandler;
		this.accessTokenDataHandler = accessTokenDataHandler;
	}

	public void accessToken( String token ) throws AuthorizationServerException {

		accessTokenDataHandler.delete( token );

		log.info( "Revoke accessToken : {}", token );
	}

	public void refreshToken( String token ) throws AuthorizationServerException {

		refreshTokenDataHandler.delete( token );

		log.info( "Revoke refreshToken : {}", token );
	}
}
