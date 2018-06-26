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

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.publicweb.entity.CertProcessData;
import org.bcia.javachain.ca.szca.publicweb.service.RegisterEntityService;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.certificate.certextensions.CustomCertificateExtension;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.cesecore.certificates.endentity.EndEntityInformation;
import org.ejbca.core.ejb.ra.raadmin.EndEntityProfileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.bcia.javachain.ca.szca.common.cesecore.certificates.ca.CaSessionLocal;




@Controller
public class RegisterEntityController extends BaseController{
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
	@Autowired 
	RegisterEntityService  registerEntityService;
	@Autowired
	CaSessionLocal caSession;

	
	@RequestMapping("/registerEntity")
	public ModelAndView applyEntitys(HttpServletRequest request,String currentPage) {
		logger.info("==========registerEntity");
		List<CertProcessData> certProcessDatas = null;
		String msg = null;
		try {
			certProcessDatas = registerEntityService.getValidCertProccessList(entityManager);
		} catch (Exception e) {
			e.printStackTrace();
			msg = String.format("Cert Process Search Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("/registerEntity");
		view.addObject("certProcessDatas", certProcessDatas);
		view.addObject("msg", msg);
		return view;

	}
	
	@RequestMapping("/addEntity")
	public ModelAndView addEntity(HttpServletRequest request,String currentPage) {
		logger.info("==========addEntity");
		String msg = null;
		LinkedHashMap<String, Object> tranProfileMap = null;
		CertProcessData certProcessData = null;
		List<CustomCertificateExtension> certificateExtensions = null;
		try {
			long processId = Long.valueOf(request.getParameter("processId"));
			certProcessData = registerEntityService.getCertProccess(entityManager, processId);
			EndEntityProfileData epd = EndEntityProfileData.findByProfileName(entityManager, certProcessData.getEndEntityProfileName());
			CertificateProfileData cpd = CertificateProfileData.findByProfileName(entityManager, certProcessData.getCertProfileName());
			tranProfileMap = registerEntityService.loadProcessEntityDn(entityManager, epd);
			if((boolean) tranProfileMap.get("USEEXTENSIONDATA"))
				certificateExtensions = registerEntityService.getCustomCertificateExtension(entityManager, cpd);
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = String.format("Cert Process Search Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("/addEntity");
		view.addObject("tranProfileMap", tranProfileMap);
		view.addObject("certProcessData", certProcessData);
		view.addObject("certificateExtensions",certificateExtensions);
		view.addObject("msg", msg);
		return view;

	}
	
	@RequestMapping("/handleEntity")
	public ModelAndView handleEntity(HttpServletRequest request,String currentPage) {
		logger.info("==========handleEntity");
		String msg = null;
		try {
			AuthenticationToken admin =  this.getAuthenticationToken(request);
			EndEntityInformation entity = registerEntityService.createEndEntityInformation(entityManager,request);
			apiService.addEndEntityWithPassword(admin, entity);
			msg = String.format("实体注册成功!");
		} catch (Exception e) {
			e.printStackTrace();
			msg = String.format("实体注册失败: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("/handleEntity");
		view.addObject("msg", msg);
		return view;

	}
	
	
}
