/*
 * **
 *  *
 *  * Copyright © 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 *  * Copyright © 2018  SZCA. All Rights Reserved.
 *  * <p>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package org.bcia.javachain.ca.szca.publicweb.controller;

import org.springframework.web.multipart.MultipartFile;

 
public class EnrollCertForm {
	private String password=null;
 	private String username=null;
// 	private String openvpn=null;
 	private String certProfile=null;
 	private String keyLength="2048";
// 
//    private String keylengthstring=null;
// 	private String keyalgstring=null;
 	private String  keyAlg;
 	private MultipartFile csrFile;
 	private String csr ;
 	
 	//默认是PKCS#12
 	private int tokenType=2;
 	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCertProfile() {
		return certProfile;
	}
	public void setCertProfile(String certProfile) {
		this.certProfile = certProfile;
	}
	public String getKeyLength() {
		return keyLength;
	}
	public void setKeyLength(String keyLength) {
		this.keyLength = keyLength;
	}
	public String getKeyAlg() {
		return keyAlg;
	}
	public void setKeyAlg(String keyAlg) {
		this.keyAlg = keyAlg;
	}
	public MultipartFile getCsrFile() {
		return csrFile;
	}
	public void setCsrFile(MultipartFile csrFile) {
		this.csrFile = csrFile;
	}
	public String getCsr() {
		return csr;
	}
	public void setCsr(String csr) {
		this.csr = csr;
	}
	public int getTokenType() {
		return tokenType;
	}
	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}
 	
 	
 	
 	
}
