package com.szca.common.exception;

public class AuthLoginException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AuthLoginException(){
		super();
	}
	public AuthLoginException(String msg){
		super(msg);
	}
}
