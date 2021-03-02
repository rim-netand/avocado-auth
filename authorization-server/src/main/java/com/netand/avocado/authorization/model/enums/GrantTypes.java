package com.netand.avocado.authorization.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Getter
public enum GrantTypes {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//@formatter:off
	AuthorizationCode   ( "authorization_code" ),
	ClientCredentials   ( "client_credentials" ),
	Password            ( "password" ),
	RefreshToken        ( "refresh_token" ),
	;
	//@formatter:on
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private final String code;
	private static final Map< String, GrantTypes > INDEX = new LinkedHashMap<>();

	static {

		Arrays.stream( GrantTypes.values() ).forEach( item -> INDEX.put( item.getCode(), item ) );
	}

	GrantTypes( String code ) {

		this.code = code;
	}

	public static Optional< GrantTypes > findByCode( String code ) {

		return Optional.ofNullable( INDEX.get( code ) );
	}
}
