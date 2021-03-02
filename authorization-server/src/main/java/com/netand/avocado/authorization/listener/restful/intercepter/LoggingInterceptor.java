package com.netand.avocado.authorization.listener.restful.intercepter;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.enums.MDCKeys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception ) {

		MDC.remove( MDCKeys.Request_Id );
		MDC.remove( MDCKeys.Request_Time );
		MDC.remove( MDCKeys.Request_RemoteAddress );
		MDC.remove( MDCKeys.Request_RemotePort );
	}

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView ) {

		MDC.put( MDCKeys.Response_Status, String.valueOf( response.getStatus() ) );

		log.info( "SEND [X] --({})--> [{}:{}] - {} {} ==> status={}",
				request.getProtocol(), request.getRemoteAddr(), request.getRemotePort(),
				request.getMethod(), request.getRequestURI(), response.getStatus() );
	}

	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler ) throws AuthorizationServerException {

		String requestId = UUID.randomUUID().toString().toUpperCase();
		ZonedDateTime requestTime = ZonedDateTime.now();

		MDC.put( MDCKeys.Request_Id, requestId );
		MDC.put( MDCKeys.Request_Time, DateTimeFormatter.ISO_OFFSET_DATE_TIME.format( requestTime ) );
		MDC.put( MDCKeys.Request_RemoteAddress, request.getRemoteAddr() );
		MDC.put( MDCKeys.Request_RemotePort, String.valueOf( request.getRemotePort() ) );

		log.info( "RECV [{}:{}] --({})--> [X] - {} {}",
				request.getRemoteAddr(), request.getRemotePort(), request.getProtocol(),
				request.getMethod(), request.getRequestURI() );

		response.addHeader( "Request-Id", MDC.get( MDCKeys.Request_Id ) );
		response.addHeader( "Request-Time", MDC.get( MDCKeys.Request_Time ) );

		return this.validate( request );
	}

	private boolean validate( HttpServletRequest request ) throws AuthorizationServerException {

		String contentType = request.getHeader( "Content-Type" );

		if( contentType == null ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.ContentTypeIsNull ).build();
		}

		if ( !contentType.toLowerCase().equals( "application/x-www-form-urlencoded" ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.InvalidContentType )
					.attribute( "type", contentType )
					.build();
		}

		return true;
	}

	@PostConstruct
	public void start() {

		log.info( "{} started.", this.getClass().getSimpleName() );
	}

	@PreDestroy
	public void shutdown() {

		log.info( "{} stopped.", this.getClass().getSimpleName() );
	}
}