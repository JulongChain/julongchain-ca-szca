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

import org.cesecore.certificates.endentity.EndEntityConstants;

public class EndEntityInformationVo implements Serializable{

 
	private static final long serialVersionUID = -8335898270548592492L;
    private String username;
    private String subjectDN;
    transient private String subjectDNClean = null;
    private int caid;
    private String subjectAltName;
    private String subjectEmail;
    private String password;
    private String cardNumber;
    private String maxLoginAttempts;
    private String remainingLoginAttempts;
     
    private boolean cleartextpwd;
    
    
    /** Status of user, from {@link EndEntityConstants#STATUS_NEW} etc*/
    private int status;
    private int endentityprofileid;
    private int certificateprofileid;
    
    private String timecreated;
    private boolean keyrecoverable;

    private String timemodified;
    /** Type of token, from {@link EndEntityConstants#TOKEN_USERGEN} etc*/
    private int tokentype;
    private int hardtokenissuerid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSubjectDN() {
		return subjectDN;
	}
	public void setSubjectDN(String subjectDN) {
		this.subjectDN = subjectDN;
	}
	public String getSubjectDNClean() {
		return subjectDNClean;
	}
	public void setSubjectDNClean(String subjectDNClean) {
		this.subjectDNClean = subjectDNClean;
	}
	public int getCaid() {
		return caid;
	}
	public void setCaid(int caid) {
		this.caid = caid;
	}
	public String getSubjectAltName() {
		return subjectAltName;
	}
	public void setSubjectAltName(String subjectAltName) {
		this.subjectAltName = subjectAltName;
	}
	public String getSubjectEmail() {
		return subjectEmail;
	}
	public void setSubjectEmail(String subjectEmail) {
		this.subjectEmail = subjectEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
 
	public int getEndentityprofileid() {
		return endentityprofileid;
	}
	public void setEndentityprofileid(int endentityprofileid) {
		this.endentityprofileid = endentityprofileid;
	}
	public int getCertificateprofileid() {
		return certificateprofileid;
	}
	public void setCertificateprofileid(int certificateprofileid) {
		this.certificateprofileid = certificateprofileid;
	}
	public String getTimecreated() {
		return timecreated;
	}
	public void setTimecreated(String timecreated) {
		this.timecreated = timecreated;
	}
	public String getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(String timemodified) {
		this.timemodified = timemodified;
	}
	public int getTokentype() {
		return tokentype;
	}
	public void setTokentype(int tokentype) {
		this.tokentype = tokentype;
	}
	public int getHardtokenissuerid() {
		return hardtokenissuerid;
	}
	public void setHardtokenissuerid(int hardtokenissuerid) {
		this.hardtokenissuerid = hardtokenissuerid;
	}
	public boolean isCleartextpwd() {
		return cleartextpwd;
	}
	public void setCleartextpwd(boolean cleartextpwd) {
		this.cleartextpwd = cleartextpwd;
	}
	public boolean isKeyrecoverable() {
		return keyrecoverable;
	}
	public void setKeyrecoverable(boolean keyrecoverable) {
		this.keyrecoverable = keyrecoverable;
	}
	public String getMaxLoginAttempts() {
		return maxLoginAttempts;
	}
	public void setMaxLoginAttempts(String maxLoginAttempts) {
		this.maxLoginAttempts = maxLoginAttempts;
	}
	public String getRemainingLoginAttempts() {
		return remainingLoginAttempts;
	}
	public void setRemainingLoginAttempts(String remainingLoginAttempts) {
		this.remainingLoginAttempts = remainingLoginAttempts;
	}
 
    
    

}
