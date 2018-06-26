
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra;

import java.util.List;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.endentity.EndEntityInformation;

public interface EndEntityAccessSession {

    /**
     * Finds a user by username.
     * 
     * @param admin the administrator performing the action
     * @return EndEntityInformation or null if the user is not found.
     */
    EndEntityInformation findUser(AuthenticationToken admin, String username) throws AuthorizationDeniedException;

    /**
     * Find users by their subject and issuer DN.
     * @return A list of all EndEntityInformations found matching those DNs, or an empty list
     */
     List<EndEntityInformation> findUserBySubjectAndIssuerDN(AuthenticationToken admin, String subjectdn, String issuerdn) throws AuthorizationDeniedException;

    /**
     * Find users by their subject DN.
     * @return A list of all EndEntityInformations matching the given DN, or an empty list
     */
     List<EndEntityInformation> findUserBySubjectDN(AuthenticationToken admin, String subjectdn) throws AuthorizationDeniedException;

    /**
     * Finds a users by subject email.
     * @return List of all matching EndEntityInformation, never null
     */
     List<EndEntityInformation> findUserByEmail(AuthenticationToken admin, String email) throws AuthorizationDeniedException;
    
}
