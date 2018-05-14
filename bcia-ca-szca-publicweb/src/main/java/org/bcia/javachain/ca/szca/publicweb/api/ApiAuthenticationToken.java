package org.bcia.javachain.ca.szca.publicweb.api;

import java.security.Principal;
import java.util.Set;

import org.cesecore.authentication.AuthenticationFailedException;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.user.AccessUserAspect;
import org.cesecore.authorization.user.matchvalues.AccessMatchValue;

public class ApiAuthenticationToken extends AuthenticationToken{

	public ApiAuthenticationToken(Set<? extends Principal> principals, Set<?> credentials) {
		super(principals, credentials);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean matches(AccessUserAspect accessUser) throws AuthenticationFailedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Object authenticationToken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matchTokenType(String tokenType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccessMatchValue getDefaultMatchValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AccessMatchValue getMatchValueFromDatabaseValue(Integer databaseValue) {
		// TODO Auto-generated method stub
		return null;
	}

}
