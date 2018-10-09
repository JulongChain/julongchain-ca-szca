/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.admin.ca.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.admin.ca.CryptoTokenBean;
import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.authorization.AuthorizationDeniedException;
import org.cesecore.certificates.ca.CAConstants;
import org.cesecore.certificates.ca.X509CAInfo;
import org.cesecore.certificates.ca.catoken.CATokenConstants;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
import org.cesecore.certificates.certificateprofile.CertificateProfileConstants;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.keys.token.CryptoTokenOfflineException;
import org.cesecore.util.SimpleTime;
import org.cesecore.util.ValidityDate;
import org.ejbca.core.model.SecConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;


@Service
@Transactional
public class CaManagementServiceImpl implements CaManagementService{
	@Autowired
	CryptoTokenBean cryptoTokenBean ;
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
	@Override
	public void loadCaInfo(AuthenticationToken auth, X509CAInfo cainfo, ModelAndView view) throws AuthorizationDeniedException, CryptoTokenOfflineException {
		LinkedHashMap<String,String> tokenMap = new LinkedHashMap<String,String>();
		String signAlg = null;
		int signedby = 0;
		String tokenName = null;
		String certProfileName = null; 
		long crlPeriod = 0;
		Map<Integer, String> revokeMap = null;
		String revokedDate = null;
		String revokedReason = "";
		signAlg = cainfo.getCAToken().getSignatureAlgorithm();
		signedby = cainfo.getSignedBy();
		tokenName = cryptoTokenBean.getCryptoTokenById(auth, cainfo.getCAToken().getCryptoTokenId()).getTokenName();
		int certProfileId = cainfo.getCertificateProfileId();
		if(certProfileId == CertificateProfileConstants.CERTPROFILE_FIXED_SUBCA)
			certProfileName = CertificateProfile.SUBCAPROFILENAME;
		else if(certProfileId == CertificateProfileConstants.CERTPROFILE_FIXED_ROOTCA)
			certProfileName = CertificateProfile.ROOTCAPROFILENAME;
		else
			certProfileName = CertificateProfileData.findById(entityManager, cainfo.getCertificateProfileId()).getCertificateProfileName();
		crlPeriod = cainfo.getCRLPeriod()/SimpleTime.MILLISECONDS_PER_DAY;
		revokeMap = new HashMap<Integer, String>();
		for(int i=0; i < SecConst.reasontexts.length; i++){ 
            if(i!= 7){
            	revokeMap.put(i, WebLanguages.getInstance().getText(SecConst.reasontexts[i]));
            }
        } 
		
		int status = cainfo.getStatus();
		boolean isRevoked = status == CAConstants.CA_REVOKED ? true : false;
		if(isRevoked) {
			revokedDate = ValidityDate.formatAsISO8601(cainfo.getRevocationDate(),ValidityDate.TIMEZONE_SERVER);
			revokedReason = revokeMap.get(cainfo.getRevocationReason());
		}
		tokenMap.put(CATokenConstants.CAKEYPURPOSE_CERTSIGN_STRING, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_CERTSIGN));
		tokenMap.put(CATokenConstants.CAKEYPURPOSE_CRLSIGN_STRING, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_CRLSIGN));
		tokenMap.put(CATokenConstants.CAKEYPURPOSE_KEYENCRYPT_STRING, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_KEYENCRYPT));
		tokenMap.put(CATokenConstants.CAKEYPURPOSE_HARDTOKENENCRYPT_STRING, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_HARDTOKENENCRYPT));
		tokenMap.put(CATokenConstants.CAKEYPURPOSE_TESTKEY_STRING, cainfo.getCAToken().getAliasFromPurpose(CATokenConstants.CAKEYPURPOSE_KEYTEST));
		view.addObject("cainfo", cainfo);
		view.addObject("signAlg", signAlg);
		view.addObject("signedby", signedby);
		view.addObject("tokenName", tokenName);
		view.addObject("certProfileName", certProfileName);
		view.addObject("crlPeriod", crlPeriod);
		view.addObject("revokeMap", revokeMap);
		view.addObject("isRevoked", isRevoked);
		view.addObject("status", status);
		view.addObject("revokedDate", revokedDate);
		view.addObject("revokedReason", revokedReason);
		view.addObject("tokenMap",tokenMap);
		view.addObject("useAKI",cainfo.getUseAuthorityKeyIdentifier());
		view.addObject("useAKICritical",cainfo.getAuthorityKeyIdentifierCritical());
		view.addObject("useSN",cainfo.getUseCRLNumber());
		view.addObject("useSNCritical",cainfo.getCRLNumberCritical());
		view.addObject("isEnforceUniquePK",cainfo.isDoEnforceUniquePublicKeys());
		view.addObject("isEnforceUniqueDN",cainfo.isDoEnforceUniqueDistinguishedName());
		view.addObject("isEnforceUniqueSN",cainfo.isDoEnforceUniqueSubjectDNSerialnumber());
		view.addObject("useUserStorage",cainfo.isUseUserStorage());
		view.addObject("useCertStorage",cainfo.isUseCertificateStorage());
		view.addObject("isLdapDNOrder",cainfo.getUseLdapDnOrder());
		view.addObject("useUTF8PolicyText",cainfo.getUseUTF8PolicyText());
		view.addObject("usePrintableDN", cainfo.getUsePrintableStringSubjectDN());
		view.addObject("defaultCDP", cainfo.getDefaultCRLDistPoint());
		view.addObject("validity", (cainfo.getValidity() - cainfo.getUpdateTime().getTime())/SimpleTime.MILLISECONDS_PER_DAY + 1);
		view.addObject("defaultCrlIssuer", cainfo.getDefaultCRLIssuer());
		view.addObject("defaultOCSP", cainfo.getDefaultOCSPServiceLocator());
		List<String> l = cainfo.getAuthorityInformationAccess();
		if(l != null && l.size() == 1)
			view.addObject("defaultIssuerCA", l.get(0));
		
	}
	@Override
	public void setCaInfo(AuthenticationToken auth, HttpServletRequest request, X509CAInfo cainfo)throws AuthorizationDeniedException {
		boolean isEnforceUniquePK = Boolean.valueOf(request.getParameter("isEnforceUniquePK"));
		boolean isEnforceUniqueDN = Boolean.valueOf(request.getParameter("isEnforceUniqueDN"));
		boolean isEnforceUniqueSN = Boolean.valueOf(request.getParameter("isEnforceUniqueSN"));
		boolean useUserStorage = Boolean.valueOf(request.getParameter("useUserStorage"));
		boolean useCertStorage = Boolean.valueOf(request.getParameter("useCertStorage"));
		boolean isLdapDNOrder = Boolean.valueOf(request.getParameter("isLdapDNOrder"));
		boolean useUTF8PolicyText = Boolean.valueOf(request.getParameter("useUTF8PolicyText"));
		boolean usePrintableDN = Boolean.valueOf(request.getParameter("usePrintableDN"));
		boolean useAKI = Boolean.valueOf(request.getParameter("useAKI"));
		boolean useAKICritical = Boolean.valueOf(request.getParameter("useAKICritical"));
		
		boolean useSN = Boolean.valueOf(request.getParameter("useSN"));
		boolean useSNCritical = Boolean.valueOf(request.getParameter("useSNCritical"));
		long crlPeriod = Long.parseLong(request.getParameter("crlPeriod"));
		long validity = Long.parseLong(request.getParameter("validity"));
		String defaultCDP = request.getParameter("defaultCDP");
		String defaultCrlIssuer = request.getParameter("defaultCrlIssuer");
		String defaultOCSP = request.getParameter("defaultOCSP");
		String defaultIssuerCA = request.getParameter("defaultIssuerCA");
		cainfo.setDoEnforceUniquePublicKeys(isEnforceUniquePK);
		cainfo.setDoEnforceUniqueDistinguishedName(isEnforceUniqueDN);
		cainfo.setDoEnforceUniqueSubjectDNSerialnumber(isEnforceUniqueSN);
		cainfo.setUseUserStorage(useUserStorage);
		cainfo.setUseCertificateStorage(useCertStorage);
		cainfo.setUseLdapDnOrder(isLdapDNOrder);
		cainfo.setUseUTF8PolicyText(useUTF8PolicyText);
		cainfo.setUsePrintableStringSubjectDN(usePrintableDN);
		cainfo.setUseAuthorityKeyIdentifier(useAKI);
		cainfo.setAuthorityKeyIdentifierCritical(useAKICritical);
		cainfo.setUseCRLNumber(useSN);
		cainfo.setCRLNumberCritical(useSNCritical);
		cainfo.setCRLPeriod(crlPeriod * SimpleTime.MILLISECONDS_PER_DAY);
		cainfo.setDefaultCRLDistPoint(defaultCDP);
		cainfo.setDefaultCRLIssuer(defaultCrlIssuer);
		cainfo.setDefaultOCSPServiceLocator(defaultOCSP);
		Date now = new Date();
		cainfo.setValidity(now.getTime() + (validity * SimpleTime.MILLISECONDS_PER_DAY));
		if(defaultIssuerCA != null && defaultIssuerCA.length() > 0) {
			List<String> l = new ArrayList<String>();
			l.add(defaultIssuerCA);
			cainfo.setAuthorityInformationAccess(l);
		}else {
			cainfo.setAuthorityInformationAccess(null);
		}
		//cainfo.setUpdateTime(new Date());
	}
	
}
