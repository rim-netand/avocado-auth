package com.netand.avocado.authorization.exception;

import com.netand.commons.exception.HiwareException;

import lombok.*;

import java.util.Map;

@Getter
@ToString
public class AuthorizationServerException extends HiwareException {

	private AuthorizationServerExceptions type;

	@Builder
	public AuthorizationServerException( AuthorizationServerExceptions type,
	                                     @Singular Map< String, Object > attributes,
	                                     Throwable cause,
	                                     boolean enableSuppression,
	                                     boolean writableStackTrace ) {

		super( type.getPrefix(), type.getSerialNumber(), type.getFormat(), attributes, type.getResultStatus(), cause, enableSuppression, writableStackTrace );
		this.type = type;
	}
}

