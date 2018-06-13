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

package org.bcia.javachain.ca.szca.ocsp.object;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
/**
 * OCSP签名证书对象
 * @author zhenght
 *
 */
public class OcspSigner {
	private String issuerFinger;  //颁发者SHA1 HASH
	private String issuerDN;  //颁发者DN
	private PrivateKey priKey; //OCSP响应签名证书私钥
	private X509Certificate cert; //OCSP响应签名证书
	private String alias;  //证书别名（保留项）
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getIssuerFinger() {
		return issuerFinger;
	}
	public void setIssuerFinger(String issuerFinger) {
		this.issuerFinger = issuerFinger;
	}
	public PrivateKey getPriKey() {
		return priKey;
	}
	public void setPriKey(PrivateKey priKey) {
		this.priKey = priKey;
	}
	public X509Certificate getCert() {
		return cert;
	}
	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}
	public String getIssuerDN() {
		return issuerDN;
	}
	public void setIssuerDN(String issuerDN) {
		this.issuerDN = issuerDN;
	}
}
