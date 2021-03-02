package com.netand.commons.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public enum ResultStatus {

	//@formatter:off
	OK					    	( ResultTypes.Success,      200 ),
	Created						( ResultTypes.Success,      201 ),
    Accepted			    	( ResultTypes.Success,      202 ),
    NoContent			    	( ResultTypes.Success,      204 ),
    PartialContent              ( ResultTypes.Success,      206 ),

	BadRequest					( ResultTypes.ClientError,  400 ),
	Unauthorized				( ResultTypes.ClientError,  401 ),
	Forbidden					( ResultTypes.ClientError,  403 ),
	NotFound					( ResultTypes.ClientError,  404 ),
    NotSupportedHttpMethod  	( ResultTypes.ClientError,  405 ),
	RequestTimeout				( ResultTypes.ClientError,  408 ),
	RangeNotSatisfiable         ( ResultTypes.ClientError,  416 ),
	SessionExpired				( ResultTypes.ClientError,  440 ),

	InternalError				( ResultTypes.ServerError,  500 ),
	NotImplemented				( ResultTypes.ServerError,  501 ),
	BadGateway					( ResultTypes.ServerError,  502 ),
	ServiceUnavailable			( ResultTypes.ServerError,  503 ),
	GatewayTimeout				( ResultTypes.ServerError,  504 ),
	NotSupportedProtocol		( ResultTypes.ServerError,  505 ),
	InternalConfigurationError	( ResultTypes.ServerError,  506 ),
	;
	//@formatter:on

	private static final Map< Integer, ResultStatus > HTTP_STATUS_INDEX;

	static {

		HTTP_STATUS_INDEX = new LinkedHashMap<>();

		Arrays.stream( ResultStatus.values() ).forEach( type -> HTTP_STATUS_INDEX.put( type.code(), type ) );
	}

	public static ResultStatus findByHttpStatus( int status ) {

		return HTTP_STATUS_INDEX.get( status );
	}

	private final ResultTypes type;
	private final int code;

	ResultStatus( ResultTypes type, int code ) {

		this.type = type;
		this.code = code;
	}

	public int code() {

		return code;
	}

	public ResultTypes type() {

		return type;
	}
}