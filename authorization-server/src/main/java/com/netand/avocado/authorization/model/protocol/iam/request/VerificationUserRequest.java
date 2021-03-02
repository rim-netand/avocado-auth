package com.netand.avocado.authorization.model.protocol.iam.request;

import com.netand.avocado.authorization.exception.AuthorizationServerException;
import com.netand.avocado.authorization.exception.AuthorizationServerExceptions;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@ToString
public class VerificationUserRequest {

	private String userName;
	private String password;

	@Builder
	public VerificationUserRequest( String userName, String password ) throws AuthorizationServerException {

		this.userName = userName;
		this.password = password;

		this.validate();
	}

	private void validate() throws AuthorizationServerException {

		if ( StringUtils.isEmpty( userName ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.UserNameIsBlank ).build();
		}

		if ( StringUtils.isEmpty( password ) ) {

			throw AuthorizationServerException.builder().type( AuthorizationServerExceptions.PasswordIsBlank ).build();
		}
	}
}
