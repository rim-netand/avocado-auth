package com.netand.avocado.authorization.configuration;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TimeZone;

@Slf4j
public class AuthorizationConfigurations {

	public static final String SERVICE_NAME = "authorization-server";
	public static final String SERVICE_VERSION = "1.0.0";

	public static final String TIMEZONE;
	public static String HOST_NAME;
	public static String HOST_ADDRESS;
	public static final String ENVIRONMENT_NAME;
	public static String INSTANCE_ID;

	static {

		ENVIRONMENT_NAME = "local";

		TIMEZONE = "Asia/Seoul";
		TimeZone.setDefault( TimeZone.getTimeZone( TIMEZONE ) );

		try {

			InetAddress inetAddress = InetAddress.getLocalHost();

			HOST_ADDRESS = inetAddress.getHostAddress();
			HOST_NAME = inetAddress.getHostName();
			INSTANCE_ID = String.format( "%s::%x", HOST_ADDRESS, System.nanoTime() );

		} catch ( UnknownHostException e ) {

			log.error( "Can't get host name", e );
		}
	}
}
