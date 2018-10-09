
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.auth;

import javax.ejb.ObjectNotFoundException;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.ejbca.core.model.ca.AuthLoginException;
import org.ejbca.core.model.ca.AuthStatusException;


public interface EndEntityAuthenticationSession {

    /**
     * Authenticates a user to the user database and returns the user DN.
     *
     * @param username unique username within the instance
     * @param password password for the user
     *
     * @return EndEntityInformation, never returns null
     *
     * @throws ObjectNotFoundException if the user does not exist.
     * @throws AuthStatusException If the users status is incorrect.
     * @throws AuthLoginException If the password is incorrect.
     */
    EndEntityInformation authenticateUser(AuthenticationToken admin, String username, String password)
            throws ObjectNotFoundException, AuthStatusException, AuthLoginException;

    /**
     * Set the status of a user to finished, called when a user has been
     * successfully processed. If possible sets users status to
     * UserData.STATUS_GENERATED, which means that the user cannot be
     * authenticated anymore. NOTE: May not have any effect of user database is
     * remote. User data may contain a counter with nr of requests before used
     * should be set to generated. In this case this counter will be decreased,
     * and if it reaches 0 status will be generated.
     * 
     * @throws ObjectNotFoundException if the user does not exist.
     */
    void finishUser(EndEntityInformation data) throws ObjectNotFoundException;

}
