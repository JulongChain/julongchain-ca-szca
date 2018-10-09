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

package org.bcia.javachain.ca.szca.ocsp.client;

import java.util.Date;
/**
 * 客户端调用OCSP响应结果解析
 * @author zhenght
 *
 */
public class SingleOcspResp {
	
	public static final int CERT_STATUS_GOOD = 1; //证书状态正常
	public static final int CERT_STATUS_REVOKED = -1; //证书已吊销
	public static final int CERT_STATUS_UNKNOWN = 0; //未知的证书
	public static final int HTTP_REQ_RESULT_SUCCESS = 0; //HTTP请求成功
	public static final int HTTP_REQ_RESULT_CONN_FAIL = 1; //HTTP请求失败
	public static final int HTTP_REQ_RESULT_CERT_ENCODE_FAIL = 2;  //证书格式转换失败
	public static final int HTTP_REQ_RESULT_FAIL = 3;  //OCSP响应解析失败
	
	private String baseCert;  //查询状态证书
	private boolean isExistCert; //结果是否包含证书
	private int status;  //证书状态
	private Date revokeTime; //吊销时间
	private int httpResult; //OCSP请求结果
	private String errorMsg; //异常信息
	
	public SingleOcspResp(String baseCert, boolean isExistCert, int status,Date revokeTime, int httpResult, String errorMsg) {
		super();
		this.baseCert = baseCert;
		this.isExistCert = isExistCert;
		this.status = status;
		this.revokeTime = revokeTime;
		this.httpResult = httpResult;
		this.errorMsg = errorMsg;
	}

	public SingleOcspResp() {
		
	}

	public static SingleOcspResp getInstance(String baseCert, boolean isExistCert, int status,
			Date revokeTime, int httpResult, String errorMsg){
		return new SingleOcspResp(baseCert, isExistCert, status, revokeTime, httpResult, errorMsg) ;
	}
	
	
	public String getBaseCert() {
		return baseCert;
	}
	public void setBaseCert(String baseCert) {
		this.baseCert = baseCert;
	}
	public boolean isExistCert() {
		return isExistCert;
	}
	public void setExistCert(boolean isExistCert) {
		this.isExistCert = isExistCert;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getHttpResult() {
		return httpResult;
	}
	public void setHttpResult(int httpResult) {
		this.httpResult = httpResult;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Date getRevokeTime() {
		return revokeTime;
	}

	public void setRevokeTime(Date revokeTime) {
		this.revokeTime = revokeTime;
	}
	
	
}
