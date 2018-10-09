/*************************************************************************
 *                                                                       *
 *  CESeCore: CE Security Core                                           *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/
package org.bcia.javachain.ca.szca.common.cesecore.certificates.certificateprofile;

import java.util.Map;

import javax.ejb.Local;

import org.cesecore.certificates.certificateprofile.CertificateProfile;

/**
 * @version $Id: CertificateProfileSessionLocal.java 20746 2015-02-25 14:52:01Z mikekushner $
 */
@Local
public interface CertificateProfileSessionLocal extends CertificateProfileSession {

    /**
     * 
     * @return a collection of all existing certificate profiles.
     */
    Map<Integer, CertificateProfile> getAllCertificateProfiles();
    
}
