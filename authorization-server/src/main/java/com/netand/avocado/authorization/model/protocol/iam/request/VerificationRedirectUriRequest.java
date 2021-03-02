package com.netand.avocado.authorization.model.protocol.iam.request;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class VerificationRedirectUriRequest {

	private String clientId;
	private String redirectUri;

	@Builder
	public VerificationRedirectUriRequest( String clientId, String redirectUri ) throws AuthorizationServerException {
		this.clientId = clientId;
		this.redirectUri = redirectUri;

		this.validate();
	}

	private void validate() throws AuthorizationServerException {

		if ( StringUtils.isEmpty( this.clientId ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.ClientIdIsBlank ).build();
		}

		if ( StringUtils.isEmpty( this.redirectUri ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.RedirectUriIsBlank ).build();
		}

	}
}
