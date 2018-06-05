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

package org.bcia.javachain.ca.szca.admin.ra.servcie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.cesecore.certificates.certificateprofile.CertificateProfile;
import org.cesecore.certificates.certificateprofile.CertificateProfileConstants;
import org.ejbca.core.model.ra.raadmin.EndEntityProfile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import net.sf.json.JSONObject;

@Service
public class EndEntityProfileServiceImpl implements EndEntityProfileService{
	private static Logger log = Logger.getLogger(EndEntityProfileServiceImpl.class);
	public String loadProfileAutoJson(LinkedHashMap<String, Object> tranProfileMap) {
		if(tranProfileMap == null || tranProfileMap.size() < 1) return null;
		ArrayList<Integer> orders = new ArrayList<Integer>();
		ArrayList<Integer> dnOrders = (ArrayList<Integer>) tranProfileMap.get("SUBJECTDNFIELDORDER");
		ArrayList<Integer> sanOrders = (ArrayList<Integer>) tranProfileMap.get("SUBJECTALTNAMEFIELDORDER");
		ArrayList<Integer> dirOrders = (ArrayList<Integer>) tranProfileMap.get("SUBJECTDIRATTRFIELDORDER");
		orders.addAll(dnOrders);
		orders.addAll(sanOrders);
		orders.addAll(dirOrders);
		JSONObject j = new JSONObject();
		for(int i=0;i<orders.size();i++) {
			int order = orders.get(i);
			int id = order / 100;
			int orderNum = order % 100;
			j.put("textfield" + order, tranProfileMap.get(String.valueOf(orderNum * 100 + id)));
			if(tranProfileMap.containsKey(String.valueOf(1 * 10000 + orderNum * 100 + id))) 
				j.put("checkboxuse" + order,tranProfileMap.get(String.valueOf(1 * 10000 + orderNum * 100 + id)));
			else 
				j.put("checkboxuse" + order, "false");
			if(tranProfileMap.containsKey(String.valueOf(2 * 10000 + orderNum * 100 + id))) 
				j.put("checkboxrequired" + order, tranProfileMap.get(String.valueOf(2 * 10000 + orderNum * 100 + id)));
			else 
				j.put("checkboxrequired" + order, "false");
			if(tranProfileMap.containsKey(String.valueOf(3 * 10000 + orderNum * 100 + id))) 
				j.put("checkboxmodifyable" + order, tranProfileMap.get(String.valueOf(3 * 10000 + orderNum * 100 + id)));
			else 
				j.put("checkboxmodifyable" + order, "false");
			if(tranProfileMap.containsKey(String.valueOf(4 * 10000 + orderNum * 100 + id))) {
				j.put("checkboxvalidation" + order, "true");
				j.put("textfield" + order + "validation" + i, tranProfileMap.get(String.valueOf(4 * 10000 + orderNum * 100 + id)));
			}else 
				j.put("checkboxvalidation" + order, "false");
			j.put("textfield" + order + "validation", "");
			
		} 
		return j.toString();
	}

	@Override
	public void handleProfileChange(HttpServletRequest request, EndEntityProfile eep) {
		setUserName(request, eep);
		setPassword(request, eep);
		setFailLogins(request, eep);
		setMutiIssue(request, eep);
		setEmail(request, eep);
		setReverseAndMergeDnWS(request, eep);
		setDns(request, eep);
		setSANs(request, eep);
		setDirs(request, eep);
		setCertProfiles(request, eep);
		setAvailCas(request, eep);
		setTokenTypes(request, eep);
		setCustomCertItem(request, eep);
		setCertValidityTime(request, eep);
		setNameConstraints(request, eep);
		setAllowRequests(request, eep);
	} 
	
	private void setAllowRequests(HttpServletRequest request, EndEntityProfile eep) {
		boolean useallowedrequests = Boolean.valueOf(request.getParameter("checkboxuseallowedrequests"));
		String allowedrequests = request.getParameter("selectallowedrequests");
		eep.setUse(97, 0, useallowedrequests);
		eep.setValue(97, 0, allowedrequests);
	}
	
	private void setNameConstraints(HttpServletRequest request, EndEntityProfile eep) {
		boolean usencpermitted = Boolean.valueOf(request.getParameter("checkboxusencpermitted"));
		boolean requiredncpermitted = Boolean.valueOf(request.getParameter("checkboxrequiredncpermitted"));
		boolean usencexcluded = Boolean.valueOf(request.getParameter("checkboxusencexcluded"));
		boolean requiredncexcluded = Boolean.valueOf(request.getParameter("checkboxrequiredncexcluded"));
		eep.setUse(89, 0, usencpermitted);
		eep.setRequired(89, 0, requiredncpermitted);
		eep.setUse(88, 0, usencexcluded);
		eep.setRequired(88, 0, requiredncexcluded);
	}
	
	private void setCertValidityTime(HttpServletRequest request, EndEntityProfile eep) {
		boolean usestarttime = Boolean.valueOf(request.getParameter("checkboxsusestarttime"));
		boolean useendtime = Boolean.valueOf(request.getParameter("checkboxsuseendtime"));
		String starttime = request.getParameter("textfieldstarttime");
		String endtime = request.getParameter("textfieldendtime");
		boolean modifyablestarttime = Boolean.valueOf(request.getParameter("checkboxmodifyablestarttime"));
		boolean modifyableendtime = Boolean.valueOf(request.getParameter("checkboxmodifyableendtime"));
		eep.setUse(98, 0, usestarttime);
		eep.setUse(99, 0, useendtime);
		eep.setValue(98, 0, starttime);
		eep.setValue(99, 0, endtime);
		eep.setModifyable(98, 0, modifyablestarttime);
		eep.setModifyable(99, 0, modifyableendtime);
	}
	
	private void setCustomCertItem(HttpServletRequest request, EndEntityProfile eep) {
		boolean usecertserialonr = Boolean.valueOf(request.getParameter("checkboxusecertserialonr"));
		boolean useextensiondata = Boolean.valueOf(request.getParameter("checkboxuseextensiondata"));
		boolean usecardnumber = Boolean.valueOf(request.getParameter("checkboxusecardnumber"));
		boolean requiredcardnumber = Boolean.valueOf(request.getParameter("checkboxrequiredcardnumber"));
		eep.setUse(92, 0, usecertserialonr);
		eep.setUseExtensiondata(useextensiondata);
		eep.setUse(91, 0, usecardnumber);
		eep.setRequired(91, 0, requiredcardnumber);
	}
	
	private void setTokenTypes(HttpServletRequest request, EndEntityProfile eep) {
		String defaultTokenType = request.getParameter("selectdefaulttokentype");
		String[] availTokenTypes = request.getParameterValues("selectavailabletokentypes");
		StringBuilder tokenTypes = new StringBuilder();
		for(int i=0;i<availTokenTypes.length;i++) {
			tokenTypes.append(availTokenTypes[i]).append(";");
		}
		String ids = tokenTypes.toString();
		if(ids.indexOf(defaultTokenType) == -1)
			ids += defaultTokenType + ";";
		if(ids.length() > 0)
			ids = ids.substring(0, ids.length() - 1);
		eep.setValue(31, 0, defaultTokenType);
		eep.setValue(32, 0, ids);
	}
	
	private void setAvailCas(HttpServletRequest request, EndEntityProfile eep) {
		String defaultCa = request.getParameter("selectdefaultca");
		String[] availCas = request.getParameterValues("selectavailablecas");
		List<Integer> ids = stringArrayToList(availCas);
		int defaultCaId = Integer.parseInt(defaultCa);
		if(!ids.contains(defaultCaId))
			ids.add(defaultCaId);
		eep.setValue(37, 0, defaultCa);
		eep.setAvailableCAs(ids);
	}
	
	private void setDns(HttpServletRequest request, EndEntityProfile eep) {
		String subjectDnOrder = request.getParameter("SUBJECTDNFIELDORDER");
		if(subjectDnOrder.length() > 2) {
			subjectDnOrder = subjectDnOrder.substring(1, subjectDnOrder.length() - 1);
			String[] dns = subjectDnOrder.split(",");
			for(int i=0;i<dns.length;i++) {
				int dn = Integer.parseInt(dns[i].trim());
				int parameter = dn/100;
				int number = dn%100;
				String textfieldsubjectdn = request.getParameter("textfieldsubjectdn" + i);
				boolean checkboxrequiredsubjectdn = Boolean.valueOf(request.getParameter("checkboxrequiredsubjectdn" + i));
				boolean checkboxmodifyablesubjectdn = Boolean.valueOf(request.getParameter("checkboxmodifyablesubjectdn" + i)); 
				eep.setValue(parameter, number, textfieldsubjectdn);
				eep.setRequired(parameter, number, checkboxrequiredsubjectdn);
				eep.setModifyable(parameter, number, checkboxmodifyablesubjectdn);
			}
		}
	}
	
	private void setCertProfiles(HttpServletRequest request, EndEntityProfile eep) {
		String defaultCp = request.getParameter("selectdefaultcertprofile");
		String[] availCps = request.getParameterValues("selectavailablecertprofiles");
		List<Integer> ids = stringArrayToList(availCps);
		int defaultCpId = Integer.parseInt(defaultCp);
		if(!ids.contains(defaultCpId))
			ids.add(defaultCpId);
		eep.setValue(29, 0, defaultCp);
		eep.setAvailableCertificateProfileIds(ids);
	}
	
	private List<Integer> stringArrayToList(String[] arrs){
		List<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<arrs.length;i++) {
			l.add(Integer.parseInt(arrs[i]));
		}
		return l;
	}
	
	private void setSANs(HttpServletRequest request, EndEntityProfile eep) {
		String subjectSanOrder = request.getParameter("SUBJECTALTNAMEFIELDORDER");
		if(subjectSanOrder.length() > 2) {
			subjectSanOrder = subjectSanOrder.substring(1, subjectSanOrder.length() - 1);
			String[] sans = subjectSanOrder.split(",");
			for(int i=0;i<sans.length;i++) {
				int san = Integer.parseInt(sans[i].trim());
				int parameter = san/100;
				int number = san%100;
				String textfieldsubjectaltname = request.getParameter("textfieldsubjectaltname" + i);
				boolean checkboxrequiredsubjectaltname = Boolean.valueOf(request.getParameter("checkboxrequiredsubjectaltname" + i));
				boolean checkboxmodifyablesubjectaltname = Boolean.valueOf(request.getParameter("checkboxmodifyablesubjectaltname" + i)); 
				if(parameter == 17) {
					boolean useemail = Boolean.valueOf(request.getParameter("checkboxusesubjectaltname" + i));
					eep.setUse(parameter, number, useemail);
				}
				eep.setValue(parameter, number, textfieldsubjectaltname);
				eep.setRequired(parameter, number, checkboxrequiredsubjectaltname);
				eep.setModifyable(parameter, number, checkboxmodifyablesubjectaltname);
			}
		}
	}
	
	private void setDirs(HttpServletRequest request, EndEntityProfile eep) {
		String subjectDirOrder = request.getParameter("SUBJECTDIRATTRFIELDORDER");
		if(subjectDirOrder.length() > 2) {
			subjectDirOrder = subjectDirOrder.substring(1, subjectDirOrder.length() - 1);
			String[] dirs = subjectDirOrder.split(",");
			for(int i=0;i<dirs.length;i++) {
				int dir = Integer.parseInt(dirs[i].trim());
				int parameter = dir/100;
				int number = dir%100;
				String textfieldsubjectdirattr = request.getParameter("textfieldsubjectdirattr" + i);
				boolean checkboxrequiredsubjectdirattr = Boolean.valueOf(request.getParameter("checkboxrequiredsubjectdirattr" + i));
				boolean checkboxmodifyablesubjectdirattr = Boolean.valueOf(request.getParameter("checkboxmodifyablesubjectdirattr" + i)); 
				eep.setValue(parameter, number, textfieldsubjectdirattr);
				eep.setRequired(parameter, number, checkboxrequiredsubjectdirattr);
				eep.setModifyable(parameter, number, checkboxmodifyablesubjectdirattr);
			}
		}
	}
	
	private void setUserName(HttpServletRequest request, EndEntityProfile eep) {
		boolean ismodifyableusername = Boolean.valueOf(request.getParameter("USERNAME"));
		if(ismodifyableusername)
			eep.setModifyable(0, 0, false);
		else
			eep.setModifyable(0, 0, true);
	}
	
	private void setPassword(HttpServletRequest request, EndEntityProfile eep) {
		boolean isUsePwd = Boolean.valueOf(request.getParameter("isUsePwd"));
		if(isUsePwd) {
			eep.setModifyable(1, 0, false);
			eep.setValue(1, 0, "");
			String selectautopasswordtype = request.getParameter("selectautopasswordtype");
			String selectautopasswordlength = request.getParameter("selectautopasswordlength");
			eep.setValue(95, 0, selectautopasswordtype);
			eep.setValue(96, 0, selectautopasswordlength);
		}else {
			eep.setModifyable(1, 0, true);
			String pwd = request.getParameter("PASSWORD");
			eep.setValue(1, 0, pwd);
			eep.setValue(95, 0, "PWGEN_DIGIT");
			eep.setValue(96, 0, "4");
		}
		boolean isRequiredPwd = Boolean.valueOf(request.getParameter("isRequiredPwd"));
		String minpwdstrength = request.getParameter("minpwdstrength");
		eep.setRequired(1, 0, isRequiredPwd);
		eep.setValue(90, 0, minpwdstrength);
	}
	
	private void setFailLogins(HttpServletRequest request, EndEntityProfile eep) {
		boolean usemaxfailedlogins = Boolean.valueOf(request.getParameter("usemaxfailedlogins"));
		String maxfailedlogins = request.getParameter("maxfailedlogins");
		boolean modifyablemaxfailedlogins = Boolean.valueOf(request.getParameter("modifyablemaxfailedlogins"));
		eep.setUse(93, 0, usemaxfailedlogins);
		eep.setValue(93, 0, StringUtils.isEmpty(maxfailedlogins) ? "-1" : maxfailedlogins);
		eep.setModifyable(93, 0, modifyablemaxfailedlogins);
	}
	
	private void setMutiIssue(HttpServletRequest request, EndEntityProfile eep) {
		boolean usecleartextpassword = Boolean.valueOf(request.getParameter("usecleartextpassword"));
		boolean requiredcleartextpassword = Boolean.valueOf(request.getParameter("requiredcleartextpassword"));
		eep.setUse(2, 0, usecleartextpassword);
		eep.setRequired(2, 0, requiredcleartextpassword);
	}
	
	private void setEmail(HttpServletRequest request, EndEntityProfile eep) {
		boolean useemail = Boolean.valueOf(request.getParameter("useemail"));
		String email = request.getParameter("email");
		boolean requiredemail = Boolean.valueOf(request.getParameter("requiredemail"));
		boolean modifyableemail = Boolean.valueOf(request.getParameter("modifyableemail"));
		eep.setUse(26, 0, useemail);
		eep.setValue(26, 0, email);
		eep.setRequired(26, 0, requiredemail);
		eep.setModifyable(26, 0, modifyableemail);
	}
	
	private void setReverseAndMergeDnWS(HttpServletRequest request, EndEntityProfile eep) {
		boolean reversefieldchecks = Boolean.valueOf(request.getParameter("reversefieldchecks"));
		boolean allowmergednwebservices = Boolean.valueOf(request.getParameter("allowmergednwebservices"));
		eep.setAllowMergeDnWebServices(allowmergednwebservices);
		eep.setReverseFieldChecks(reversefieldchecks);
	}
	
	public Map<String, String> loadProfileMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("EMAILADDRESS","EMail, DN中的email地址");
		map.put("UID","UID, 唯一识别符");
		map.put("COMMONNAME","CN, 通用名");
		map.put("SERIALNUMBER","SN, 序列号");
		map.put("GIVENNAME","GN, 名字");
		map.put("INITIALS","Initials, 名字缩写");
		map.put("SURNAME","SurName, 姓");
		map.put("TITLE","T, 头衔");
		map.put("ORGANIZATIONALUNIT","OU, 组织部门");
		map.put("ORGANIZATION","O, 组织");
		map.put("LOCALITY","L, 城市");
		map.put("STATEORPROVINCE","ST, 省");
		map.put("DOMAINCOMPONENT","DC, 域名");
		map.put("COUNTRY","C, 国家");
		map.put("UNSTRUCTUREDADDRESS","非结构化地址(IP)");
		map.put("UNSTRUCTUREDNAME","非结构化名称(FQDN)");
		map.put("POSTALCODE","postalCode 邮编号码");
		map.put("BUSINESSCATEGORY","企业、机构类型");
		map.put("POSTALADDRESS","postalAddress 邮编地址");
		map.put("TELEPHONENUMBER","telephoneNumber 电话号码");
		map.put("PSEUDONYM","pseudonym 笔名");
		map.put("STREETADDRESS","streetAddress 街道地址");
		map.put("NAME","name 名字");
		map.put("DESCRIPTION","Description 描述");
		
		map.put("RFC822NAME","RFC822 名字");
		map.put("DNSNAME","DNS 名字");
		map.put("IPADDRESS","IP地址");
		map.put("DIRECTORYNAME","目录名");
		map.put("UNIFORMRESOURCEID","URI, 统一资源标识符");
		map.put("REGISTEREDID","已经注册的Id");
		map.put("UPN","UPN, 用户名");
		map.put("GUID","唯一ID");
		map.put("KRB5PRINCIPAL","Kerberos KPN, Kerberos 5 Principal Name");
		map.put("PERMANENTIDENTIFIER","Permanent Identifier");
		map.put("XMPPADDR","XmppAddr");
		map.put("SRVNAME","Service Name");
		map.put("SUBJECTIDENTIFICATIONMETHOD","Subject Identification Method (SIM)");
		map.put("DATEOFBIRTH","生日(yyyymmdd)");
		map.put("PLACEOFBIRTH","出生地");
		map.put("GENDER","性别(M/F)"); 
		map.put("COUNTRYOFCITIZENSHIP","国籍(ISO3166) ");
		map.put("COUNTRYOFRESIDENCE","居住国(ISO3166)");
		return map;
	}
	
	public Map<Integer, String> loadDefaultCertProfile(){
		Map<Integer, String> defaultCertificateProfileMap = new HashMap<Integer, String>();
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_ENDUSER),CertificateProfile.ENDUSERPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_SUBCA),CertificateProfile.SUBCAPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_ROOTCA),CertificateProfile.ROOTCAPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_OCSPSIGNER),CertificateProfile.OCSPSIGNERPROFILENAME);
		defaultCertificateProfileMap.put(Integer.valueOf(CertificateProfileConstants.CERTPROFILE_FIXED_SERVER),CertificateProfile.SERVERPROFILENAME);
		return defaultCertificateProfileMap;
	}
}
