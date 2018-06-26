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

package org.bcia.javachain.ca.szca.publicweb.service;

import java.math.BigInteger;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;
import org.bcia.javachain.ca.szca.publicweb.CertificateGuiInfo;
import org.bcia.javachain.ca.szca.publicweb.RetrieveCaInfo;
import org.bouncycastle.util.encoders.DecoderException;
import org.bouncycastle.util.encoders.Hex;
import org.cesecore.certificates.ca.CADoesntExistsException;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.certificate.CertificateStatus;
import org.cesecore.certificates.crl.RevokedCertInfo;
import org.ejbca.core.model.util.EjbLocalHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RetrieveServiceImpl implements RetrieveService {
	Logger log = LoggerFactory.getLogger(RetrieveServiceImpl.class);
	
	@Autowired
	cn.net.bcia.cesecore.certificates.certificate.CertificateStoreSessionLocal mStoreSession;
	// private EjbLocalHelper ejb = new EjbLocalHelper();
	@Autowired 
	cn.net.bcia.bcca.core.ejb.ca.sign.SignSession mSignSession  ;
	@Autowired 
	cn.net.bcia.cesecore.certificates.ca.CaSessionLocal caSession ;
	// private CertificateStoreSessionLocal mStoreSession =
	// ejb.getCertificateStoreSession();
	
	
	public RevokedCertInfo checkStatus(String issuerDN, String serialNumber) {
		if ((issuerDN == null || "".equals(issuerDN)) && (serialNumber == null || "".equals(serialNumber)))
			return null;
		//EjbLocalHelper ejb = new EjbLocalHelper();
		// private EjbLocalHelper ejb = new EjbLocalHelper();
		// private SignSession mSignSession = ejb.getSignSession();
		// private CaSessionLocal caSession = ejb.getCaSession();
		// private CertificateStoreSessionLocal mStoreSession =
		// ejb.getCertificateStoreSession();

		//CertificateStoreSessionLocal mStoreSession = ejb.getCertificateStoreSession();

		RevokedCertInfo result = new RevokedCertInfo();

		serialNumber = ("0000000000000000" + serialNumber).substring(serialNumber.length());
		// Pad with zeroes up to 16 chars
		if (log.isTraceEnabled()) {
			log.trace(">lookupRevokedInfo(" + issuerDN + ", " + serialNumber + ", " + result + ")");
		}
		// if (result == null) {
		// return result; // There's nothing we can do here.
		// }
		try {
			BigInteger serialBignum = new BigInteger(Hex.decode(StringUtils.trimToEmpty(serialNumber)));
			CertificateStatus info = mStoreSession.getStatus(StringUtils.trimToEmpty(issuerDN), serialBignum);
			if (info.equals(CertificateStatus.NOT_AVAILABLE)) {
				result.setRevocationDate(null);
				result.setUserCertificate(null);
			} else {
				result.setReason(info.revocationReason);
				result.setRevocationDate(info.revocationDate);
				result.setUserCertificate(serialBignum);
			}
		} catch (NumberFormatException e) {
			log.info("Invalid serial number entered (NumberFormatException): " + serialNumber + ": " + e.getMessage());
		} catch (StringIndexOutOfBoundsException e) {
			log.info("Invalid serial number entered (StringIndexOutOfBoundsException): " + serialNumber + ": "
					+ e.getMessage());
		} catch (DecoderException e) {
			log.info("Invalid serial number entered (DecoderException): " + serialNumber + ": " + e.getMessage());
		}
		return result;
	}

	public Collection<RetrieveCaInfo> caCerts() {
		EjbLocalHelper ejb = new EjbLocalHelper();
		// log.trace(">getAvailableCAs()");
//		CaSessionLocal caSession = ejb.getCaSession();
//		SignSession mSignSession = ejb.getSignSession();
		Collection<Integer> caIdList = caSession.getAllCaIds();
		Collection<RetrieveCaInfo> caList = new ArrayList<RetrieveCaInfo>();
		for (Integer caId : caIdList) {
			RetrieveCaInfo ca = null;
			try {
				ca = new RetrieveCaInfo();
				ca.setCaid(caId);
				CAInfo cainfo = caSession.getCAInfoInternal(caId);
				ca.setCa(cainfo);

				ArrayList<CertificateGuiInfo> ret = new ArrayList<CertificateGuiInfo>();
				Collection<Certificate> certs = mSignSession.getCertificateChain(caId);
				for (Certificate cert : certs) {
					ret.add(new CertificateGuiInfo(cert));
				}

				Collections.reverse((ArrayList<CertificateGuiInfo>) ret);
				
				ca.setChain(ret); 				
				caList.add(ca);
			} catch (CADoesntExistsException e) {
				log.info("CA does not exist : " + caId, e);
			}
		}
		return caList;
	}
	 

	public Collection<RetrieveCaInfo> caCrls() {

		//EjbLocalHelper ejb = new EjbLocalHelper();
//		// log.trace(">getAvailableCAs()");
//		CaSessionLocal caSession = ejb.getCaSession();
//		SignSession mSignSession = ejb.getSignSession();
		Collection<Integer> caIdList = caSession.getAllCaIds();
		Collection<RetrieveCaInfo> caList = new ArrayList<RetrieveCaInfo>();
		for (Integer caId : caIdList) {
			RetrieveCaInfo ca = null;
			try {
				ca = new RetrieveCaInfo();
				ca.setCaid(caId);
				CAInfo cainfo = caSession.getCAInfoInternal(caId);
				ca.setCa(cainfo);

				ArrayList<CertificateGuiInfo> ret = new ArrayList<CertificateGuiInfo>();
				Collection<Certificate> certs = mSignSession.getCertificateChain(caId);
				for (Certificate cert : certs) {
					ret.add(new CertificateGuiInfo(cert));
				}

				Collections.reverse((ArrayList<CertificateGuiInfo>) ret);
				
				ca.setChain(ret); 				
				caList.add(ca);
			} catch (CADoesntExistsException e) {
				log.info("CA does not exist : " + caId, e);
			}
		}
		return caList;
	
	}
}
