/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

import java.security.KeyPair;
import java.security.Security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.CertInfo;
import org.bcia.javachain.ca.szca.publicweb.service.MiscellaneousService;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
		Security.addProvider(new BouncyCastleProvider());
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
		view.addObject("priKey", miscellaneousService.getPemPrivateKey(kp, keyType));
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
