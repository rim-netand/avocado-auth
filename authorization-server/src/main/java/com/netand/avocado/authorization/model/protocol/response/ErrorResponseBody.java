package com.netand.avocado.authorization.model.protocol.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.commons.exception.HiwareException;
import com.netand.commons.model.ResultStatus;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonInclude( JsonInclude.Include.NON_EMPTY )
@ToString
public class ErrorResponseBody {

	private String code;

	private String message;

	private ResultStatus status;

	private String cause;

	private String messageFormat;

	private Map< String, Object > attributes;

	@Builder
	public ErrorResponseBody( String code,
	                          String message,
	                          ResultStatus status,
	                          String cause,
	                          String messageFormat,
	                          @Singular Map< String, Object > attributes ) {

		this.code = code;
		this.message = message;
		this.status = status;
		this.cause = cause;
		this.messageFormat = messageFormat;
		this.attributes = attributes;
	}

	public ErrorResponseBody( HiwareException exception ) {

		this.code = exception.getErrorCode();
		this.message = exception.getMessage();
		this.status = exception.getResultStatus();
		this.cause = ( exception.getCause() != null ) ? exception.getCause().getMessage() : null;
		this.messageFormat = exception.getFormat();
		this.attributes = exception.getAttributes();
	}

	public static ErrorResponseBody of( AuthorizationServerException exception ) {

		return ErrorResponseBody.builder()
				.code( exception.getErrorCode() )
				.attributes( exception.getAttributes() )
				.message( exception.getMessage() )
				.messageFormat( exception.getFormat() )
				.build();
	}
}

