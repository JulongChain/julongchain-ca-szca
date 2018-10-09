/*
 *
 * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright © 2018  SZCA. All Rights Reserved.
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
 *
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

import org.springframework.web.multipart.MultipartFile;

 
public class EntityCertForm {
	private String serialNum=null;
 	private String user =null;
 
 	private String subjectDn=null;
 	private String issuerDn=null;
 	//
 	private int reason=0;
 	private String adminName=null;
 	private String adminPasswd=null;
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
	public int getReason() {
		return reason;
	}
	public void setReason(int reason) {
		this.reason = reason;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPasswd() {
		return adminPasswd;
	}
	public void setAdminPasswd(String adminPasswd) {
		this.adminPasswd = adminPasswd;
	}
 
 	 
}
