package org.bcia.javachain.ca.szca.publicweb.controller;

import java.io.ByteArrayOutputStream;
import java.security.KeyStore;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestInstance;
import org.bcia.javachain.ca.szca.publicweb.api.service.BciaRequestResult;
import org.bcia.javachain.ca.szca.publicweb.service.InspectService;
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
public class EntityCertController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	InspectService inspectService;

	@RequestMapping("/user_cert")
	public ModelAndView enrollCert() {
		List<String> caList = inspectService.getCaList();
		ModelAndView view = new ModelAndView("user_cert");
		view.addObject("caList", caList);
		return view;
	}

	@RequestMapping("/downloadEndEntityCert")
	public void downloadEndEntityCert(HttpServletRequest req, HttpServletResponse res, EntityCertForm form) {
//	public void downloadEndEntityCert(HttpServletRequest req, HttpServletResponse res, String issuerDn,
//			String subjectDn, String serialNum, String user) {
		logger.info("==downloadEndEntityCert");
		try {
		 
			byte[] certData = inspectService.getCert(form.getIssuerDn(),form.getSubjectDn(),form.getSerialNum(),form.getUser());

			if (certData != null) {
				String filename = form.getUser();
				if (filename == null || "".equals(filename.trim()))
					filename = form.getSerialNum();
				if (filename == null || "".equals(filename.trim()))
					filename = form.getSubjectDn();
				res.setCharacterEncoding("utf-8");
				res.setContentType("application/octet-stream");
				res.setHeader("Content-disposition",
						" attachment; filename=\"" + StringTools.stripFilename(filename + ".cer") + "\"");
				res.setContentLength(certData.length);
				res.getOutputStream().write(certData);
				res.flushBuffer();
			} else {
				res.setCharacterEncoding("utf-8");
				res.setContentType("html/text");
				res.getWriter().println("无法获得指定条件对应的证书");
			}
		} catch (Exception e) {
			try {
				res.setCharacterEncoding("utf-8");
				res.setContentType("html/text");
				res.getWriter().println("系统异常：" + e.getMessage());
			} catch (Exception ee) {
				e.printStackTrace();
			}
		}
	}

}
