
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ra.raadmin;

import javax.ejb.Local;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.ejbca.core.model.ra.raadmin.EndEntityProfile;


@Local
public interface EndEntityProfileSessionLocal extends EndEntityProfileSession {

    /**
     * A method designed to be called at startup time to (possibly) upgrade end
     * entity profiles. This method will read all End Entity Profiles and as a
     * side-effect upgrade them if the version if changed for upgrade. Can have
     * a side-effect of upgrading a profile, therefore the Required transaction
     * setting.
     */
    void initializeAndUpgradeProfiles();

    /** Helper method that checks if an administrator is authorized to all CAs present in the profiles "available CAs"
     * 
     * @param admin administrator to check
     * @param profile the profile to check
     * @throws AuthorizationDeniedException if admin is not authorized to one of the available CAs in the profile
     */
    void authorizedToProfileCas(AuthenticationToken admin, EndEntityProfile profile) throws AuthorizationDeniedException;
    
    /** WARNING: This method must only be used when doing read_only operation on the profile. Otherwise
     * any changes to the profile will affect the profile in the cache and thus affect all other threads.
     * 
     * Use the normal getEndEntityProfile for all edit operations, followed by a changeEndEntityProfile if you want to persist your changes and make 
     * them visible for other threads.
     * 
     * This method exists only for speed purposes since a clone() done by the proper getEndEntityProfile method is slightly expensive.
     * 
     * Finds a end entity profile by id.
     * @return EndEntityProfile (shared in cache) or null if it does not exist
     */
    EndEntityProfile getEndEntityProfileNoClone(int id);

    /** WARNING: This method must only be used when doing read_only operation on the profile. Otherwise
     * any changes to the profile will affect the profile in the cache and thus affect all other threads.
     * 
     * Use the normal getEndEntityProfile for all edit operations, followed by a changeEndEntityProfile if you want to persist your changes and make 
     * them visible for other threads.
     * 
     * This method exists only for speed purposes since a clone() done by the proper getEndEntityProfile method is slightly expensive.
     * 
     * Finds an EndEntityProfile by name.
     * @return EndEntityProfile (shared in cache) or null if it does not exist
     */
    EndEntityProfile getEndEntityProfileNoClone(String profilename);

}
