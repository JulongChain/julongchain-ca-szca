/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.bcia.javachain.ca.szca.publicweb.CertAndRequestDumpBean;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.util.QueryResultWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class InspectServiceImpl implements InspectService {
	Logger log = LoggerFactory.getLogger(InspectServiceImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	public CertAndRequestDumpBean inspectReqFile(byte[] reqData) {
		if (reqData == null || reqData.length == 0)
			return null;

		CertAndRequestDumpBean dump = new CertAndRequestDumpBean();
		dump.setBytes(reqData);
		// dump.getDump();
		/**
		 * 
		 * 
		 * <jsp:useBean id="dump" class=
		 * "org.ejbca.ui.web.pub.inspect.CertAndRequestDumpBean" scope="page" />
		 * 
		 * <% // Check that we have a file upload request boolean isMultipart =
		 * ServletFileUpload.isMultipartContent(request); if (isMultipart) { final
		 * DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		 * diskFileItemFactory.setSizeThreshold(9999); ServletFileUpload upload = new
		 * ServletFileUpload(diskFileItemFactory); upload.setSizeMax(10000);
		 * List<FileItem> items = upload.parseRequest(request); for(FileItem item :
		 * items) { if (!item.isFormField()) { InputStream is = item.getInputStream();
		 * byte[] bytes = FileTools.readInputStreamtoBuffer(is); dump.setBytes(bytes); }
		 * } } %>
		 * <h1 class="title"><c:out value="Certificate/CSR dump" /></h1>
		 * <hr/>
		 * <p>
		 * File is of type: <c:out value="${dump.type}"/>
		 * </p>
		 * 
		 * <pre>
		<c:out value="${dump.dump}"></c:out>
		 * </pre>
		 **/

		return dump;
	}

	@Override
	public byte[] getCert(String issuerDN, String subjectDN, String serialNum, String user) {
	 
		boolean useDn = false, useSn = false, useUser = false;
		if (subjectDN != null && !"".equals(subjectDN.trim()))
			useDn = true;

		if (serialNum != null && !"".equals(serialNum.trim()))
			useSn = true;

		if (user != null && !"".equals(user.trim()))
			useUser = true;

		if (!useDn && !useSn && !useUser)
			return null;

		String hql = "SELECT a.base64Cert FROM CertificateData a WHERE a.status=:status AND a.issuerDN=:issuerDN ";
		if (useDn)
			hql += " AND a.subjectDN=:subjectDN";
		if (useSn)
			hql += " AND a.serialNumber=:serialNumber";
		if (useUser)
			hql += " AND a.username=:username";

		hql += " ORDER BY updateTime DESC";

		BigInteger serialNumber=new BigInteger("0");
	 
		if (useSn) {
			try {
				serialNumber = new BigInteger(serialNum, 10);
			} catch (Exception e1) {
				log.warn(String.format("Parse string[%s] to BigInteger error: %s",serialNum,e1.getMessage()));
				
			}
			if(serialNumber.longValue()==0)
			try {
				serialNumber = new BigInteger(serialNum, 16);
			} catch (Exception e2) {
				log.warn(String.format("Parse string[%s] to BigInteger error: %s",serialNum,e2.getMessage()));
			}
		}
		 
		
		final Query query = entityManager.createQuery(hql);
		query.setParameter("status", 20);
		query.setParameter("issuerDN", issuerDN);
		if (useDn)
			query.setParameter("subjectDN", subjectDN);
		if (useSn)
			query.setParameter("serialNumber", serialNumber.toString(10));
		if (useUser)
			query.setParameter("username", user);

		List<String> certList = query.getResultList();
		byte[] cert = null;
		for (String str : certList) {
			cert = str.getBytes();
			break;
		}
		return cert;

	}

	@Override
	public List<String> getCaList() {

		// List<String> caList = new ArrayList<String>();
		// final Query query = entityManager.createQuery("SELECT a FROM CAData a WHERE
		// a.status=:status");
		// query.setParameter("status", 1);
		// // return (CAData) QueryResultWrapper.getSingleResult(query);
		// List<CAData> list = query.getResultList();
		// for (CAData ca : list) {
		// caList.add(ca.getSubjectDN());
		// }
		// return caList;

		final Query query = entityManager.createQuery("SELECT a.subjectDN FROM CAData a WHERE a.status=:status");
		query.setParameter("status", 1);
		return query.getResultList();

	}
}
