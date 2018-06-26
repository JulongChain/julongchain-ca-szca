
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

package org.bcia.javachain.ca.szca.admin.configuration;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authentication.tokens.X509CertificateAuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.ejbca.core.ejb.ra.raadmin.AdminPreferenceSessionLocal;
import org.ejbca.core.model.authorization.AccessRulesConstants;
import org.ejbca.core.model.ra.raadmin.AdminPreference;
import org.ejbca.core.model.util.EjbLocalHelper;

public class AdminPreferenceDataHandler implements java.io.Serializable {

	private static final long serialVersionUID = 2L;

	private AdminPreferenceSessionLocal raadminsession;
	private X509CertificateAuthenticationToken administrator;

	/** Creates a new instance of AdminPreferences */
	public AdminPreferenceDataHandler(X509CertificateAuthenticationToken administrator) {
		raadminsession = new EjbLocalHelper().getRaAdminSession();
		this.administrator = administrator;
	}

	/** Retrieves the admin from the database or null if the admin does not exist. */
	public AdminPreference getAdminPreference(String certificatefingerprint) {
		AdminPreference returnvalue=null;
		try {
			returnvalue = raadminsession.getAdminPreference(certificatefingerprint);
		} catch(Exception e) {
		}
		return returnvalue;
	}

	/** Adds a admin preference to the database */
	public void addAdminPreference(String certificatefingerprint, AdminPreference adminpreference) throws AdminExistsException {
		if (!raadminsession.addAdminPreference(administrator, adminpreference)) {
			throw new AdminExistsException("Admin already exists in the database.");
		}
	}

	/** Changes the admin preference for the given admin. */
	public void changeAdminPreference(String certificatefingerprint, AdminPreference adminpreference) throws AdminDoesntExistException {
		if (!raadminsession.changeAdminPreference(administrator, adminpreference)) {
			throw new AdminDoesntExistException("Admin does not exist in the database.");
		}
	}

	/** Changes the admin preference for the given admin, without performing any logging. */
	public void changeAdminPreferenceNoLog(String certificatefingerprint, AdminPreference adminpreference) throws AdminDoesntExistException {
		if (!raadminsession.changeAdminPreferenceNoLog(administrator, adminpreference)) {
			throw new AdminDoesntExistException("Admin does not exist in the database.");
		}
	}    

	/** Checks if admin preference exists in database. */
	public boolean existsAdminPreference(String certificatefingerprint) {
		return raadminsession.existsAdminPreference(certificatefingerprint);
	}

	/** Returns the default administrator preference. */
	public AdminPreference getDefaultAdminPreference() {
		return raadminsession.getDefaultAdminPreference();  
	}

    /** Saves the default administrator preference. 
     * @param adminpreference The {@link AdminPreference} to save as default.
     * @throws AuthorizationDeniedException if the local {@link AuthenticationToken} wasn't authorized to {@link AccessRulesConstants}.ROLE_ROOT
     */
    public void saveDefaultAdminPreference(AdminPreference adminpreference) throws AuthorizationDeniedException {
        raadminsession.saveDefaultAdminPreference(administrator, adminpreference);
    }
}
