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

package org.bcia.javachain.ca.szca.publicweb.servlet;

import java.io.IOException;
import java.security.Security;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.service.ISzcaApiService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class LoadConfigServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String SPRING_MVC_CONTEXT_KEY="org.springframework.web.servlet.FrameworkServlet.CONTEXT.SpringMVC";
	private static final Logger log = LoggerFactory.getLogger(LoadConfigServlet.class);
	
	
	private XmlWebApplicationContext getAppContext(HttpServletRequest req) {

		ServletContext servContext = req.getSession().getServletContext();

//		Enumeration<String> ars = servContext.getAttributeNames();
//		while (ars.hasMoreElements()) {
//			String name = ars.nextElement();
//			Object ao = servContext.getAttribute(name);
//			System.out.println("====name=" + name + "==>>" + ao.getClass().getName());
//		}
		 
		XmlWebApplicationContext context = null;
		Object o = servContext.getAttribute(SPRING_MVC_CONTEXT_KEY);
		if (o != null && o instanceof XmlWebApplicationContext)
			context = (XmlWebApplicationContext) o;
 
		return context;
	}
	private <T> T  getBean(HttpServletRequest req, Class<T> beanClass) throws Exception{
		log.debug(String.format("获取指定类[%s]的实例。",beanClass.getName()));
		XmlWebApplicationContext context = this.getAppContext(req);
		if(context ==null)
			throw new Exception("无法获取SpringMVC的上下文环境。");
		T bean = context.getBean(beanClass);
		if(bean==null)
			throw new Exception(String.format("无法获取指定类[%s]的实例。",beanClass.getName()));
		
		return bean;
	}
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		log.info("==init LoadConfigServlet==");
		if(Security.getProvider("BC") ==null )
			Security.addProvider(new BouncyCastleProvider());
		 
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException, ServletException {
		try {
			//CaSessionLocal bean = this.getBean(req, CaSessionLocal.class);
			ISzcaApiService   bean = this.getBean(req, ISzcaApiService.class);
			System.out.println("------"+bean);
		}catch(Exception e) {
			e.printStackTrace();
		}
		 
		 
	}
}
