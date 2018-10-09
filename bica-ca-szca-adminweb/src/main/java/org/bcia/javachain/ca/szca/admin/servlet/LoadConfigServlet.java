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

package org.bcia.javachain.ca.szca.admin.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.admin.common.WebLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadConfigServlet  extends HttpServlet {

		private static final long serialVersionUID = 1L;
		private static final Logger log = LoggerFactory.getLogger(LoadConfigServlet.class);

	   
	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        log.info("==init LoadConfigServlet==");
	    	WebLanguages.initWebLanguages(config.getServletContext());
	    }
	    
	    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
	      
	    }

	    public void doGet(HttpServletRequest req,  HttpServletResponse res) throws java.io.IOException, ServletException { } // doGet
}
