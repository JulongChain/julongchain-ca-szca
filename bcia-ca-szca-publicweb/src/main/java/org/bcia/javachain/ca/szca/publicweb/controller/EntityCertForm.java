package org.bcia.javachain.ca.szca.publicweb.controller;

import org.springframework.web.multipart.MultipartFile;

 
public class EntityCertForm {
	private String serialNum=null;
 	private String user =null;
 
 	private String subjectDn=null;
 	private String issuerDn=null;
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getSubjectDn() {
		return subjectDn;
	}
	public void setSubjectDn(String subjectDn) {
		this.subjectDn = subjectDn;
	}
	public String getIssuerDn() {
		return issuerDn;
	}
	public void setIssuerDn(String issuerDn) {
		this.issuerDn = issuerDn;
	}
 
 	 
}
