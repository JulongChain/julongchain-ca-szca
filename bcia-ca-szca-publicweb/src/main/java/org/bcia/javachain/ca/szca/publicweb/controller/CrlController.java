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
	cn.net.bcia.cesecore.certificates.crl.CrlStoreSessionLocal crlStore;

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
