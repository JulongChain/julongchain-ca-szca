/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.io.ByteArrayOutputStream;
import java.security.KeyStore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestInstance;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestResult;
import org.cesecore.keys.util.KeyTools;
import org.cesecore.util.StringTools;
import org.ejbca.core.model.SecConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EnrollCertController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	BciaRequestInstance requestInstance;

	@RequestMapping("/enrollCert")
	public ModelAndView enrollCert() {
		return new ModelAndView("enrollCert");
		// requestInstance.doPost(req, res);
	}

	@RequestMapping("/genEndEntityCert")
	public void genEndEntityCert(HttpServletRequest req, HttpServletResponse res,EnrollCertForm form ) {
		logger.info("==genEndEntityCert");
		try {
 
			
			BciaRequestResult result = requestInstance.doPost(req,form);
			if(result.isSuccess()) {
				if(result.getTokenType()==SecConst.TOKEN_SOFT_JKS)
					this.sendJKSToken(form.getUsername(), result.getResultData(), res);
				if(result.getTokenType()==SecConst.TOKEN_SOFT_P12)
					this.sendP12Token(form.getUsername(), result.getResultData(), res);
				if(result.getTokenType()==SecConst.TOKEN_SOFT_PEM)
					this.sendPEMTokens(form.getUsername(), result.getResultData(), res);
				if(result.getTokenType()==SecConst.TOKEN_SOFT_BROWSERGEN)
					this.sendCert(form.getUsername(), result.getResultData(), res);
				if(result.getTokenType()==SecConst.TOKEN_SOFT_BROWSERGEN)
					this.sendCert(form.getUsername(), result.getResultData(), res);
			}else {
				res.setCharacterEncoding("utf-8");
				res.getWriter().println("系统异常："+result.getMessage());
			}	
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	private void sendP12Token( String username, byte[] tokenData, HttpServletResponse res) throws Exception {
 
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/x-pkcs12");
		res.setHeader("Content-disposition", "filename=\"" + StringTools.stripFilename(username + ".p12") + "\"");
		res.setContentLength(tokenData.length);
 
		res.getOutputStream().write(tokenData);
		res.flushBuffer();
 
	}

	private void sendJKSToken( String username, byte[] tokenData, HttpServletResponse res) throws Exception {
 
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-disposition", "filename=\"" + StringTools.stripFilename(username + ".jks") + "\"");
		res.setContentLength(tokenData.length);
 
		res.getOutputStream().write(tokenData);
		res.flushBuffer();
 
	}

	private void sendPEMTokens( String username, byte[] tokenData, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-disposition", " attachment; filename=\"" + StringTools.stripFilename(username + ".pem") + "\"");
		res.getOutputStream().write(tokenData);
		res.flushBuffer();
	}
	private void sendCert( String username, byte[] tokenData, HttpServletResponse res) throws Exception {
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-disposition", " attachment; filename=\"" + StringTools.stripFilename(username + ".cer") + "\"");
		res.getOutputStream().write(tokenData);
		res.flushBuffer();
	}
}
