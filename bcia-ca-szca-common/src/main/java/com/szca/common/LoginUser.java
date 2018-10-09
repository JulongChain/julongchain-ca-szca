
package com.szca.common;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import org.cesecore.authentication.tokens.AuthenticationToken;


/**
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common<br>
 * File Name : LoginUser.java<br>
 * Type Name : LoginUser<br>
 * Created on : 2017-2-7 下午4:21:14<br>
 * Created by : JackyLuo <br>
 * Note:<br>
 *
 * 
 */
public class LoginUser {
	public static final String LOGIN_USER = "LOGIN_USER_KEY_IN_SESSION";
	public static final String SUPPER_RIGHT = "*";
	private String username;
	private String passwd;
	private String fullName;
	private boolean success;
	private String message;
	private Long loginId;
	private Long loginTime;
	private String sn;
	private String dn;
	private String issuerDn;
	private Certificate cert;
	private String loginClientIp;
	private AuthenticationToken authenticationToken =null; 
	private org.cesecore.authentication.tokens.AuthenticationToken authenticationToken_2 =null; 
	private List<String> rightCodeTable = null;

	public String toString() {
		return "username:" + username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public long getLoginId() {
		return loginId;
	}

	public void setLoginId(long loginId) {
		this.loginId = loginId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	 

	
	
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getIssuerDn() {
		return issuerDn;
	}

	public void setIssuerDn(String issuerDn) {
		this.issuerDn = issuerDn;
	}

	public Certificate getCert() {
		return cert;
	}

	public void setCert(Certificate cert) {
		this.cert = cert;
	}

	public String getLoginClientIp() {
		return loginClientIp;
	}

	public void setLoginClientIp(String loginClientIp) {
		this.loginClientIp = loginClientIp;
	}

	public void addRights(String rightCode) {
		if (rightCodeTable == null)
			rightCodeTable = new ArrayList<String>();
		rightCodeTable.add(rightCode.toUpperCase());
	}

	/**
	 * 检查是否包含了指定权限
	 * @param rightCode
	 * @return
	 */
	public boolean hasRight(String rightCode) {
		if (rightCode == null || "".equals(rightCode.trim()))
			return true;

		if (rightCodeTable == null)
			return false;
		else
			return rightCodeTable.contains(rightCode.toUpperCase()) || rightCodeTable.contains(SUPPER_RIGHT);
	}

	/**
	 * 检查是否包含了一组权限中的任何一个
	 * @param rightCodePrefix
	 * @return
	 */
	public boolean likeRight(String rightCodePrefix) {
		if (rightCodePrefix == null || "".equals(rightCodePrefix.trim()))
			return false;

		if (rightCodeTable == null)
			return false;

		boolean result = false;
		for (String rc : rightCodeTable) {
			if (rc.toUpperCase().startsWith(rightCodePrefix) || "*".equals(rc)) {
				result = true;
				break;
			}
		}
		return result;

	}

	public AuthenticationToken getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(AuthenticationToken authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

//	public org.cesecore.authentication.tokens.AuthenticationToken getAuthenticationToken_2() {
//		return authenticationToken_2;
//	}
//
//	public void setAuthenticationToken_2(org.cesecore.authentication.tokens.AuthenticationToken authenticationToken_2) {
//		this.authenticationToken_2 = authenticationToken_2;
//	}
	
	
}
