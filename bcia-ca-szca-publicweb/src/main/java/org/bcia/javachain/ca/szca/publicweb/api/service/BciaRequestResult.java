package org.bcia.javachain.ca.szca.publicweb.api.service;

public class BciaRequestResult { 
	private boolean success;
	private String message;
	private int tokenType;
	private byte[] resultData;
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
	
	
}

