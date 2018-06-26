
package com.szca.common.exception;
/** 
 * Project Name : SZCA-COMMON<br>
 * Package Name : cn.szca.common.exception<br>
 * File Name    : JspViewNotFoundException.java<br>
 * Type Name    : JspViewNotFoundException<br>
 * Created on   : 2017-2-9 下午12:36:31<br>
 * Created by   : JackyLuo <br>
 * Note:<br>
 *
 * 
 */
public class JspViewNotFoundException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JspViewNotFoundException(){
		super();
	}
	public JspViewNotFoundException(String msg){
		super(msg);
	}
}
