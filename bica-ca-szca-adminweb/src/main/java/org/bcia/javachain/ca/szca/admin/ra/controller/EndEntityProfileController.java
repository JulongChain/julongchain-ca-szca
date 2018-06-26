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

package org.bcia.javachain.ca.szca.admin.ra.controller;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.admin.ca.CaManagementBean;
import org.bcia.javachain.ca.szca.admin.ra.servcie.CertProcessService;
import org.bcia.javachain.ca.szca.admin.ra.servcie.EndEntityProfileService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.certificates.util.DnComponents;
import org.cesecore.util.JBossUnmarshaller;
import org.ejbca.core.ejb.ra.raadmin.EndEntityProfileData;
import org.ejbca.core.model.ra.raadmin.EndEntityProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;

import cn.net.bcia.bcca.core.ejb.ra.raadmin.EndEntityProfileSessionBean;
 

@Controller
public class EndEntityProfileController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	EndEntityProfileSessionBean endEntityProfileSessionBean;
	@Autowired
	CaManagementBean caBean ;
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
	@Autowired 
	EndEntityProfileService  endEntityProfileService;
	@Autowired
	CertProcessService certProcessService;
	private Map<String, String> dnMap;
	private Map<String, String> altMap;
	private Map<String, String> dirMap;
	
	@RequestMapping("/ra/endEntityProfileList")
	public ModelAndView endEntityProfileList(HttpServletRequest request,String currentPage) {
		logger.info("==========endEntityProfileList");
		Map<Integer, String> endEntityProfiles = null;
		String msg;
		try {
			endEntityProfiles = endEntityProfileSessionBean.getEndEntityProfileIdToNameMap();
		} catch (Exception e) {
			msg = String.format("End Entity Profile Search Failed: " + e.getMessage());
		}
		if(endEntityProfiles.containsKey(1)) {
			endEntityProfiles.remove(1);
		}
		ModelAndView view = new ModelAndView("/ra/endEntityProfileList");

		view.addObject("endEntityProfiles", endEntityProfiles);
		return view;

	}
	
	@RequestMapping("/ra/addEndEntityProfile")
	@Transactional
	public ModelAndView addEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========addEndEntityProfile");
		
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有操作终端实体模板的权限。");
			//EndEntityProfileData epd = EndEntityProfileData.findByProfileName(entityManager, "EMPTY");
			endEntityProfileSessionBean.addEndEntityProfile(auth, profileName, new EndEntityProfile(true));
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		ModelAndView view = new ModelAndView("redirect:/ra/endEntityProfileList.html");
		return view;

	}
	
	@RequestMapping("/ra/removeEndEntityProfile")
	@Transactional
	public ModelAndView removeEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========removeEndEntityProfile");
		
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有操作终端实体模板的权限。");
			endEntityProfileSessionBean.removeEndEntityProfile(auth, profileName);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		ModelAndView view = new ModelAndView("redirect:/ra/endEntityProfileList.html");
		return view;

	}
	
	@RequestMapping("/ra/cloneEndEntityProfile")
	@Transactional
	public ModelAndView cloneEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========cloneEndEntityProfile");
		
		try {
			String profileName = request.getParameter("profileName");
			String orgname = request.getParameter("orgName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有操作终端实体模板的权限。");
			endEntityProfileSessionBean.cloneEndEntityProfile(auth, orgname, profileName);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		ModelAndView view = new ModelAndView("redirect:/ra/endEntityProfileList.html");
		return view;

	}
	
	@RequestMapping("/ra/renameEndEntityProfile")
	@Transactional
	public ModelAndView renameEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========renameEndEntityProfile");
		
		try {
			String profileName = request.getParameter("profileName");
			String orgname = request.getParameter("orgName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有操作终端实体模板的权限。");
			endEntityProfileSessionBean.renameEndEntityProfile(auth, orgname, profileName);
			certProcessService.updateEndEntityProfileName(entityManager, orgname, profileName);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		ModelAndView view = new ModelAndView("redirect:/ra/endEntityProfileList.html");
		return view;

	}
	
	@RequestMapping("/ra/editEndEntityProfile")
	public ModelAndView editEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========editEndEntityProfile");
		EndEntityProfileData epd = null;
		LinkedHashMap<?, ?> profileMap = null;
		LinkedHashMap<String, Object> tranProfileMap = new LinkedHashMap<String, Object>();
		List<CAData> cas = null;
		String subjectOrderValue = null;
		Map<Integer, String> defaultCertificateProfileMap = null;
		List<CertificateProfileData> certificateProfileDatas = null;
		try {
			String profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有编辑终端实体模板的权限。");
			//终端实体模板数据解析
			epd = EndEntityProfileData.findByProfileName(entityManager, profileName);
			profileMap = JBossUnmarshaller.extractLinkedHashMap(epd.getDataUnsafe());
			for(Iterator it = profileMap.keySet().iterator();it.hasNext();) {
				Object key = it.next();
				tranProfileMap.put(String.valueOf(key), profileMap.get(key));
			}
			subjectOrderValue = endEntityProfileService.loadProfileAutoJson(tranProfileMap);
			loadDnProfileMap();
			//查询CA列表
			cas = caBean.getCaList();
			//查询默认证书模板和自定义证书模板
			defaultCertificateProfileMap = endEntityProfileService.loadDefaultCertProfile();			
			certificateProfileDatas = CertificateProfileData.findAll(entityManager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("/ra/editEndEntityProfile");
		view.addObject("profileMap", tranProfileMap);
		view.addObject("profileId", epd.getId());
		view.addObject("profileName", epd.getProfileName());
		view.addObject("dnMap", dnMap);
		view.addObject("altMap", altMap);
		view.addObject("dirMap", dirMap);
		view.addObject("subjectOrderValue", subjectOrderValue);
		view.addObject("certificateProfileDatas", certificateProfileDatas);
		view.addObject("defaultCertificateProfileMap", defaultCertificateProfileMap);
		view.addObject("cas", cas);
		return view;
	}
	
	@RequestMapping("/ra/removeSubject")
	@Transactional
	public ModelAndView removeSubject(HttpServletRequest request,String currentPage) {
		logger.info("==========removeSubject");
		String profileName = null;
		try {
			int dnOrder = Integer.parseInt(request.getParameter("dnOrder"));
			profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			
			EndEntityProfile eep = endEntityProfileSessionBean.getEndEntityProfile(profileName);
			eep.removeField(dnOrder/100, dnOrder%100);
			endEntityProfileSessionBean.changeEndEntityProfile(auth, profileName, eep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ra/editEndEntityProfile.html?profileName=" + profileName);
		
		return view;
	}
	
	@RequestMapping("/ra/addSubject")
	@Transactional
	public ModelAndView addSubject(HttpServletRequest request,String currentPage) {
		logger.info("==========addSubject");
		String profileName = null;
		try {
			int dnOrder = Integer.parseInt(request.getParameter("dnOrder"));
			profileName = request.getParameter("profileName");
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			
			EndEntityProfile eep = endEntityProfileSessionBean.getEndEntityProfile(profileName);
			eep.addField(dnOrder);
			endEntityProfileSessionBean.changeEndEntityProfile(auth, profileName, eep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ra/editEndEntityProfile.html?profileName=" + profileName);
		
		return view;
	}
	
	@RequestMapping("/ra/handleEndEntityProfile")
	@Transactional
	public ModelAndView handleEndEntityProfile(HttpServletRequest request,String currentPage) {
		logger.info("==========handleEndEntityProfile");
		try {
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			AuthenticationToken auth = user.getAuthenticationToken();
			if (auth == null)
				throw new Exception("没有查询证书模板的权限。");
			String profileName = request.getParameter("profileName");
			EndEntityProfile eep = endEntityProfileSessionBean.getEndEntityProfile(profileName);
			endEntityProfileService.handleProfileChange(request, eep);
			endEntityProfileSessionBean.changeEndEntityProfile(auth, profileName, eep);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ModelAndView view = new ModelAndView("redirect:/ra/endEntityProfileList.html");
		return view;
	}
	
	private void loadDnProfileMap() {
		Map<String, Integer> pfMap = DnComponents.getProfilenameIdMap();
		Map<String, String> loadMap =  endEntityProfileService.loadProfileMap();
		dnMap = new LinkedHashMap<String, String>();
		altMap = new LinkedHashMap<String, String>();
		dirMap = new LinkedHashMap<String, String>();
		for(Iterator<String> it = (Iterator<String>) loadMap.keySet().iterator();it.hasNext();) {
			String key = it.next();
			if(pfMap.get(key) != null && loadMap.get(key) != null) {
				if(DnComponents.isDnProfileField(key)) 
					dnMap.put(String.valueOf(pfMap.get(key)), loadMap.get(key)); 
				if(DnComponents.isAltNameField(key)) 
					altMap.put(String.valueOf(pfMap.get(key)), loadMap.get(key)); 
				if(DnComponents.isDirAttrField(key)) 
					dirMap.put(String.valueOf(pfMap.get(key)), loadMap.get(key)); 
			}
		}
	} 
}
