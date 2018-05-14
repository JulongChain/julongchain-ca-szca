package org.bcia.javachain.ca.szca.publicweb.service;

import java.util.List;

public interface InspectService {
	public org.bcia.javachain.ca.szca.publicweb.CertAndRequestDumpBean inspectReqFile(byte[] reqData);
	public List<String> getCaList();
	public byte[] getCert(String issuerDn,String subjectDn,String sn,String user);
}
