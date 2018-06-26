
package com.szca.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common<br>
 * File Name    : ActionResult.java<br>
 * Type Name    : ActionResult<br>
 * Created on   : 2017-2-14 上午11:10:42<br>
 * Created by   : JackyLuo <br>
 * Note:<br>
 *
 * 
 */
public class ActionResult {
	
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	protected boolean success;
	protected Throwable exception;
	protected String message;
	protected int resultCode;



	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
