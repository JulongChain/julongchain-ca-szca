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

import java.security.cert.X509CRL;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bcia.javachain.ca.szca.publicweb.RetrieveCaInfo;
import org.bcia.javachain.ca.szca.publicweb.service.RetrieveService;
import org.cesecore.util.CertTools;
import org.cesecore.util.StringTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class CrlController {
	private static final String MOZILLA_PROPERTY = "moz";
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private RetrieveService service;
	@Autowired
	org.bcia.javachain.ca.szca.common.cesecore.certificates.crl.CrlStoreSessionLocal crlStore;

	@RequestMapping("ca_crls")
	public ModelAndView ca_crls() {
		logger.info("ca_crls");
		ModelAndView view = new ModelAndView("ca_crls");
		Collection<RetrieveCaInfo> availableCAs = service.caCrls();
		view.addObject("availableCAs", availableCAs);
		return view;

	}

	@RequestMapping("downloadCrl")
	public void downloadCrl(HttpServletRequest req, HttpServletResponse res, String cmd, String format, String issuer) {

		logger.info(">>>>>>>>>>>>>>>>>>downloadCrl" + cmd + "-" + format + "-" + issuer);
		try {
			if (cmd == null || "".equals(cmd))
				cmd = "crl";
			if (issuer == null || "".equals(issuer))
				issuer = "CN=ManagementCA,O=EJBCA Sample,C=SE";
			// ModelAndView view = new ModelAndView("/retrieve/ca_crls");
			// Collection<RetrieveCaInfo> availableCAs = service.caCrls();
			// view.addObject("availableCAs", availableCAs);
			byte[] crl = crlStore.getLastCRL(issuer, false);

			X509CRL x509crl = CertTools.getCRLfromByteArray(crl);
			String dn = CertTools.getIssuerDN(x509crl);
			// We must remove cache headers for IE
			
			// moz is only kept for backwards compatibility, can be removed in
			// EJBCA 6.4 or 6.5
			String moz = req.getParameter(MOZILLA_PROPERTY);
			String filename = CertTools.getPartFromDN(dn, "CN") + ".crl";

			if ((moz == null) || !moz.equalsIgnoreCase("y")) {
				res.setHeader("Content-disposition",
						"attachment; filename=\"" + StringTools.stripFilename(filename) + "\"");
			}
			res.setContentType("application/x-x509-crl");

			res.setContentLength(crl.length);
			res.getOutputStream().write(crl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
