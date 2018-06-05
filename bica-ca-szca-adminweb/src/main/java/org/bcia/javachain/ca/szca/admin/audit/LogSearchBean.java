/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.bcia.javachain.ca.szca.admin.audit;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.util.DatetimeHandle;
import org.cesecore.audit.AuditLogEntry;
import org.cesecore.audit.enums.ModuleTypes;
import org.cesecore.audit.impl.integrityprotected.AuditRecordData;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class LogSearchBean {

	@PersistenceContext // (unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;

	@Autowired
	cn.net.bcia.cesecore.certificates.ca.CaSessionLocal caSession;

	@Transactional
	public List<AuditLogEntry> searchLog(Map<String,String> paramMap,
			AuthenticationToken authenticationToken) {
		// results = AuditorQueryHelper.getResults(authenticationToken,
		// columnNameMap.keySet(), device, getConditions(), sortColumn, sortOrder,
		// startIndex-1, maxResults);

		final StringBuilder queryBuilder = new StringBuilder("SELECT a FROM ")
				.append(AuditRecordData.class.getSimpleName())
				.append(" a where 1=1");
				
		
		String eventTypeValue = null;
		if(paramMap.containsKey("eventTypeValue")) {
			queryBuilder.append(" and a.eventType like :eventTypeValue");
			eventTypeValue = paramMap.get("eventTypeValue");
		}
		String authToken = null;
		if(paramMap.containsKey("authToken")) {
			queryBuilder.append(" and a.authToken like :authToken");
			authToken = paramMap.get("authToken");
		}
		String timeStampBegin = null;
		if(paramMap.containsKey("timeStampBegin")) {
			queryBuilder.append(" and a.timeStamp >= :timeStampBegin");
			timeStampBegin = paramMap.get("timeStampBegin");
		}
		String timeStampEng = null;
		if(paramMap.containsKey("timeStampEng")) {
			queryBuilder.append(" and a.timeStamp <= :timeStampEng");
			timeStampEng = paramMap.get("timeStampEng");
		}
		
		queryBuilder.append("  ORDER by a.timeStamp DESC");
		final String queryString = queryBuilder.toString();
		final Query query = entityManager.createQuery(queryString);
		
		if(eventTypeValue != null) {
			query.setParameter("eventTypeValue", "%" + eventTypeValue + "%" );
		}
		if(authToken != null) {
			query.setParameter("authToken", "%" + authToken + "%" );
		}
		if(timeStampBegin != null) {
			try {
				query.setParameter("timeStampBegin", DatetimeHandle.parseStringToDate(timeStampBegin,
						DatetimeHandle.LONG_TIME_FORMAT_STRING).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(timeStampEng != null) {
			try {
				query.setParameter("timeStampEng", DatetimeHandle.parseStringToDate(timeStampEng,
						DatetimeHandle.LONG_TIME_FORMAT_STRING).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		// query.setFirstResult(firstResult);
		// if (maxResults>0) {
		// query.setMaxResults(maxResults);
		// }
		// if (parameters!=null) {
		// for (int i=0; i<parameters.size(); i++) {
		// query.setParameter(i, parameters.get(i));
		// }
		// }

		// Prune out result pertaining to unauthorized CAs
		Set<Integer> authorizedCaIds = new HashSet<>(caSession.getAuthorizedCaIds(authenticationToken));
		List<AuditLogEntry> qResult = query.getResultList();
		List<AuditLogEntry> resultList = new ArrayList<AuditLogEntry>();
		for (AuditLogEntry auditLogEntry : qResult) {
			// The following values may leak CA Ids.
			if (auditLogEntry.getModuleTypeValue().equals(ModuleTypes.CA)
					|| auditLogEntry.getModuleTypeValue().equals(ModuleTypes.CERTIFICATE)
					|| auditLogEntry.getModuleTypeValue().equals(ModuleTypes.CRL)) {
				if (!StringUtils.isEmpty(auditLogEntry.getCustomId())) {
					if (!authorizedCaIds.contains(Integer.valueOf(auditLogEntry.getCustomId()))) {
						continue;
					}
				}
			}
			resultList.add(auditLogEntry);
		}
		return resultList;

	}
}
