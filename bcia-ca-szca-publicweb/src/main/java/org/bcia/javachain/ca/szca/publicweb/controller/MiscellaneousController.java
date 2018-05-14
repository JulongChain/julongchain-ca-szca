package org.bcia.javachain.ca.szca.publicweb.controller;

import java.security.KeyPair;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.CertInfo;
import org.bcia.javachain.ca.szca.publicweb.service.MiscellaneousService;
import org.bouncycastle.asn1.x500.X500Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MiscellaneousController{
	Logger logger = LoggerFactory.getLogger(getClass());


	@Autowired
	MiscellaneousService miscellaneousService;
	
	@RequestMapping("/miscellaneous")
	public ModelAndView miscellaneous() {
		return new ModelAndView("miscellaneous");
	}
	
	@RequestMapping("/createCsr")
	public ModelAndView inputCsr() {
		return new ModelAndView("createCsr");
	}
	
	@RequestMapping("/genCsr")
	public ModelAndView genCsr(HttpServletRequest req, HttpServletResponse res) {
		String cn = req.getParameter("cn");
		String o = req.getParameter("o");
		String ou = req.getParameter("ou");
		String l = req.getParameter("l");
		String s = req.getParameter("s");
		String c = req.getParameter("c");
		String keyType = req.getParameter("keyType");
		X500Name subjectDn = miscellaneousService.getSubjectDn(cn, o, ou, l, s, c, null);
		KeyPair kp = miscellaneousService.genKeyPair(keyType);
		ModelAndView view = new ModelAndView("genCsr");
		view.addObject("csr", miscellaneousService.genCSR(kp, subjectDn, keyType));
		view.addObject("priKey", miscellaneousService.getPemPrivateKey(kp));
		return view;
	}
	
	@RequestMapping("/decodeCsr")
	public ModelAndView readCsr(HttpServletRequest req, HttpServletResponse res) {
		String p10 = req.getParameter("csr");
		CertInfo certInfo = miscellaneousService.readCSR(p10);
		ModelAndView view = new ModelAndView("decodeCsr");
		view.addObject("csrInfo", certInfo);
		return view;
	}
	
	@RequestMapping("/doValidate")
	public ModelAndView doValidate(HttpServletRequest req, HttpServletResponse res) {
		boolean flag = false;
		String priKey = req.getParameter("priKey");
		String cert = req.getParameter("cert");
		flag = miscellaneousService.validateKeyAndCert(priKey, cert);
		ModelAndView view = new ModelAndView("doValidate");
		view.addObject("isMatch", flag);
		return view;
	}
}
