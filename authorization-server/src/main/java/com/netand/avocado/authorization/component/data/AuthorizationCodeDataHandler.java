package com.netand.avocado.authorization.component.data;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.AuthorizationCodeDescription;
import com.netand.avocado.authorization.redis.entity.AuthorizationCodeEntity;
import com.netand.avocado.authorization.redis.repository.AuthorizationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationCodeDataHandler {

	private final AuthorizationCodeRepository repository;

	public AuthorizationCodeDataHandler( @Autowired AuthorizationCodeRepository repository ) {

		this.repository = repository;
	}

	public AuthorizationCodeDescription save( AuthorizationCodeDescription authorizationCodeDescription ) {

		repository.save( AuthorizationCodeEntity.of( authorizationCodeDescription ) );

		return authorizationCodeDescription;
	}

	public AuthorizationCodeDescription get( String id ) throws AuthorizationServerException {

		AuthorizationCodeEntity entity = repository.findById( id ).orElseThrow( () ->
				AuthorizationServerException.builder()
						.type( AuthorizationServerExceptions.NotFoundAuthorizationCode )
						.attribute( "authorizationCode", id )
		.build());

		return entity.to();
	}

}
