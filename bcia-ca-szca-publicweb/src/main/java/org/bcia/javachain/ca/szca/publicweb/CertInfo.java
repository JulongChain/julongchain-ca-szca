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

package org.bcia.javachain.ca.szca.publicweb;

public class CertInfo {
	private String csr;
	private String pubKey;
	private String cn;
	private String o;
	private String ou;
	private String c;
	private String l;
	private String s;
	private String keySize;
	private String signAlg;
	
	public String getCsr() {
		return csr;
	}
	public void setCsr(String csr) {
		this.csr = csr;
	}
	public String getPubKey() {
		return pubKey;
	}
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getO() {
		return o;
	}
	public void setO(String o) {
		this.o = o;
	}
	public String getOu() {
		return ou;
	}
	public void setOu(String ou) {
		this.ou = ou;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	public String getL() {
		return l;
	}
	public void setL(String l) {
		this.l = l;
	}
	public String getS() {
		return s;
	}
	public void setS(String s) {
		this.s = s;
	}
	
	public String getKeySize() {
		return keySize;
	}
	public void setKeySize(String keySize) {
		this.keySize = keySize;
	}
	public String getSignAlg() {
		return signAlg;
	}
	public void setSignAlg(String signAlg) {
		this.signAlg = signAlg;
	}
	
	
}
