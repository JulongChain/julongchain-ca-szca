package org.bcia.javachain.ca.szca.common.bcca.common;

import java.io.Serializable;

public class BaseCodeId implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String section;
	private String keyname;
	public BaseCodeId() {
		
	}
	public BaseCodeId(String section,String key) {
		this.section = section;
		this.keyname = key;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getKeyname() {
		return keyname;
	}
	public void setKeyname(String key) {
		this.keyname = key;
	}
	
}
