package com.netand.avocado.authorization.component.data;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.avocado.authorization.redis.entity.AccessTokenEntity;
import com.netand.avocado.authorization.redis.repository.AccessTokenRepository;
import com.netand.commons.model.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AccessTokenDataHandler {

	private final AccessTokenRepository repository;

	public AccessTokenDataHandler( @Autowired AccessTokenRepository repository ) {

		this.repository = repository;
	}

	public AccessTokenDescription save( AccessTokenDescription accessTokenDescription ) {

		repository.save( AccessTokenEntity.of( accessTokenDescription ) );

		return accessTokenDescription;
	}

	public AccessTokenDescription get( String id ) throws AuthorizationServerException {

		AccessTokenEntity entity = repository.findById( id ).orElseThrow( () ->
				AuthorizationServerException.builder()
						.type( AuthorizationServerExceptions.NotFoundAccessToken )
						.attribute( "accessToken", id )
						.build() );

		return entity.to();
	}

	public Page< AccessTokenDescription > findAll( Range range ) {

		int offset = ( int ) range.getOffset();
		int limit = ( int ) range.getLimit();

		Page< AccessTokenEntity > entities = repository.findAll( PageRequest.of( offset, limit ) );
		List< AccessTokenDescription > descriptions = new ArrayList<>();

		if ( null != entities && entities.hasContent() ) {
			for ( AccessTokenEntity entity : entities.getContent() ) {

				if( null == entity ) continue;
				descriptions.add( entity.to() );
			}
		}

		return new PageImpl<>( descriptions, PageRequest.of( offset, limit ), entities.getTotalElements() );
	}

	public void delete( String id ) throws AuthorizationServerException {

		AccessTokenEntity entity = repository.findById( id ).orElseThrow( () ->
				AuthorizationServerException.builder()
						.type( AuthorizationServerExceptions.NotFoundAccessToken )
						.attribute( "accessToken", id )
						.build() );

		repository.delete( entity );
	}
}
