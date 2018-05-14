package org.bcia.javachain.ca.szca.publicweb.service;

import java.util.Collection;

import org.bcia.javachain.ca.szca.publicweb.RetrieveCaInfo;
import org.cesecore.certificates.crl.RevokedCertInfo;

public interface RetrieveService {
	public RevokedCertInfo checkStatus(String issuerDN,String sn);
	public Collection<RetrieveCaInfo> caCerts();
	public Collection<RetrieveCaInfo> caCrls();
	
}
