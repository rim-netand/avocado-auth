package com.netand.avocado.authorization.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum ResponseTypes {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//@formatter:off
	Code    ( "code" ),
	Token   ( "token" ),
	;
	//@formatter:on
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String code;
	private static final Map< String, ResponseTypes > INDEX = new LinkedHashMap<>();

	static {

		Arrays.stream( ResponseTypes.values() ).forEach( item -> INDEX.put( item.getCode(), item ) );
	}

	ResponseTypes( String code ) {

		this.code = code;
	}

	public static Optional< ResponseTypes > findByCode( String code ) {

		return Optional.ofNullable( INDEX.get( code ) );
	}
}
