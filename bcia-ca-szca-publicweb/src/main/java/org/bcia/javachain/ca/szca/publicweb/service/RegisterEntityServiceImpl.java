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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.publicweb.entity.CertProcessData;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.certificate.certextensions.CustomCertificateExtension;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.cesecore.certificates.endentity.EndEntityType;
import org.cesecore.certificates.endentity.EndEntityTypes;
import org.cesecore.certificates.endentity.ExtendedInformation;
import org.cesecore.util.JBossUnmarshaller;
import org.ejbca.core.ejb.ra.raadmin.EndEntityProfileData;
import org.springframework.stereotype.Repository;

import cn.net.bcia.cesecore.certificates.certificate.certextensions.AvailableCustomCertificateExtensionsConfiguration;

@Repository
public class RegisterEntityServiceImpl implements RegisterEntityService{
	
	@Override
	public List<CertProcessData> getValidCertProccessList(EntityManager entityManager) {
		return entityManager.createQuery("from CertProcessData where status=:status").setParameter("status", 1).getResultList();
	}

	@Override
	public CertProcessData getCertProccess(EntityManager entityManager, long id) {
		return entityManager.find(CertProcessData.class, id);
	}

	@Override
	public LinkedHashMap<String, Object> loadProcessEntityDn(EntityManager entityManager, EndEntityProfileData epd) {
		LinkedHashMap<?, ?> profileMap = null;
		LinkedHashMap<String, Object> tranProfileMap = new LinkedHashMap<String, Object>();
		profileMap = JBossUnmarshaller.extractLinkedHashMap(epd.getDataUnsafe());
		
		for(Iterator it = profileMap.keySet().iterator();it.hasNext();) {
			Object key = it.next();
			tranProfileMap.put(String.valueOf(key), profileMap.get(key));
		}
		loadSubjectDnName(tranProfileMap,ENTITY_SUBJECT_DN_ORDER);
		loadSubjectDnName(tranProfileMap,ENTITY_SUBJECT_SAN_ORDER);
		loadSubjectDnName(tranProfileMap,ENTITY_SUBJECT_DIR_ORDER);
		return tranProfileMap;
	}
	
	private void loadSubjectDnName(LinkedHashMap<String, Object> tranProfileMap, String key) {
		ArrayList<Integer> dnOrder = (ArrayList<Integer>) tranProfileMap.get(key);
		ArrayList<String> dnOrderName = new ArrayList<String>();
		ArrayList<Boolean> dnOrderRequired = new ArrayList<Boolean>(); 
		ArrayList<Boolean> dnOrderModifyable = new ArrayList<Boolean>();
		for(int i=0;i<dnOrder.size();i++) {
			int dnId = dnOrder.get(i)/100;
			int num = dnOrder.get(i)%100;
			boolean required = (boolean) tranProfileMap.get(String.valueOf(ISREQUIRED * 10000 + num * 100 + dnId));
			boolean modifyable = (boolean) tranProfileMap.get(String.valueOf(MODIFYABLE * 10000 + num * 100 + dnId));
			dnOrderName.add(getSubjectName(dnOrder.get(i)/100));
			dnOrderRequired.add(required);
			dnOrderModifyable.add(modifyable);
		}
		tranProfileMap.put(key + "NAME", dnOrderName);
		tranProfileMap.put(key + "REQUIRED", dnOrderRequired);
		tranProfileMap.put(key + "MODIFY", dnOrderModifyable);
	}
	
	private String getSubjectName(int id) {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(3,"EMail DN中的email地址");
		map.put(4,"UID 唯一识别符");
		map.put(5,"CN 通用名");
		map.put(6,"SN 序列号");
		map.put(7,"GN 名字");
		map.put(8,"Initials 名字缩写");
		map.put(9,"SurName 姓");
		map.put(10,"T 头衔");
		map.put(11,"OU 部门");
		map.put(12,"O 组织/机构全称");
		map.put(13,"L 城市");
		map.put(14,"ST 省");
		map.put(15,"DC 域名");
		map.put(16,"C 国家");
		map.put(39,"非结构化地址(IP)");
		map.put(40,"非结构化名称(FQDN)");
		map.put(49,"postalCode 邮编号码");
		map.put(48,"企业、机构类型");
		map.put(50,"postalAddress 邮编地址");
		map.put(51,"telephoneNumber 电话号码");
		map.put(53,"pseudonym 笔名");
		map.put(54,"streetAddress 街道地址");
		map.put(55,"name 名字");
		//map.put("DESCRIPTION","Description 描述");
		
		map.put(17,"RFC822 名字");
		map.put(18,"DNS 名字");
		map.put(19,"IP地址");
		map.put(23,"目录名");
		map.put(21,"URI 统一资源标识符");
		map.put(25,"已经注册的Id");
		map.put(36,"UPN 用户名");
		map.put(41,"唯一ID");
		map.put(52,"Kerberos KPN Kerberos 5 Principal Name");
		//map.put(56,"Permanent Identifier");
		//map.put("XMPPADDR","XmppAddr");
		//map.put("SRVNAME","Service Name");
		//map.put("SUBJECTIDENTIFICATIONMETHOD","Subject Identification Method (SIM)");
		map.put(42,"生日(yyyymmdd)");
		map.put(43,"出生地");
		map.put(44,"性别(M/F)"); 
		map.put(45,"国籍(ISO3166) ");
		map.put(46,"居住国(ISO3166)");
		return map.get(id);
	}
	
	private Map<Integer, String> getSubjectCode() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(3,"E");
		map.put(4,"UID");
		map.put(5,"CN");
		map.put(6,"SN");
		map.put(7,"GIVENNAME");
		map.put(8,"INITIALS");
		map.put(9,"SURNAME");
		map.put(10,"T");
		map.put(11,"OU");
		map.put(12,"O");
		map.put(13,"L");
		map.put(14,"ST");
		map.put(15,"DC");
		map.put(16,"C");
		map.put(39,"nstructuredAddress");
		map.put(40,"unstructuredName");
		map.put(49,"PostalCode");
		map.put(48,"BusinessCategory");
		map.put(50,"PostalAddress");
		map.put(51,"TelephoneNumber");
		map.put(53,"Pseudonym");
		map.put(54,"STREET");
		map.put(55,"Name");
		map.put(17,"rfc822name");
		map.put(18,"dNSName");
		map.put(19,"iPAddress");
		map.put(23,"directoryName");
		map.put(21,"uniformResourceIdentifier");
		map.put(36,"upn");
		map.put(41,"guid");
		map.put(52,"krb5principal");
		
		map.put(42,"DATEOFBIRTH");
		map.put(43,"PLACEOFBIRTH");
		map.put(44,"GENDER"); 
		map.put(45,"COUNTRYOFCITIZENSHIP");
		map.put(46,"COUNTRYOFRESIDENCE");
		return map;
	}

	@Override
	public List<CustomCertificateExtension> getCustomCertificateExtension(EntityManager entityManager,CertificateProfileData cpd) {
		//CertificateProfileData cpd = CertificateProfileData.findByProfileName(entityManager, certProfileName);
		List<Integer> useCertExtensions = cpd.getCertificateProfile().getUsedCertificateExtensions();
		List<CustomCertificateExtension> certificateExtensions = new ArrayList<CustomCertificateExtension>();
		if(useCertExtensions != null && useCertExtensions.size() > 0) {
			AvailableCustomCertificateExtensionsConfiguration a = AvailableCustomCertificateExtensionsConfiguration.getAvailableCustomCertExtensionsFromFile();
			
			for(int i=0;i<useCertExtensions.size();i++) {
				certificateExtensions.add(a.getCustomCertificateExtension(useCertExtensions.get(i)));
			}
		}
		return certificateExtensions;
	}

	@Override
	public EndEntityInformation createEndEntityInformation(EntityManager entityManager,HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		long processId = Long.valueOf(request.getParameter("processId"));
		CertProcessData certProcessData = getCertProccess(entityManager, processId);
		EndEntityProfileData epd = EndEntityProfileData.findByProfileName(entityManager, certProcessData.getEndEntityProfileName());
		CertificateProfileData cpd = CertificateProfileData.findByProfileName(entityManager, certProcessData.getCertProfileName());
		//LinkedHashMap<String, Object> tranProfileMap = loadProcessEntityDn(entityManager, epd);
		LinkedHashMap<?, ?> profileMap = JBossUnmarshaller.extractLinkedHashMap(epd.getDataUnsafe());
		EndEntityInformation entity = new EndEntityInformation();
		entity.setEndEntityProfileId(epd.getId());
		entity.setCertificateProfileId(cpd.getId());
		entity.setDN(getEntitySubject(ENTITY_SUBJECT_DN_ORDER, request, profileMap));
		entity.setSubjectAltName(getEntitySubject(ENTITY_SUBJECT_SAN_ORDER, request, profileMap));
		ExtendedInformation ei = new ExtendedInformation();
		if(epd.getProfile().getUseExtensiondata()) {
			List<Integer> ids = cpd.getCertificateProfile().getUsedCertificateExtensions();
			if(ids != null && ids.size() > 0) {
				List<CustomCertificateExtension> certificateExtensions = getCustomCertificateExtension(entityManager, cpd);
				for(int i=0;i<certificateExtensions.size();i++) {
					String extValue = request.getParameter("extension" + certificateExtensions.get(i).getId());
					ei.setExtensionData(certificateExtensions.get(i).getOID(), extValue);
					
				}
			}
		}
		entity.setExtendedinformation(ei);
		entity.setPassword(password);
		CAData caData = CAData.findByName(entityManager, certProcessData.getCaName());
		entity.setCAId(caData.getCaId());
		entity.setUsername(username);
		entity.setTokenType(2);
		entity.setType(new EndEntityType(EndEntityTypes.ENDUSER));
		return entity;
	}

	private String getEntitySubject(String type, HttpServletRequest request, LinkedHashMap<?, ?> profileMap) {
		Map<Integer, String> map= getSubjectCode();
		ArrayList<Integer> dnOrder = (ArrayList<Integer>) profileMap.get(type);
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<dnOrder.size();i++) {
			String dnValue = request.getParameter(String.valueOf(dnOrder.get(i)));
			if(dnValue != null && dnValue.trim().length() > 0) {
				String dnCode = map.get(dnOrder.get(i)/100);
				if(sb.length() > 0)
					sb.append(",");
				sb.append(dnCode).append("=").append(dnValue);
			}
		}
		return sb.toString();
	}
	
}
