package com.netand.avocado.authorization.listener.restful.controller.v1;

import com.netand.avocado.authorization.component.DecodeCodec;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.credentials.ClientDescription;
import com.netand.avocado.authorization.model.credentials.UserDescription;
import com.netand.avocado.authorization.model.enums.ErrorResponseTypes;
import com.netand.avocado.authorization.model.enums.GrantTypes;
import com.netand.avocado.authorization.model.enums.ResponseTypes;
import com.netand.avocado.authorization.model.protocol.response.AccessTokenResponseBody;
import com.netand.avocado.authorization.model.protocol.response.OAuthErrorResponse;
import com.netand.avocado.authorization.model.protocol.response.Response;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.avocado.authorization.model.token.AuthorizationCodeDescription;
import com.netand.avocado.authorization.model.token.TokenBase;
import com.netand.avocado.authorization.service.*;
import com.netand.commons.model.Range;
import com.netand.commons.model.ResultStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class AuthorizationController {

	private final Authorize authorize;
	private final Create create;
	private final Validate validate;
	private final Revoke revoke;
	private final Refresh refresh;
	private final Find find;

	public AuthorizationController( @Autowired Authorize authorize,
	                                @Autowired Create create,
	                                @Autowired Validate validate,
	                                @Autowired Revoke revoke,
	                                @Autowired Refresh refresh,
	                                @Autowired Find find ) {

		this.authorize = authorize;
		this.create = create;
		this.validate = validate;
		this.revoke = revoke;
		this.refresh = refresh;
		this.find = find;
	}

	@GetMapping( ApiPaths.Authentication.Authorize )
	public ResponseEntity< ? > authorize( @RequestParam( "response_type" ) ResponseTypes responseType,
	                                      @RequestParam( "client_id" ) String clientId,
	                                      @RequestParam( "redirect_uri" ) String redirectUri,
	                                      @RequestParam( value = "scope", required = false ) String scope,
	                                      @RequestParam( "state" ) String state ) throws AuthorizationServerException {

		String location = "";

		try {

			authorize.verifyOfClientAndRedirectUri( clientId, redirectUri );

		} catch ( AuthorizationServerException e ) {

			location = this.getLocationByResultStatus( e, redirectUri );

			return ResponseEntity.status( HttpStatus.FOUND ).location( URI.create( location ) ).build();
		}

		switch ( responseType ) {

			case Code:

				AuthorizationCodeDescription authorizationCodeDescription = create.getAuthorizationCode( clientId, scope );
				location = String.format( "%s?code=%s", redirectUri, authorizationCodeDescription.getCode() );

				break;
			case Token:

				AccessTokenDescription accessTokenDescription = create.getToken( clientId, "" );
				location = String.format( "%s#access_token=%s&token_type=%s&expires_in=%s", redirectUri, accessTokenDescription.getToken(), accessTokenDescription.getTokenType(), accessTokenDescription.getExpiresIn() );

				break;
		}

		if ( !StringUtils.isEmpty( state ) ) {

			location = String.format( "%s&state=%s", location, state );
		}

		return ResponseEntity.status( HttpStatus.FOUND )
				.location( URI.create( location ) )
				.build();
	}

	@PostMapping( ApiPaths.Authentication.Login )
	public ResponseEntity< Response > login( @RequestHeader( "Authorization" ) String authorization ) throws AuthorizationServerException {

		AccessTokenDescription accessTokenDescription;

		UserDescription userDescription = authorize.authorizeOfUser( authorization );

		accessTokenDescription = create.getAccessTokenAndRefreshToken( "myId", userDescription.getId() );

		return ResponseEntity.ok()
				.header( HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8" )
				.header( HttpHeaders.CACHE_CONTROL, "no-store" )
				.header( HttpHeaders.PRAGMA, "no-cache" )
				.body( new AccessTokenResponseBody( accessTokenDescription ) );
	}

	@PostMapping( ApiPaths.Authentication.Logout )
	public ResponseEntity< ? > logout( @RequestHeader( "Authorization" ) String authorization ) throws AuthorizationServerException {

		DecodeCodec decodeCodec = DecodeCodec.getInstance();
		TokenBase tokenBase = decodeCodec.decodeAccessToken( authorization );

		revoke.accessToken( tokenBase.getToken() );

		return ResponseEntity.ok().build();
	}

	@PostMapping( ApiPaths.Authentication.Verify )
	public ResponseEntity< AccessTokenDescription > verify( @RequestHeader( "Authorization" ) String authorization ) throws AuthorizationServerException {

		DecodeCodec decodeCodec = DecodeCodec.getInstance();
		TokenBase tokenBase = decodeCodec.decodeAccessToken( authorization );

		AccessTokenDescription description = validate.accessToken( tokenBase.getToken() );

		return ResponseEntity.ok().body( description );
	}

	@PostMapping( ApiPaths.Authentication.Token )
	public ResponseEntity< Response > token( @RequestHeader( "Authorization" ) String authorization,
	                                         @RequestParam( "grant_type" ) GrantTypes grantType,
	                                         @RequestParam( value = "code", required = false ) String code,
	                                         @RequestParam( value = "redirect_uri", required = false ) String redirectUri,
	                                         @RequestParam( value = "username", required = false ) String userName,
	                                         @RequestParam( value = "password", required = false ) String password,
	                                         @RequestParam( value = "refresh_token", required = false ) String refreshToken ) {

		ClientDescription clientDescription = null;
		AccessTokenDescription accessTokenDescription = null;

		try {

			clientDescription = authorize.authorizeOfClient( authorization );

			switch ( grantType ) {

				case AuthorizationCode:
					authorize.verifyOfAuthorizationCode( clientDescription.getId(), code, redirectUri );
					accessTokenDescription = create.getAccessTokenAndRefreshToken( clientDescription.getId(), "" );
					break;

				case Password:
					authorize.verifyOfUser( userName, password );
					accessTokenDescription = create.getToken( clientDescription.getId(), userName );
					break;

				case ClientCredentials:
					accessTokenDescription = create.getToken( clientDescription.getId(), "" );
					break;

				case RefreshToken:
					accessTokenDescription = refresh.create( clientDescription.getId(), refreshToken );
					break;
			}

		} catch ( AuthorizationServerException e ) {

			return ResponseEntity.badRequest()
					.header( HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8" )
					.header( HttpHeaders.CACHE_CONTROL, "no-store" )
					.header( HttpHeaders.PRAGMA, "no-cache" )
					.body( new OAuthErrorResponse( ErrorResponseTypes.INVALID_REQUEST.getName(), e.getMessageWithoutErrorCode() ) );
		}

		return ResponseEntity.ok()
				.header( HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8" )
				.header( HttpHeaders.CACHE_CONTROL, "no-store" )
				.header( HttpHeaders.PRAGMA, "no-cache" )
				.body( new AccessTokenResponseBody( accessTokenDescription ) );
	}

	@GetMapping( ApiPaths.Authentication.FindAll )
	public ResponseEntity< List< AccessTokenResponseBody > > findAll( @RequestHeader( value = "Range", required = true ) String rangeHeader ) throws AuthorizationServerException {

		if ( StringUtils.isBlank( rangeHeader ) ) {

			return null;
		}

		Range range = null;

		try {

			range = Range.parse( rangeHeader );

		} catch ( ParseException e ) {

			throw AuthorizationServerException.builder()
					.type( AuthorizationServerExceptions.BadRequest )
					.attribute( "message", e.getMessage() )
					.build();

		}

		Page< AccessTokenDescription > descriptions = find.findAllAccessTokens( range );

		List< AccessTokenResponseBody > responsesBody = new ArrayList<>( descriptions.getSize() );
		descriptions.forEach( c -> responsesBody.add( new AccessTokenResponseBody( c ) ) );

		return ResponseEntity.status( HttpStatus.PARTIAL_CONTENT )
				.header( "Content-Range", String.format( "%s %s-%s/%s", range.getUnit(), range.getFrom(), range.getTo(), descriptions.getTotalElements() ) )
				.body( responsesBody );
	}


	private String getLocationByResultStatus( AuthorizationServerException e, String redirectUri ) {

		if ( e.getResultStatus() == ResultStatus.Unauthorized ) {

			return String.format( "%s?error=%s}", redirectUri, ErrorResponseTypes.UNAUTHORIZED_CLIENT.getName() );

		} else if ( e.getResultStatus() == ResultStatus.BadRequest ) {

			return String.format( "%s?error=%s}", redirectUri, ErrorResponseTypes.INVALID_REQUEST.getName() );

		} else {

			return String.format( "%s?error=%s}", redirectUri, ErrorResponseTypes.SERVER_ERROR.getName() );
		}
	}
}
