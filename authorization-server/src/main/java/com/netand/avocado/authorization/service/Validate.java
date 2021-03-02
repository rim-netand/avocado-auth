package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.DecodeCodec;
import com.netand.avocado.authorization.component.data.AccessTokenDataHandler;
import com.netand.avocado.authorization.component.data.RefreshTokenDataHandler;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.avocado.authorization.model.token.RefreshTokenDescription;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Validate {

	private final AccessTokenDataHandler accessTokenDataHandler;
	private final RefreshTokenDataHandler refreshTokenDataHandler;
	private final DecodeCodec decodeCodec;

	public Validate( @Autowired AccessTokenDataHandler accessTokenDataHandler,
	                 @Autowired RefreshTokenDataHandler refreshTokenDataHandler ) {

		this.accessTokenDataHandler = accessTokenDataHandler;
		this.refreshTokenDataHandler = refreshTokenDataHandler;
		this.decodeCodec = DecodeCodec.getInstance();
	}

	public AccessTokenDescription accessToken( String token ) throws AuthorizationServerException {

		if ( StringUtils.isEmpty( token ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.AccessTokenIsBlank ).build();
		}

		return accessTokenDataHandler.get( token );
	}

	public Boolean refreshToken( String clientId, String token ) throws AuthorizationServerException {

		if ( StringUtils.isEmpty( clientId ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.ClientIdIsBlank ).build();
		}

		if ( StringUtils.isEmpty( token ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.RefreshTokenIsBlank ).build();
		}

		RefreshTokenDescription refreshTokenDescription = refreshTokenDataHandler.get( token );

		return refreshTokenDescription.getClientId().equals( clientId );
	}
}
