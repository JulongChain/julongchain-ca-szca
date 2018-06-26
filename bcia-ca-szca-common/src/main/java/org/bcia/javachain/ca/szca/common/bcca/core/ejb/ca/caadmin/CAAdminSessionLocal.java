

package org.bcia.javachain.ca.szca.common.bcca.core.ejb.ca.caadmin;

import javax.ejb.Local;

import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAOfflineException;
import org.cesecore.certificates.certificate.CertificateRevokeException;

 
public interface CAAdminSessionLocal extends CAAdminSession {

	/**
     * A method designed to be called at startup time to speed up the (next)
     * first request to a CA. This method will initialize the CA-cache with all
     * CAs, if they are not already in the cache. Can have a side-effect of
     * upgrading a CA, therefore the Required transaction setting.
     */
    void initializeAndUpgradeCAs();

    /** Method that loads a CA in order to possibly upgrade it, in a separate transaction. 
     * This method is called from initializeAndUpgradeCAs in order to limit the transaction scope of CA upgrades.
     * @param caid The CA to load/upgrade
     * @throws CADoesntExistsException is the CA does not exist
     */
    void initializeAndUpgradeCA(Integer caid) throws CADoesntExistsException;

    /**
     * Help method that checks the CA data config and the certificate profile if
     * the specified action requires approvals and how many
     * 
     * @param action one of CAInfo.REQ_APPROVAL_ constants
     * @param caid of the ca to check
     * @param certprofile of the ca to check
     * @return 0 if no approvals is required otherwise the number of approvals
     */
    int getNumOfApprovalRequired(int action, int caid, int certProfileId);

    /**
     * Used by health-check. Validate that CAs are online and optionally performs
     * a signature test.
     * 
     * @return an error message or an empty String if all are ok.
     */
    String healthCheck();

    /**
     * Regenerates the CMS certificate for a CA.
     */
    void renewAndRevokeCmsCertificate(AuthenticationToken admin, int caid) throws AuthorizationDeniedException, CADoesntExistsException,
            CAOfflineException, CertificateRevokeException;

}
