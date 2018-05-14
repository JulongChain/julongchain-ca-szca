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
