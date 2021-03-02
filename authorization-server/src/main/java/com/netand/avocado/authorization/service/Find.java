package com.netand.avocado.authorization.service;

import com.netand.avocado.authorization.component.data.AccessTokenDataHandler;
import com.netand.avocado.authorization.model.token.AccessTokenDescription;
import com.netand.commons.model.Range;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

// TODO. Only Administrators
@Service
@Slf4j
public class Find {

	private final AccessTokenDataHandler dataHandler;

	public Find( @Autowired AccessTokenDataHandler dataHandler) {

		this.dataHandler = dataHandler;
	}

	public Page< AccessTokenDescription > findAllAccessTokens( Range range ) {

		return dataHandler.findAll( range );
	}

}
