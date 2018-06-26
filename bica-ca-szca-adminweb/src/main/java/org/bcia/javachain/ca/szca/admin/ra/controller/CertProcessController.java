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

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.admin.ca.CaManagementBean;
import org.bcia.javachain.ca.szca.admin.ra.entity.CertProcessData;
import org.bcia.javachain.ca.szca.admin.ra.servcie.CertProcessService;
import org.cesecore.certificates.ca.CAData;
import org.cesecore.certificates.certificateprofile.CertificateProfileData;
import org.ejbca.core.ejb.ra.raadmin.EndEntityProfileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CertProcessController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@PersistenceContext//(unitName = CesecoreConfiguration.PERSISTENCE_UNIT)
	private EntityManager entityManager;
	@Autowired 
	CertProcessService  certProcessService;
	@Autowired
	CaManagementBean caBean ;
	
	@RequestMapping("/ra/certProcessList")
	public ModelAndView endEntityProfileList(HttpServletRequest request,String currentPage) {
		logger.info("==========certProcessList");
		List<CertProcessData> certProcessDatas = null;
		String msg = null;
		try {
			certProcessDatas = certProcessService.getAllCertProccessList(entityManager);
		} catch (Exception e) {
			msg = String.format("End Entity Profile Search Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("/ra/certProcessList");
		view.addObject("certProcessDatas", certProcessDatas);
		view.addObject("msg", msg);
		return view;

	}
	
	@RequestMapping("/ra/addCertProcess")
	public ModelAndView addCertProcess(HttpServletRequest request,String currentPage) {
		logger.info("==========addCertProcess");
		List<CertificateProfileData> certificateProfileDatas = null;
		List<EndEntityProfileData> endEntityProfileDatas = null;
		List<CAData> cas = null;
		String msg = null;
		try {
			certificateProfileDatas = CertificateProfileData.findAll(entityManager);
			endEntityProfileDatas = EndEntityProfileData.findAll(entityManager);
			cas = caBean.getCaList();
		} catch (Exception e) {
			msg = String.format("addCertProcess Init Search Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("/ra/addCertProcess");
		view.addObject("certificateProfileDatas", certificateProfileDatas);
		view.addObject("endEntityProfileDatas", endEntityProfileDatas);
		view.addObject("cas", cas);
		view.addObject("msg", msg);
		return view;

	}
	
	@RequestMapping("/ra/handleCertProcess")
	@Transactional
	public ModelAndView handleCertProcess(HttpServletRequest request,String currentPage) {
		logger.info("==========handleCertProcess");
		String msg = null;
		try {
			String processName = request.getParameter("processName");
			String certProfileName = request.getParameter("certProfileName");
			String endEntityProfileName = request.getParameter("endEntityProfileName");
			String caName = request.getParameter("caName");
			int status = Integer.parseInt(request.getParameter("status"));
			String memo = request.getParameter("memo");
			CertProcessData cpd = new CertProcessData();
			cpd.setCertProfileName(certProfileName);
			cpd.setProcessName(processName);
			cpd.setEndEntityProfileName(endEntityProfileName);
			cpd.setStatus(status);
			cpd.setCreateTime(new Date());
			cpd.setCaName(caName);
			cpd.setMemo(memo);
			certProcessService.addCertProcess(entityManager, cpd);
		} catch (Exception e) {
			msg = String.format("Handle Cert Process Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("redirect:/ra/certProcessList.html");
		return view;

	}
	
	@RequestMapping("/ra/editCertProcess")
	public ModelAndView editCertProcess(HttpServletRequest request,String currentPage) {
		logger.info("==========editCertProcess");
		String msg = null;
		CertProcessData cpd = null;
		List<CertificateProfileData> certificateProfileDatas = null;
		List<EndEntityProfileData> endEntityProfileDatas = null;
		List<CAData> cas = null;
		try {
			long id = Long.valueOf(request.getParameter("id"));
			cpd = certProcessService.getCertProcess(entityManager, id);
			certificateProfileDatas = CertificateProfileData.findAll(entityManager);
			endEntityProfileDatas = EndEntityProfileData.findAll(entityManager);
			cas = caBean.getCaList();
		} catch (Exception e) {
			msg = String.format("Find Cert Process Failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/ra/editCertProcess");
		view.addObject("cpd", cpd);
		view.addObject("certificateProfileDatas", certificateProfileDatas);
		view.addObject("endEntityProfileDatas", endEntityProfileDatas);
		view.addObject("cas", cas);
		return view;

	}
	
	@RequestMapping("/ra/delCertProcess")
	public ModelAndView delCertProcess(HttpServletRequest request,String currentPage) {
		logger.info("==========editCertProcess");
		String msg = "";
		try {
			long id = Long.valueOf(request.getParameter("processId"));
			certProcessService.delCertProcess(entityManager, id);
		} catch (Exception e) {
			msg = String.format("Find Cert Process Failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("redirect:/ra/certProcessList.html");
		return view;

	}
	
	@RequestMapping("/ra/updateCertProcess")
	@Transactional
	public ModelAndView updateCertProcess(HttpServletRequest request,String currentPage) {
		logger.info("==========updateCertProcess");
		String msg = null;
		try {
			long processId = Long.valueOf(request.getParameter("processId"));
			String processName = request.getParameter("processName");
			String certProfileName = request.getParameter("certProfileName");
			String endEntityProfileName = request.getParameter("endEntityProfileName");
			String caName = request.getParameter("caName");
			int status = Integer.parseInt(request.getParameter("status"));
			String memo = request.getParameter("memo");
			CertProcessData cpd = certProcessService.getCertProcess(entityManager, processId);
			cpd.setCertProfileName(certProfileName);
			cpd.setProcessName(processName);
			cpd.setEndEntityProfileName(endEntityProfileName);
			cpd.setStatus(status);
			cpd.setMemo(memo);
			cpd.setCaName(caName);
			certProcessService.updateCertProcess(entityManager, cpd);
		} catch (Exception e) {
			e.printStackTrace();
			msg = String.format("Update Cert Process Failed: " + e.getMessage());
		}
		
		ModelAndView view = new ModelAndView("redirect:/ra/certProcessList.html");
		return view;

	}
}
