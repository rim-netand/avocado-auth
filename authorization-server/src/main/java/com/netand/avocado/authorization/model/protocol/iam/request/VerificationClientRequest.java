package com.netand.avocado.authorization.model.protocol.iam.request;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class VerificationClientRequest {

	private String clientId;
	private String secretKey;

	@Builder
	public VerificationClientRequest( String clientId, String secretKey ) throws AuthorizationServerException {

		this.clientId = clientId;
		this.secretKey = secretKey;

		this.validate();
	}

	private void validate() throws AuthorizationServerException {

		if( StringUtils.isEmpty( this.clientId ) ){

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.ClientIdIsBlank ).build();
		}

		if( StringUtils.isEmpty( this.secretKey ) ){

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.SecretKeyIsBlank ).build();
		}
	}
}
