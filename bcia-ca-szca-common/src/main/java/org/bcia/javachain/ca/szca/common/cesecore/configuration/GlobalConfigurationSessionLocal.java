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
package org.bcia.javachain.ca.szca.common.cesecore.configuration;

import java.util.Set;

import javax.ejb.Local;

import org.cesecore.configuration.ConfigurationBase;
import org.cesecore.configuration.GlobalConfigurationData;

/**
 * Local interface for GlobalConfigurationSession.
 * @version $Id: GlobalConfigurationSessionLocal.java 20096 2014-10-27 15:50:39Z jeklund $
 */
@Local
public interface GlobalConfigurationSessionLocal extends GlobalConfigurationSession {
    
    /** @return the found entity instance or null if the entity does not exist */
    GlobalConfigurationData findByConfigurationId(String configurationId);
    
    /** Saves the GlobalConfiguration without logging, should be used only for creating an initial configuration.
    *
    * @param globconf the new Configuration
    */
    void saveConfigurationNoLog(ConfigurationBase conf);

    /** @return all registered configuration IDs. */
    Set<String> getIds();
}
