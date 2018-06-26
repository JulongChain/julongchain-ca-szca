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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.bcia.javachain.ca.szca.admin.audit.LogSearchBean;
import org.bcia.javachain.ca.szca.util.ServletUtils;
import org.cesecore.audit.AuditLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.LoginUser;
import com.szca.wfs.common.WebUtils;

@Controller
public class LogSearchController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	LogSearchBean logSearchBean;

	@RequestMapping("/audit/logSearch")
	public ModelAndView search(HttpServletRequest request, LogSearchForm form) {
		logger.info("==========search");

		List<AuditLogEntry> logList = null;
		String msg = "";
		Map<String,String> paramMap = Collections.emptyMap();
		try {
			// crlBean.initialize(request);
			LoginUser user = (LoginUser) request.getSession().getAttribute(LoginUser.LOGIN_USER);
			// obj !=null AuthenticationToken
			// caList = crlBean.getAllCa(user.getAuthenticationToken());
			// aalist =
			// caFunctionBean.getAuthorizedTokensAndCas(user.getAuthenticationToken());
			
			paramMap = ServletUtils.getParameters(request);
			logList = logSearchBean.searchLog(paramMap,user.getAuthenticationToken());

		} catch (Exception e) {
			msg = String.format("cafunctions    failed: " + e.getMessage());
		}

		int rc = 0;
		if (logList != null && !logList.isEmpty())
			rc = (int) logList.size();

		ModelAndView view = new ModelAndView("/audit/logSearch");
		view.addObject("totalRowsCount", rc);
		view.addObject("currentPage", form.getCurrentPage());
		view.addObject("rowsPerPage", form.getRowsPerPage());
		view.addObject("resultList", WebUtils.getPageList(logList, form.getCurrentPage(), form.getRowsPerPage()));
		
		ServletUtils.putParam(view, paramMap);
		
		return view;
	}

}
