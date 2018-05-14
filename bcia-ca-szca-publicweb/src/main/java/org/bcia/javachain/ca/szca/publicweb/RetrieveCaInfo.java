package org.bcia.javachain.ca.szca.publicweb;

import java.util.Collection;

import org.cesecore.certificates.ca.CAInfo;

 
 

public class RetrieveCaInfo {
	private int caid;
	private CAInfo ca;
	private Collection<CertificateGuiInfo> chain;
	
	public int getCaid() {
		return caid;
	}
	public void setCaid(int caid) {
		this.caid = caid;
	}
	public CAInfo getCa() {
		return ca;
	}
	public void setCa(CAInfo ca) {
		this.ca = ca;
	}
	public Collection<CertificateGuiInfo> getChain() {
		return chain;
	}
	public void setChain(Collection<CertificateGuiInfo> chain) {
		this.chain = chain;
	} 
	
	
}
