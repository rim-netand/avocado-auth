package com.netand.avocado.authorization.exception;

import com.netand.commons.exception.HiwareException;
import com.netand.commons.exception.HiwareExceptions;
import com.netand.commons.model.ResultStatus;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum AuthorizationServerExceptions implements HiwareExceptions {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//@formatter:off
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 공통
	BadRequest                          ( ResultStatus.BadRequest,              0,   "Bad request : #{message}" ),
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	NotFound                            ( ResultStatus.NotFound,                0,   "Not Found : #{message}" ),
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	InternalError                       ( ResultStatus.InternalError,           0,   "Internal Error : #{message}" ),

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 인증 관련 (10)
    InvalidContentType                  ( ResultStatus.BadRequest,              1,   "Invalid contentType : #{type}" ),
	InvalidClientId                     ( ResultStatus.BadRequest,              1,   "Invalid clientId : #{clientId}" ),
	InvalidDataType                     ( ResultStatus.BadRequest,              1,   "Invalid data type." ),
	InvalidRefreshToken                 ( ResultStatus.BadRequest,              1,   "Invalid refreshToken : #{refreshToken}" ),
	ClientIdIsBlank                     ( ResultStatus.BadRequest,              1,   "ClientId is blank." ),
	RedirectUriIsBlank                  ( ResultStatus.BadRequest,              1,   "RedirectUri is blank." ),
	AuthorizationCodeIsBlank            ( ResultStatus.BadRequest,              1,   "AuthorizationCode is blank." ),
	UserNameIsBlank                     ( ResultStatus.BadRequest,              1,   "UserName is blank." ),
	PasswordIsBlank                     ( ResultStatus.BadRequest,              1,   "Password is blank." ),
	RefreshTokenIsBlank                 ( ResultStatus.BadRequest,              1,   "RefreshToken is blank." ),
	SecretKeyIsBlank                    ( ResultStatus.BadRequest,              1,   "SecretKey is blank." ),
	AccessTokenIsBlank                  ( ResultStatus.BadRequest,              1,   "AccessToken is blank." ),
	AuthorizationIsBlank                ( ResultStatus.BadRequest,              1,   "Authorization is blank." ),
	ContentTypeIsNull                   ( ResultStatus.BadRequest,              1,   "ContentType is null." ),
	UnauthorizedClient                  ( ResultStatus.Unauthorized,         1001,   "Unauthorized client : #{clientId}" ),
	UnauthorizedUser                    ( ResultStatus.Unauthorized,         1001,   "Unauthorized user : #{userId}" ),
	NotSupportedAuthType                ( ResultStatus.Unauthorized,         1003,   "Not supported auth-type : #{type}" ),

	NotFoundAccessToken                 ( ResultStatus.NotFound,                0,   "Not Found accessToken : #{accessToken}" ),
	NotFoundRefreshToken                ( ResultStatus.NotFound,                0,   "Not Found refreshToken : #{refreshToken}" ),
	NotFoundAuthorizationCode           ( ResultStatus.NotFound,                0,   "Not Found authorizationCode : #{authorizationCode}" ),

	HttpCommunicationError              ( ResultStatus.InternalError,           0,   "Communication error. (url:#{url})" ),
	NoSuchAlgorithm                     ( ResultStatus.InternalError,           0,   "No such algorithm :#{algorithm}" ),

	;
    //@formatter:on
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String prefix;
	private final ResultStatus resultStatus;
	private final int serialNumber;
	private final String format;

	private static final Map< String, AuthorizationServerExceptions > INDEX = new LinkedHashMap<>();

	static {

		Arrays.stream( AuthorizationServerExceptions.values() ).forEach( e -> {

			String code = HiwareException.createErrorCode( e.prefix, e.getResultStatus(), e.getSerialNumber() );

			INDEX.put( code, e );
		} );
	}

	AuthorizationServerExceptions( ResultStatus resultStatus, int serialNumber, String format ) {

		this.prefix = "AUTH";
		this.serialNumber = serialNumber;
		this.format = format;
		this.resultStatus = resultStatus;
	}

	public static Optional< AuthorizationServerExceptions > findByCode( String code ) {

		return Optional.ofNullable( INDEX.get( code ) );
	}
}
