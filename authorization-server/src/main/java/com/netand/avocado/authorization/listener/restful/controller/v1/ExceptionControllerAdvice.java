package com.netand.avocado.authorization.listener.restful.controller.v1;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.protocol.response.ErrorResponseBody;
import com.netand.commons.exception.HiwareException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

	@ExceptionHandler( HiwareException.class )
	public ResponseEntity< ErrorResponseBody > handleCloudManagerException( HiwareException e ) {

		ErrorResponseBody responseBody = new ErrorResponseBody( e );

		log.warn( e.getMessage(), e );

		return ResponseEntity.status( e.getResultStatus().code() )
				.body( responseBody );
	}

	@ExceptionHandler( {
			HttpMessageNotReadableException.class,
			MissingServletRequestParameterException.class,
			MethodArgumentTypeMismatchException.class,
			MethodArgumentNotValidException.class,
			MaxUploadSizeExceededException.class,
			MissingRequestHeaderException.class } )
	public ResponseEntity< ErrorResponseBody > handleBadRequest( Exception e ) {

		AuthorizationServerException exception = AuthorizationServerException.builder()
				.type( AuthorizationServerExceptions.BadRequest )
				.attribute( "message", e.getMessage() )
				.cause( e )
				.build();

		log.warn( e.getMessage(), e );

		return ResponseEntity.status( exception.getResultStatus().code() )
				.body( new ErrorResponseBody( exception ) );
	}

	@ExceptionHandler( Exception.class )
	public ResponseEntity< ErrorResponseBody > handleInternalServer( Exception e ) {

		AuthorizationServerException exception = AuthorizationServerException.builder()
				.type( AuthorizationServerExceptions.InternalError )
				.attribute( "message", e.getMessage() )
				.cause( e )
				.build();

		log.warn( e.getMessage(), e );

		return ResponseEntity.status( exception.getResultStatus().code() )
				.body( new ErrorResponseBody( exception ) );
	}
}
