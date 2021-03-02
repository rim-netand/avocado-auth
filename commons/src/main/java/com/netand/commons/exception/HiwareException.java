package com.netand.commons.exception;

import com.netand.commons.model.ResultStatus;
import com.netand.commons.model.ResultTypes;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString
public class HiwareException extends Exception {

	protected final ResultStatus resultStatus;
	protected final String prefix;
	protected final int serialNumber;
	protected final String format;
	protected final Map< String, Object > attributes = new LinkedHashMap<>();

	public static final int MIN_SERIAL_NUMBER = 0;
	public static final int MAX_SERIAL_NUMBER = 9999;

	public HiwareException( String prefix,
	                        int serialNumber,
	                        String format,
	                        Map< String, Object > attributes,
	                        ResultStatus resultStatus,
	                        Throwable cause,
	                        boolean enableSuppression,
	                        boolean writableStackTrace ) {

		super( format, cause, enableSuppression, writableStackTrace );

		this.prefix = prefix;

		if ( serialNumber < MIN_SERIAL_NUMBER || serialNumber > MAX_SERIAL_NUMBER ) {

			String message = String.format( "The serial number is out of range. (input : %s, range : %s ~ %s)", serialNumber, MIN_SERIAL_NUMBER, MAX_SERIAL_NUMBER );
			throw new IllegalArgumentException( message );
		}

		this.serialNumber = serialNumber;
		this.format = format;

		if ( resultStatus.type() == ResultTypes.Success ) {

			throw new IllegalArgumentException( "The result status is not error status. (input : " + resultStatus.type() + ")" );
		}

		this.resultStatus = resultStatus;

		if ( attributes != null ) this.attributes.putAll( attributes );
	}

	private String createMessage() {

		String message = format;

		for ( Map.Entry< String, Object > entry : attributes.entrySet() ) {

			String key = String.format( "#{%s}", entry.getKey() );
			String replace = ( entry.getValue() == null ) ? "null" : entry.getValue().toString();

			message = message.replace( key, replace );
		}

		return message;
	}

	public static String createErrorCode( String prefix, ResultStatus resultStatus, int serialNumber ) {

		return String.format( "%s::%03d-%04d", prefix, resultStatus.code(), serialNumber );
	}

	public String getMessage() {

		return String.format( "[%s] %s", this.getErrorCode(), this.createMessage() );
	}

	public String getErrorCode() {

		return HiwareException.createErrorCode( this.prefix, this.resultStatus, this.serialNumber );
	}

	public String getMessageWithoutErrorCode() {

		return String.format( "%s", this.createMessage() );
	}
}

