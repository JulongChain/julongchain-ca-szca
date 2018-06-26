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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.InitGlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.szca.common.exception.JspViewNotFoundException;
import com.szca.common.web.ControllerExceptionHandler;

@Controller
public class IndexController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@Autowired
	InitGlobalConfig initCfg;
	
	
	@RequestMapping("index")
	public ModelAndView index(String cmd, String format, String issuer) {
		return new ModelAndView("index");
	}

	@RequestMapping("clear")
	public void clear(String cmd, String format, String issuer) {
		initCfg.initSysConfig();
	}
	 
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             <br>
	 *             Note:<br>
	 *             有些简单的功能不用编写单独的Controller，则在此直接跳转到对应的jsp,<br>
	 *             比如/test/testObj.html -> /views/test/testObj.jsp
	 *             /test/a/b/c/d.html -> /views/test/a/b/c/d.jsp
	 */
	@RequestMapping(value = "/**", method = { RequestMethod.POST, RequestMethod.GET })
	public String doDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("doDefault:" + request.getRequestURI());
		String jspView = ControllerExceptionHandler.getView(request);
		if (ControllerExceptionHandler.checkJSP(request))
			return jspView;
		else
			// throw exception , ControllerExceptionHandler would catch this
			// exception
			throw new JspViewNotFoundException(String.format("View[%s] not found.", jspView));
	}

}
