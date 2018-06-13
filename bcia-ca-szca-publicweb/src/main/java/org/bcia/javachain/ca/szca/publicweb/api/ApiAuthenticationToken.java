/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
