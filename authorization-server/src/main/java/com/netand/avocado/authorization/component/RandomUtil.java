package com.netand.avocado.authorization.component;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
public class RandomUtil {

	private static final String ALGORITHM = "SHA1PRNG";
	private byte[] randomBytes = new byte[ 64 ];

	private static class Holder {
		private static final RandomUtil instance = new RandomUtil();
	}

	public static RandomUtil getInstance() {

		return RandomUtil.Holder.instance;
	}

	public String getRandomCode( int size ) throws AuthorizationServerException {

		try {

			SecureRandom secureRandom = SecureRandom.getInstance( ALGORITHM );
			secureRandom.nextBytes( randomBytes );

		} catch ( NoSuchAlgorithmException e ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.NoSuchAlgorithm )
					.attribute( "algorithm", ALGORITHM )
					.cause( e )
					.build();
		}

		Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		return encoder.encodeToString( randomBytes ).substring( 0, size );
	}

	public static void main( String[] args ) {

		try {
			RandomUtil util = new RandomUtil();

			for ( int i = 0; i <= 100; ++i ) {

				log.info( "random : {}", util.getRandomCode( 80 ) );

			}
		} catch( AuthorizationServerException e ){

			log.error( e.getMessage(), e);
		}
	}
}
