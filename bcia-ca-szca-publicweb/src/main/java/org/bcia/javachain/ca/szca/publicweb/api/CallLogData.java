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

/*************************************************************************
 *                                                                       *
 *  CESeCore: CE Security Core                                           *
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/  
package org.bcia.javachain.ca.szca.publicweb.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Type;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Representation of a CA instance.
 * 
 * @version $Id: CAData.java 22592 2016-01-18 12:09:44Z marko $
 */
@Entity
@Table(name="api_call_log")
public class CallLogData   implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CallLogData.class);

	private Integer logId;
	private long callTime = 0;
	private long logTime = 0;
	private String ip;
	private String uri;
	private String received;
	private String result; 
	private String authorization; 
	private String username;
	private String password;
	 
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getLogId() {
		return logId;
	}
	
	 
	public JsonObject receivedAsJson() {
		JsonObject json = null;
		if(received!=null) {
			json =(JsonObject) new JsonParser().parse(received);
		}
		return json;
	}
			
	public void setLogId(Integer logId) {
		this.logId = logId;
	}

 
	public long getCallTime() {
		return callTime;
	}

	public void setCallTime(long callTime) {
		this.callTime = callTime;
	}

 
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	 
	@Column(length=65535)
	@Type(type="text")
	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}
	@Column(length=65535)
	@Type(type="text")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

 
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(length=65535)
	@Type(type="text")
	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	
 
	public long getLogTime() {
		return logTime;
	}

	public void setLogTime(long logTime) {
		this.logTime = logTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	   
}
