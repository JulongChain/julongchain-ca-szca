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
package org.bcia.javachain.ca.szca.common.cesecore.authorization.cache;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.cesecore.authorization.cache.AccessTreeUpdateData;
import org.cesecore.certificates.crl.CRLData;
import org.cesecore.config.CesecoreConfiguration;
import org.cesecore.internal.InternalResources;
import org.cesecore.jndi.JndiConstants;
import org.cesecore.util.QueryResultWrapper;
import org.springframework.stereotype.Repository;

/**
 * Bean to handle the AuthorizationTreeUpdateData entity.
 * 
 * 
 * @version $Id: AccessTreeUpdateSessionBean.java 20608 2015-02-02 11:43:15Z
 *          jeklund $
 */
// @Stateless(mappedName = JndiConstants.APP_JNDI_PREFIX +
// "AccessTreeUpdateSessionLocal")
// @TransactionAttribute(TransactionAttributeType.REQUIRED)
@Repository
public class AccessTreeUpdateSessionBean implements AccessTreeUpdateSessionLocal {

	private static final Logger LOG = Logger.getLogger(AccessTreeUpdateSessionBean.class);

	@PersistenceContext // (unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;

	//@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS) // We don't modify
																// the database
																// in this call
	public int getAccessTreeUpdateNumber_bak() {
		try {
			final AccessTreeUpdateData accessTreeUpdateData = entityManager.find(AccessTreeUpdateData.class,
					AccessTreeUpdateData.AUTHORIZATIONTREEUPDATEDATA);

			if (accessTreeUpdateData == null) {
				// No update has yet been persisted, so we return the default
				// value
				return AccessTreeUpdateData.DEFAULTACCESSTREEUPDATENUMBER;
			}
			return accessTreeUpdateData.getAccessTreeUpdateNumber();
		} catch (Exception e) {
			e.printStackTrace();
			// return -1;
			return AccessTreeUpdateData.DEFAULTACCESSTREEUPDATENUMBER;

		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public int getAccessTreeUpdateNumber() {
		try {
			// final AccessTreeUpdateData accessTreeUpdateData =
			// entityManager.find(AccessTreeUpdateData.class,
			// AccessTreeUpdateData.AUTHORIZATIONTREEUPDATEDATA);
			final Query query = entityManager.createQuery("SELECT a FROM AccessTreeUpdateData a ");
			// query.setParameter("issuerDN", issuerDN);
			// query.setParameter("crlNumber", crlNumber);
			AccessTreeUpdateData accessTreeUpdateData = (AccessTreeUpdateData) QueryResultWrapper
					.getSingleResult(query);

			if (accessTreeUpdateData == null) {
				// No update has yet been persisted, so we return the default
				// value
				return AccessTreeUpdateData.DEFAULTACCESSTREEUPDATENUMBER;
			}
			return accessTreeUpdateData.getAccessTreeUpdateNumber();
		} catch (Exception e) {
			e.printStackTrace();
			// return -1;
			 return AccessTreeUpdateData.DEFAULTACCESSTREEUPDATENUMBER;

			// final Query query = entityManager.createQuery("SELECT a FROM
			// CRLData a WHERE a.issuerDN=:issuerDN AND
			// a.crlNumber=:crlNumber");

		}
	}

	@Override
	public void signalForAccessTreeUpdate() {
		AccessTreeUpdateData accessTreeUpdateData = entityManager.find(AccessTreeUpdateData.class,
				AccessTreeUpdateData.AUTHORIZATIONTREEUPDATEDATA);
		if (accessTreeUpdateData == null) {
			// We need to create the database row and incremented the value
			// directly since this is an call to update it
			try {
				accessTreeUpdateData = new AccessTreeUpdateData();
				accessTreeUpdateData.setAccessTreeUpdateNumber(AccessTreeUpdateData.DEFAULTACCESSTREEUPDATENUMBER + 1);
				entityManager.persist(accessTreeUpdateData);
			} catch (Exception e) {
				LOG.error(InternalResources.getInstance().getLocalizedMessage("authorization.errorcreateauthtree"), e);
				throw new EJBException(e);
			}
		} else {
			accessTreeUpdateData.setAccessTreeUpdateNumber(accessTreeUpdateData.getAccessTreeUpdateNumber() + 1);
		}
	}
}
