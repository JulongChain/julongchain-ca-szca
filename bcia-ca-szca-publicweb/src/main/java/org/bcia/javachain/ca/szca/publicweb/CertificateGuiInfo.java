
package org.bcia.javachain.ca.szca.publicweb;

import java.security.cert.Certificate;

import org.cesecore.util.CertTools;

public class CertificateGuiInfo {
	
	private Certificate mCurrentCert;


	/**
	 * default constructor.
	 */
	public CertificateGuiInfo(Certificate cert) {
		mCurrentCert = cert;
	}

	public String getIssuerDN() {
		return CertTools.getIssuerDN(mCurrentCert);
	}

	public String getSubjectDN() {
		return CertTools.getSubjectDN(mCurrentCert);
	}

}
