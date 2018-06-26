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
package org.bcia.javachain.ca.szca.common.cesecore.audit.impl.integrityprotected;

import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cesecore.audit.enums.EventStatus;
import org.cesecore.audit.enums.EventType;
import org.cesecore.audit.enums.ModuleType;
import org.cesecore.audit.enums.ServiceType;
import org.cesecore.audit.impl.integrityprotected.AuditRecordData;
import org.cesecore.audit.impl.integrityprotected.NodeSequenceHolder;
import org.cesecore.audit.log.AuditRecordStorageException;
import org.cesecore.config.CesecoreConfiguration;
import org.cesecore.time.TrustedTime;
import org.cesecore.util.CryptoProviderTools;
import org.springframework.stereotype.Repository;

/**
 * An alternative implementation of the SecurityEventsLogger interface. It handles the creation of a signed log for an event.
 * 
 * This was created to evaluate the performance of using database integrity protection instead of custom code for log singing.
 * 
 * @version $Id: IntegrityProtectedLoggerSessionBean.java 18194 2013-11-21 18:09:12Z jeklund $
 */
//@Stateless
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Repository
public class IntegrityProtectedLoggerSessionBean implements IntegrityProtectedLoggerSessionLocal {

    private static final Logger log = Logger.getLogger(IntegrityProtectedLoggerSessionBean.class);

    @PersistenceContext 
    private EntityManager entityManager;

    @PostConstruct
    public void postConstruct() {
        CryptoProviderTools.installBCProviderIfNotAvailable();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    // Always persist audit log
    public void log(final TrustedTime trustedTime, final EventType eventType, final EventStatus eventStatus, final ModuleType module,
            final ServiceType service, final String authToken, final String customId, final String searchDetail1, final String searchDetail2,
            final Map<String, Object> additionalDetails, final Properties properties) throws AuditRecordStorageException {
        if (log.isTraceEnabled()) {
            log.trace(String.format(">log:%s:%s:%s:%s:%s:%s", eventType, eventStatus, module, service, authToken, additionalDetails));
        }
        try {
            final String nodeId = CesecoreConfiguration.getNodeIdentifier();
            final Long sequenceNumber = NodeSequenceHolder.INSTANCE.getNext(entityManager, nodeId);
            final Long timeStamp = Long.valueOf(trustedTime.getTime().getTime());
            final AuditRecordData auditRecordData = new AuditRecordData(nodeId, sequenceNumber, timeStamp, eventType, eventStatus, authToken,
                    service, module, customId, searchDetail1, searchDetail2, additionalDetails);
            entityManager.persist(auditRecordData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AuditRecordStorageException(e.getMessage(), e);
        } finally {
            if (log.isTraceEnabled()) {
                log.trace("<log");
            }
        }
    }
}
