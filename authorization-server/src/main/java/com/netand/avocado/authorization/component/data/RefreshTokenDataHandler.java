package com.netand.avocado.authorization.component.data;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.RefreshTokenDescription;
import com.netand.avocado.authorization.redis.entity.RefreshTokenEntity;
import com.netand.avocado.authorization.redis.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenDataHandler {

	private final RefreshTokenRepository repository;

	public RefreshTokenDataHandler( @Autowired RefreshTokenRepository repository ) {

		this.repository = repository;
	}

	public RefreshTokenDescription save( RefreshTokenDescription refreshTokenDescription ) {

		repository.save( RefreshTokenEntity.of( refreshTokenDescription ) );

		return refreshTokenDescription;
	}

	public RefreshTokenDescription get( String id ) throws AuthorizationServerException {

		RefreshTokenEntity refreshTokenEntity = repository.findById( id ).orElseThrow( () ->
				AuthorizationServerException.builder()
						.type( AuthorizationServerExceptions.NotFoundRefreshToken )
						.attribute( "refreshToken", id )
						.build() );

		return refreshTokenEntity.to();
	}

	public void delete( String id ) throws AuthorizationServerException {

		RefreshTokenEntity entity = repository.findById( id ).orElseThrow( () ->
				AuthorizationServerException.builder()
						.type( AuthorizationServerExceptions.NotFoundRefreshToken )
						.attribute( "refreshToken", id )
						.build() );

		repository.delete( entity );
	}
}
