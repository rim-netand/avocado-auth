package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.data.RefreshTokenDataHandler;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.avocado.authorization.model.token.RefreshTokenDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Refresh {

	private final RefreshTokenDataHandler dataHandler;
	private final Validate validate;
	private final Create create;
	private final Revoke revoke;

	public Refresh( @Autowired RefreshTokenDataHandler dataHandler,
					@Autowired Validate validate,
	                @Autowired Create create,
	                @Autowired Revoke revoke ) {

		this.dataHandler = dataHandler;
		this.validate = validate;
		this.create = create;
		this.revoke = revoke;
	}

	public AccessTokenDescription create( String clientId, String refreshToken ) throws AuthorizationServerException {

		// 검증하고
		if ( !validate.refreshToken( clientId, refreshToken ) ) {
			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.InvalidRefreshToken )
					.attribute( "refreshToken", refreshToken )
					.build();
		}

		// token 정보 가져오기
		RefreshTokenDescription token = dataHandler.get( refreshToken );

		// 제거하기
		revoke.refreshToken( token.getToken() );

		return create.getAccessTokenAndRefreshToken( clientId, token.getUserId() );
	}

}
