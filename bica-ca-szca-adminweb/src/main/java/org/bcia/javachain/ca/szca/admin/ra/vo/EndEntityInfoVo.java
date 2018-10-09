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

package org.bcia.javachain.ca.szca.admin.ra.vo;

import java.io.Serializable;

public class EndEntityInfoVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6909786503437799567L;
	private String caName;
	private String commonName;
	private String subjectDNFieldOU;
	private String subjectDNFieldO;
	private String status;
	private boolean viewendentity;
	private boolean authorizedCaView_cert;
	private boolean authorizedHardtokenViewRights;
	private boolean authorizedRaHistoryRights;
	private String username;
	public String getCaName() {
		return caName;
	}
	public void setCaName(String caName) {
		this.caName = caName;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getSubjectDNFieldOU() {
		return subjectDNFieldOU;
	}
	public void setSubjectDNFieldOU(String subjectDNFieldOU) {
		this.subjectDNFieldOU = subjectDNFieldOU;
	}
	public String getSubjectDNFieldO() {
		return subjectDNFieldO;
	}
	public void setSubjectDNFieldO(String subjectDNFieldO) {
		this.subjectDNFieldO = subjectDNFieldO;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isViewendentity() {
		return viewendentity;
	}
	public void setViewendentity(boolean viewendentity) {
		this.viewendentity = viewendentity;
	}
	public boolean isAuthorizedCaView_cert() {
		return authorizedCaView_cert;
	}
	public void setAuthorizedCaView_cert(boolean authorizedCaView_cert) {
		this.authorizedCaView_cert = authorizedCaView_cert;
	}
	public boolean isAuthorizedHardtokenViewRights() {
		return authorizedHardtokenViewRights;
	}
	public void setAuthorizedHardtokenViewRights(boolean authorizedHardtokenViewRights) {
		this.authorizedHardtokenViewRights = authorizedHardtokenViewRights;
	}
	public boolean isAuthorizedRaHistoryRights() {
		return authorizedRaHistoryRights;
	}
	public void setAuthorizedRaHistoryRights(boolean authorizedRaHistoryRights) {
		this.authorizedRaHistoryRights = authorizedRaHistoryRights;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	
	
	
}
