
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra;

import java.util.AbstractMap;

import javax.ejb.Local;

import org.cesecore.certificates.endentity.EndEntityInformation;
import org.ejbca.core.model.ra.NotFoundException;
import org.ejbca.util.crypto.SupportedPasswordHashAlgorithm;


@Local
public interface EndEntityAccessSessionLocal extends EndEntityAccessSession {

    /**
     * Finds a user by username, performs no authorization
     * 
     * @return EndEntityInformation or null if the user is not found.
     */
    EndEntityInformation findUser(String username);
    
    /**
     * Using some heuristics and tarot cards, returns which algorithm and method that's been used to hash this user's password.
     * 
     * @param username the user name of the sought user.
     * @return the password and algorithm for the sought user. If algorithm is hashed, so will the password be, otherwise cleartext. Null if user was not found.
     * @throws NotFoundException 
     */
    AbstractMap.SimpleEntry<String, SupportedPasswordHashAlgorithm> getPasswordAndHashAlgorithmForUser(String username) throws NotFoundException;
}
