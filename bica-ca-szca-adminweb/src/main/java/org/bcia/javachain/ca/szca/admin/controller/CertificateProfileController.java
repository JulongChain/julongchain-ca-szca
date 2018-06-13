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

package org.bcia.javachain.ca.szca.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.admin.ca.CaManagementBean;
import org.bcia.javachain.ca.szca.admin.ra.servcie.CertProcessService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.certificate.certextensions.CertificateExtension;
import org.cesecore.certificates.certificateprofile.CertificatePolicy;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
import org.cesecore.certificates.certificateprofile.CertificateProfileConstants;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.util.JBossUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;

import cn.net.bcia.cesecore.certificates.certificate.certextensions.AvailableCustomCertificateExtensionsConfiguration;
import cn.net.bcia.cesecore.certificates.certificateprofile.CertificateProfileSessionBean;
import cn.net.bcia.cesecore.configuration.GlobalConfigurationSessionBean;
 

@Controller
public class CertificateProfileController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	 
	@Autowired
	CertificateProfileSessionBean certificateProfileSessionBean ;
	@Autowired
	CaManagementBean caBean ;
	@Autowired
	GlobalConfigurationSessionBean globalConfigurationSessionBean;
	@Autowired
	CertProcessService certProcessService;
	
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
	
	@RequestMapping("/ca/certProfileList")
	public ModelAndView certProfileList(HttpServletRequest request,String currentPage) {
		logger.info("==========certProfileList");
		String msg = "";
		Map<Integer, String> defaultCertificateProfileMap = new HashMap<Integer, String>();
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_ENDUSER),CertificateProfile.ENDUSERPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_SUBCA),CertificateProfile.SUBCAPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_ROOTCA),CertificateProfile.ROOTCAPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_OCSPSIGNER),CertificateProfile.OCSPSIGNERPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_SERVER),CertificateProfile.SERVERPROFILENAME);

		List<CertificateProfileData> certificateProfileDatas = null;
		
		try {

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			
			certificateProfileDatas = CertificateProfileData.findAll(entityManager);
		} catch (Exception e) {
			msg = String.format("Certifcate Profile Search Failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/ca/certProfileList");

		view.addObject("defaultCertificateProfileMap", defaultCertificateProfileMap);
		view.addObject("certificateProfileDatas", certificateProfileDatas);
		return view;

	}
	
	@RequestMapping("/ca/addCertProfile")
	public ModelAndView addCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========addCertProfile");
		
		try {
			int type = Integer.parseInt(request.getParameter("profileType"));
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			CertificateProfile cp = new CertificateProfile(type);
			certificateProfileSessionBean.addCertificateProfile(auth, profileName, cp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ca/certProfileList.html");
		return view;

	}
	
	@RequestMapping("/ca/cloneCertProfile")
	public ModelAndView cloneCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========cloneCertProfile");
		
		try {
			String orgname = request.getParameter("orgname");
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			CertificateProfile cp = certificateProfileSessionBean.getCertificateProfile(orgname);
			certificateProfileSessionBean.cloneCertificateProfile(auth, orgname, profileName, cp.getAvailableCAs());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ca/certProfileList.html");
		return view;

	}
	
	@RequestMapping("/ca/removeCertProfile")
	public ModelAndView removeCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========removeCertProfile");
		
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			certificateProfileSessionBean.removeCertificateProfile(auth, profileName);;
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ca/certProfileList.html");
		return view;

	}
	
	@RequestMapping("/ca/renameCertProfile")
	public ModelAndView renameCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========renameCertProfile");
		
		try {
			String oldName = request.getParameter("oldName");
			String newName = request.getParameter("newName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			certificateProfileSessionBean.renameCertificateProfile(auth, oldName, newName);
			certProcessService.updateCertProfileName(entityManager, oldName, newName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ca/certProfileList.html");
		return view;

	}
	
	@RequestMapping("/ca/editCertProfile")
	public ModelAndView editCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========editCertProfile");
		CertificateProfileData cfd = null;
		LinkedHashMap<?, ?> profileMap = null;
		String existCP = "";
		List<CAData> cas = null;
		List<CertificateExtension> certificateExtensions = null;
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			cfd = CertificateProfileData.findByProfileName(entityManager, profileName);
			profileMap = JBossUnmarshaller.extractLinkedHashMap(cfd.getDataUnsafe());
			AvailableCustomCertificateExtensionsConfiguration a = AvailableCustomCertificateExtensionsConfiguration.getAvailableCustomCertExtensionsFromFile();
			certificateExtensions = a.getAllAvailableCustomCertificateExtensions();
			
			if(cfd.getCertificateProfile().getUseCertificatePolicies()) {
				List<CertificatePolicy> l = cfd.getCertificateProfile().getCertificatePolicies();
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<l.size();i++) {
					sb.append(l.get(i).getPolicyID()).append("|").append(l.get(i).getQualifierId()).append("|").append(l.get(i).getQualifier()).append(",");
				}
				if(sb.length() > 1) {
					existCP = sb.substring(0, sb.length() - 1);
				} 
			}
			cas = caBean.getCaList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("/ca/editCertProfile");
		
		view.addObject("existCP", existCP);
		view.addObject("profileMap", profileMap);
		view.addObject("profileId", cfd.getId());
		view.addObject("profileName", cfd.getCertificateProfileName());
		view.addObject("cas", cas);
		view.addObject("certificateExtensions",certificateExtensions);
		return view;
	}
	
	@RequestMapping("/ca/viewCertProfile")
	public ModelAndView viewCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========editCertProfile");
		CertificateProfileData cfd = null;
		LinkedHashMap<?, ?> profileMap = null;
		String existCP = "";
		List<CAData> cas = null;
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有操作证书模板的权限。");
			cfd = CertificateProfileData.findByProfileName(entityManager, profileName);
			profileMap = JBossUnmarshaller.extractLinkedHashMap(cfd.getDataUnsafe());
			if(cfd.getCertificateProfile().getUseCertificatePolicies()) {
				List<CertificatePolicy> l = cfd.getCertificateProfile().getCertificatePolicies();
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<l.size();i++) {
					sb.append(l.get(i).getPolicyID()).append("|").append(l.get(i).getQualifierId()).append("|").append(l.get(i).getQualifier()).append(",");
				}
				if(sb.length() > 1) {
					existCP = sb.substring(0, sb.length() - 1);
				} 
			}
			cas = caBean.getCaList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("/ca/viewCertProfile");
		view.addObject("existCP", existCP);
		view.addObject("profileMap", profileMap);
		view.addObject("profileId", cfd.getId());
		view.addObject("profileName", cfd.getCertificateProfileName());
		view.addObject("cas", cas);
		return view;
	}
	
	@RequestMapping("/ca/handleCertProfile")
	public ModelAndView handleCertProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========handleCertProfile");
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			String certProfileId = request.getParameter("certProfileId");
			CertificateProfileData cpd = CertificateProfileData.findById(entityManager, Integer.parseInt(certProfileId));
			CertificateProfile cp = cpd.getCertificateProfile();
			
			String[] availablekeyalgorithms = request.getParameterValues("availablekeyalgorithms");
			String[] availableeccurves = request.getParameterValues("availableeccurves");
			String[] availablebitlengths = request.getParameterValues("availablebitlengths");
			String signaturealgorithm = request.getParameter("signaturealgorithm");
			long validity = Long.parseLong(StringUtils.isEmpty(request.getParameter("validity")) ? "0" : request.getParameter("validity"));
			int type = Integer.parseInt(request.getParameter("type"));
			boolean allowvalidityoverride = Boolean.valueOf(request.getParameter("allowvalidityoverride"));
			boolean allowextensionoverride = Boolean.valueOf(request.getParameter("allowextensionoverride"));
			boolean allowdnoverride = Boolean.valueOf(request.getParameter("allowdnoverride"));
			boolean allowdnoverridebyeei = Boolean.valueOf(request.getParameter("allowdnoverridebyeei"));
			boolean allowkeyusageoverride = Boolean.valueOf(request.getParameter("allowkeyusageoverride"));
			boolean allowbackdatedrevokation = Boolean.valueOf(request.getParameter("allowbackdatedrevokation"));
			boolean usecertificatestorage = Boolean.valueOf(request.getParameter("usecertificatestorage"));
			boolean storecertificatedata = Boolean.valueOf(request.getParameter("storecertificatedata"));
			boolean usebasicconstrants = Boolean.valueOf(request.getParameter("usebasicconstrants"));
			boolean basicconstraintscritical = Boolean.valueOf(request.getParameter("basicconstraintscritical"));
			boolean usepathlengthconstraint = Boolean.valueOf(request.getParameter("usepathlengthconstraint"));
			boolean useauthoritykeyidentifier = Boolean.valueOf(request.getParameter("useauthoritykeyidentifier"));
			boolean usesubjectkeyidentifier = Boolean.valueOf(request.getParameter("usesubjectkeyidentifier"));
			boolean useldapdnorder = Boolean.valueOf(request.getParameter("useldapdnorder"));
			boolean usekeyusage = Boolean.valueOf(request.getParameter("usekeyusage"));
			boolean keyusagecritical = Boolean.valueOf(request.getParameter("keyusagecritical"));
			String[] keyusage = request.getParameterValues("keyusage");
			boolean useextendedkeyusage = Boolean.valueOf(request.getParameter("useextendedkeyusage"));
			boolean extendedkeyusagecritical = Boolean.valueOf(request.getParameter("extendedkeyusagecritical"));
			String[] extendedkeyusage = request.getParameterValues("extendedkeyusage");
			boolean usecertificatepolicies = Boolean.valueOf(request.getParameter("usecertificatepolicies"));
			boolean certificatepoliciescritical = Boolean.valueOf(request.getParameter("certificatepoliciescritical"));
			boolean usesubjectalternativename = Boolean.valueOf(request.getParameter("usesubjectalternativename"));
			boolean subjectalternativenamecritical = Boolean.valueOf(request.getParameter("subjectalternativenamecritical"));
			boolean useissueralternativename = Boolean.valueOf(request.getParameter("useissueralternativename"));
			boolean issueralternativenamecritical = Boolean.valueOf(request.getParameter("issueralternativenamecritical"));
			boolean usecrldistributionpoint = Boolean.valueOf(request.getParameter("usecrldistributionpoint"));
			boolean crldistributionpointcritical = Boolean.valueOf(request.getParameter("crldistributionpointcritical"));
			boolean usedefaultcrldistributionpoint = Boolean.valueOf(request.getParameter("usedefaultcrldistributionpoint"));
			boolean useauthorityinformationaccess = Boolean.valueOf(request.getParameter("useauthorityinformationaccess"));
			boolean usedefaultocspservicelocator = Boolean.valueOf(request.getParameter("usedefaultocspservicelocator"));
			//boolean usecaissuersuri = Boolean.valueOf(request.getParameter("usecaissuersuri"));
			boolean useocspnocheck = Boolean.valueOf(request.getParameter("useocspnocheck"));
			boolean usecardnum = Boolean.valueOf(request.getParameter("usecardnum"));
			String[] availablecas = request.getParameterValues("availablecas");
			String[] usecustomextensions = request.getParameterValues("usecustomextensions");
			
			cp.setType(type);
			cp.setAvailableKeyAlgorithms(availablekeyalgorithms);
			cp.setAvailableEcCurves(availableeccurves);
			cp.setAvailableBitLengths(arrayStringToInt(availablebitlengths));
			cp.setSignatureAlgorithm(signaturealgorithm);
			cp.setValidity(validity);
			cp.setAllowValidityOverride(allowvalidityoverride);
			cp.setAllowExtensionOverride(allowextensionoverride);
			cp.setAllowCertSerialNumberOverride(allowdnoverride);
			cp.setAllowDNOverrideByEndEntityInformation(allowdnoverridebyeei);
			cp.setAllowKeyUsageOverride(allowkeyusageoverride);
			cp.setAllowBackdatedRevocation(allowbackdatedrevokation);
			if(type == 2 || type == 8) {
				cp.setUsePathLengthConstraint(usepathlengthconstraint);
				if(usepathlengthconstraint) 
					cp.setPathLengthConstraint(Integer.parseInt(request.getParameter("pathlengthconstraint")));
				else 
					cp.setPathLengthConstraint(0);
				cp.setUseCertificateStorage(true);
				cp.setStoreCertificateData(true);
			}else {
				cp.setUsePathLengthConstraint(false);
				cp.setPathLengthConstraint(0);
				cp.setUseCertificateStorage(usecertificatestorage);
				cp.setStoreCertificateData(storecertificatedata);
			}
			cp.setUseBasicConstraints(usebasicconstrants);
			cp.setBasicConstraintsCritical(basicconstraintscritical);
			
			cp.setUseAuthorityKeyIdentifier(useauthoritykeyidentifier);
			cp.setUseSubjectKeyIdentifier(usesubjectkeyidentifier);
			cp.setUseLdapDnOrder(useldapdnorder);
			cp.setUseKeyUsage(usekeyusage);
			cp.setKeyUsageCritical(keyusagecritical);
			cp.setKeyUsage(keyusageToBoolean(keyusage));
			cp.setUseExtendedKeyUsage(useextendedkeyusage);
			cp.setExtendedKeyUsageCritical(extendedkeyusagecritical);
			cp.setExtendedKeyUsage(arrayStringToList(extendedkeyusage));
			cp.setUseCertificatePolicies(usecertificatepolicies);
			cp.setCertificatePoliciesCritical(certificatepoliciescritical);
			List<CertificatePolicy> cpList = new ArrayList<CertificatePolicy>();
			if(usecertificatepolicies) {
				String existCPVal = request.getParameter("existCPVal");
				String[] policyMsg = existCPVal.split(",");
				for(int i=0;i<policyMsg.length;i++) {
					String[] cpArr = policyMsg[i].split("\\|");
					if(cpArr[0] != null && cpArr[0].length() > 0) {
						CertificatePolicy c = null;
						if(CertificatePolicy.id_qt_cps.equals(cpArr[1])) 
							c = new CertificatePolicy(cpArr[0], CertificatePolicy.id_qt_cps, cpArr[2]);
						else if(CertificatePolicy.id_qt_unotice.equals(cpArr[1])) 
							c = new CertificatePolicy(cpArr[0], CertificatePolicy.id_qt_unotice, cpArr[2]);
						else 
							c = new CertificatePolicy(cpArr[0], null, null);
						
						cpList.add(c);
					}
					
				}
				
			}
			cp.setCertificatePolicies(cpList);
			cp.setUseSubjectAlternativeName(usesubjectalternativename);
			cp.setSubjectAlternativeNameCritical(subjectalternativenamecritical);
			cp.setIssuerAlternativeNameCritical(issueralternativenamecritical);
			cp.setUseIssuerAlternativeName(useissueralternativename);
			cp.setUseCRLDistributionPoint(usecrldistributionpoint);
			cp.setCRLDistributionPointCritical(crldistributionpointcritical);
			cp.setUseDefaultCRLDistributionPoint(usedefaultcrldistributionpoint);
			if(usecrldistributionpoint) {
				if(!usedefaultcrldistributionpoint) {
					String crldistributionpointuri = request.getParameter("crldistributionpointuri");
					String crlissuer = request.getParameter("crlissuer");
					cp.setCRLDistributionPointURI(crldistributionpointuri);
					cp.setCRLIssuer(crlissuer);
				}else {
					cp.setCRLDistributionPointURI(null);
					cp.setCRLIssuer(null);
				}
					
			}
			cp.setUseAuthorityInformationAccess(useauthorityinformationaccess);
			cp.setUseDefaultOCSPServiceLocator(usedefaultocspservicelocator);
			if(!usedefaultocspservicelocator) {
				String ocspservicelocatoruri = request.getParameter("ocspservicelocatoruri");
				cp.setOCSPServiceLocatorURI(ocspservicelocatoruri);
			}else {
				cp.setOCSPServiceLocatorURI(null);
			}
			String caissuers = request.getParameter("existIssuers");
			caissuers = caissuers.replace("[", "");
			caissuers = caissuers.replace("]", "");
			cp.setCaIssuers(arrayStringToList(caissuers.split(",")));
			cp.setUseOcspNoCheck(useocspnocheck);
			cp.setUseCardNumber(usecardnum);
			cp.setUsedCertificateExtensions(arrayStringToIntList(usecustomextensions));
			cp.setAvailableCAs(arrayStringToIntList(availablecas));
			
			certificateProfileSessionBean.changeCertificateProfile(auth, cpd.getCertificateProfileName(), cp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ModelAndView view = new ModelAndView("redirect:/ca/certProfileList.html");
		return view;

	}
	
	private int[] arrayStringToInt(String[] strs) {
		if(strs == null || strs.length == 0)
			return null;
		int[] is = new int[strs.length];
		for(int i=0;i<strs.length;i++) {
			is[i] = Integer.parseInt(strs[i]);
		}
		return is;
	}
	
	private List<Integer> arrayStringToIntList(String[] strs) {
		List<Integer> l = new ArrayList<Integer>();
		if(strs == null || strs.length == 0)
			return l;
		for(int i=0;i<strs.length;i++) {
			l.add(Integer.parseInt(strs[i]));
		}
		return l;
	}
	
	private ArrayList<String> arrayStringToList(String[] strs) {
		ArrayList<String> l = new ArrayList<String>();
		if(strs == null || strs.length == 0)
			return l;
		for(int i=0;i<strs.length;i++) {
			l.add(strs[i].trim());
		}
		return l;
	}
	
	private boolean[] keyusageToBoolean(String[] strs) {
		boolean[] is = new boolean[] {false,false,false,false,false,false,false,false,false};
		for(int i=0;i<strs.length;i++) {
			int index = Integer.parseInt(strs[i]);
			is[index] = true;
		}
		return is;
	}
}
