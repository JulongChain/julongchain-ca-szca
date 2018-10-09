
package org.bcia.javachain.ca.szca.common.bcca.core.ejb.authorization;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import org.cesecore.authentication.tokens.AuthenticationToken;

/**
 * @version $Id: ComplexAccessControlSessionLocal.java 22869 2016-02-25 15:30:11Z samuellb $
 * 
 */
@Local
public interface ComplexAccessControlSessionLocal extends ComplexAccessControlSession {

    public static final String SUPERADMIN_ROLE = "Super Administrator Role";
    public static final String TEMPORARY_SUPERADMIN_ROLE = "Temporary Super Administrator Group";
    
    /**
     * Creates a super administrator role and a default CLI user. A role and default CLI user is needed in order
     * to do operations with the CLI (command line interface).  
     */
    void createSuperAdministrator();
    
    /** 
     * initializes the authorization module, if no roles or CAs exists in the system. This is done during startup 
     * so that we can use the CLI after this to install and configure the system further.
     * This method only performs any operation of RoleData and CAData both have no entries. 
     * 
     * @return true if initialization was done. This also means that this is a fresh installation of EJBCA.
     */
    boolean initializeAuthorizationModule();
    
    /**
     * Method to check if an end entity profile exists in any end entity profile
     * rules. Used to avoid desynchronization of profilerules.
     * 
     * @param profileid the profile id to search for.
     * @return true if profile exists in any of the accessrules.
     */
    boolean existsEndEntityProfileInRules(int profileid);
    
    /**
     * Method used to collect all access rules, but will perform authorization checks on CAs, and EEPs and CPs dependent on CAs. Is used by
     * the advanced roles administration page in the UI to display all rules without leaking information about CA's.
     * 
     * @param admin is the administrator calling the method.
     * @param availableCaIds A Collection<Integer> of all CA IDs
     * @param enableendentityprofilelimitations Include End Entity Profile access rules
     * @param usehardtokenissuing Include Hard Token access rules
     * @param usekeyrecovery Include Key Recovery access rules
     * @param authorizedEndEntityProfileIds A Collection<Integer> of all authorized End Entity Profile IDs
     * @param authorizedUserDataSourceIds A Collection<Integer> of all authorized user data sources IDs
     * @param 
     * @return a LinkedHashMap of strings representing the available access rules, keyed by category
     */
    Map<String, Set<String>> getAllAccessRulesRedactUnauthorizedCas(AuthenticationToken authenticationToken, boolean enableendentityprofilelimitations,
                                                                    boolean usehardtokenissuing, boolean usekeyrecovery, Collection<Integer> authorizedEndEntityProfileIds,
                                                                    Collection<Integer> authorizedUserDataSourceIds, String[] customaccessrules);
   
}
