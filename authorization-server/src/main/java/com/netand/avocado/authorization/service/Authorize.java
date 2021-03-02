package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.DecodeCodec;
import com.netand.avocado.authorization.connector.IamConnector;
import com.netand.avocado.authorization.component.data.AuthorizationCodeDataHandler;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.AuthorizationCodeDescription;
import com.netand.avocado.authorization.model.credentials.ClientDescription;
import com.netand.avocado.authorization.model.credentials.UserDescription;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationClientRequest;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationRedirectUriRequest;
import com.netand.avocado.authorization.model.protocol.iam.request.VerificationUserRequest;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationClientResponse;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationRedirectUriResponse;
import com.netand.avocado.authorization.model.protocol.iam.response.VerificationUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Authorize {

	private final DecodeCodec decodeCodec;
	private final AuthorizationCodeDataHandler authorizationCodeDataHandler;
	private final IamConnector iamConnector;

	public Authorize( @Autowired AuthorizationCodeDataHandler authorizationCodeDataHandler,
	                  @Autowired IamConnector iamConnector ) {

		this.decodeCodec = DecodeCodec.getInstance();
		this.authorizationCodeDataHandler = authorizationCodeDataHandler;
		this.iamConnector = iamConnector;
	}

	public UserDescription authorizeOfUser( String authorization ) throws AuthorizationServerException {

		if ( StringUtils.isEmpty( authorization ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.AuthorizationIsBlank ).build();
		}

		UserDescription userDescription = decodeCodec.decodeOfUser( authorization );

		this.verifyOfUser( userDescription.getId(), userDescription.getPassword() );

		return userDescription;
	}

	public ClientDescription authorizeOfClient( String authorization ) throws AuthorizationServerException {

		if ( StringUtils.isEmpty( authorization ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.AuthorizationIsBlank ).build();
		}

		ClientDescription description = decodeCodec.decodeOfClient( authorization );

		this.verifyOfClient( description.getId(), description.getPassword() );

		return description;
	}

	public void verifyOfAuthorizationCode( String clientId, String code, String redirectUri ) throws AuthorizationServerException {

		if ( StringUtils.isEmpty( code ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.AuthorizationCodeIsBlank ).build();
		}

		// code 검증
		AuthorizationCodeDescription authorizationCodeDescription = authorizationCodeDataHandler.get( code );

		if ( !authorizationCodeDescription.getClientId().equals( clientId ) ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.InvalidClientId )
					.attribute( "clientId", clientId )
					.build();
		}

		// 클라이언트 id, redirectUri 검증
		this.verifyOfClientAndRedirectUri( clientId, redirectUri );
	}

	public Boolean verifyOfClientAndRedirectUri( String clientId, String redirectUri ) throws AuthorizationServerException {

		VerificationRedirectUriResponse response = iamConnector.verifyOfClientAndRedirectUri( VerificationRedirectUriRequest.builder()
				.clientId( clientId )
				.redirectUri( redirectUri )
				.build() );

		return response.isResult();
	}

	private Boolean verifyOfClient( String clientId, String secretKey ) throws AuthorizationServerException {

		VerificationClientResponse response = iamConnector.verifyOfClient( VerificationClientRequest.builder()
				.clientId( clientId )
				.secretKey( secretKey )
				.build() );

		return response.isResult();
	}

	public Boolean verifyOfUser( String userName, String password ) throws AuthorizationServerException {

		VerificationUserResponse response = iamConnector.verifyOfUser( VerificationUserRequest.builder()
				.userName( userName )
				.password( password )
				.build() );

		return response.isResult();
	}

}
