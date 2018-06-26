
package org.bcia.javachain.ca.szca.common.bcca.cesecoreintegration;

import java.util.Map;
import java.util.Properties;

import javax.ejb.Stateless;

import org.cesecore.audit.enums.EventStatus;
import org.cesecore.audit.enums.EventType;
import org.cesecore.audit.enums.ModuleType;
import org.cesecore.audit.enums.ServiceType;
//import org.cesecore.audit.impl.queued.QueuedLoggerSessionLocal;
import org.cesecore.audit.log.AuditRecordStorageException;
import org.cesecore.time.TrustedTime;
import org.springframework.stereotype.Repository;

import org.bcia.javachain.ca.szca.common.cesecore.audit.impl.queued.QueuedLoggerSessionLocal;

/**
 * Mock implementation of QueuedDevice interface to allow the secure audit code imported from
 * CESeCore to stay the same without bundling the queued implementation.
 * 
 * @version $Id: QueuedLoggerMockSessionBean.java 19901 2014-09-30 14:29:38Z anatom $
 */
//@Stateless
@Repository
public class QueuedLoggerMockSessionBean implements QueuedLoggerSessionLocal {

	private static final String UNSUPPORTED = "Unsupported operation. QueuedDevice is not bundled with EJBCA.";

	@Override
	public void log(TrustedTime trustedTime, EventType eventType, EventStatus eventStatus, ModuleType module, ServiceType service, String authToken,
			String customId, String searchDetail1, String searchDetail2, Map<String, Object> additionalDetails, Properties properties) throws AuditRecordStorageException {
		throw new RuntimeException(UNSUPPORTED);
	}
}
