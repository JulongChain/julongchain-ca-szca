/*
 * Copyright ? 2018  深圳市电子商务安全证书管理有限公司(SZCA,深圳CA) 版权所有
 * Copyright ? 2018  SZCA. All Rights Reserved.
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
 */

package org.bcia.javachain.ca.szca.publicweb.api.service;

public class BciaRequestResult { 
	public static String RESULT_CODE_SUCCESS = "001";
	public static String RESULT_CODE_USERNAME_EXISTS_ERROR = "002";
	public static String RESULT_CODE_USER_NO_EXISTS_ERROR = "003";
	public static String RESULT_CODE_USERNAME_PASSWORD_ERROR = "004";
	public static String RESULT_CODE_EE_INFO_ERROR = "005";
	public static String RESULT_CODE_CERT_PROCESS_ERROR = "006";
	public static String RESULT_CODE_USER_REGISTER_ERROR = "007";
	public static String RESULT_CODE_CERT_ISSUE_ERROR = "008";
	public static String RESULT_CODE_CSR_ERROR = "009";
	public static String RESULT_CODE_SYSTEM_ERROR = "999";
	public static String RESULT_CODE_REVOKE_PARAM_ERROR = "002";
	public static String RESULT_CODE_REVOKED = "004";
	public static String RESULT_CODE_REVOKE_FAILURE = "005";
	public static int REQ_TYPE_NEW = 1;
	public static int REQ_TYPE_UPDATE = 2;
	public static int REQ_TYPE_REVOKE_USER = 1;
	public static int REQ_TYPE_REVOKE_CERT = 2;
	private boolean success;
	private String errorCode;
	private String message;
	private int tokenType;
	private byte[] resultData;
	
	public BciaRequestResult() {}
	
	public BciaRequestResult(boolean success, String errorCode, String message, int tokenType, byte[] resultData) {
		super();
		this.success = success;
		this.errorCode = errorCode;
		this.message = message;
		this.tokenType = tokenType;
		this.resultData = resultData;
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
	public int getTokenType() {
		return tokenType;
	}
	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}
	public byte[] getResultData() {
		return resultData;
	}
	public void setResultData(byte[] resultData) {
		this.resultData = resultData;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}

