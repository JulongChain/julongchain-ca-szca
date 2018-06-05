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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.admin.ca.CaFunctionBean;
import org.bcia.javachain.ca.szca.admin.ca.TokenAndCaActivationGuiComboInfo;
import org.bcia.javachain.ca.szca.admin.crl.CrlBean;
import org.cesecore.authentication.tokens.AuthenticationToken;
import org.cesecore.certificates.ca.CAInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;
import com.szca.common.exception.JspViewNotFoundException;
import com.szca.common.web.ControllerExceptionHandler;

@Controller
public class CaStatusController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	CrlBean crlBean;
	
	@Autowired
	CaFunctionBean caFunctionBean;

	@RequestMapping("/ca/caactivation")
	public ModelAndView caActivations(HttpServletRequest request) {
		logger.info("==========caActivations");
	 
		List<TokenAndCaActivationGuiComboInfo> aalist=null;
		String msg="";
		
		try {
//			crlBean.initialize(request);
			LoginUser  user =(LoginUser)request.getSession().getAttribute(LoginUser.LOGIN_USER) ;
			//obj !=null AuthenticationToken 
			//  caList = crlBean.getAllCa(user.getAuthenticationToken());
			  aalist = caFunctionBean.getAuthorizedTokensAndCas(user.getAuthenticationToken());
			 
		} catch (Exception e) {
			msg = String.format("cafunctions    failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/ca/caactivation");
		//view.addObject("cas", caList);
		view.addObject("aalist", aalist);
		//view.addObject("list", list);
		return view;
	}
	
	@RequestMapping("/ca/activation")
	public ModelAndView activation(HttpServletRequest request,int caid,String authCode,String cryptoTokenName,boolean caNewState ,boolean cryptoTokenNewState,boolean  monitoredNewState) {
		logger.info(String.format("==========activation, caId=%s; cryptoTokenName=%s]",caid,cryptoTokenName));
	 
		List<TokenAndCaActivationGuiComboInfo> aalist=null;
		String msg="";
		
		try {
//			crlBean.initialize(request);
			LoginUser  user =(LoginUser)request.getSession().getAttribute(LoginUser.LOGIN_USER) ;
			//obj !=null AuthenticationToken 
			//  caList = crlBean.getAllCa(user.getAuthenticationToken());
			  aalist = caFunctionBean.getAuthorizedTokensAndCas(user.getAuthenticationToken());
			  caFunctionBean.applyChanges(caid,cryptoTokenName , user.getAuthenticationToken(), authCode, aalist,caNewState,cryptoTokenNewState,monitoredNewState);
		} catch (Exception e) {
			msg = String.format("cafunctions    failed: " + e.getMessage());
		}
		//ModelAndView view = new ModelAndView("/ca/caactivation");
		//view.addObject("cas", caList);
		//view.addObject("aalist", aalist);
		//view.addObject("list", list);
		//return view;
		return new ModelAndView("redirect:/ca/caactivation.html");
	}
	
	

}
