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

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.util.Collection;

import org.bcia.javachain.ca.szca.publicweb.RetrieveCaInfo;
import org.bcia.javachain.ca.szca.publicweb.service.RetrieveService;
import org.cesecore.certificates.ca.CAInfo;
import org.cesecore.certificates.crl.RevokedCertInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 

@Controller
public class RetrieveController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	private RetrieveService service;
 
	 
	/**
	 * @param issuerDN
	 * @param serno
	 * @return
	 */
	@RequestMapping("check_status")
	public ModelAndView check_status(String issuerDN,String serno) {
		logger.info(String.format("check_status for cert: issuerDN=%s; SN=%s", issuerDN,serno));
		ModelAndView view = new ModelAndView("/retrieve/check_status");
		
		if((issuerDN==null || "".equals(issuerDN))&& (serno ==null|| "".equals(serno)))
			view.addObject("showResult", false);
		else
			view.addObject("showResult", true);
		
		RevokedCertInfo certInfo = service.checkStatus(issuerDN, serno);
		view.addObject("issuerDN", issuerDN);
		view.addObject("serno", serno);	
		view.addObject("certInfo", certInfo);	
		return view;

	}
	/**
	 * @return
	 */
	@RequestMapping("ca_certs")
	public ModelAndView ca_certs() {
		logger.info("ca_certs");
		ModelAndView view = new ModelAndView("ca_certs");
		Collection<RetrieveCaInfo> availableCAs = service.caCerts(); 
 		view.addObject("availableCAs", availableCAs);	
		return view;

	}
	
}