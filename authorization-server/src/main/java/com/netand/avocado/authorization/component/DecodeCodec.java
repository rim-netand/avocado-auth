package com.netand.avocado.authorization.component;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.TokenBase;
import com.netand.avocado.authorization.model.credentials.ClientDescription;
import com.netand.avocado.authorization.model.credentials.Credentials;
import com.netand.avocado.authorization.model.credentials.UserDescription;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DecodeCodec {

	private static class Holder {
		private static final DecodeCodec instance = new DecodeCodec();
	}

	public static DecodeCodec getInstance() {

		return DecodeCodec.Holder.instance;
	}

	public TokenBase decodeAccessToken( String authorization ) throws AuthorizationServerException {

		if ( StringUtils.isBlank( authorization ) ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.AuthorizationIsBlank )
					.build();
		}

		String[] credentials = authorization.split( " " );
		String type = credentials[ 0 ];
		String token = credentials[ 1 ];

		if ( !"BEARER".equalsIgnoreCase( type ) ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.NotSupportedAuthType )
					.attribute( "type", type )
					.build();
		}

		return new TokenBase( token, type.toLowerCase() );
	}

	public ClientDescription decodeOfClient( String authorization ) throws AuthorizationServerException {

		Credentials credentials = this.decode( authorization );

		return ClientDescription.builder()
				.id( credentials.getId() )
				.password( credentials.getPassword() )
				.build();
	}

	public UserDescription decodeOfUser( String authorization ) throws AuthorizationServerException {

		Credentials credentials = this.decode( authorization );

		return UserDescription.builder()
				.id( credentials.getId() )
				.password( credentials.getPassword() )
				.build();
	}

	private Credentials decode( String authorization ) throws AuthorizationServerException {

		if ( StringUtils.isBlank( authorization ) ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.AuthorizationIsBlank )
					.build();
		}

		String[] credentials = authorization.split( " " );
		String type = credentials[ 0 ];
		String credential = credentials[ 1 ];

		if ( !"BASIC".equalsIgnoreCase( type ) ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.NotSupportedAuthType )
					.attribute( "type", type )
					.build();
		}

		byte[] apiKeyBytes = Base64.getDecoder().decode( credential );
		String apiKeyString = new String( apiKeyBytes, StandardCharsets.UTF_8 );

		String[] authTokens = apiKeyString.split( ":" );
		String id = authTokens[ 0 ];

		if ( authTokens.length != 2 ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.UnauthorizedClient )
					.attribute( "clientId", id )
					.build();
		}

		String secretKey = authTokens[ 1 ];

		return new Credentials( id, secretKey );
	}
}
