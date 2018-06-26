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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.result.Result;
import org.bcia.javachain.ca.szca.admin.crl.CrlBean;
import org.bcia.javachain.ca.szca.admin.crl.CrlStrategyData;
import org.bcia.javachain.ca.szca.admin.crl.service.CrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.AjaxActionResult;
import com.szca.common.LoginUser;

@Controller
public class CaFunctionsController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	CrlBean crlBean;

	@RequestMapping("/ca/cafunctions")
	public ModelAndView cafunctions(HttpServletRequest request) {
		logger.info("cafunctions");
		List<CaInfoHolder> caList = null;
		List<CrlStrategyData> crlStrategyList =null;
		String msg = "";
		try {
			// crlBean.initialize(request);
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			// obj !=null AuthenticationToken
			caList = crlBean.getAllCa(user.getAuthenticationToken());
			crlStrategyList = crlBean.getAllCrlStrategyData();
			
		} catch (Exception e) {
			msg = String.format("cafunctions    failed: " + e.getMessage());
		}
		ModelAndView view = new ModelAndView("/ca/cafunctions");
		view.addObject("caList", caList);
		view.addObject("crlStrategyList", crlStrategyList);
		return view;
	}

	@RequestMapping("/ca/createCrl")
	public ModelAndView createCrl(HttpServletRequest request, String caId) {
		logger.info("createCrl:" + caId);
		String msg = "";
		try {
			int caid = Integer.parseInt(caId);
			// crlBean.initialize(request);
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			crlBean.createCRL(caid, user.getAuthenticationToken());
			msg = "";
		} catch (Exception e) {
			msg = String.format("Create CRL for CA[%s] failed: " + e.getMessage(), caId);
		}
		logger.info(msg);

		return new ModelAndView("redirect:/ca/cafunctions.html");
	}

	@RequestMapping("/ca/updateCrlStrategy")
	public void updateCrlStrategy(HttpServletRequest request, HttpServletResponse res, String caId, String cronExpression) {
		logger.info("updateCrlStrategy:" + caId + ", " + cronExpression);

		AjaxActionResult result = new AjaxActionResult();
		String msg = "";
		try {
			int caid = Integer.parseInt(caId);

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);

			Result crlResult = crlBean.udpateCrlStrategyData(caid, cronExpression, user);
			// crlBean.initialize(request);
			msg = crlResult.getMsg();
			result.setSuccess(crlResult.isSuccess());
		} catch (Exception e) {
			msg = String.format("切换CA[%s]的CRL策略状态失败失败: " + e.getMessage(), caId);

			result.setResultCode(-1);
			result.setSuccess(false);
			result.setException(e);

		}
		result.setMessage(msg);
		logger.info(msg);

		try {
			res.setContentType("text/json;charset=utf-8");
			res.getWriter().write(result.getJSONString());
			res.getWriter().flush();
			res.getWriter().close();
		} catch (Exception e) {
			msg = String.format("切换CA[%s]的CRL策略状态失败失败:%s。", caId, e.getMessage());
			logger.error(msg);
			e.printStackTrace();
		}
	}

	@RequestMapping("/ca/switchCrlStrategy")
	public void switchCrlStrategy(HttpServletRequest request, HttpServletResponse res, String caId) {
		logger.info("switchCrlStrategy:" + caId);
		AjaxActionResult result = new AjaxActionResult();
		String msg = "";
		try {
			int caid = Integer.parseInt(caId);

			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			// crlBean.createCRL(caid,user.getAuthenticationToken());
			Result crlResult = crlBean.switchCrlStrategyData(caid, user);
			// crlBean.initialize(request);
			msg = crlResult.getMsg();
			result.setSuccess(crlResult.isSuccess());
		} catch (Exception e) {
			msg = String.format("切换CA[%s]的CRL策略状态失败失败: " + e.getMessage(), caId);

			result.setResultCode(-1);
			result.setSuccess(false);
			result.setException(e);

		}
		result.setMessage(msg);
		logger.info(msg);

		try {
			res.setContentType("text/json;charset=utf-8");
			res.getWriter().write(result.getJSONString());
			res.getWriter().flush();
			res.getWriter().close();
		} catch (Exception e) {
			msg = String.format("切换CA[%s]的CRL策略状态失败失败:%s。", caId, e.getMessage());
			logger.error(msg);
			e.printStackTrace();
		}
	}

}
