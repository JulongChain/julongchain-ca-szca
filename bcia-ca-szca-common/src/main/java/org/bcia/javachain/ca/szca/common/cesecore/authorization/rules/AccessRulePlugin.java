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
package org.bcia.javachain.ca.szca.common.cesecore.authorization.rules;

import java.util.List;


/**
 * Marker interface to allow access rules to be plugged in. 
 * 
 * @version $Id: AccessRulePlugin.java 19596 2014-08-27 08:13:49Z mikekushner $
 *
 */
public interface AccessRulePlugin {

    List<String> getRules();
    
    String getCategory();
    
}
